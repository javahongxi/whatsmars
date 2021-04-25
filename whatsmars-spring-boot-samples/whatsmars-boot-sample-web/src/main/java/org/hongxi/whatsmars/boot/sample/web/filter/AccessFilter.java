package org.hongxi.whatsmars.boot.sample.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hongxi.whatsmars.boot.sample.web.constants.Constants.WEB_ACCESS_FILTER_ORDER;

/**
 * Created by shenhongxi on 2021/4/25.
 */
@Slf4j
public class AccessFilter extends OncePerRequestFilter implements OrderedFilter {

    private int order = REQUEST_WRAPPER_FILTER_MAX_ORDER + WEB_ACCESS_FILTER_ORDER;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        log.info("request {} cost {}ms", request.getRequestURI(), System.currentTimeMillis() - start);
    }
}
