package org.hongxi.java.util.function;

/**
 * Created by shenhongxi on 2021/1/6.
 */
public class CalculatorTest {

    public static void main(String[] args) {
        int r = Calculator.adder().action(1, 2, 3, 4, 5);
        System.out.println(r);
        r = Calculator.avg().action(1, 2 ,3, 4, 5);
        System.out.println(r);
    }
}
