package org.hongxi.whatsmars.spring.context.annotation;

import org.hongxi.whatsmars.spring.context.annotation.service.DemoService;
import org.hongxi.whatsmars.spring.model.Mars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Demonstrates:
 * <ul>
 *   <li>{@code @Configuration(proxyBeanMethods = false)} - marks this as a lite configuration class (no CGLIB proxy)</li>
 *   <li>{@code @ComponentScan} - auto-detects {@code @Service}, {@code @Repository} etc.</li>
 *   <li>{@code @Autowired} - field injection of a scanned component</li>
 *   <li>{@code @Bean} - programmatic bean definition inside a configuration class</li>
 * </ul>
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {
        "org.hongxi.whatsmars.spring.context.annotation.service",
        "org.hongxi.whatsmars.spring.context.annotation.repository"
})
public class AppConfiguration {

    @Autowired
    private DemoService demoService;

    @Bean
    public Mars mars() {
        System.out.println("[AppConfiguration] @Bean mars() creating Mars, calling service first...");
        demoService.service();
        return new Mars(1000, "火星");
    }
}
