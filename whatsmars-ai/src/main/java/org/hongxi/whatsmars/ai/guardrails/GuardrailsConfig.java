package org.hongxi.whatsmars.ai.guardrails;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 护栏配置
 * <p>
 * 演示护栏配置方式：在接口方法上使用 {@code @InputGuardrails} 注解，
 * 通过 {@link AiServices#builder} 构建流式 AI 助手。
 * </p>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class GuardrailsConfig {

    /**
     * 构建带护栏的流式 AI 助手
     * <p>
     * 使用 {@link AiServices#builder} 手动构建，
     * 护栏通过接口注解自动绑定，无需在 builder 中重复配置。
     * </p>
     */
    @Bean
    public GuardrailsAssistant guardrailsAssistant(StreamingChatModel streamingChatModel) {
        return AiServices.builder(GuardrailsAssistant.class)
                .streamingChatModel(streamingChatModel)
                .build();
    }
}
