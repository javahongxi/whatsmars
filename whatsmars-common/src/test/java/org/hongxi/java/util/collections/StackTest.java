package org.hongxi.java.util.collections;

/**
 * @author shenhongxi 2019/8/12
 */
public class StackTest {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
