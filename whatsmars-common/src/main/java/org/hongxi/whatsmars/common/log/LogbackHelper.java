package org.hongxi.whatsmars.common.log;

/**
 * logback 发现工具类
 *
 * Created by shenhongxi on 2020/11/7.
 */
public class LogbackHelper {

    private static boolean logback = false;

    static {

        try {
            Class<?> loggerClass = LogbackHelper.class.getClassLoader().loadClass("ch.qos.logback.classic.Logger");
            // 这里可能会加载到应用中依赖的logback，因此需要判断classloader
            if (loggerClass.getClassLoader().equals(LogbackHelper.class.getClassLoader())) {
                org.slf4j.ILoggerFactory loggerFactory = org.slf4j.LoggerFactory.getILoggerFactory();
                if (loggerFactory instanceof ch.qos.logback.classic.LoggerContext) {
                    logback = true;
                }
            }
        } catch (Throwable t) {
            // ignore
        }
    }

    public static boolean isLogback() {
        return logback;
    }

    public static boolean hasBridge() {
        return logback;
    }
}
