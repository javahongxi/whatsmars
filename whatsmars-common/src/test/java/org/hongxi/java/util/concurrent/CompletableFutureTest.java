package org.hongxi.java.util.concurrent;

import java.util.concurrent.CompletableFuture;

/**
 * @author shenhongxi 2019/8/13
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> i + 1)
                .thenApply(i -> i * i)
                .whenComplete((r, e) -> System.out.println(r));
    }
}
