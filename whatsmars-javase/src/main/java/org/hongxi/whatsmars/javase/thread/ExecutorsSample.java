package org.hongxi.whatsmars.javase.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shenhongxi on 15/8/13.
 */
public class ExecutorsSample {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool = Executors.newSingleThreadExecutor();
        threadPool = Executors.newFixedThreadPool(5, new NameThreadFactory());

        for (int i = 0; i < 10; i++) {
            final int j = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + j);
                }
            });
        }


        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("bombing!");

                    }
                },
                6,
                2,
                TimeUnit.SECONDS);
    }

    private static class NameThreadFactory implements ThreadFactory {

        private static AtomicInteger idx = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("MARS-thread-" + idx.getAndIncrement());
            return thread;
        }
    }

}
