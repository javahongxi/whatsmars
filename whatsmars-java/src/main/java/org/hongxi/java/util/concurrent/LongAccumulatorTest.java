package org.hongxi.java.util.concurrent;

import java.util.concurrent.atomic.LongAccumulator;

/**
 * @author shenhongxi 2019/09/01
 */
public class LongAccumulatorTest {

    private static LongAccumulator count = new LongAccumulator((left, right) -> left + right, 0);

    private static Integer[] arrayOne = new Integer[] {0, 1, 2, 3, 0, 5, 6, 0, 56, 0};
    private static Integer[] arrayTwo = new Integer[] {10, 1, 2, 3, 0, 5, 6, 0, 56, 0};

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            int size = arrayOne.length;
            for (int i = 0; i < size; i++) {
                if (arrayOne[i].intValue() == 0)
                    count.accumulate(1);
            }
        });

        Thread threadTwo = new Thread(() -> {
            int size = arrayTwo.length;
            for (int i = 0; i < size; i++) {
                if (arrayTwo[i].intValue() == 0)
                    count.accumulate(1);
            }
        });

        threadOne.start();
        threadTwo.start();

        // 等待线程执行完毕
        threadOne.join();
        threadTwo.join();

        System.out.println("count 0:" + count.get());
    }
}
