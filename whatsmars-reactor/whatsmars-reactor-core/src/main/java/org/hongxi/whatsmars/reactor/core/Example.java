package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Created by shenhongxi on 2020/10/6.
 *
 * * @see org.hongxi.whatsmars.reactor.core.FluxTests
 */
public class Example {

    public static void main(String[] args) {
        Mono.fromFuture(CompletableFuture.supplyAsync(() -> "123"))
                .subscribe(System.out::println);
    }
}
