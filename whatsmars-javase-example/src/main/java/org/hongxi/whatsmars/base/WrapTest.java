package org.hongxi.whatsmars.base;

/**
 * Created by shenhongxi on 15/7/27.
 */
public class WrapTest {

    public static void main(String[] args) {
        Integer a = new Integer(2000);
        Integer b = new Integer(2000);
        System.out.println(a.equals(b));
        System.out.println(a == b);
        System.out.println(1 << 30);
        System.out.println(Integer.MAX_VALUE);


    }
}
