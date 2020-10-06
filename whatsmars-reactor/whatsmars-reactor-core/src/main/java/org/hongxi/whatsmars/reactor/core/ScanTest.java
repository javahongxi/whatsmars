package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class ScanTest {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .scan(0, (a, b) -> a + b)
                .subscribe(System.out::println);
    }
}
