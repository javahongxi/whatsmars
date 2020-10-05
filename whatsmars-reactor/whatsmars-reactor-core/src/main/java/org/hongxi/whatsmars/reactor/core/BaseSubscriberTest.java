package org.hongxi.whatsmars.reactor.core;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * Created by shenhongxi on 2020/10/5.
 */
public class BaseSubscriberTest {

    public static void main(String[] args) {
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
}
