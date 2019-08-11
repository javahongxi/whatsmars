package org.hongxi.java.util.lang.singleton;

/**
 * @author shenhongxi 2019/8/11
 */
public class InnerClassSingleton {

    private InnerClassSingleton() {}

    private static class SingletonHolder {
        private static final InnerClassSingleton instance = new InnerClassSingleton();
    }

    public static InnerClassSingleton getInstance() {
        return SingletonHolder.instance;
    }
}
