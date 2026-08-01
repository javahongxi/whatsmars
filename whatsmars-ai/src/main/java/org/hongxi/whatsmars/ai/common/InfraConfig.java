package org.hongxi.whatsmars.ai.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * 共享基础设施配置
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class InfraConfig {

    /**
     * 共享的 HttpClient 实例，供各 Tool 复用
     */
    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }
}
