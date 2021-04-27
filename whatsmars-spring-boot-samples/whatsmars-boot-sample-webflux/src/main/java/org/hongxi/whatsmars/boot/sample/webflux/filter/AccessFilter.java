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
 * Created by shenhongxi on 2021/4/26.
 */
@Slf4j
@Order(-2)
@Component
public class AccessFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .transformDeferred((call) -> filter(exchange, call));
    }

    private Publisher<Void> filter(ServerWebExchange exchange, Mono<Void> call) {
        return call.doOnEach((any) -> onEach(exchange));
    }

    private void onEach(ServerWebExchange exchange) {
        log.info("start clear context like ThreadLocal, Attributes");
        exchange.getAttributes().remove("test");
    }
}
