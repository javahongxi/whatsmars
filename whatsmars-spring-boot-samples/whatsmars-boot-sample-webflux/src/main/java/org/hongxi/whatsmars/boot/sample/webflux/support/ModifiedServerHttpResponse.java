package org.hongxi.whatsmars.boot.sample.webflux.support;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by shenhongxi on 2021/4/29.
 */
public class ModifiedServerHttpResponse extends ServerHttpResponseDecorator {

    private final ServerWebExchange exchange;
    private final List<HttpMessageReader<?>> messageReaders;

    public ModifiedServerHttpResponse(ServerWebExchange exchange,
                                      List<HttpMessageReader<?>> messageReaders) {
        super(exchange.getResponse());
        this.exchange = exchange;
        this.messageReaders = messageReaders;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // 这里只是借用 ClientResponse 这个类获取修改之前的 body
        // server 端最终返回的是 ServerResponse/ServerHttpResponse
        ClientResponse clientResponse = prepareClientResponse(body, httpHeaders);
        Mono<byte[]> modifiedBody = clientResponse.bodyToMono(byte[].class)
                .flatMap(originalBody -> Mono.just(Crypto.encrypt(originalBody)));

        BodyInserter<Mono<byte[]>, ReactiveHttpOutputMessage> bodyInserter =
                BodyInserters.fromPublisher(modifiedBody, byte[].class);
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,
                exchange.getResponse().getHeaders());
        return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
            Mono<DataBuffer> messageBody = DataBufferUtils.join(outputMessage.getBody());
            HttpHeaders headers = getDelegate().getHeaders();
            if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)
                    || headers.containsKey(HttpHeaders.CONTENT_LENGTH)) {
                messageBody = messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
            }
            return getDelegate().writeWith(messageBody);
        }));
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return writeWith(Flux.from(body).flatMapSequential(p -> p));
    }

    private ClientResponse prepareClientResponse(Publisher<? extends DataBuffer> body, HttpHeaders httpHeaders) {
        ClientResponse.Builder builder = ClientResponse.create(HttpStatus.OK, messageReaders);
        return builder.headers(headers -> headers.putAll(httpHeaders)).body(Flux.from(body)).build();
    }
}
