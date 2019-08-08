package org.hongxi.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created on 2019/8/8.
 *
 * @author shenhongxi
 */
public class RunnableTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> System.out.println("hello"));

        Future<String> future = executorService.submit(() ->
                System.out.println("..."), "world");
        System.out.println(future.get());
        executorService.shutdown();
    }
}
