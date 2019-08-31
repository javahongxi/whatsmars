package org.hongxi.java.util.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by shenhongxi on 2019-08-31.
 */
public class UnsafeTest {

    static final Unsafe UNSAFE;
    static final long stateOffset;
    private volatile long state = 0;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
            stateOffset = UNSAFE.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        UnsafeTest test = new UnsafeTest();
        Boolean success = UNSAFE.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(success);
    }
}
