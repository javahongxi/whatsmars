package org.hongxi.whatsmars.spring.boot.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2017/6/29.
 */
@Component
@Order(value = 1)
public class InitRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("init......");
    }
}
