package org.hongxi.whatsmars.boot.sample.webflux.support;

import org.springframework.web.server.ServerWebExchange;

import java.util.*;

/**
 * Created by shenhongxi on 2021/4/29.
 */
public abstract class WebUtils {

    public static final String START_TIMESTAMP_ATTR = qualify("startTimestamp");
    public static final String PATH_PATTERN_ATTR = qualify("pathPattern");
    public static final String SHOULD_NOT_FILTER_ATTR = qualify("shouldNotFilter");
    public static final String SESSION_CONTEXT_ATTR = qualify("sessionContext");
    public static final String REQUEST_PARAMS_ATTR = qualify("requestParams");

    private static String qualify(String attr) {
        return WebUtils.class.getName() + "." + attr;
    }

    public static String getPath(ServerWebExchange exchange) {
        return exchange.getRequest().getPath().value();
    }

    public static String getHeader(ServerWebExchange exchange, String name) {
        return exchange.getRequest().getHeaders().getFirst(name);
    }

    public static String getQueryParam(ServerWebExchange exchange, String name) {
        return exchange.getRequest().getQueryParams().getFirst(name);
    }

    public static String getQueryString(ServerWebExchange exchange) {
        Map<String, String> params = exchange.getRequest().getQueryParams().toSingleValueMap();
        if (!params.isEmpty()) {
            StringBuilder queryString = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            return queryString.substring(0, queryString.length() - 1);
        }
        return null;
    }
}
