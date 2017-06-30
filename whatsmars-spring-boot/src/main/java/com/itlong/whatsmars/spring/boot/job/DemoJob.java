package com.itlong.whatsmars.spring.boot.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2017/6/30.
 */
@Component
public class DemoJob {

    @Scheduled(cron = "*/1 * * * * ?")
    public void execute() {
        System.out.println(System.currentTimeMillis());
    }
}
