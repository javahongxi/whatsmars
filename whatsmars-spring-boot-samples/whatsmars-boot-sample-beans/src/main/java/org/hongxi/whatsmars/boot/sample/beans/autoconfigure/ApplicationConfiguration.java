package org.hongxi.whatsmars.boot.sample.beans.autoconfigure;

import org.hongxi.whatsmars.boot.sample.beans.*;
import org.hongxi.whatsmars.boot.sample.beans.condition.OrCondition;
import org.hongxi.whatsmars.boot.sample.beans.postprocessor.DemoBeanPostProcessor;
import org.hongxi.whatsmars.boot.sample.beans.register.PlanetBeanDefinitionRegistryPostProcessor;
import org.hongxi.whatsmars.boot.sample.beans.register.PlanetBeanDefinitionRegistryPostProcessor2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

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

    @Bean
    @ConditionalOnProperty(name = "planet.names[0]")
    public PlanetBeanDefinitionRegistryPostProcessor planetBeanDefinitionRegistryPostProcessor(Environment environment) {
        return new PlanetBeanDefinitionRegistryPostProcessor(parseNames(environment));
    }

    @Bean
    @ConditionalOnProperty(name = "planet.names[0]")
    public PlanetBeanDefinitionRegistryPostProcessor2 planetBeanDefinitionRegistryPostProcessor2(Environment environment) {
        return new PlanetBeanDefinitionRegistryPostProcessor2(parseNames(environment));
    }

    public List<String> parseNames(Environment environment) {
        List<String> names = new ArrayList<>();
        String configsKey = "planet.names[%d]";
        int configIndex = 0;
        String name;
        while ((name = environment.getProperty(String.format(configsKey, configIndex++))) != null) {
            names.add(name);
        }
        return names;
    }
}
