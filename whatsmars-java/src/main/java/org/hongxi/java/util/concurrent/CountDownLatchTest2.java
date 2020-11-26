package org.hongxi.java.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shenhongxi 2019/8/11
 *
 * @see JoinTest
 * @see InvokeAllTest
 */
public class CountDownLatchTest2 {

    public static void main(String[] args) {
        int nThreads = 3;
        CountDownLatch latch = new CountDownLatch(nThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            executorService.submit(new Task(i + 1, latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - begin);
        executorService.shutdown();
    }

    static class Task implements Runnable {
        private int id;
        private CountDownLatch latch;

        Task(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000 * id);
                System.out.println(String.format("Sub Thread %d finished", id));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
}
