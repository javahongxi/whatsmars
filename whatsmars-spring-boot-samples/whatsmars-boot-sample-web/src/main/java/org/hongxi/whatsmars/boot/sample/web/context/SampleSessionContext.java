package org.hongxi.whatsmars.boot.sample.web.context;

import java.util.Objects;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class SampleSessionContext {

    private static final ThreadLocal<SampleSessionContext> CONTEXT =
            ThreadLocal.withInitial(() -> new SampleSessionContext());

    private String userId;

    public static SampleSessionContext get() {
        return CONTEXT.get();
    }

    public static void set(SampleSessionContext context) {
        CONTEXT.set(context);
    }

    public static void remove() {
        CONTEXT.remove();
    }

    public static String getUserId() {
        SampleSessionContext context = get();
        if (Objects.isNull(context)) {
            return null;
        }
        return context.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
