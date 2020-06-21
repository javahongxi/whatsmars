package org.hongxi.whatsmars.boot.sample.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * Created by shenhongxi on 2020/6/8.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Order(1)
    @ConditionalOnMissingBean
    @Bean
    public DemoBean demoBean() {
        return new DemoBean();
    }

    @Bean
    public DemoBean demoBean2() {
        return new DemoBean();
    }

    @Bean
    public OrderedDemoBean orderedDemoBean() {
        return new OrderedDemoBean();
    }

    @Bean
    public DemoBeanPostProcessor demoBeanPostProcessor() {
        return new DemoBeanPostProcessor();
    }
}
