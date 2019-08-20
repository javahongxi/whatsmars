package org.hongxi.java.lang.singleton;

/**
 * @author shenhongxi 2019/8/11
 */
public class EagerSingleton {

    public static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {}

}
