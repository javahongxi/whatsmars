package org.hongxi.whatsmars.common.log;

/**
 * log4j2 发现工具类
 *
 * Created by shenhongxi on 2020/11/7.
 */
public class Log4j2Helper {

    private static boolean log4j2 = false;
    private static boolean hasSlf4jBridge = false;

    static {
        try {
            Class<?> loggerClass = Log4j2Helper.class.getClassLoader().loadClass("org.apache.logging.log4j.Logger");
            // 这里可能会加载到其它上游ClassLoader的log4j2，因此需要判断是否当前classloader
            if (loggerClass.getClassLoader().equals(Log4j2Helper.class.getClassLoader())) {
                log4j2 = true;
            }

            Class<?> bridgeClass = Log4j2Helper.class.getClassLoader().loadClass("org.apache.logging.slf4j.Log4jLogger");
            if (bridgeClass.getClassLoader().equals(Log4j2Helper.class.getClassLoader())) {
                hasSlf4jBridge = true;
            }
        } catch (Throwable t) {
        }
    }

    public static boolean isLog4j2() {
        return log4j2;
    }

    public static boolean hasBridge() {
        return hasSlf4jBridge;
    }
}
