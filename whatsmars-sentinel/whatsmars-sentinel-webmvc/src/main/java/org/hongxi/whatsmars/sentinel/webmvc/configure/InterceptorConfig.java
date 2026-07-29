package org.hongxi.whatsmars.sentinel.webmvc.configure;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.SentinelWebTotalInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.config.SentinelWebMvcConfig;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.config.SentinelWebMvcTotalConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config sentinel interceptor
 */
@Configuration(proxyBeanMethods = false)
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add Sentinel interceptor
        addSpringMvcInterceptor(registry);

        // If you want to sentinel the total flow, you can add total interceptor
        addSpringMvcTotalInterceptor(registry);
    }

    private void addSpringMvcInterceptor(InterceptorRegistry registry) {
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();

        // Default handler is DefaultBlockExceptionHandler.
        // Here we just throw, so the GlobalExceptionHandler will handle it.
        config.setBlockExceptionHandler(((request, response, resourceName, e) -> {
            throw e;
        }));

        // 这两个配置项是 Sentinel 在进行 Web 请求拦截和流量控制时的核心开关，
        // 它们分别决定了资源名称的生成规则和调用链路的上下文收敛方式
        config.setHttpMethodSpecify(true);
        config.setWebContextUnify(true);

        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");
    }

    private void addSpringMvcTotalInterceptor(InterceptorRegistry registry) {
        SentinelWebMvcTotalConfig config = new SentinelWebMvcTotalConfig();
        config.setRequestAttributeName("my_sentinel_spring_mvc_total_entity_container");
        config.setTotalResourceName("my-spring-mvc-total-url-request");
        registry.addInterceptor(new SentinelWebTotalInterceptor(config)).addPathPatterns("/**");
    }
}
