package org.hongxi.java.util.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @author shenhongxi 2019/09/01
 */
public class LockSupportTest2 {

    public static void main(String[] args) {
        System.out.println("begin park!");
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("end park!");
    }
}
