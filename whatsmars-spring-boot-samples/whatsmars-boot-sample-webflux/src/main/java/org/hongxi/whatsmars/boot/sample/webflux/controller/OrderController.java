package org.hongxi.whatsmars.boot.sample.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.boot.sample.webflux.dao.OrderRepository;
import org.hongxi.whatsmars.boot.sample.webflux.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by shenhongxi on 2021/4/22.
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create")
    public Mono<Order> create(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @GetMapping("/detail/{id}")
    public Mono<Order> findById(@PathVariable String id) {
        log.info("id: {}", id);
        Assert.isTrue(id.length() >= 6, "id length < 6");

        return orderRepository.findById(id);
    }

    /**
     * The Servlet API “request parameter” concept conflates query parameters,
     * form data, and multiparts into one. However, in WebFlux, each is accessed
     * individually through ServerWebExchange. While @RequestParam binds to query
     * parameters only, you can use data binding to apply query parameters,
     * form data, and multiparts to a command object.
     *
     * @param userId
     * @param exchange
     * @return
     */
    @PostMapping("/delete")
    public Mono<Order> deleteById(@RequestParam String userId,
                                  ServerWebExchange exchange) {
        log.info("userId: {}", userId);
        return exchange.getFormData().
                flatMap(data -> {
                    log.info("start: {}", data.getFirst("start"));
                    return orderRepository.deleteById(data.getFirst("id"));
                });
    }
}
