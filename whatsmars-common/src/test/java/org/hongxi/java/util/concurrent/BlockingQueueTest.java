package org.hongxi.java.util.concurrent;

/**
 * @author shenhongxi 2019/8/13
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        final BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(10);
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    blockingQueue.put(i);
                    System.out.println("put: " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    System.out.println("take: " + blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
