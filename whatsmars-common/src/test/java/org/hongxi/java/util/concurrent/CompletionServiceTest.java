package org.hongxi.java.util.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author shenhongxi 2019/8/11
 */
public class CompletionServiceTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        for (int i = 1; i <= 10; i++) {
            final int seq = i;
            completionService.submit(() -> {
                Thread.sleep(new Random().nextInt(5000));
                return seq;
            });
        }
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
