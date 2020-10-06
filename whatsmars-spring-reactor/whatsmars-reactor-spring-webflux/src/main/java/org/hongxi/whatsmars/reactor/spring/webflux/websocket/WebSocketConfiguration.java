package org.hongxi.whatsmars.reactor.spring.webflux.websocket;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerAdapter;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Configuration
public class WebSocketConfiguration {

    @Bean
    public HandlerMapping handlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(Collections.singletonMap("/ws/echo", new EchoWebSocketHandler()));
        mapping.setOrder(-1);
        return mapping;
    }

    @Bean
    public HandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
