package org.hongxi.whatsmars.common.log;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by shenhongxi on 2020/11/7.
 */
public class LogUtils {

    public static Logger createLogger(String name, String logPath) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("logger name cannot be empty");
        }
        String file = getFile(name, logPath);
        if (LogbackHelper.isLogback()) {
            LogbackLoggerCreator.create(name, file);
        } else if (Log4j2Helper.isLog4j2()) {
            Log4j2LoggerCreator.create(name, file);
        } else if (Log4jHelper.isLog4j()) {
            Log4jLoggerCreator.create(name, file);
        }
        return LoggerFactory.getLogger(name);
    }

    public static Logger createLogger(String name, String logPath, String pattern) {
        return createLogger(name, logPath, pattern, null);
    }

    public static Logger createLogger(String name, String logPath, String pattern, String level) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("logger name cannot be empty");
        }
        String file = getFile(name, logPath);
        if (LogbackHelper.isLogback()) {
            LogbackLoggerCreator.create(name, file, pattern, level);
        } else if (Log4j2Helper.isLog4j2() && Log4j2Helper.hasBridge()) {
            Log4j2LoggerCreator.create(name, file, pattern, level);
        } else if (Log4jHelper.isLog4j() && Log4jHelper.hasBridge()) {
            Log4jLoggerCreator.create(name, file, pattern, level);
        }
        return LoggerFactory.getLogger(name);
    }

    public static Logger createLogger(String name, String logPath,
                                      String maxFileSize, int maxBackupIndex) {
        return createLogger(name, logPath, null, maxFileSize, maxBackupIndex);
    }

    public static Logger createLogger(String name, String logPath, String pattern,
                                      String maxFileSize, int maxBackupIndex) {
        return createLogger(name, logPath, pattern, null, maxFileSize, maxBackupIndex);
    }

    public static Logger createLogger(String name, String logPath, String pattern,
                                      String level, String maxFileSize, int maxBackupIndex) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("logger name cannot be empty");
        }
        String file = getFile(name, logPath);
        if (LogbackHelper.isLogback()) {
            LogbackLoggerCreator.create(name, file, pattern, level, maxFileSize, maxBackupIndex);
        } else if (Log4j2Helper.isLog4j2() && Log4j2Helper.hasBridge()) {
            Log4j2LoggerCreator.create(name, file, pattern, level, maxFileSize, maxBackupIndex);
        } else if (Log4jHelper.isLog4j() && Log4jHelper.hasBridge()) {
            Log4jLoggerCreator.create(name, file, pattern, level, maxFileSize, maxBackupIndex);
        }
        return LoggerFactory.getLogger(name);
    }

    public static Logger createLogger(String name, String logPath, String pattern,
                                      String level, String maxFileSize, int maxBackupIndex, String filePatternSuffix) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("logger name cannot be empty");
        }
        String file = getFile(name, logPath);
        if (LogbackHelper.isLogback()) {
            throw new IllegalArgumentException("logback not support this method");
        } else if (Log4j2Helper.isLog4j2() && Log4j2Helper.hasBridge()) {
            Log4j2LoggerCreator.create(name, file, pattern, level, maxFileSize, maxBackupIndex, filePatternSuffix);
        } else if (Log4jHelper.isLog4j() && Log4jHelper.hasBridge()) {
            throw new IllegalArgumentException("log4j not support this method");
        }
        return LoggerFactory.getLogger(name);
    }

    private static String getFile(String name, String logPath) {
        if (StringUtils.isBlank(logPath)) {
            logPath = ".";
        }
        if (logPath.endsWith(".log")) {
            return logPath;
        }
        if (!logPath.endsWith(File.separator)) {
            logPath = logPath + File.separator;
        }
        return logPath + name + ".log";
    }
}
