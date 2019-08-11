package org.hongxi.java.util.lang.oop;

/**
 * @author shenhongxi 2019/8/11
 */
public class Cat extends Animal {
    private int color;

    Cat() {
        System.out.println("Cat()");
    }

    Cat(int type, int color) {
        super(type);
        this.color = color;
    }

    @Override
    void run() {
        System.out.println("Cat#run()");
    }
}
