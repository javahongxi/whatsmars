package org.hongxi.whatsmars.boot.sample.webflux.support;

import io.netty.buffer.ByteBufAllocator;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * Created by shenhongxi on 2021/4/29.
 */
public class ModifiedServerHttpRequest extends ServerHttpRequestDecorator {

    private Map<String, Object> params;

    private Flux<DataBuffer> body;

    public ModifiedServerHttpRequest(ServerHttpRequest delegate, Map<String, Object> params) {
        super(delegate);
        this.params = params;

        byte[] bytes = JacksonUtils.serialize(params);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        body = Flux.just(buffer);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return body;
    }
}
