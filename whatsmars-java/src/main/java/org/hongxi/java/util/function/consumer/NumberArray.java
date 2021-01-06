package org.hongxi.java.util.function.consumer;

import java.util.function.Consumer;

/**
 * Created by shenhongxi on 2021/1/6.
 */
public class NumberArray {

    private int[] numbers;

    public NumberArray(int[] numbers) {
        this.numbers = numbers;
    }

    public void forEach(Consumer<Integer> action) {
        for (int i = 0; i < numbers.length; i++) {
            action.accept(numbers[i]);
        }
    }
}
