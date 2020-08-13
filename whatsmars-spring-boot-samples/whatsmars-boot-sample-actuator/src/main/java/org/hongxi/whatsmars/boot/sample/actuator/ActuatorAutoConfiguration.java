package org.hongxi.whatsmars.boot.sample.actuator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shenhongxi on 2020/7/17.
 */
@Configuration
public class ActuatorAutoConfiguration {

    @Bean
    public ActuatorReporter actuatorReporter() {
        return new  ActuatorReporter();
    }
}
