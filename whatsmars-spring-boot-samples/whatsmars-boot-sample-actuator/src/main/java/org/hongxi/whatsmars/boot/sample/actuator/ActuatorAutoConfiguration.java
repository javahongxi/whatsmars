package org.hongxi.whatsmars.boot.sample.actuator;

import org.hongxi.whatsmars.boot.sample.actuator.filter.ActuatorFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shenhongxi on 2020/7/17.
 */
@Configuration
@ConditionalOnWebApplication
public class ActuatorAutoConfiguration {

    @Bean
    public ActuatorFilter actuatorFilter() {
        return new ActuatorFilter();
    }
}
