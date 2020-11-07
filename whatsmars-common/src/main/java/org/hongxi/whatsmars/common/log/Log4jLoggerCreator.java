package org.hongxi.whatsmars.common.log;

import org.apache.log4j.*;
import org.slf4j.helpers.Util;

/**
 * Created by shenhongxi on 2020/2/22.
 */
public class Log4jLoggerCreator {

    private static final String DEFAULT_PATTERN = "%d{yyyy-MM-dd HH:mm:ss,SSS} %5p (%c{1}#%M:%L) %t - %m%n";

    public static void create(String name, String file) {
        create(name, file, DEFAULT_PATTERN);
    }

    public static void create(String name, String file, String pattern) {
        create(name, file, pattern, null);
    }

    public static void create(String name, String file, String pattern, String level) {
        Logger logger = LogManager.exists(name);
        if (logger != null) {
            Util.report("Log4jLoggerCreator.create, logger exists: " + name);
            return;
        }

        DailyRollingFileAppender appender = new DailyRollingFileAppender();
        appender.setName(name);
        appender.setLayout(new PatternLayout(pattern));
        appender.setFile(file);
        appender.setThreshold(Level.toLevel(level, Level.INFO));
        appender.activateOptions();

        logger = LogManager.getLogger(name);
        logger.addAppender(appender);
        logger.setAdditivity(false);
        logger.setLevel(Level.toLevel(level, Level.INFO));
    }

    public static void create(String name, String file, String pattern, String level, String maxFileSize, int maxBackupIndex) {
        Logger logger = LogManager.exists(name);
        if (logger != null) {
            Util.report("Log4jLoggerCreator.create, logger exists: " + name);
            return;
        }

        RollingFileAppender appender = new RollingFileAppender();
        appender.setName(name);
        if (pattern != null) {
            appender.setLayout(new PatternLayout(pattern));
        } else {
            appender.setLayout(new PatternLayout(DEFAULT_PATTERN));
        }

        appender.setFile(file);
        appender.setThreshold(Level.toLevel(level, Level.INFO));
        appender.setMaxFileSize(maxFileSize);
        appender.setMaxBackupIndex(maxBackupIndex);
        appender.activateOptions();

        logger = LogManager.getLogger(name);
        logger.addAppender(appender);
        logger.setAdditivity(false);
        logger.setLevel(Level.toLevel(level, Level.INFO));
    }
}