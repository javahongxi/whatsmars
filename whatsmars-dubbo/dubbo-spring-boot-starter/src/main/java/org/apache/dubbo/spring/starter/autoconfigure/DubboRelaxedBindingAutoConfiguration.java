package org.apache.dubbo.spring.starter.autoconfigure;

import org.apache.dubbo.config.spring.context.properties.DubboConfigBinder;
import org.apache.dubbo.config.spring.util.PropertySourcesUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;

import java.util.Map;

import static org.apache.dubbo.spring.starter.util.DubboUtils.*;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * Dubbo Relaxed Binding Auto-{@link Configuration} for Spring Boot 2.0
 *
 * @since 2.7.0
 */
@Configuration
@ConditionalOnProperty(prefix = DUBBO_PREFIX, name = "enabled", matchIfMissing = true)
@ConditionalOnClass(name = "org.springframework.boot.context.properties.bind.Binder")
public class DubboRelaxedBindingAutoConfiguration {

    @Bean(name = BASE_PACKAGES_PROPERTY_RESOLVER_BEAN_NAME)
    public PropertyResolver dubboScanBasePackagesPropertyResolver(ConfigurableEnvironment environment) {
        ConfigurableEnvironment propertyResolver = new AbstractEnvironment() {
            @Override
            protected void customizePropertySources(MutablePropertySources propertySources) {
                Map<String, Object> dubboScanProperties = PropertySourcesUtils.getSubProperties(environment, DUBBO_SCAN_PREFIX);
                propertySources.addLast(new MapPropertySource("dubboScanProperties", dubboScanProperties));
            }
        };
        ConfigurationPropertySources.attach(propertyResolver);
        return new DelegatingPropertyResolver(propertyResolver);
    }

    @ConditionalOnMissingBean(name = RELAXED_DUBBO_CONFIG_BINDER_BEAN_NAME, value = DubboConfigBinder.class)
    @Bean(RELAXED_DUBBO_CONFIG_BINDER_BEAN_NAME)
    @Scope(scopeName = SCOPE_PROTOTYPE)
    public DubboConfigBinder relaxedDubboConfigBinder() {
        return new RelaxedDubboConfigBinder();
    }

}