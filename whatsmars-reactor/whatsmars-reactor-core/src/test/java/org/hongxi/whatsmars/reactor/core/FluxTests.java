package org.hongxi.whatsmars.reactor.core;

import org.hongxi.whatsmars.reactor.core.model.User;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by shenhongxi on 2020/10/6.
 */
public class FluxTests {

    private final Random random = new Random();

    @Test
    public void createFlux() {
        Flux<String> stream1 = Flux.just("Hello", "world");
        Flux<Integer> stream2 = Flux.fromArray(new Integer[] {1, 2, 3});
        Flux<Integer> stream3 = Flux.fromIterable(Arrays.asList(9, 8, 7));
        Flux<Integer> stream4 = Flux.range(2010, 9);

        Flux<String> empty = Flux.empty();
        Flux<String> never = Flux.never();
    }

    public void createMono() {
        Mono<String> stream5 = Mono.just("One");
        Mono<String> stream6 = Mono.justOrEmpty(null);
        Mono<String> stream7 = Mono.justOrEmpty(Optional.empty());

        Mono<String> stream8 = Mono.fromCallable(this::httpRequest);

        Mono<String> error = Mono.error(new RuntimeException("Unknown id"));
    }

    private String httpRequest() {
        return "OK";
    }

    public Mono<User> requestUserData(String sessionId) {
        return Mono.defer(() ->
                isValidSession(sessionId)
                        ? Mono.fromCallable(() -> requestUser(sessionId))
                        : Mono.error(new RuntimeException("Invalid user session")));
    }

    private boolean isValidSession(String sessionId) {
        return true;
    }

    private User requestUser(String sessionId) {
        return new User();
    }

    @Test
    public void subscribeExample() {
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

    @Test
    public void customSubscriberExample() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            volatile Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                System.out.println("initial request for 1 element");
                subscription.request(1);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
                System.out.println("requesting 1 more element");
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        Flux<String> stream = Flux.just("Hello", "world", "!");
        stream.subscribe(subscriber);
    }

    @Test
    public void baseSubscriberExample() {
        Flux<String> stream = Flux.just("Hello", "world", "!");
        stream.subscribe(new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("initial request for 1 element");
                subscription.request(1);
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("onNext: " + value);
                System.out.println("requesting 1 more element");
                request(1);
            }
        });
    }

    @Test
    public void disposableExample() {
        Disposable disposable = Flux.interval(Duration.ofMillis(50))
                .subscribe(data -> System.out.println("onNext: " + data));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
    }

    @Test
    public void mapExample() {
        Flux.just(1, 2, 3)
                .map(i -> i * 2)
                .subscribe(System.out::println);
    }

    @Test
    public void indexExample() {
        Flux.range(2018, 5)
                .timestamp()
                .index()
                .subscribe(e -> System.out.println(
                        String.format("index: %d, ts: %s, value: %s",
                                e.getT1(),
                                Instant.ofEpochMilli(e.getT2().getT1()),
                                e.getT2().getT2())
                ));
    }

    @Test
    public void filterExample() {
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

    @Test
    public void collectExample() {
        Flux.just(1, 6, 2, 8, 3, 1, 5, 1)
                .collectSortedList(Comparator.reverseOrder())
                .subscribe(System.out::println);
    }

    @Test
    public void distinctExample() {
        Flux.just(1, 2, 2, 3, 3, 3, 1, 1, 4)
                .distinct()
                .subscribe(System.out::println);

        System.out.println();

        Flux.just(1, 2, 2, 3, 3, 3, 1, 1, 4)
                .distinctUntilChanged()
                .subscribe(System.out::println);
    }

    @Test
    public void anyExample() {
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

    @Test
    public void sortExample() {
        Flux.just(1, 4, 2, 3)
                .sort()
                .subscribe(System.out::println);

        Flux.just(1, 4, 2, 3)
                .sort(Comparator.reverseOrder())
                .subscribe(System.out::println);
    }

    @Test
    public void reduceExample() {
        Flux.range(1, 5)
                .reduce(0, (a, b) -> a + b)
                .subscribe(System.out::println);
    }

    @Test
    public void scanExample() {
        Flux.range(1, 5)
                .scan(0, (a, b) -> a + b)
                .subscribe(System.out::println);
    }

    @Test
    public void thenExample() {
        Flux.just(1, 2, 3)
                .thenMany(Flux.just(4, 5))
                .subscribe(System.out::println);
    }

    @Test
    public void concatExample() {
        Flux.concat(
                Flux.range(1, 3),
                Flux.range(4, 2),
                Flux.range(6, 5)
        ).subscribe(System.out::println);
    }

    @Test
    public void mergeExample() {
        Flux.merge(
                Flux.range(1, 3),
                Flux.range(4, 2),
                Flux.range(6, 5)
        ).subscribe(System.out::println);
    }

    @Test
    public void zipExample() {
        Flux.zip(
                Flux.range(1, 3),
                Flux.range(4, 2),
                Flux.range(6, 5)
        ).subscribe(System.out::println);
    }

    @Test
    public void rangeExample() {
        Flux.range(1, 13)
                .buffer(4)
                .subscribe(System.out::println);
    }

    @Test
    public void windowExample() {
        Flux<Flux<Integer>> fluxFlux = Flux.range(101, 20)
                .windowUntil(this::isPrime, true);

        fluxFlux.subscribe(window -> window
                .collectList()
                .subscribe(System.out::println));
    }

    private boolean isPrime(int number) {
        return number > 2
                && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(n -> (number % n == 0));
    }

    @Test
    public void groupByExample() {
        Flux.range(1, 7)
                .groupBy(e -> e % 2 == 0 ? "Even" : "Odd")
                .subscribe(groupFlux -> groupFlux
                        .scan(
                                new LinkedList<>(),
                                (list, elem) -> {
                                    if (list.size() > 1) {
                                        list.remove(0);
                                    }
                                    list.add(elem);
                                    return list;
                                })
                        .filter(arr -> !arr.isEmpty())
                        .subscribe(data ->
                                System.out.println(groupFlux.key() + ": " + data)));
    }

    private Flux<String> requestBooks(String user) {
        return Flux.range(1, random.nextInt(3) + 1)
                .delayElements(Duration.ofMillis(3))
                .map(i -> "book-" + i);
    }

    @Test
    public void flatMapExample() throws InterruptedException {
        Flux.just("user-1", "user-2", "user-3")
                .flatMap(u -> requestBooks(u)
                        .map(b -> u + "/" + b))
                .subscribe(System.out::println);

        Thread.sleep(1000);
    }

    @Test
    public void sampleExample() throws InterruptedException {
        Flux.range(1, 100)
                .delayElements(Duration.ofMillis(1))
                .sample(Duration.ofMillis(20))
                .subscribe(System.out::println);

        Thread.sleep(1000);
    }

    @Test
    public void doOnExample() {
        Flux.just(1, 2, 3)
                .concatWith(Flux.error(new RuntimeException("Conn error")))
                .doOnEach(System.out::println)
                .subscribe();
    }

    @Test
    public void signalProcessing() {
        Flux.range(1, 3)
                .doOnNext(e -> System.out.println("data  : " + e))
                .materialize()
                .doOnNext(e -> System.out.println("signal: " + e))
                .dematerialize()
                .collectList()
                .subscribe(r -> System.out.println("result: " + r));
    }

    @Test
    public void signalProcessingWithLog() {
        Flux.range(1, 3)
                .log("FluxEvents")
                .subscribe(e -> {}, e -> {}, () -> {}, s -> s.request(2));
    }
}
