package org.hongxi.whatsmars.ai.chatmemory;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于 JPA 的 ChatMemoryStore 实现
 * <p>
 * 将 LangChain4j 的 ChatMessage 序列化为 JSON 后持久化到 PostgreSQL，
 * 实现对话记忆的生产级持久化存储。
 * </p>
 * <p>
 * 工作流程：
 * <ul>
 *   <li>getMessages: 从 DB 按 memoryId 查询 → 反序列化 JSON → 返回 ChatMessage 列表</li>
 *   <li>updateMessages: 序列化 ChatMessage → 删除旧记录 → 批量写入新记录</li>
 *   <li>deleteMessages: 按 memoryId 删除所有关联记录</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
public class JpaChatMemoryStore implements ChatMemoryStore {

    private static final Logger log = LoggerFactory.getLogger(JpaChatMemoryStore.class);

    private final ChatMemoryJpaRepository repository;

    public JpaChatMemoryStore(ChatMemoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String id = String.valueOf(memoryId);
        List<ChatMemoryEntity> entities = repository.findByMemoryIdOrderByMessageIndexAsc(id);
        List<ChatMessage> messages = new ArrayList<>(entities.size());
        for (ChatMemoryEntity entity : entities) {
            messages.add(ChatMessageDeserializer.messageFromJson(entity.getMessageJson()));
        }
        log.debug("从 DB 加载会话 [{}] 的 {} 条消息", id, messages.size());
        return messages;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String id = String.valueOf(memoryId);
        // 先删除旧记录，再全量写入当前消息列表（保证与内存中的滑动窗口一致）
        repository.deleteByMemoryId(id);
        List<ChatMemoryEntity> entities = new ArrayList<>(messages.size());
        for (int i = 0; i < messages.size(); i++) {
            String json = ChatMessageSerializer.messageToJson(messages.get(i));
            entities.add(new ChatMemoryEntity(id, i, json));
        }
        repository.saveAll(entities);
        log.debug("已更新会话 [{}] 的 {} 条消息", id, messages.size());
    }

    @Override
    public void deleteMessages(Object memoryId) {
        String id = String.valueOf(memoryId);
        repository.deleteByMemoryId(id);
        log.info("已删除会话 [{}] 的所有消息", id);
    }
}
