package org.hongxi.java.util.concurrent.sync;

/**
 * @author shenhongxi 2019/8/11
 */
public class Main {

    public static void main(String[] args) {
        WorkStack stack = new WorkStack();

        new Thread(new Producer(stack, "p1")).start();
        new Thread(new Consumer(stack, "c1")).start();
        new Thread(new Producer(stack, "p2")).start();
        new Thread(new Consumer(stack, "c2")).start();
    }
}
