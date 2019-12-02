package org.hongxi.whatsmars.spring.profile;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertyConfiguration {

    @Bean
    public PropertyPlaceholderConfigurer configurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        String path = String.format("application-%s.properties", ProfileUtils.getProfile());
        configurer.setLocation(new ClassPathResource(path));
        return configurer;
    }
}
