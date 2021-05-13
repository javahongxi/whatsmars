package org.hongxi.whatsmars.boot.sample.webflux.support;

import io.netty.buffer.ByteBufAllocator;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * Created by shenhongxi on 2021/4/29.
 */
public class ModifiedServerHttpRequest extends ServerHttpRequestDecorator {

    private final byte[] rawBody;

    public ModifiedServerHttpRequest(ServerHttpRequest delegate, byte[] rawBody) {
        super(delegate);
        this.rawBody = rawBody;
    }

    @Override
    public Flux<DataBuffer> getBody() {
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(this.rawBody.length);
        buffer.write(this.rawBody);
        return Flux.just(buffer);
    }

    @Override
    public HttpHeaders getHeaders() {
        // 必须 new，不能直接操作 super.getHeaders()（readonly）
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(super.getHeaders());
        headers.setContentLength(this.rawBody.length);
        return headers;
    }

    /**
     * @return body json string
     */
    public String bodyString() {
        return new String(rawBody, StandardCharsets.UTF_8);
    }
}
