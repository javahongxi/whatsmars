package org.hongxi.java.util.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @author shenhongxi 2019/8/13
 */
public class LockSupportTest {

    public static void main(String[] args) {
        Object blocker = new Object();
        Thread t = new Thread(() -> {
            System.out.println(System.currentTimeMillis());
            LockSupport.park(blocker);
            System.out.println(System.currentTimeMillis());
        });
        t.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t);
    }
}
