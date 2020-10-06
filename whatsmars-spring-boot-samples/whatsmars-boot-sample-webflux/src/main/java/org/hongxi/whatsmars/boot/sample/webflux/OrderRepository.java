package org.hongxi.whatsmars.boot.sample.webflux;

import reactor.core.publisher.Mono;


public interface OrderRepository {

    Mono<Order> findById(String id);

    Mono<Order> save(Order order);

    Mono<Void> deleteById(String id);
}
