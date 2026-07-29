package org.hongxi.whatsmars.spring.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Demonstrates {@code @Conditional} with a custom {@link Condition} implementation.
 *
 * <p>The {@link #cacheManager()} bean is only registered when
 * {@code feature.cache.enabled=true} in the environment (e.g. application.yml).</p>
 *
 * @see OnPropertyCondition
 */
@Configuration(proxyBeanMethods = false)
@Conditional(OnPropertyCondition.class)
public class ConditionalConfig {

    @Bean
    public CacheManager cacheManager() {
        System.out.println("[ConditionalConfig] CacheManager bean registered");
        return new CacheManager();
    }

    public static class CacheManager {
        public void put(String key, Object value) {
            System.out.printf("[CacheManager] put(%s, %s)%n", key, value);
        }

        public Object get(String key) {
            System.out.printf("[CacheManager] get(%s)%n", key);
            return null;
        }

        @Override
        public String toString() {
            return "CacheManager{}";
        }
    }
}
