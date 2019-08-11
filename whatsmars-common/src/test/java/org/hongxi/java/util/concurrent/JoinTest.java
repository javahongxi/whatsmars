package org.hongxi.java.util.concurrent;

import java.util.Arrays;

/**
 * join():
 * while (isAlive()) {
 *     wait(0);
 * }
 *
 * @author shenhongxi 2019/8/11
 */
public class JoinTest {

    public static void main(String[] args) {
        int nThreads = 3;
        Thread[] threads = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            final int j = i + 1;
            threads[i] = new Thread(() -> {
                try {
                    Thread.sleep(1000 * j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        long begin = System.currentTimeMillis();
        Arrays.stream(threads).forEach(t -> t.start());
        Arrays.stream(threads).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(System.currentTimeMillis() - begin);
    }
}
