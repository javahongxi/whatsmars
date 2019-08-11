package org.hongxi.java.util.concurrent.sync;

/**
 * @author shenhongxi 2019/8/11
 */
public class WorkStack {

    private int index = 0;
    private Work[] works = new Work[6];

    public synchronized void push(Work work) {
        while (index == works.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        this.notifyAll();
        works[index++] = work;
    }

    public synchronized Work pop() {
        while (index == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        this.notifyAll();
        return works[--index];
    }
}
