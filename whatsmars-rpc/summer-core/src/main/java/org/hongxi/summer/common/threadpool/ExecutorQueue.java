package org.hongxi.summer.common.threadpool;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RejectedExecutionException;

/**
 * LinkedTransferQueue 能保证更高性能，相比与LinkedBlockingQueue有明显提升
 *
 * <pre>
 * 		1) 不过LinkedTransferQueue的缺点是没有队列长度控制，需要在外层协助控制
 * </pre>
 *
 * Created by shenhongxi on 2020/7/6.
 *
 */
public class ExecutorQueue extends LinkedTransferQueue<Runnable> {
    private static final long serialVersionUID = -3392627914941820087L;

    private StandardThreadPoolExecutor threadPoolExecutor;

    public ExecutorQueue() {
        super();
    }

    public void setThreadPoolExecutor(StandardThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public boolean force(Runnable command) {
        if (threadPoolExecutor.isShutdown()) {
            throw new RejectedExecutionException("Executor not running, cannot force a task into the queue");
        }
        return super.offer(command);
    }

    public boolean offer(Runnable command) {
        int poolSize = threadPoolExecutor.getPoolSize();

        if (poolSize == threadPoolExecutor.getMaximumPoolSize()) {
            return super.offer(command);
        }

        if (threadPoolExecutor.getSubmittedTasksCount() <= poolSize) {
            return super.offer(command);
        }

        if (poolSize < threadPoolExecutor.getMaximumPoolSize()) {
            return false;
        }

        return super.offer(command);
    }
}
