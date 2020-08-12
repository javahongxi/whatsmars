package org.hongxi.whatsmars.boot.sample.actuator;

import org.hongxi.whatsmars.boot.sample.actuator.filter.ActuatorFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

/**
 * Created by shenhongxi on 2020/7/17.
 */
@Configuration
public class ActuatorAutoConfiguration {

    @Order(99)
    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public WebSecurityConfigurer standardWebSecurityConfigurer() {
        return new StandardWebSecurityConfigurer();
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public ActuatorFilter actuatorFilter() {
        return new ActuatorFilter();
    }

    @Bean
    public ActuatorReporter actuatorReporter() {
        return new  ActuatorReporter();
    }
}
