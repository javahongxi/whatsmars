package org.hongxi.whatsmars.common.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.*;
import org.apache.logging.log4j.core.appender.rolling.action.*;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.helpers.Util;

import java.io.File;

/**
 * Created by shenhongxi on 2020/11/7.
 */
public class Log4j2LoggerCreator {

    private static final String DEFAULT_PATTERN = "%d{yyyy-MM-dd HH:mm:ss,SSS} %5p [%c{1}#%M:%L] %t - %m%n";

    public static void create(String name, String file) {
        create(name, file, DEFAULT_PATTERN);
    }

    public static void create(String name, String file, String pattern) {
        create(name, file, pattern, null);
    }

    public static void create(String name, String file, String pattern, String level) {
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        if (loggerContext.hasLogger(name)) {
            Util.report("Log4j2LoggerCreator.create, logger exists: " + name);
            return;
        }
        Configuration configuration = loggerContext.getConfiguration();

        Layout layout = PatternLayout.newBuilder()
                .withPattern(pattern)
                .build();
        String filePattern = file + ".%d{yyyy-MM-dd}";
        TriggeringPolicy policy = TimeBasedTriggeringPolicy.newBuilder().build();

        String path = file.substring(0, file.lastIndexOf(File.separator));
        IfLastModified ifLastModified = IfLastModified.createAgeCondition(Duration.parse("P7D"));
        DeleteAction deleteAction = DeleteAction.createDeleteAction(
                path, false, 1, false, null,
                new PathCondition[]{ifLastModified}, null, configuration);
        Action[] actions = new Action[]{deleteAction};
        DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.newBuilder()
                .withMax("20")
                .withMin("1")
                .withCompressionLevelStr("1")
                .withCustomActions(actions)
                .withStopCustomActionsOnError(true)
                .withConfig(configuration)
                .build();

        RollingFileAppender appender = RollingFileAppender.newBuilder()
                .setName(name)
                .setLayout(layout)
                .withFileName(file)
                .withFilePattern(filePattern)
                .withPolicy(policy)
                .withStrategy(rolloverStrategy)
                .build();
        appender.start();

        configuration.addAppender(appender);
        AppenderRef appenderRef = AppenderRef.createAppenderRef(name, null, null);
        AppenderRef[] refs = new AppenderRef[] {appenderRef};
        LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.toLevel(level, Level.INFO), name,
                null, refs, null, configuration, null);
        loggerConfig.addAppender(appender, Level.toLevel(level, Level.INFO), null);
        configuration.addLogger(name, loggerConfig);
        loggerContext.updateLoggers();
    }

    public static void create(String name, String file, String pattern, String level, String maxFileSize, int maxBackupIndex) {
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        if (loggerContext.hasLogger(name)) {
            Util.report("Log4j2LoggerCreator.create, logger exists: " + name);
            return;
        }
        Configuration configuration = loggerContext.getConfiguration();

        Layout layout = PatternLayout.newBuilder()
                .withPattern(pattern != null ? pattern : DEFAULT_PATTERN)
                .build();
        String filePattern = file + ".%i.gz";
        TriggeringPolicy triggeringPolicy = SizeBasedTriggeringPolicy.createPolicy(maxFileSize);

        DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.newBuilder()
                .withMax(maxBackupIndex + "")
                .withMin("1")
                .withCompressionLevelStr("1")
                .withConfig(configuration)
                .build();

        RollingFileAppender appender = RollingFileAppender.newBuilder()
                .setName(name)
                .setLayout(layout)
                .withFileName(file)
                .withFilePattern(filePattern)
                .withPolicy(triggeringPolicy)
                .withStrategy(rolloverStrategy)
                .build();
        appender.start();

        configuration.addAppender(appender);
        AppenderRef appenderRef = AppenderRef.createAppenderRef(name, null, null);
        AppenderRef[] refs = new AppenderRef[] {appenderRef};
        LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.toLevel(level, Level.INFO), name,
                null, refs, null, configuration, null);
        loggerConfig.addAppender(appender, Level.toLevel(level, Level.INFO), null);
        configuration.addLogger(name, loggerConfig);
        loggerContext.updateLoggers();
    }

    public static void create(String name, String file, String pattern, String level, String maxFileSize, int maxBackupIndex, String filePatternSuffix) {
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        if (loggerContext.hasLogger(name)) {
            Util.report("Log4j2LoggerCreator.create, logger exists: " + name);
            return;
        }
        Configuration configuration = loggerContext.getConfiguration();

        Layout layout = PatternLayout.newBuilder()
                .withPattern(pattern != null ? pattern : DEFAULT_PATTERN)
                .build();
        if (filePatternSuffix == null || filePatternSuffix.isEmpty()) {
            filePatternSuffix = ".%d{yyyy-MM-dd-HH}-%i";
        }
        String filePattern = file + filePatternSuffix;
        CompositeTriggeringPolicy triggeringPolicy = CompositeTriggeringPolicy.createPolicy(
                SizeBasedTriggeringPolicy.createPolicy(maxFileSize),
                TimeBasedTriggeringPolicy.newBuilder().build());

        DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.newBuilder()
                .withMax(maxBackupIndex + "")
                .withMin("1")
                .withCompressionLevelStr("1")
                .withConfig(configuration)
                .build();

        RollingFileAppender appender = RollingFileAppender.newBuilder()
                .setName(name)
                .setLayout(layout)
                .withFileName(file)
                .withFilePattern(filePattern)
                .withPolicy(triggeringPolicy)
                .withStrategy(rolloverStrategy)
                .build();
        appender.start();

        configuration.addAppender(appender);
        AppenderRef appenderRef = AppenderRef.createAppenderRef(name, null, null);
        AppenderRef[] refs = new AppenderRef[] {appenderRef};
        LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.toLevel(level, Level.INFO), name,
                null, refs, null, configuration, null);
        loggerConfig.addAppender(appender, Level.toLevel(level, Level.INFO), null);
        configuration.addLogger(name, loggerConfig);
        loggerContext.updateLoggers();
    }
}