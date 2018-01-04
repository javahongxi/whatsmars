package org.hongxi.whatsmars.earth.domain.misc;


import org.hongxi.whatsmars.earth.domain.pojo.User;

/**
 * Author: qing
 * Date: 14-10-29
 */
public class LoginContextHolder {

    private static final ThreadLocal<LoginContext> holder = new ThreadLocal<LoginContext>();

    public static LoginContext get() {
        return holder.get();
    }

    public static void set(LoginContext context) {
        holder.set(context);
    }

    public static void clear() {
        holder.remove();
    }

    public static User getLoginUser() {
        LoginContext context = holder.get();
        return context == null ? null : context.getUser();
    }
}
