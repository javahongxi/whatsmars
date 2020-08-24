package org.hongxi.summer.common.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *
 * 代码和思路主要来自于：
 *
 * tomcat :
 * 		org.apache.catalina.core.StandardThreadExecutor
 *
 * java.util.concurrent
 * threadPoolExecutor execute执行策略： 		优先offer到queue，queue满后再扩充线程到maxThread，如果已经到了maxThread就reject
 * 						   		比较适合于CPU密集型应用（比如runnable内部执行的操作都在JVM内部，memory copy, or compute等等）
 *
 * StandardThreadExecutor execute执行策略：	优先扩充线程到maxThread，再offer到queue，如果满了就reject
 * 						      	比较适合于业务处理需要远程资源的场景
 *
 * </pre>
 *
 * Created by shenhongxi on 2020/6/27.
 */
public class StandardThreadPoolExecutor extends ThreadPoolExecutor {
    public static final int DEFAULT_MIN_THREADS = 20;
    public static final int DEFAULT_MAX_THREADS = 200;
    public static final int DEFAULT_MAX_IDLE_TIME = 60 * 1000; // 1 min

    protected AtomicInteger submittedTasksCount;
    private int maxSubmittedTasks;

    public StandardThreadPoolExecutor() {
        this(DEFAULT_MIN_THREADS, DEFAULT_MAX_THREADS);
    }

    public StandardThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
        this(corePoolSize, maximumPoolSize, maximumPoolSize);
    }

    public StandardThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, maximumPoolSize);
    }

    public StandardThreadPoolExecutor(int corePoolSize, int maximumPoolSize, int queueCapacity) {
        this(corePoolSize, maximumPoolSize, queueCapacity, Executors.defaultThreadFactory());
    }

    public StandardThreadPoolExecutor(int corePoolSize, int maximumPoolSize, int queueCapacity, ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, DEFAULT_MAX_IDLE_TIME, TimeUnit.MILLISECONDS, queueCapacity, threadFactory);
    }

    public StandardThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int queueCapacity) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, queueCapacity, Executors.defaultThreadFactory());
    }

    public StandardThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int queueCapacity, ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, queueCapacity, threadFactory, new AbortPolicy());
    }

    public StandardThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int queueCapacity, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new ExecutorQueue(), threadFactory, handler);
        ((ExecutorQueue) getQueue()).setThreadPoolExecutor(this);

        submittedTasksCount = new AtomicInteger(0);

        maxSubmittedTasks = maximumPoolSize + queueCapacity;
    }

    @Override
    public void execute(Runnable command) {
        int count = submittedTasksCount.incrementAndGet();

        if (count > maxSubmittedTasks) {
            submittedTasksCount.decrementAndGet();
            getRejectedExecutionHandler().rejectedExecution(command, this);
        }

        try {
            super.execute(command);
        } catch (RejectedExecutionException e) {
            if (!((ExecutorQueue) getQueue()).force(command)) {
                submittedTasksCount.decrementAndGet();
                getRejectedExecutionHandler().rejectedExecution(command, this);
            }
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        submittedTasksCount.decrementAndGet();
    }

    public int getSubmittedTasksCount() {
        return submittedTasksCount.get();
    }

    public int getMaxSubmittedTasks() {
        return maxSubmittedTasks;
    }
}
