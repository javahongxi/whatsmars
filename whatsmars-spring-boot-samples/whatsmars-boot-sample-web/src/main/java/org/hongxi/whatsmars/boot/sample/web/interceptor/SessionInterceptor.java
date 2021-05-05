package org.hongxi.whatsmars.boot.sample.web.interceptor;

import org.hongxi.whatsmars.boot.sample.web.support.SampleSessionContext;
import org.hongxi.whatsmars.boot.sample.web.exception.BusinessException;
import org.hongxi.whatsmars.boot.sample.web.support.WebUtils;
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
        if (request.getAttribute(WebUtils.PATH_PATTERN_ATTR) == null) {
            return true;
        }

        SampleSessionContext context = SampleSessionContext.get();
        if (context == null || !StringUtils.hasLength(SampleSessionContext.getUserId())) {
            throw new BusinessException(403, "请先登录");
        }
        return true;
    }
}
