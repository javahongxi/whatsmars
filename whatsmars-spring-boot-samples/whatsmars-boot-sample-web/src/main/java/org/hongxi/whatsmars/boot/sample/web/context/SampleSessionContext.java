package org.hongxi.whatsmars.boot.sample.web.context;

import java.util.Objects;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class SampleSessionContext {

    private static final ThreadLocal<SampleSessionContext> CONTEXT =
            ThreadLocal.withInitial(() -> new SampleSessionContext());

    private String userId;

    /**
     * Controller 方法的参数对象，仅当用 @RequestBody 时才有值
     */
    private Object request;
    /**
     * Controller 方法的返回对象，仅当用 @ResponseBody 时才有值
     */
    private Object response;

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

    public static Object getRequest() {
        SampleSessionContext context = get();
        if (Objects.isNull(context)) {
            return null;
        }
        return context.request;
    }

    public void setRequest(Object body) {
        this.request = body;
    }

    public static Object getResponse() {
        SampleSessionContext context = get();
        if (Objects.isNull(context)) {
            return null;
        }
        return context.response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
