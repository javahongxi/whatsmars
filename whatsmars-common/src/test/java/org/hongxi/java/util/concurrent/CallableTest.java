package org.hongxi.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author shenhongxi 2019/8/8
 */
public class CallableTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // submit(Callable<T>)
        Future<String> future = executorService.submit(() -> {
           Thread.sleep(2000);
           return "hello";
        });
        long begin = System.currentTimeMillis();
        System.out.println(future.get());
        System.out.println(System.currentTimeMillis() - begin);
        executorService.shutdown();
    }
}
