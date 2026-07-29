package org.hongxi.whatsmars.spring.profile;

import org.hongxi.whatsmars.common.profile.ProfileUtils;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Demonstrates profile-specific property loading via
 * {@link PropertySourcesPlaceholderConfigurer}.
 *
 * <p>Loads {@code application-{profile}.properties} based on the
 * active profile determined by {@link ProfileUtils}.</p>
 */
@Configuration(proxyBeanMethods = false)
public class PropertyConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        String path = String.format("application-%s.properties", ProfileUtils.getProfile());
        configurer.setLocation(new ClassPathResource(path));
        return configurer;
    }
}
