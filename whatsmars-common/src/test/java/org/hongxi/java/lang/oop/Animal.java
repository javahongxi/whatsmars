package org.hongxi.java.lang.oop;

/**
 * @author shenhongxi 2019/8/11
 */
public abstract class Animal {
    private int type;

    Animal() {
        System.out.println("Animal()");
    }

    Animal(int type) {
        this.type = type;
    }

    abstract void run();
}
