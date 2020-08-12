package org.hongxi.whatsmars.boot.sample.actuator.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shenhongxi on 2020/8/13.
 */
public class ActuatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (request.getServletPath().startsWith("/actuator")) {
//            response.sendError(HttpStatus.FORBIDDEN.value());
//        } else {
//            filterChain.doFilter(request, response);
//        }
    }
}
