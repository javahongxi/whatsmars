package com.whatsmars.spring.context.annotation;

import com.whatsmars.spring.model.Mars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by javahongxi on 2017/10/31.
 */
@Configuration
public class AppConfig {

    @Bean
    public Mars mars() {
        return new Mars(1000, "火星");
    }
}
