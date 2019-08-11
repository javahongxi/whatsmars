package org.hongxi.java.util.lang;

/**
 * @author shenhongxi 2019/8/11
 */
public class ChinaCat extends Cat {
    private int province;

    ChinaCat() {
        System.out.println("ChinaCat()");
    }

    @Override
    void run() {
        System.out.println("ChinaCat#run()");
    }

    public static void main(String[] args) {
        Animal animal = new ChinaCat();
        animal.run();
    }
}
