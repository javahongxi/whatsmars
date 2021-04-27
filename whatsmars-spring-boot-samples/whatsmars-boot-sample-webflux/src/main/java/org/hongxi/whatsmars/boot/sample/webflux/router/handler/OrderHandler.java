package org.hongxi.whatsmars.boot.sample.webflux.router.handler;

import java.net.URI;

import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.boot.sample.webflux.dao.OrderRepository;
import org.hongxi.whatsmars.boot.sample.webflux.model.Order;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
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
                ServerResponse.created(URI.create("/order/" + o.getId()))
                              .build()
            );
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        String id = request.pathVariable("id");
        log.info("id: {}", id);
        Assert.isTrue(id.length() >= 6, "id length < 6");

        return orderRepository
            .findById(id)
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
