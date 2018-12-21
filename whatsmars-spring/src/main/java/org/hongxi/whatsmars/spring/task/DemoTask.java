package org.hongxi.whatsmars.spring.task;

/**
 * Created by shenhongxi on 2018/12/21.
 */
public class DemoTask implements Runnable {
    @Override
    public void run() {
        System.out.println("demo task...");
    }
}
