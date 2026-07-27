package org.hongxi.whatsmars.ai.chatmemory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 对话记忆 JPA 实体
 * <p>
 * 将 LangChain4j 的 ChatMessage 序列化为 JSON 后持久化到 PostgreSQL。
 * 每条记录对应一条聊天消息，通过 memoryId 关联到同一个会话。
 * </p>
 *
 * @author hongxi
 */
@Entity
@Table(name = "chat_memory")
public class ChatMemoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会话 ID，用于隔离不同用户/对话的上下文
     */
    @Column(name = "memory_id", nullable = false, length = 255)
    private String memoryId;

    /**
     * 消息在会话中的顺序（用于恢复时排序）
     */
    @Column(name = "message_index", nullable = false)
    private Integer messageIndex;

    /**
     * ChatMessage 序列化后的 JSON 字符串
     */
    @Column(name = "message_json", nullable = false, columnDefinition = "TEXT")
    private String messageJson;

    public ChatMemoryEntity() {
    }

    public ChatMemoryEntity(String memoryId, Integer messageIndex, String messageJson) {
        this.memoryId = memoryId;
        this.messageIndex = messageIndex;
        this.messageJson = messageJson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(String memoryId) {
        this.memoryId = memoryId;
    }

    public Integer getMessageIndex() {
        return messageIndex;
    }

    public void setMessageIndex(Integer messageIndex) {
        this.messageIndex = messageIndex;
    }

    public String getMessageJson() {
        return messageJson;
    }

    public void setMessageJson(String messageJson) {
        this.messageJson = messageJson;
    }
}
