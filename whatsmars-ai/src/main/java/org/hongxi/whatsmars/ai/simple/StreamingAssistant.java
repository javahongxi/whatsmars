package org.hongxi.whatsmars.ai.simple;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;

/**
 * 支持流式响应的 AI 助手
 * <p>
 * 使用 TokenStream 实现流式输出。
 * 不使用 @AiService，在 SimpleConfig 中手动构建，避免自动注入 RAG。
 * </p>
 *
 * @author hongxi
 */
public interface StreamingAssistant {

    /**
     * 流式对话
     * <p>
     * 返回 TokenStream，可以通过 onPartialResponse/onCompleteResponse/onError 处理流式数据
     * </p>
     *
     * @param userMessage 用户消息
     * @return 流式响应
     */
    @SystemMessage("你是一个专业的 Java 技术专家，回答要简洁、准确。")
    TokenStream chat(String userMessage);
}
