package org.hongxi.whatsmars.boot.sample.webflux.dao;

import org.hongxi.whatsmars.boot.sample.webflux.model.Order;
import reactor.core.publisher.Mono;

public interface OrderRepository {

    Mono<Order> findById(String id);

    Mono<Order> save(Order order);

    Mono<Void> deleteById(String id);
}
