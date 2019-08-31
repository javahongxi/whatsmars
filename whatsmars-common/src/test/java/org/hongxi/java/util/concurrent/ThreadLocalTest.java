package org.hongxi.java.util.concurrent;

/**
 * Created by shenhongxi on 2019-08-31.
 */
public class ThreadLocalTest {

    static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("hello world");

        new Thread(() -> System.out.println("thread:" + threadLocal.get())).start();

        System.out.println("main:" + threadLocal.get());
    }
}
