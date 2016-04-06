package com.whatsmars.support.web;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Author: qing
 * Date: 14-11-7
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mav = new ModelAndView();

        try{
            mav.setViewName("redirect:/error.jhtml?messages=" + URLEncoder.encode(ex.getMessage(),"utf-8"));
        } catch (Exception e) {
            mav.setViewName("redirect:/error.jhtml");
        }

        return mav;
    }
}
