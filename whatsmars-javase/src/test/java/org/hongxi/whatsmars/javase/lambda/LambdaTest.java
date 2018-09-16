package org.hongxi.whatsmars.javase.lambda;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by javahongxi on 2018/1/2.
 */
public class LambdaTest {

    @Test
    public void t() {
        new Thread(() -> System.out.println("Hi, I am lambda!")).start();
        System.out.println("main thread ...");
    }

    @Test
    public void t2() {
        Arrays.asList("a", "b", "c").forEach(e -> System.out.println(e));
    }

    @Test
    public void t3() {
        Arrays.asList("a", "b", "c").forEach(e -> {
            System.out.println(System.currentTimeMillis());
            System.out.println(e);
        });
    }

    @Test
    public void t4() {
        List<String> names = new ArrayList<>();
        names.add("d");
        names.add("a");
        names.add("ax");
        names.add("bbb");
        names.sort(String::compareTo);
        System.out.println(names);
    }

    @Test
    public void t5() {
        Optional<String> fullName = Optional.ofNullable(null);
        System.out.println("Full Name is set? " + fullName.isPresent());
        System.out.println("Full Name: " + fullName.orElseGet(() -> "[none]"));
        System.out.println(fullName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));
    }

    @Test
    public void t6() {
        long[] arr = new long [20000];

        Arrays.parallelSetAll(arr,
                index -> ThreadLocalRandom.current().nextInt(1000000));
        Arrays.stream(arr).limit(10).forEach(
                i -> System.out.print(i + " "));
        System.out.println();

        Arrays.parallelSort(arr);
        Arrays.stream(arr).limit(10).forEach(
                i -> System.out.print(i + " "));
        System.out.println();
    }

}
