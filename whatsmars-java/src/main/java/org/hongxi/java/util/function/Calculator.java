package org.hongxi.java.util.function;

/**
 * Created by shenhongxi on 2021/1/6.
 */
@FunctionalInterface
public interface Calculator {

    int action(int... numbers);

    static Calculator adder() {
        // lambda表达式和匿名类有区别
        // lambda表达式想要替代匿名类是有条件的，即这个匿名类
        // 实现的接口必须是函数式接口，即只能有一个抽象方法的接口，
        // 如 java.lang.Runnable
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
