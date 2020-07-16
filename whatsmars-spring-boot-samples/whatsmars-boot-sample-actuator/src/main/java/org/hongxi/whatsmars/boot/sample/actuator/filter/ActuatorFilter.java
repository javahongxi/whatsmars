package org.hongxi.whatsmars.boot.sample.actuator.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shenhongxi on 2020/7/17.
 */
public class ActuatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (security(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private boolean security(HttpServletRequest request) {
        // 当用requestURI时用 curl http://127.0.0.1:8080/actuator/svg/../prometheus 可以访问
        if (request.getServletPath() == null) {
            return true;
        }
        if (!request.getServletPath().startsWith("/actuator")) {
            return true;
        }
        return "whatsmars-spring-boot".equals(request.getParameter("x_token"));
    }
}
