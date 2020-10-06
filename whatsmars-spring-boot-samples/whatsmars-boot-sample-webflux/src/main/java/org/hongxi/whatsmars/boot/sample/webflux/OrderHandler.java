package org.hongxi.whatsmars.boot.sample.webflux;

import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Service
public class OrderHandler {

    final OrderRepository orderRepository;

    public OrderHandler(OrderRepository repository) {
        orderRepository = repository;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request
            .bodyToMono(Order.class)
            .flatMap(orderRepository::save)
            .flatMap(o ->
                ServerResponse.created(URI.create("/orders/" + o.getId()))
                              .build()
            );
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        return orderRepository
            .findById(request.pathVariable("id"))
            .flatMap(order ->
                ServerResponse
                    .ok()
                    .syncBody(order)
            )
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        return null;
    }
}
