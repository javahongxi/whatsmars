package org.hongxi.java.util.concurrent;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author shenhongxi 2019/8/10
 */
public class ScheduleTest {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 开始执行的时间间隔固定(任务执行时长超过间隔时，next任务会delay，不会发生并行执行)
        final ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() ->
                System.out.println(System.currentTimeMillis() / 1000), 1, 1, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(() -> scheduledFuture.cancel(true), 5, TimeUnit.SECONDS);

        final Random r = new Random();
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        // 每次执行结束到下一次执行开始之间的时间间隔固定
        scheduledExecutor.scheduleWithFixedDelay(() -> System.out.println(System.currentTimeMillis()),
                0, 1, TimeUnit.SECONDS);
    }
}
