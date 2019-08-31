package org.hongxi.java.util.concurrent;

/**
 * Created by shenhongxi on 2019-08-31.
 */
public class DaemonThreadTest {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (;;) {}
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("main thread is over");
    }
}
