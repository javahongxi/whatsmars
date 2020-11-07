package org.hongxi.whatsmars.common.log;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.Util;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;

/**
 * 引用了多种日志框架时，尝试指定使用其中某一个
 * 需在项目最起始阶段执行
 *
 * Created by shenhongxi on 2020/11/7.
 */
public class LogRegister {

    enum LogFrame {
        LOG4J("slf4j-log4j12"),
        LOG4J2("log4j-slf4j-impl"),
        LOGBACK("logback");

        private String packageName;

        LogFrame(String packageName) {
            this.packageName = packageName;
        }

        public String getPackageName() {
            return packageName;
        }
    }

    public static void tryRegisterLog4j() {
        tryRegister(LogFrame.LOG4J.getPackageName());
    }

    public static void tryRegisterLog4j2() {
        tryRegister(LogFrame.LOG4J2.getPackageName());
    }

    public static void tryRegisterLogback() {
        tryRegister(LogFrame.LOGBACK.getPackageName());
    }

    @SuppressWarnings("rawtypes")
    public static void tryRegister(String packagePartName) {
        try {
            ClassLoader loggerFactoryClassLoader = LoggerFactory.class.getClassLoader();
            Enumeration paths = loggerFactoryClassLoader.getResources("org/slf4j/impl/StaticLoggerBinder.class");
            while(paths.hasMoreElements()) {
                URL path = (URL)paths.nextElement();
                if (path.getFile().contains(packagePartName)) {
                    byte[] data = IOUtils.toByteArray(path.openStream());
                    Method method = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                    method.setAccessible(true);
                    method.invoke(Thread.currentThread().getContextClassLoader(), data, 0, data.length);
                }
            }
        } catch (Exception e) {
            Util.report("Error getting resources from path", e);
        }
    }

}