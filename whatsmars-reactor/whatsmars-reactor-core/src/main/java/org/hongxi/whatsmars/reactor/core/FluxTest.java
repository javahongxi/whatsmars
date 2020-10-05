package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by shenhongxi on 2020/10/3.
 */
public class FluxTest {

    public static void main(String[] args) {
        Flux.just("A", "B", "C")
                .subscribe(
                        data -> System.out.println("onNext: " + data),
                        err -> { /* ignored */ },
                        () -> System.out.println("onComplete")
                );
    }

    public void base() {
        Flux<String> stream1 = Flux.just("Hello", "world");
        Flux<Integer> stream2 = Flux.fromArray(new Integer[] {1, 2, 3});
        Flux<Integer> stream3 = Flux.fromIterable(Arrays.asList(9, 8, 7));
        Flux<Integer> stream4 = Flux.range(2010, 9);

        Mono<String> stream5 = Mono.just("One");
        Mono<String> stream6 = Mono.justOrEmpty(null);
        Mono<String> stream7 = Mono.justOrEmpty(Optional.empty());

        Flux<String> empty = Flux.empty();
        Flux<String> never = Flux.never();
        Mono<String> error = Mono.error(new RuntimeException("Unknown id"));
    }

    public void mono() {
        Mono<String> stream8 = Mono.fromCallable(this::httpRequest);
    }

    public Mono<User> requestUserData(String sessionId) {
         return Mono.defer(() ->
                 isValidSession(sessionId)
                 ? Mono.fromCallable(() -> requestUser(sessionId))
                 : Mono.error(new RuntimeException("Invalid user session")));
    }

    private String httpRequest() {
        return "OK";
    }

    private boolean isValidSession(String sessionId) {
        return true;
    }

    private User requestUser(String sessionId) {
        return new User();
    }
}
