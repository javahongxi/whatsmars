package org.hongxi.whatsmars.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * AI 配置类
 * <p>
 * 定义不同场景的 ChatClient：
 * - 默认的 chatClient 使用 application.yml 中配置的模型（qwen-plus，纯文本）
 * - visionChatClient 使用支持多模态的模型（如 qwen3.7-plus），用于图像识别
 * </p>
 *
 * @author hongxi
 */
@Configuration
public class AiConfig {

    /**
     * 标记 OpenAI ChatModel 为 Primary，解决多 Provider 共存时 ChatClient.Builder 的 Bean 歧义
     */
    @Bean
    @Primary
    public ChatModel primaryChatModel(OpenAiChatModel openAiChatModel) {
        return openAiChatModel;
    }

    /**
     * 多模态视觉 ChatClient
     * <p>
     * 使用支持视觉识别的模型，预配置 model 选项，
     * VisionService 直接注入即可，无需每次调用时覆盖模型。
     * </p>
     *
     * @param builder     ChatClient 构建器
     * @param visionModel 视觉模型名称，通过 spring.ai.vision.model 配置
     * @return 预配置多模态模型的 ChatClient
     */
    @Bean
    public ChatClient visionChatClient(ChatClient.Builder builder,
                                       @Value("${spring.ai.vision.model:qwen3.7-plus}") String visionModel) {
        return builder
                .defaultOptions(OpenAiChatOptions.builder().model(visionModel).build())
                .build();
    }
}
