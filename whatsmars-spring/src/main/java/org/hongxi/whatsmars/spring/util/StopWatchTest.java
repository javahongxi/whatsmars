package org.hongxi.whatsmars.spring.util;

import org.springframework.util.StopWatch;

/**
 * Created by shenhongxi on 2019/12/16.
 */
public class StopWatchTest {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch("Test");
        int n = 0;
        stopWatch.start("task1");
        for (int i = 0; i < 100000; i++) {
            n++;
        }
        stopWatch.stop();
        stopWatch.start("task2");
        for (int i = 0; i < 1000000; i++) {
            n++;
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.shortSummary());
        System.out.println(stopWatch);
    }
}
