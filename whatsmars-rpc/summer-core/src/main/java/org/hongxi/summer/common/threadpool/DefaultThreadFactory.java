package org.hongxi.summer.common.threadpool;

import org.hongxi.summer.common.SummerConstants;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shenhongxi on 2020/7/6.
 */
public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup threadGroup;
    private final AtomicInteger currentThreadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private int priority = Thread.NORM_PRIORITY;
    private boolean isDaemon = false;

    public DefaultThreadFactory() {
        this(SummerConstants.FRAMEWORK_NAME);
    }

    public DefaultThreadFactory(String prefix) {
        this(prefix, false);
    }

    public DefaultThreadFactory(String prefix, boolean isDaemon) {
        this(prefix, isDaemon, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(String prefix, boolean isDaemon, int priority) {
        SecurityManager s = System.getSecurityManager();
        this.threadGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = prefix + "-" + poolNumber.getAndIncrement() + "-thread-";
        this.isDaemon = isDaemon;
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(threadGroup, r, namePrefix + currentThreadNumber.getAndIncrement(), 0);
        thread.setDaemon(isDaemon);
        thread.setPriority(priority);
        return thread;
    }
}
