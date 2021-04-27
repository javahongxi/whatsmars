package org.hongxi.whatsmars.boot.sample.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

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
                .transformDeferred((call) -> filter(exchange, call));
    }

    private Publisher<Void> filter(ServerWebExchange exchange, Mono<Void> call) {
        log.info("filter");
        long start = System.currentTimeMillis();
        return call.doOnEach((any) -> onEach(exchange, start));
    }

    private void preHandle(ServerWebExchange exchange) {
        log.info("preHandle");
        exchange.getAttributes().put("test", true);
//        throw new RuntimeException("test exception");
    }

    private void onEach(ServerWebExchange exchange, long start) {
        log.info("postHandle");
        long cost = System.currentTimeMillis() - start;
        log.info("uri: {}, cost: {}", exchange.getRequest().getPath(), cost);
    }
}
