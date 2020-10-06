package org.hongxi.whatsmars.reactor.spring.webflux.websocket;

import reactor.core.publisher.Mono;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

public class EchoWebSocketHandler implements WebSocketHandler {        // (1)

    @Override                                                          // (2)
    public Mono<Void> handle(WebSocketSession session) {               //
        return session                                                 // (3)
            .receive()                                                 // (4)
            .map(WebSocketMessage::getPayloadAsText)                   // (5)
            .map(tm -> "Echo: " + tm)                                  // (6)
            .map(session::textMessage)                                 // (7)
            .as(session::send);                                        // (8)
    }                                                                  //
}
