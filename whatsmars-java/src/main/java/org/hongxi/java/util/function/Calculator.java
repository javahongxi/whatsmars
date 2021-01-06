package org.hongxi.java.util.function;

/**
 * Created by shenhongxi on 2021/1/6.
 */
@FunctionalInterface
public interface Calculator {

    int action(int... numbers);

    static Calculator adder() {
        return numbers -> {
            int sum = 0;
            for (Integer number : numbers) {
                sum += number;
            }
            return sum;
        };
    }

    static Calculator avg() {
        return numbers -> {
            int sum = 0;
            for (Integer number : numbers) {
                sum += number;
            }
            return sum / numbers.length;
        };
    }
}
