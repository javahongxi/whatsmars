package org.hongxi.whatsmars.boot.sample.webflux.router.handler;


import reactor.core.publisher.Mono;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public class ServerRedirectHandler implements HandlerFunction<ServerResponse> {

    final WebClient webClient = WebClient.create();

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return webClient
            .method(request.method())
            .uri(request.headers()
                        .header("Redirect-Traffic")
                        .get(0))
            .headers((h) -> h.addAll(request.headers().asHttpHeaders()))
            .body(BodyInserters.fromDataBuffers(
                request.bodyToFlux(DataBuffer.class)
            ))
            .cookies(c -> request
                .cookies()
                .forEach((key, list) -> list.forEach(cookie -> c.add(key, cookie.getValue())))
            )
            .exchange()
            .flatMap(cr -> ServerResponse
                .status(cr.statusCode())
                .cookies(c -> c.addAll(cr.cookies()))
                .headers(hh -> hh.addAll(cr.headers().asHttpHeaders()))
                .body(BodyInserters.fromDataBuffers(
                    cr.bodyToFlux(DataBuffer.class)
                ))
            );
    }
}
