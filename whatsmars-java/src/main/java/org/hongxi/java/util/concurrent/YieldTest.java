package org.hongxi.java.util.concurrent;

/**
 * @author shenhongxi 2019/8/12
 */
public class YieldTest {

    public static void main(String[] args) {
        new MyThread("t1").start();
        new MyThread("t2").start();
    }

    static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println(getName() + ", " + i);
                if (i % 10 == 0) {
                    Thread.yield();
                }
            }
        }
    }
}
