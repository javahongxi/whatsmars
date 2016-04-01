package com.whatsmars.mars001.web.interceptor;

import com.whatsmars.mars001.domain.misc.LoginContext;
import com.whatsmars.mars001.domain.misc.LoginContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: qing
 * Date: 14-10-29
 */
public class SecurityInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginContext loginContext = LoginContextHolder.get();
        if(loginContext != null && loginContext.getUser() != null) {
            return true;
        }
        response.sendRedirect("/login.jhtml");//登陆
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
