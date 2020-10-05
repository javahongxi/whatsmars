package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class FilterTest {

    public static void main(String[] args) {
        Flux.just(1, 2, 3)
                .filter(i -> i % 2 == 0)
                .subscribe(System.out::println);

        System.out.println("---");

        Flux.just(1, 2, 3)
                .take(2)
                .subscribe(System.out::println);

        System.out.println("---");

        Flux.just(1, 2, 3)
                .takeLast(1)
                .subscribe(System.out::println);

        System.out.println("---");

        Flux.just(1, 2, 3)
                .takeUntil(i -> i % 2 == 0)
                .subscribe(System.out::println);

        System.out.println("---");

        Flux.just(1, 2, 3)
                .elementAt(1)
                .subscribe(System.out::println);
    }
}
