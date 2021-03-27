package org.hongxi.whatsmars.boot.sample.web.configure;

import org.hongxi.whatsmars.boot.sample.web.filter.FirewallFilter;
import org.hongxi.whatsmars.boot.sample.web.filter.RequestResponseWrapperFilter;
import org.hongxi.whatsmars.boot.sample.web.filter.SessionFilter;
import org.hongxi.whatsmars.boot.sample.web.filter.MonitorFilter;
import org.hongxi.whatsmars.boot.sample.web.interceptor.SessionInterceptor;
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
    public MonitorFilter uriFilter() {
        return new MonitorFilter();
    }

    @Bean
    public SessionFilter sampleSessionFilter() {
        return new SessionFilter();
    }

    @Bean
    public RequestResponseWrapperFilter sampleCryptoFilter() {
        return new RequestResponseWrapperFilter();
    }

    /**
     * interceptor 必须声明为单例
     * @return
     */
    @Bean
    public SessionInterceptor sampleSessionInterceptor() {
        return new SessionInterceptor();
    }
}
