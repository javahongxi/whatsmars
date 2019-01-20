package org.hongxi.whatsmars.common.util;

public abstract class Assert {

    protected Assert() {
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object obj, RuntimeException exeception) {
        if (obj == null) {
            throw exeception;
        }
    }

}