package org.hongxi.java.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by shenhongxi on 2021/1/6.
 */
public class FunctionTest {

    public static void main(String[] args) {
        Function<Integer, Integer> f = x -> x * 2;
        System.out.println("1*2=" + f.apply(1));

        BiFunction<Integer, Integer, Integer> f2 = (x, y) -> x - y;
        BiFunction<Integer, Integer, Integer> f3 = Integer::sum;
        BiFunction<Integer, Integer, Integer> f4 = FunctionTest::sum;
        System.out.println("3-1=" + f2.apply(3, 1));
        System.out.println("1+2=" + f3.apply(1, 2));
        System.out.println("(1+2)*2=" + f4.andThen(f).apply(1, 2));
    }

    public static int sum(int a, int b) {
        return a + b;
    }
}
