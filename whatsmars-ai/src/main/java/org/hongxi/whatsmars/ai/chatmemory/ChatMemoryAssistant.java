package org.hongxi.whatsmars.ai.chatmemory;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * 支持多轮对话记忆的 AI 助手
 * <p>
 * 使用 @MemoryId 注解实现会话隔离，每个 sessionId 维护独立的对话历史。
 * 不使用 @AiService 注解，而是在 ChatMemoryConfig 中通过 AiServices.builder() 手动构建，
 * 避免 langchain4j-spring-boot-starter 自动注入 RetrievalAugmentor（RAG 污染）。
 * </p>
 *
 * @author hongxi
 */
public interface ChatMemoryAssistant {

    /**
     * 带记忆的多轮对话
     *
     * @param sessionId 会话 ID，用于隔离不同用户的对话上下文
     * @param message   用户消息
     * @return AI 回复（包含上下文记忆）
     */
    @SystemMessage("你是一个友好的 Java 技术助手，能够进行多轮对话。请结合之前的对话上下文来回答用户的问题。")
    TokenStream chat(@MemoryId String sessionId, @UserMessage String message);
}
