package org.hongxi.java.util.concurrent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author shenhongxi 2019-09-01
 */
public class ThreadLocalRandomTest {

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }
}
