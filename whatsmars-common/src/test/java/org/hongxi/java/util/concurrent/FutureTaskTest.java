package org.hongxi.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created on 2019/8/8.
 *
 * @author shenhongxi
 */
public class FutureTaskTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        FutureTask<String> task = new FutureTask<>(() -> "hello");
        executorService.submit(task);
        FutureTask<String> task2 = new FutureTask<>(() ->
                System.out.println("..."), "world");
        executorService.submit(task2);
        System.out.println(task.get());
        task2.cancel(true);
        System.out.println(task2.isCancelled()); // maybe true or false, because need time
        System.out.println(task2.isDone()); // must be true
        if (!task2.isCancelled()) {
            System.out.println(task2.get());
        }
        executorService.shutdown();
    }
}
