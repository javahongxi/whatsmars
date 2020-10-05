package org.hongxi.whatsmars.reactor.core;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Created by shenhongxi on 2020/10/5.
 */
public class DisposableTest {

    public static void main(String[] args) {
        Disposable disposable = Flux.interval(Duration.ofMillis(50))
                .subscribe(data -> System.out.println("onNext: " + data));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
    }
}
