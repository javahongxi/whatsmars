package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class ReduceTest {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .reduce(0, (a, b) -> a + b)
                .subscribe(System.out::println);
    }
}
