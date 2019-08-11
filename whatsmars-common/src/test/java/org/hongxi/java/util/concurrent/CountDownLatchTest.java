package org.hongxi.java.util.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created on 2019/8/11.
 *
 * @author shenhongxi
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        int nThreads = 3;
        CountDownLatch latch = new CountDownLatch(nThreads);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            new Thread(new Task(i + 1, latch)).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - begin);
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
