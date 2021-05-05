package org.hongxi.whatsmars.boot.sample.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.boot.sample.webflux.support.WebUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.condition.PatternsRequestCondition;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by shenhongxi on 2021/4/22.
 */
@Slf4j
@Order(-3)
@Component
public class MonitorFilter implements WebFilter, InitializingBean {

    private static final String UNKNOWN_PATH = "/unknown";

    // 针对只有一个path映射到的method
    // key: methodSign value: pathPattern
    private final Map<String, String> pathPatterns = new HashMap<>(128);
    // 多个path映射到同一个method  @RequestMapping({"/hi", "/hello"})
    // key: methodSign value: RequestMappingInfo
    private final Map<String, RequestMappingInfo> requestMappings = new HashMap<>();
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getAttributeOrDefault(WebUtils.SHOULD_NOT_FILTER_ATTR, false)) {
            return chain.filter(exchange);
        }
        preHandle(exchange);
        return chain.filter(exchange)
                .doOnSuccess(signal -> postHandle(exchange, null))
                .doOnError(cause -> postHandle(exchange, cause));
    }

    private void preHandle(ServerWebExchange exchange) {
        log.info("preHandle");
        exchange.getAttributes().put(WebUtils.START_TIMESTAMP_ATTR, System.currentTimeMillis());
        String pathPattern = getPathPattern(exchange);
        if (pathPattern != null && !pathPattern.equals(UNKNOWN_PATH)) {
            exchange.getAttributes().put(WebUtils.PATH_PATTERN_ATTR, pathPattern);
        }
        // 可以记录接口维度的耗时、调用次数、异常率等数据
    }

    private void postHandle(ServerWebExchange exchange, Throwable throwable) {
        log.info("postHandle");
        Long start = exchange.getAttribute(WebUtils.START_TIMESTAMP_ATTR);
        if (start != null) {
            long cost = System.currentTimeMillis() - start;
            log.info("path: {}, cost: {}, error: {}",
                    exchange.getRequest().getPath(), cost, throwable != null);
        }
    }

    private void preLoadAllPaths() {
        Map<RequestMappingInfo, HandlerMethod> requestMapInfoHandlerMap = requestMappingHandlerMapping.getHandlerMethods();
        for (RequestMappingInfo info : requestMapInfoHandlerMap.keySet()) {
            PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
            Set<PathPattern> patterns = patternsRequestCondition.getPatterns();
            String methodSignal = requestMapInfoHandlerMap.get(info).getMethod().toGenericString();
            if (patterns.size() == 1) {
                pathPatterns.put(methodSignal, patterns.iterator().next().getPatternString());
            } else {
                requestMappings.put(methodSignal, info);
            }
        }
    }

    private String getPathPattern(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().value();
        if (!StringUtils.hasLength(path)) {
            return UNKNOWN_PATH;
        }
        if (pathPatterns.containsValue(path)) {
            return path;
        }
        try {
            Mono<Object> handlerMono = requestMappingHandlerMapping.getHandler(exchange);
            String handlerKey = path + "_handler";
            handlerMono.subscribe(handler -> exchange.getAttributes().put(handlerKey, handler));
            Object handler = exchange.getAttribute(handlerKey);
            if (handler == null) {
                return UNKNOWN_PATH;
            }
            exchange.getAttributes().remove(handlerKey);
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                String methodSign = handlerMethod.getMethod().toGenericString();
                String pathPattern = pathPatterns.get(methodSign);
                if (StringUtils.hasLength(pathPattern)) {
                    return pathPattern;
                }
                if (requestMappings.containsKey(methodSign)) {
                    RequestMappingInfo mappingInfo = requestMappings.get(methodSign);
                    PatternsRequestCondition patternsRequestCondition = mappingInfo.getPatternsCondition();
                    Set<PathPattern> pathPatterns = patternsRequestCondition.getPatterns();
                    if (!CollectionUtils.isEmpty(pathPatterns)) {
                        return pathPatterns.iterator().next().getPatternString();
                    }
                }
            }
        } catch (Exception e) {
            log.warn("get path pattern error, {}", e.getClass());
        }
        return UNKNOWN_PATH;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        preLoadAllPaths();
    }
}
