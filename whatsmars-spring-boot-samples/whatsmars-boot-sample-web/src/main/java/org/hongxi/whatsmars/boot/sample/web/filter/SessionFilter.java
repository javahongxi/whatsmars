package org.hongxi.whatsmars.boot.sample.web.filter;

import org.hongxi.whatsmars.boot.sample.web.support.SampleSessionContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@Order(-2)
@Component
public class SessionFilter extends OncePerRequestFilter {

    private static final String COOKIE_NAME = "SESSIONID";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ((Boolean) request.getAttribute(org.hongxi.whatsmars.boot.sample.web.support.WebUtils.SHOULD_NOT_FILTER_ATTR)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
            String userId;
            if (cookie != null) {
                String sessionId = cookie.getValue();
                userId = getUserIdBySessionId(sessionId);
            } else {
                // just for test
                userId = request.getParameter("userId");
            }
            SampleSessionContext context = SampleSessionContext.get();
            context.setUserId(userId);
            filterChain.doFilter(request, response);
        } finally {
            SampleSessionContext.remove();
        }
    }

    private String getUserIdBySessionId(String sessionId) {
        return null;
    }
}
