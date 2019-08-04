package org.hongxi.whatsmars.common.threadpool;

import org.hongxi.whatsmars.common.threadlocal.NamedInternalThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * EagerThreadPool
 * When the core threads are all in busy,
 * create new thread instead of putting task into blocking queue.
 */
public class EagerThreadPool {

    public Executor getExecutor(String name, int cores, int nThreads, int queues, int alive) {
        // init queue and executor
        TaskQueue<Runnable> taskQueue = new TaskQueue<Runnable>(queues <= 0 ? 1 : queues);
        EagerThreadPoolExecutor executor = new EagerThreadPoolExecutor(cores,
                nThreads,
                alive,
                TimeUnit.MILLISECONDS,
                taskQueue,
                new NamedInternalThreadFactory(name, true),
                new ThreadPoolExecutor.AbortPolicy());
        taskQueue.setExecutor(executor);
        return executor;
    }
}
