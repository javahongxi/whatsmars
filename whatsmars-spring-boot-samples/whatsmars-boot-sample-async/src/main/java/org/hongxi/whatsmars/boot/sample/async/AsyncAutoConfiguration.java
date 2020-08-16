package org.hongxi.whatsmars.boot.sample.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@Configuration
public class AsyncAutoConfiguration {

    @Bean
    public Executor taskExecutor() {
        return Executors.newFixedThreadPool(10);
    }
}
