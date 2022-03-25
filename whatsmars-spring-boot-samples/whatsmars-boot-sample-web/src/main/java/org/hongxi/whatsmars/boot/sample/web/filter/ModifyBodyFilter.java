package org.hongxi.whatsmars.boot.sample.web.filter;

import org.apache.commons.io.IOUtils;
import org.hongxi.whatsmars.boot.sample.web.support.Crypto;
import org.hongxi.whatsmars.boot.sample.web.support.ModifiedHttpServletRequest;
import org.hongxi.whatsmars.boot.sample.web.support.ModifiedHttpServletResponse;
import org.hongxi.whatsmars.boot.sample.web.support.WebUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shenhongxi on 2020/10/23.
 */
@Order(-1)
@Component
public class ModifyBodyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if ((Boolean) request.getAttribute(WebUtils.SHOULD_NOT_FILTER_ATTR)) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("parameter userId: " + request.getParameter("userId"));
        byte[] requestBody = IOUtils.toByteArray(request.getInputStream());
        System.out.println("requestBody: " + new String(requestBody));
        System.out.println("parameter userId: " + request.getParameter("userId"));

        ModifiedHttpServletRequest wrapperRequest =
                new ModifiedHttpServletRequest(request, Crypto.decrypt(requestBody));
        ModifiedHttpServletResponse wrapperResponse =
                new ModifiedHttpServletResponse(response);
        filterChain.doFilter(wrapperRequest, wrapperResponse);
        wrapperResponse.copyBodyToResponse();
    }
}
