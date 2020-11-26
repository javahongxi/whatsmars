package org.hongxi.java.util.concurrent;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author shenhongxi 2019/09/01
 */
public class LongAdderTest {

    private static LongAdder count = new LongAdder();

    private static Integer[] arrayOne = new Integer[] {0, 1, 2, 3, 0, 5, 6, 0, 56, 0};
    private static Integer[] arrayTwo = new Integer[] {10, 1, 2, 3, 0, 5, 6, 0, 56, 0};

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            int size = arrayOne.length;
            for (int i = 0; i < size; i++) {
                if (arrayOne[i].intValue() == 0)
                    count.increment();
            }
        });

        Thread threadTwo = new Thread(() -> {
            int size = arrayTwo.length;
            for (int i = 0; i < size; i++) {
                if (arrayTwo[i].intValue() == 0)
                    count.increment();
            }
        });

        threadOne.start();
        threadTwo.start();

        // 等待线程执行完毕
        threadOne.join();
        threadTwo.join();

        System.out.println("count 0:" + count.sum());
    }
}
