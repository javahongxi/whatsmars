package org.hongxi.java.util.concurrent;

/**
 * Created on 2019/8/10.
 *
 * @author shenhongxi
 */
public class InterruptedTest {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println(Thread.interrupted()); // currentThread().isInterrupted(true);
            System.out.println(Thread.interrupted());
        });
        t.start();
        t.interrupt(); // Just to set the interrupt flag
    }
}
