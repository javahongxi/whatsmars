package org.hongxi.whatsmars.boot.sample.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by shenhongxi on 2020/6/8.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DemoBean demoBean() {
        return new DemoBean();
    }

    @Bean
    public DemoBeanPostProcessor demoBeanPostProcessor() {
        return new DemoBeanPostProcessor();
    }
}
