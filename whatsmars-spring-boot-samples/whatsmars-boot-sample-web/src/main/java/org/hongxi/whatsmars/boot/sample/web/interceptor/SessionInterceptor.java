package org.hongxi.whatsmars.boot.sample.web.interceptor;

import org.hongxi.whatsmars.boot.sample.web.context.SampleSessionContext;
import org.hongxi.whatsmars.boot.sample.web.exception.BusinessException;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SampleSessionContext context = SampleSessionContext.get();
        if (context == null || !StringUtils.hasLength(SampleSessionContext.getUserId())) {
            throw new BusinessException(460, "请先登录");
        }
        return true;
    }
}
