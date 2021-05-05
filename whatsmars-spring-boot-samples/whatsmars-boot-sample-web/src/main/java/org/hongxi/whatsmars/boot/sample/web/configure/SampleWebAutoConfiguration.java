package org.hongxi.whatsmars.boot.sample.web.configure;

import org.hongxi.whatsmars.boot.sample.web.interceptor.SessionInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SampleWebAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
                .excludePathPatterns("/health", "/error/**", "/actuator/**")
                .order(-100);
    }
}
