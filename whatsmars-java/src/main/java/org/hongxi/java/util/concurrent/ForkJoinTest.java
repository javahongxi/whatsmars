package org.hongxi.java.util.concurrent;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

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

        long[] array = new long[10000];
        Random r = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt(10000);
        }
        for (int i = 0; i < 10; i++) {
            System.out.print(array[i] + " ");
        }
        forkJoinPool.invoke(new SortTask(array));
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(array[i] + " ");
        }

        System.out.println();
        IntStream.range(1, 10).forEach(e -> System.out.print(e + " "));
        System.out.println();
        IntStream.range(1, 10).parallel().forEach(e -> System.out.print(e + " "));
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

    static class SortTask extends RecursiveAction {
        final long[] array;
        final int lo, hi;

        SortTask(long[] array, int lo, int hi) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
        }

        SortTask(long[] array) {
            this(array, 0, array.length);
        }

        protected void compute() {
            if (hi - lo < THRESHOLD)
                sortSequentially(lo, hi);
            else {
                int mid = (lo + hi) >>> 1;
                invokeAll(new SortTask(array, lo, mid),
                        new SortTask(array, mid, hi));
                merge(lo, mid, hi);
            }
        }

        // implementation details follow:
        static final int THRESHOLD = 1000;

        void sortSequentially(int lo, int hi) {
            Arrays.sort(array, lo, hi);
        }

        void merge(int lo, int mid, int hi) {
            long[] buf = Arrays.copyOfRange(array, lo, mid);
            for (int i = 0, j = lo, k = mid; i < buf.length; j++)
                array[j] = (k == hi || buf[i] < array[k]) ?
                        buf[i++] : array[k++];
        }
    }
}
