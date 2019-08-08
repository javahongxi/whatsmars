package org.hongxi.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created on 2019/8/8.
 *
 * @author shenhongxi
 */
public class CallableTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
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
