package org.hongxi.java.util.function.predicate;

import java.util.function.Predicate;

/**
 * Created by shenhongxi on 2021/1/6.
 */
public class NumberArray {

    private int[] numbers;

    public NumberArray(int[] numbers) {
        this.numbers = numbers;
    }

    public void printIf(Predicate<Integer> filter) {
        for (int i = 0; i < numbers.length; i++) {
            if (filter.test(numbers[i])) {
                System.out.println(numbers[i]);
            }
        }
    }
}
