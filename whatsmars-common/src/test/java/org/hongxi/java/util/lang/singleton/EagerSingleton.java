package org.hongxi.java.util.lang.singleton;

/**
 * @author shenhongxi 2019/8/11
 */
public class EagerSingleton {

    private static EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return instance;
    }
}
