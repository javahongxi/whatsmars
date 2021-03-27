package org.hongxi.whatsmars.boot.sample.web.filter;

import org.hongxi.whatsmars.boot.sample.web.context.SampleSessionContext;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hongxi.whatsmars.boot.sample.web.constants.Constants.WEB_SESSION_FILTER_ORDER;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class SessionFilter extends OncePerRequestFilter implements OrderedFilter {

    private int order = REQUEST_WRAPPER_FILTER_MAX_ORDER + WEB_SESSION_FILTER_ORDER;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            SampleSessionContext context = SampleSessionContext.get();
            context.setUserId(request.getParameter("userId"));
            filterChain.doFilter(request, response);
        } finally {
            SampleSessionContext.remove();
        }
    }
}
