package com.itlong.whatsmars.base;

/**
 * Created by shenhongxi on 2017/11/17.
 */
public class CodeBlockTest {

    int a = 110;

    static int b = 112;

//    static CodeBlockTest instance = new CodeBlockTest(); // 这种情况输出的是2 3 1 4

    static {
        System.out.println("1 b=" + b); // b=112
    }

    {
        System.out.println("2 a=" + a + " b=" + b); // a=110 b=112
    }

    CodeBlockTest() {
        System.out.println("3 a=" + a + " b=" + b); // a=110 b=112
    }

    public static void main(String[] args) { // 1 2 3 2 3 4
        new CodeBlockTest();
        new CodeBlockTest();
        System.out.println("4");
    }
}
