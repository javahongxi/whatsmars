package org.hongxi.whatsmars.ai.chatmemory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 对话记忆 JPA Repository
 * <p>
 * 提供按 memoryId 查询和删除消息的能力，
 * 供 {@link JpaChatMemoryStore} 使用。
 * </p>
 *
 * @author hongxi
 */
public interface ChatMemoryJpaRepository extends JpaRepository<ChatMemoryEntity, Long> {

    /**
     * 按 memoryId 查询所有消息，按 messageIndex 排序
     */
    List<ChatMemoryEntity> findByMemoryIdOrderByMessageIndexAsc(String memoryId);

    /**
     * 按 memoryId 删除所有消息（清除会话时使用）
     */
    @Transactional
    void deleteByMemoryId(String memoryId);
}
