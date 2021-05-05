package org.hongxi.whatsmars.boot.sample.webflux.model;

import lombok.Data;

@Data
public class Order {

    private String id;

    private Long start;

    public Order() {
    }

    public Order(String id) {
        this.id = id;
    }
}
