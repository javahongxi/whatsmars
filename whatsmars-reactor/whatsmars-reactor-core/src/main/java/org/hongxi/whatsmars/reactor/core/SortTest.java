package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

import java.util.Comparator;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class SortTest {

    public static void main(String[] args) {
        Flux.just(1, 4, 2, 3)
                .sort()
                .subscribe(System.out::println);

        Flux.just(1, 4, 2, 3)
                .sort(Comparator.reverseOrder())
                .subscribe(System.out::println);
    }
}
