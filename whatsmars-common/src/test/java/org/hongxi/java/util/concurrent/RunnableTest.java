package org.hongxi.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * Created on 2019/8/8.
 *
 * @author shenhongxi
 */
public class RunnableTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        IntStream.range(0, 10).forEach(n -> {
            executorService.execute(() -> System.out.println(n));
        });
        executorService.submit(() -> System.out.println("hello"));

        Future<String> future = executorService.submit(() ->
                System.out.println("..."), "world");
        System.out.println(future.get());
        executorService.shutdown();
    }
}
