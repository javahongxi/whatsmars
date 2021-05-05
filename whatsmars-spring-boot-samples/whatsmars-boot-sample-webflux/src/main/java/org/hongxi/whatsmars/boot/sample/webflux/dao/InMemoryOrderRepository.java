package org.hongxi.whatsmars.boot.sample.webflux.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hongxi.whatsmars.boot.sample.webflux.model.Order;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    final Map<String, Order> orders;

    public InMemoryOrderRepository() {
        orders = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<Order> findById(String id) {
        return Mono.justOrEmpty(orders.get(id));
    }

    @Override
    public Mono<Order> save(Order order) {
        orders.put(order.getId(), order);

        return Mono.just(order);
    }

    @Override
    public Mono<Order> deleteById(String id) {
        return Mono.justOrEmpty(orders.remove(id));
    }
}
