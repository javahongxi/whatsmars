package org.hongxi.whatsmars.boot.sample.webflux.controller;

import org.hongxi.whatsmars.boot.sample.webflux.dao.OrderRepository;
import org.hongxi.whatsmars.boot.sample.webflux.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Created by shenhongxi on 2021/4/22.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create")
    public Mono<Order> create(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @GetMapping("/{id}")
    public Mono<Order> findById(@PathVariable String id) {
        return orderRepository.findById(id);
    }

    @PostMapping("/delete")
    public Mono<Void> deleteById(@RequestParam String id) {
        return orderRepository.deleteById(id);
    }
}
