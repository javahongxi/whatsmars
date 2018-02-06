package org.hongxi.whatsmars.spring.context.annotation;

import org.hongxi.whatsmars.spring.context.annotation.service.DemoService;
import org.hongxi.whatsmars.spring.model.Mars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by javahongxi on 2017/10/31.
 */
//@Configuration
@ComponentScan //("org.hongxi.whatsmars.spring.context.annotation.service")
public class AppConfig {
    @Autowired
    private DemoService demoService;

    @Bean
    public Mars mars() {
        demoService.service();
        return new Mars(1000, "火星");
    }
}
