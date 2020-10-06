package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class AnyTest {

    public static void main(String[] args) {
        Flux.just(3, 5, 7, 9, 11, 15, 16, 17)
                .count()
                .subscribe(System.out::println);

        Flux.just(3, 5, 7, 9, 11, 15, 16, 17)
                .any(e -> e % 2 == 0)
                .subscribe(System.out::println);

        Flux.just(3, 5, 7, 9, 11, 15, 16, 17)
                .all(e -> e % 2 == 0)
                .subscribe(System.out::println);

        Flux.just(3, 5, 7, 9, 11, 15, 16, 17)
                .hasElement(7)
                .subscribe(System.out::println);

        Flux.just(3, 5, 7, 9, 11, 15, 16, 17)
                .hasElements()
                .subscribe(System.out::println);
    }
}
