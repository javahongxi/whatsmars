package org.hongxi.whatsmars.boot.sample.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by shenhongxi on 2022/1/5.
 */
@Component
public class SimpleJob {

    @Scheduled(cron = "0/1 * * * * ?")
    public void time() {
        System.out.println(System.currentTimeMillis());
    }

    @Scheduled(cron = "0/1 * * * * ?")
    public void uuid() {
        System.out.println(UUID.randomUUID());
    }
}
