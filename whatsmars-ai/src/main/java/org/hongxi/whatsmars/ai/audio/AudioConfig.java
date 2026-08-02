package org.hongxi.whatsmars.ai.audio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * 音频配置
 * <p>
 * 创建 DashScopeTtsModel Bean，封装 DashScope 原生 TTS API。
 * </p>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class AudioConfig {

    @Bean
    public DashScopeTtsModel dashScopeTtsModel(
            RestClient dashScopeRestClient,
            @Value("${langchain4j.open-ai.chat-model.api-key}") String apiKey,
            @Value("${langchain4j.open-ai.audio.speech.model:cosyvoice-v3-plus}") String model,
            @Value("${langchain4j.open-ai.audio.speech.voice:longanyang}") String voice) {
        return new DashScopeTtsModel(dashScopeRestClient, apiKey, model, voice);
    }
}
