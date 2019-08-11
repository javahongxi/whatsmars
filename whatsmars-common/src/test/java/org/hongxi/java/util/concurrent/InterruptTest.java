package org.hongxi.java.util.concurrent;

/**
 * Created on 2019/8/10.
 *
 * @author shenhongxi
 *
 * @see InterruptedTest
 */
public class InterruptTest {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().isInterrupted()); // false
        });
        t.start();
        t.interrupt(); // Just to set the interrupt flag
        System.out.println(t.isInterrupted()); // true
    }
}
