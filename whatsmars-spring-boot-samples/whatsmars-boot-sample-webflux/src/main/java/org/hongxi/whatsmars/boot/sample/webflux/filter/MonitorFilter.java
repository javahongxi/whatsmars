package org.hongxi.whatsmars.boot.sample.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.hongxi.whatsmars.boot.sample.webflux.support.RequestAttributes.START_TIMESTAMP;

/**
 * Created by shenhongxi on 2021/4/22.
 */
@Slf4j
@Order(-1)
@Component
public class MonitorFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        preHandle(exchange);
        return chain.filter(exchange)
                .doOnEach((signal) -> postHandle(exchange));
    }

    private void preHandle(ServerWebExchange exchange) {
        log.info("preHandle");
        exchange.getAttributes().put(START_TIMESTAMP, System.currentTimeMillis());
        throw new RuntimeException("test exception");
    }

    private void postHandle(ServerWebExchange exchange) {
        log.info("postHandle");
        Long start = exchange.getAttribute(START_TIMESTAMP);
        if (start != null) {
            long cost = System.currentTimeMillis() - start;
            log.info("uri: {}, cost: {}", exchange.getRequest().getPath(), cost);
        }
    }
}
