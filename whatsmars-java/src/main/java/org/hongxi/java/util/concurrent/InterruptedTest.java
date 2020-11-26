package org.hongxi.java.util.concurrent;

/**
 * @author shenhongxi 2019/8/10
 */
public class InterruptedTest {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            // currentThread().isInterrupted(true);
            System.out.println(Thread.interrupted()); // true
            System.out.println(Thread.interrupted()); // false
        });
        t.start();
        t.interrupt(); // Just to set the interrupt flag
    }
}
