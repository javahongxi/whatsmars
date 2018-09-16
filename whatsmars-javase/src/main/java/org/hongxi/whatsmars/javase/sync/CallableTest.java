package org.hongxi.whatsmars.javase.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shenhongxi on 2016/8/12.
 */
public class CallableTest {

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        List<Callable<Void>> subs = new ArrayList<Callable<Void>>();
        for (int i = 0; i < 3; i++) {
            subs.add(new SubCallable(i));
        }

        long start = System.currentTimeMillis();
        try {
            pool.invokeAll(subs);
        } finally {
            pool.shutdown();
        }
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("Main finished");
    }

    static class SubCallable implements Callable<Void> {
        private int id = -1;

        public SubCallable(int id) {
            this.id = id;
        }

        @Override
        public Void call() throws Exception {
            try {
                Thread.sleep(3000);
                System.out.println(String
                        .format("Child Thread %d finished", id));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
