package org.hongxi.java.util.function.consumer;

/**
 * Created by shenhongxi on 2021/1/6.
 */
public class Test {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        NumberArray numberArray = new NumberArray(numbers);

        numberArray.forEach(e -> {
            e *= 2;
            System.out.println(e);
        });

        numberArray.forEach(System.out::println);
    }
}
