package org.hongxi.whatsmars.reactor.core;

import reactor.core.publisher.Flux;

import java.time.Instant;

/**
 * Created by shenhongxi on 2020/10/5.
 */
public class IndexTest {

    public static void main(String[] args) {
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
}
