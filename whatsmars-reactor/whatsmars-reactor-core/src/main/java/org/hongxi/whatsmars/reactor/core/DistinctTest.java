package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class DistinctTest {

    public static void main(String[] args) {
        Flux.just(1, 2, 2, 3, 3, 3, 1, 1, 4)
                .distinct()
                .subscribe(System.out::println);

        System.out.println();

        Flux.just(1, 2, 2, 3, 3, 3, 1, 1, 4)
                .distinctUntilChanged()
                .subscribe(System.out::println);
    }
}
