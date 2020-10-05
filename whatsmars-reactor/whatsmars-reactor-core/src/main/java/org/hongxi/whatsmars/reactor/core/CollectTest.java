package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

import java.util.Comparator;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class CollectTest {

    public static void main(String[] args) {
        Flux.just(1, 6, 2, 8, 3, 1, 5, 1)
                .collectSortedList(Comparator.reverseOrder())
                .subscribe(System.out::println);
    }
}
