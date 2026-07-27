package org.hongxi.whatsmars.ai.chatmemory;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对话记忆配置
 * <p>
 * 配置 ChatMemoryProvider，为每个会话维护独立的对话历史。
 * langchain4j-spring-boot-starter 检测到该 Bean 后，
 * 会自动为使用 @MemoryId 的 @AiService 接口提供记忆能力。
 * </p>
 * <p>
 * 使用 {@link JpaChatMemoryStore} 将对话消息持久化到 PostgreSQL，
 * 服务重启后对话历史不会丢失。
 * </p>
 *
 * @author hongxi
 */
@Configuration
public class ChatMemoryConfig {

    /**
     * 基于 JPA 的持久化存储
     */
    @Bean
    public ChatMemoryStore chatMemoryStore(ChatMemoryJpaRepository repository) {
        return new JpaChatMemoryStore(repository);
    }

    /**
     * 对话记忆提供者
     * <p>
     * 为每个 memoryId 创建独立的 MessageWindowChatMemory，
     * 保留最近 20 条消息作为上下文窗口，
     * 底层通过 JpaChatMemoryStore 持久化到 PostgreSQL。
     * </p>
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider(ChatMemoryStore chatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .chatMemoryStore(chatMemoryStore)
                .build();
    }
}
