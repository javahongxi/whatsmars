package org.hongxi.whatsmars.boot.sample.beans.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public class EnvironmentProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("postProcessEnvironment");
        System.setProperty("mars.consumer.dubboPort", "2000");
        System.out.println(environment.getProperty("mars.consumer.dubbo-port"));
    }
}