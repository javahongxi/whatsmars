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
                .whenComplete((r, e) -> System.out.println("supplyAsync completed: " + r));

        CompletableFuture.runAsync(() -> System.out.println("runAsync"))
                .thenRun(() -> System.out.println("thenRun"))
                .whenCompleteAsync((r, e) -> System.out.println("whenCompleteAsync"));

        CompletableFuture.supplyAsync(() -> "Completable")
                .thenApply(s -> s + " ")
                .thenApply(s -> s + "Future")
                .thenApply(s -> s + " ")
                .thenCombine(CompletableFuture.completedFuture("Test"),
                        (s1, s2) -> s1 + s2)
                .thenApply(String::toUpperCase)
                .exceptionally(e -> "error: " + e.getMessage())
                .thenAccept(System.out::println);

        final CompletableFuture f = CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> ++i)
                .handle((r, e) -> {
                    if (e != null)
                        return "error";
                    else {
                        System.out.println("handle result: " + r);
                        return r;
                    }
                });

        CompletableFuture.supplyAsync(() -> "f1")
                .thenAcceptBoth(CompletableFuture.completedFuture("f2"),
                        (r1, r2) -> System.out.println(String.join(",", r1, r2)));

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        }).acceptEither(f, (r) -> System.out.println(r));

        CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 10;
            }).runAfterEither(f, () -> System.out.println("runAfterEither"));

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        }).applyToEither(f, i -> ++i);

        CompletableFuture.supplyAsync(() -> 10)
                .runAfterBoth(f, () -> System.out.println("runAfterBoth"));

        CompletableFuture.supplyAsync(() -> 10)
                .thenCompose(n -> CompletableFuture.completedFuture(n))
                .thenAccept(System.out::println);
    }
}
