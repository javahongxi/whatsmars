package org.hongxi.whatsmars.spring.context.annotation;

import org.hongxi.whatsmars.spring.model.Earth;
import org.springframework.context.annotation.Bean;

public class JustConfiguration {

    @Bean
    public Earth earth() {
        return new Earth(800, "地球");
    }
}
