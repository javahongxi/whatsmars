package org.hongxi.whatsmars.spring.context.annotation;

import org.hongxi.whatsmars.spring.model.Earth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Demonstrates a separate {@code @Configuration} class registered alongside
 * {@link AppConfiguration}. Shows that multiple configuration classes can
 * coexist in the same application context.
 */
@Configuration(proxyBeanMethods = false)
public class JustConfiguration {

    @Bean
    public Earth earth() {
        System.out.println("[JustConfiguration] @Bean earth() creating Earth");
        return new Earth(800, "地球");
    }
}
