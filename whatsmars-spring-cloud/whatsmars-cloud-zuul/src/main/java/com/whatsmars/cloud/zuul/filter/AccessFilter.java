package com.whatsmars.cloud.zuul.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shenhongxi on 2017/12/27.
 */
@Component
public class AccessFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String LOGIN_URL = "http://hongxi.org/login";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("{} request to {}", request.getMethod(), request.getRequestURL().toString());

        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            try {
                logger.info("access token is empty");
                ReturnMessage message = new ReturnMessage(360, LOGIN_URL, "未登录");
                ctx.setSendZuulResponse(false);
                ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                ctx.setResponseStatusCode(HttpStatus.OK.value());
                ctx.setResponseBody(new ObjectMapper().writeValueAsString(message));
            } catch (Exception e) {
                logger.error("build response fail", e);
            }
        }
        return null;
    }
}
