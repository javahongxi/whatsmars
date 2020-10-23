package org.hongxi.whatsmars.boot.sample.web.filter;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hongxi.whatsmars.boot.sample.web.constants.Constants.WEB_CRYPTO_FILTER_ORDER;

/**
 * Created by shenhongxi on 2020/10/23.
 */
public class SampleWrapperFilter extends OncePerRequestFilter implements OrderedFilter {

    private int order = REQUEST_WRAPPER_FILTER_MAX_ORDER + WEB_CRYPTO_FILTER_ORDER;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        byte[] requestBody = IOUtils.toByteArray(request.getInputStream());
        SampleHttpServletRequestWrapper wrapperRequest =
                new SampleHttpServletRequestWrapper(request, requestBody);
        SampleHttpServletResponseWrapper wrapperResponse =
                new SampleHttpServletResponseWrapper(response, new SampleResponseBodyHandler());
        filterChain.doFilter(wrapperRequest, wrapperResponse);
        wrapperResponse.copyBodyToResponse();
    }
}
