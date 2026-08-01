package org.hongxi.whatsmars.ai.simple;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基础 AI 助手配置
 * <p>
 * 手动构建 {@link SimpleAssistant} 和 {@link StreamingAssistant}，
 * 不使用 @AiService，避免 langchain4j-spring-boot-starter 自动注入 RetrievalAugmentor，
 * 导致纯对话接口也被 RAG 知识库内容污染。
 * </p>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class SimpleConfig {

    /**
     * 单轮对话助手（同步）
     */
    @Bean
    public SimpleAssistant simpleAssistant(ChatModel chatModel) {
        return AiServices.builder(SimpleAssistant.class)
                .chatModel(chatModel)
                .build();
    }

    /**
     * 流式对话助手
     */
    @Bean
    public StreamingAssistant streamingAssistant(StreamingChatModel streamingChatModel) {
        return AiServices.builder(StreamingAssistant.class)
                .streamingChatModel(streamingChatModel)
                .build();
    }
}
