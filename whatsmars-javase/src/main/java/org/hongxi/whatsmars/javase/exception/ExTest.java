package org.hongxi.whatsmars.javase.exception;

/**
 * Created by shenhongxi on 2018/6/1.
 */
public class ExTest {

    public static void main(String[] args) {
        System.out.println(t(-10));
    }

    private static int t(int i) {
        try {
            System.out.println("try lock");
            try {
                if (i < 0) throw new RuntimeException("i<0");
                if (i > 100) return -2;
                i++;
            } finally {
                System.out.println("release lock");
            }
            return i;
        } catch (Exception e) {
            return -1;
        }
    }
}
