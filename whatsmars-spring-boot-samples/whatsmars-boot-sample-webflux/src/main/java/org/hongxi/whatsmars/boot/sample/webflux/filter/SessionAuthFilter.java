package org.hongxi.whatsmars.boot.sample.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.boot.sample.webflux.exception.BusinessException;
import org.hongxi.whatsmars.boot.sample.webflux.support.SessionContext;
import org.hongxi.whatsmars.boot.sample.webflux.support.WebUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Created by shenhongxi on 2021/4/22.
 */
@Slf4j
@Order(0)
@Component
public class SessionAuthFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getAttribute(WebUtils.PATH_PATTERN_ATTR) == null) {
            return chain.filter(exchange);
        }

        SessionContext sessionContext = exchange.getAttribute(WebUtils.SESSION_CONTEXT_ATTR);
        if (sessionContext == null) {
            throw new BusinessException(403, "请先登录");
        }
        String userId = sessionContext.getUserId();
        log.info("userId: {}", userId);
        if (!StringUtils.hasLength(userId)) {
            throw new BusinessException(403, "请先登录");
        }
        return chain.filter(exchange);
    }
}
