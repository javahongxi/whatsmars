package org.hongxi.java.util.concurrent.sync;

/**
 * @author shenhongxi 2019/8/11
 */
public class Work {

    private int id;

    public Work(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Work" + getId();
    }
}
