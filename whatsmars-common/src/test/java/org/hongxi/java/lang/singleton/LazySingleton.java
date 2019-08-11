package org.hongxi.java.lang.singleton;

/**
 * @author shenhongxi 2019/8/11
 */
public class LazySingleton {

    private volatile static LazySingleton instance;

    private LazySingleton() {}

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
