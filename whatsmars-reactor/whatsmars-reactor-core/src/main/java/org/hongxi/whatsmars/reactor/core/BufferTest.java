package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class BufferTest {

    public static void main(String[] args) {
        Flux.range(1, 13)
                .buffer(4)
                .subscribe(System.out::println);
    }
}
