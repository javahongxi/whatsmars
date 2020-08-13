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
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ActuatorWebAutoConfiguration {

    @Order(99)
    @Bean
    public WebSecurityConfigurer standardWebSecurityConfigurer() {
        return new StandardWebSecurityConfigurer();
    }

    @Bean
    public ActuatorFilter actuatorFilter() {
        return new ActuatorFilter();
    }
}
