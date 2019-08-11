package org.hongxi.java.util.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created on 2019/8/11.
 *
 * @author shenhongxi
 */
public class InvokeAllTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Collection<Callable<Long>> tasks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final int j = i + 1;
            tasks.add(() -> {
                Thread.sleep(1000 * j);
                return System.currentTimeMillis();
            });
        }
        try {
            long begin = System.currentTimeMillis();
            List<Future<Long>> futures = executorService.invokeAll(tasks);
            futures.forEach(f -> {
                try {
                    System.out.println(f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(System.currentTimeMillis() - begin);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
