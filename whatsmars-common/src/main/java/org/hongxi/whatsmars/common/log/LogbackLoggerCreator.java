package org.hongxi.whatsmars.common.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.Util;

import java.nio.charset.Charset;

/**
 * Created by shenhongxi on 2020/2/22.
 */
public class LogbackLoggerCreator {

    private static final String DEFAULT_PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level \\(%logger{36}:%line\\) [%thread] - %msg%n";

    public static void create(String name, String file) {
        create(name, file, DEFAULT_PATTERN);
    }

    public static void create(String name, String file, String pattern) {
        create(name, file, pattern, null);
    }

    public static void create(String name, String file, String pattern, String level) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (loggerContext.exists(name) != null) {
            Util.report("LogbackLoggerCreator.create, logger exists: " + name);
            return;
        }

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern(pattern);
        encoder.setCharset(Charset.forName("UTF-8"));
        encoder.setContext(loggerContext);
        encoder.start();

        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        appender.setContext(loggerContext);
        appender.setName(name);
        appender.setFile(file);
        appender.setEncoder(encoder);

        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(appender);
        rollingPolicy.setFileNamePattern(file + ".%d{yyyy-MM-dd}");
        rollingPolicy.setMaxHistory(20);
        rollingPolicy.setTotalSizeCap(new FileSize(6 * 1024 * 1024 * 1024)); // 6G
        rollingPolicy.start();

        appender.setRollingPolicy(rollingPolicy);
        appender.start();

        Logger logger = loggerContext.getLogger(name);
        logger.addAppender(appender);
        logger.setAdditive(false);
        logger.setLevel(Level.toLevel(level, Level.INFO));
    }

    public static void create(String name, String file, String pattern, String level, String maxFileSize, int maxBackupIndex) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (loggerContext.exists(name) != null) {
            Util.report("LogbackLoggerCreator.create, logger exists: " + name);
            return;
        }

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern(pattern != null ? pattern : DEFAULT_PATTERN);
        encoder.setCharset(Charset.forName("UTF-8"));
        encoder.setContext(loggerContext);
        encoder.start();

        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        appender.setContext(loggerContext);
        appender.setName(name);
        appender.setFile(file);
        appender.setEncoder(encoder);

        SizeBasedTriggeringPolicy<ILoggingEvent> triggeringPolicy = new SizeBasedTriggeringPolicy<>();
        triggeringPolicy.setContext(loggerContext);
        triggeringPolicy.setMaxFileSize(FileSize.valueOf(maxFileSize));
        triggeringPolicy.start();

        FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(appender);
        rollingPolicy.setFileNamePattern(file + ".%i.gz");
        rollingPolicy.setMinIndex(1);
        rollingPolicy.setMaxIndex(maxBackupIndex);
        rollingPolicy.start();

        appender.setTriggeringPolicy(triggeringPolicy);
        appender.setRollingPolicy(rollingPolicy);
        appender.start();

        Logger logger = loggerContext.getLogger(name);
        logger.addAppender(appender);
        logger.setAdditive(false);
        logger.setLevel(Level.toLevel(level, Level.INFO));
    }
}