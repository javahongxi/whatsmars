package org.hongxi.whatsmars.ai.simple;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * 基础 AI 助手接口
 * <p>
 * 简单的单轮对话示例，不带上下文记忆
 * </p>
 *
 * @author hongxi
 */
@AiService
public interface SimpleAssistant {

    /**
     * 进行单轮对话
     *
     * @param userMessage 用户消息
     * @return AI 回复
     */
    @SystemMessage("你是一个专业的 Java 技术专家，回答要简洁、准确。")
    String chat(String userMessage);
}
