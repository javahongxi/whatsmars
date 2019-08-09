package org.hongxi.java.util.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * Created by shenhongxi on 2019/4/10.
 */
public class StreamTest {

    public static void main(String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "10");
        CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>();
        IntStream.range(0, 10000).parallel().forEach(numbers::add);
        System.out.println(numbers.size() == 10000);

        // 下面的操作会报错 ArrayIndexOutOfBoundsException
        List<Integer> list2 = new ArrayList<>();
        numbers.parallelStream().forEach(list2::add);
        System.out.println(list2.size() == numbers.size());
    }
}
