package org.hongxi.whatsmars.boot.sample.web.configure;

import org.hongxi.whatsmars.boot.sample.web.filter.FirewallFilter;
import org.hongxi.whatsmars.boot.sample.web.filter.SampleWrapperFilter;
import org.hongxi.whatsmars.boot.sample.web.filter.SampleSessionFilter;
import org.hongxi.whatsmars.boot.sample.web.interceptor.SampleSessionInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.hongxi.whatsmars.boot.sample.web.constants.Constants.EXCLUDE_PATHS;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SampleWebAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sampleSessionInterceptor())
                .excludePathPatterns(Arrays.asList(EXCLUDE_PATHS))
                .order(-100);
    }

    @Bean
    public FirewallFilter firewallFilter() {
        return new FirewallFilter();
    }

    @Bean
    public SampleSessionFilter sampleSessionFilter() {
        return new SampleSessionFilter();
    }

    @Bean
    public SampleWrapperFilter sampleCryptoFilter() {
        return new SampleWrapperFilter();
    }

    /**
     * interceptor 必须声明为单例
     * @return
     */
    @Bean
    public SampleSessionInterceptor sampleSessionInterceptor() {
        return new SampleSessionInterceptor();
    }
}
