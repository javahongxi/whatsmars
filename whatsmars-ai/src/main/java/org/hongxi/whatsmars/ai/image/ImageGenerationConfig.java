package org.hongxi.whatsmars.ai.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * 图片生成配置
 * <p>
 * 创建 DashScopeImageModel Bean，封装 DashScope 原生异步图片生成 API。
 * </p>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class ImageGenerationConfig {

    @Bean
    public RestClient dashScopeRestClient() {
        return RestClient.builder().build();
    }

    @Bean
    public DashScopeImageModel dashScopeImageModel(
            RestClient dashScopeRestClient,
            @Value("${langchain4j.open-ai.chat-model.api-key}") String apiKey,
            @Value("${langchain4j.open-ai.image.model:wan2.7-image-pro}") String model) {
        return new DashScopeImageModel(dashScopeRestClient, apiKey, model);
    }
}
