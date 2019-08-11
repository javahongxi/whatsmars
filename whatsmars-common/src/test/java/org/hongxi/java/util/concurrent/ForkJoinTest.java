package org.hongxi.java.util.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.CountedCompleter;

/**
 * @author shenhongxi 2019/8/11
 *
 * @see RecursiveAction
 * @see CountedCompleter
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() * 2);
        int sum = forkJoinPool.invoke(new AccumulationTask(1, 60000));
        System.out.println(sum);
    }

    static class AccumulationTask extends RecursiveTask<Integer> {
        private int start;
        private int end;

        AccumulationTask(int start, int end) {
            super();
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int result = 0;
            if (end - start <= 2) {
                for (int i = start; i <= end; i++) {
                    result += i;
                }
            } else {
                int middle = (start + end) / 2;
                AccumulationTask left = new AccumulationTask(start, middle);
                AccumulationTask right = new AccumulationTask(middle + 1, end);
                left.fork();
                right.fork();
                result = left.join() + right.join();
            }
            return result;
        }
    }
}
