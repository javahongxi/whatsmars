package org.hongxi.whatsmars.boot.sample.webflux.support;

import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.bind.support.WebExchangeDataBinder;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

/**
 * Created by shenhongxi on 2021/4/29.
 */
public abstract class ParamUtils {

    @SuppressWarnings("rawtypes")
    private static final HttpMessageReader messageReader =
            new DecoderHttpMessageReader<>(new Jackson2JsonDecoder());

    @SuppressWarnings("unchecked")
    public static Mono<Map<String, Object>> from(ServerWebExchange exchange) {
        Map<String, Object> data = exchange.getAttribute(WebUtils.REQUEST_PARAMS_ATTR);
        if (data != null) {
            return Mono.just(data);
        }

        Mono<Map<String, Object>> params;
        MediaType contentType = exchange.getRequest().getHeaders().getContentType();
        if (contentType != null && contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            params = (Mono<Map<String, Object>>) messageReader.readMono(
                    ResolvableType.forType(Map.class), exchange.getRequest(), Collections.emptyMap());
        } else {
            params = WebExchangeDataBinder.extractValuesToBind(exchange);
        }
        return params.doOnNext(e -> exchange.getAttributes().put(WebUtils.REQUEST_PARAMS_ATTR, e));
    }
}
