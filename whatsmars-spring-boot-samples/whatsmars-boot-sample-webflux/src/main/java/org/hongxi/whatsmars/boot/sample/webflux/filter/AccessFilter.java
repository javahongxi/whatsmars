package org.hongxi.whatsmars.boot.sample.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.boot.sample.webflux.support.WebConstants;
import org.hongxi.whatsmars.boot.sample.webflux.support.WebUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * Created by shenhongxi on 2021/4/26.
 */
@Slf4j
@Order(-4)
@Component
public class AccessFilter implements WebFilter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        log.info("access start from path: {}", path);
        exchange.getAttributes().put(WebUtils.SHOULD_NOT_FILTER_ATTR, shouldNotFilter(path));
        return chain.filter(exchange)
                .doOnEach(signal -> onEach(exchange));
    }

    private void onEach(ServerWebExchange exchange) {
        log.info("access end, now start clear some context attributes");
        exchange.getAttributes().remove(WebUtils.START_TIMESTAMP_ATTR);
    }

    private boolean shouldNotFilter(String path) {
        if (path == null) {
            return false;
        }
        if (path.equals("/health") || path.startsWith("/actuator")
                || path.startsWith("/error")) {
            return true;
        }
        return path.contains(".") && Arrays.stream(WebConstants.EXCLUDE_RESOURCE_PATHS)
                .anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }
}
