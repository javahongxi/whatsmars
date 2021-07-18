package org.hongxi.whatsmars.boot.sample.webflux.support;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by shenhongxi on 2021/4/29.
 */
public class ModifiedServerHttpResponse extends ServerHttpResponseDecorator {

    private final List<HttpMessageReader<?>> messageReaders;

    private String body;

    public ModifiedServerHttpResponse(ServerWebExchange exchange,
                                      List<HttpMessageReader<?>> messageReaders) {
        super(exchange.getResponse());
        this.messageReaders = messageReaders;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        // 这里只是借用 ClientResponse 这个类获取修改之前的 body
        // server 端最终返回的是 ServerResponse/ServerHttpResponse
        ClientResponse clientResponse = prepareClientResponse(body);
        Mono<DataBuffer> modifiedBody = clientResponse.bodyToMono(byte[].class)
                .map(originalBody -> {
                    this.body = new String(originalBody, StandardCharsets.UTF_8);
                    return Crypto.encrypt(originalBody);
                }).map(encrypted -> getDelegate().bufferFactory().wrap(encrypted))
                .doOnNext(data -> getDelegate().getHeaders().setContentLength(data.readableByteCount()));
        return super.writeWith(modifiedBody);
    }

    /**
     * @return body json string
     */
    public String bodyString() {
        return this.body;
    }

    private ClientResponse prepareClientResponse(Publisher<? extends DataBuffer> body) {
        ClientResponse.Builder builder = ClientResponse.create(HttpStatus.OK, messageReaders);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return builder.headers(headers -> headers.putAll(httpHeaders)).body(Flux.from(body)).build();
    }
}
