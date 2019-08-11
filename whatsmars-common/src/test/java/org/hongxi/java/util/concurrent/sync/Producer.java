package org.hongxi.java.util.concurrent.sync;

/**
 * @author shenhongxi 2019/8/11
 */
public class Producer implements Runnable {

    private WorkStack stack;
    private String name;

    public Producer(WorkStack stack, String name) {
        this.stack = stack;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 60; i++) {
            Work work = new Work(i);
            stack.push(work);
            System.out.println(getName() + "生产" + work);
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {}
        }
    }
}
