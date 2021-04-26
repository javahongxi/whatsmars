package org.hongxi.whatsmars.boot.sample.webflux.filter;

import lombok.extern.slf4j.Slf4j;
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
public class ExceptionFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .doFinally(signalType -> postHandle(exchange));
    }

    private void postHandle(ServerWebExchange exchange) {
        log.info("postHandle");
        Boolean test = exchange.getAttribute("test");
        log.info("attribute `test`: {}", test);
    }
}
