package com.itlong.whatsmars.base.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenhongxi on 15/8/13.
 */
public class ExcutorsSample {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool = Executors.newFixedThreadPool(10);
        threadPool = Executors.newSingleThreadExecutor();







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
}
