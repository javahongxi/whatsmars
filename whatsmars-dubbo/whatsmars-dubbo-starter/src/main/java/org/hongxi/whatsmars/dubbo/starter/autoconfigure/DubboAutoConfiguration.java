
package org.hongxi.whatsmars.dubbo.starter.autoconfigure;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import com.alibaba.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.DubboConfigConfiguration;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Set;

import static org.hongxi.whatsmars.dubbo.starter.util.DubboUtils.*;
import static java.util.Collections.emptySet;

/**
 * Dubbo Auto {@link Configuration}
 *
 * @see ApplicationConfig
 * @see Service
 * @see Reference
 * @see DubboComponentScan
 * @see EnableDubboConfig
 * @see EnableDubbo
 */
@Configuration
@ConditionalOnProperty(prefix = DUBBO_PREFIX, name = "enabled", matchIfMissing = true, havingValue = "true")
@ConditionalOnClass(AbstractConfig.class)
@EnableConfigurationProperties(value = {DubboScanProperties.class, DubboConfigProperties.class})
public class DubboAutoConfiguration {

    /**
     * Single Dubbo Config Configuration
     *
     * @see EnableDubboConfig
     * @see DubboConfigConfiguration.Single
     */
    @ConditionalOnProperty(name = MULTIPLE_CONFIG_PROPERTY_NAME, havingValue = "false", matchIfMissing = true)
    @EnableDubboConfig
    @EnableConfigurationProperties(SingleDubboConfigBindingProperties.class)
    protected static class SingleDubboConfigConfiguration {
    }

    /**
     * Multiple Dubbo Config Configuration , equals {@link EnableDubboConfig#multiple()} == <code>true</code>
     *
     * @see EnableDubboConfig#multiple()
     * @see DubboConfigConfiguration.Multiple
     */
    @ConditionalOnProperty(name = MULTIPLE_CONFIG_PROPERTY_NAME, havingValue = "true")
    @EnableDubboConfig(multiple = true)
    @EnableConfigurationProperties(MultipleDubboConfigBindingProperties.class)
    protected static class MultipleDubboConfigConfiguration {
    }

    /**
     * Creates {@link ServiceAnnotationBeanPostProcessor} Bean
     *
     * @return {@link ServiceAnnotationBeanPostProcessor}
     */
    @ConditionalOnProperty(name = BASE_PACKAGES_PROPERTY_NAME)
    @Autowired
    @Bean
    public ServiceAnnotationBeanPostProcessor serviceAnnotationBeanPostProcessor(Environment environment) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment);
        Set<String> packagesToScan = resolver.getProperty(BASE_PACKAGES_PROPERTY_NAME, Set.class, emptySet());
        return new ServiceAnnotationBeanPostProcessor(packagesToScan);
    }

    /**
     * Creates {@link ReferenceAnnotationBeanPostProcessor} Bean if Absent
     *
     * @return {@link ReferenceAnnotationBeanPostProcessor}
     */
    @ConditionalOnMissingBean
    @Bean(name = ReferenceAnnotationBeanPostProcessor.BEAN_NAME)
    public ReferenceAnnotationBeanPostProcessor referenceAnnotationBeanPostProcessor() {
        return new ReferenceAnnotationBeanPostProcessor();
    }

}
