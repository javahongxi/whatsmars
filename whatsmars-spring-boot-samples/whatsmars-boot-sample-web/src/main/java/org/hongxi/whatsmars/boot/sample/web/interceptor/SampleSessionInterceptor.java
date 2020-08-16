package org.hongxi.whatsmars.boot.sample.web.interceptor;

import org.hongxi.whatsmars.boot.sample.web.context.SampleSessionContext;
import org.hongxi.whatsmars.boot.sample.web.exception.BusinessException;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class SampleSessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SampleSessionContext context = SampleSessionContext.get();
        if (context == null || !StringUtils.hasLength(SampleSessionContext.getUserId())) {
            throw new BusinessException(BusinessException.ErrorCode.AUTH_FAIL);
        }
        return super.preHandle(request, response, handler);
    }
}
