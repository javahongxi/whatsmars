package org.hongxi.whatsmars.common.log;

/**
 * log4j 发现工具类
 *
 * Created by shenhongxi on 2020/11/7.
 */
public class Log4jHelper {

    private static boolean log4j = false;
    private static boolean hasSlf4jBridge = false;

    static {
        try {

            Class<?> loggerClass = Log4jHelper.class.getClassLoader().loadClass("org.apache.log4j.Logger");
            // 这里可能会加载到其它上游ClassLoader的log4j，因此需要判断是否当前classloader
            if (loggerClass.getClassLoader().equals(Log4jHelper.class.getClassLoader())) {
                log4j = true;
            }

            Class<?> bridgeClass = Log4jHelper.class.getClassLoader().loadClass("org.slf4j.impl.Log4jLoggerAdapter");
            if (bridgeClass.getClassLoader().equals(Log4j2Helper.class.getClassLoader())) {
                hasSlf4jBridge = true;
            }
        } catch (Throwable t) {
        }
    }

    public static boolean isLog4j() {
        return log4j;
    }

    public static boolean hasBridge() {
        return hasSlf4jBridge;
    }
}
