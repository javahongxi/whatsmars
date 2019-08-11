package org.hongxi.java.util.concurrent.sync;

/**
 * @author shenhongxi 2019/8/11
 */
public class Consumer implements Runnable {

    private WorkStack stack;
    private String name;

    public Consumer(WorkStack stack, String name) {
        this.stack = stack;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 60; i++) {
            Work work = stack.pop();
            System.out.println(getName() + "消费" + work);
            try {
                Thread.sleep((long) (Math.random() * 400));
            } catch (InterruptedException e) {}
        }
    }
}
