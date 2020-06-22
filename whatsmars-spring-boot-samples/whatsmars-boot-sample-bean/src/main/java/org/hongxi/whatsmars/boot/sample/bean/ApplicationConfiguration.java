package org.hongxi.whatsmars.boot.sample.bean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by shenhongxi on 2020/6/22.
 */
@Configuration
public class ApplicationConfiguration {

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

    @Bean
    @Conditional(OrCondition.class)
    public ConditionalBean conditionalBean() {
        return new ConditionalBean();
    }
}
