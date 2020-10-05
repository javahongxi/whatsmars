package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

/**
 * Created by shenhongxi on 2020/10/3.
 */
public class FluxTest {

    public static void main(String[] args) {
        Flux.range(1, 5).subscribe(System.out::println);

        System.out.println("---");

        Flux.just("A", "B", "C")
                .subscribe(
                        data -> System.out.println("onNext: " + data),
                        err -> { /* ignored */ }
                );

        System.out.println("---");

        Flux.just("A", "B", "C")
                .subscribe(
                        data -> System.out.println("onNext: " + data),
                        err -> { /* ignored */ },
                        () -> System.out.println("onComplete")
                );

        System.out.println("---");

        Flux.range(1, 100)
                .subscribe(
                        data -> System.out.println("onNext: " + data),
                        err -> { /* ignored */ },
                        () -> System.out.println("onComplete"),
                        subscription -> {
                            subscription.request(4);
                            subscription.cancel();
                        }
                );
    }
}
