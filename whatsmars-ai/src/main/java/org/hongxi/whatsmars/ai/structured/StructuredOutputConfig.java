package org.hongxi.whatsmars.ai.structured;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 结构化输出配置
 * <p>
 * 构建 {@link StructuredOutputAssistant}，使用 AiServices 编程式配置。
 * 不使用 @AiService 注解，避免自动注入 RetrievalAugmentor 污染结构化输出。
 * </p>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class StructuredOutputConfig {

    /**
     * 结构化输出助手
     * <p>
     * LangChain4j 会自动处理 POJO 返回值：
     * 1. 在 prompt 中指示 LLM 返回 JSON 格式
     * 2. 将 LLM 的 JSON 响应反序列化为对应的 record/POJO 类型
     * </p>
     */
    @Bean
    public StructuredOutputAssistant structuredOutputAssistant(ChatModel chatModel) {
        return AiServices.builder(StructuredOutputAssistant.class)
                .chatModel(chatModel)
                .build();
    }
}
