package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class MapTest {

    public static void main(String[] args) {
        Flux.just(1, 2, 3)
                .map(i -> i * 2)
                .subscribe(System.out::println);
    }
}
