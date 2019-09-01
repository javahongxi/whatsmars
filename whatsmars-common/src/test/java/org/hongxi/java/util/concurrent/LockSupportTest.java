package org.hongxi.java.util.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @author shenhongxi 2019/8/13
 */
public class LockSupportTest {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println(System.currentTimeMillis());
            /**
             * 默认情况下调用线程是不持有许可证的，这里会阻塞。
             * 因调用park()方法而被阻塞的线程被其他线程中断而返回时并不会抛出InterruptedException异常。
             * 使用带有blocker参数的park方法，线程堆栈可以提供更多有关阻塞对象的信息。
             */
            LockSupport.park(new LockSupportTest());
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
