package org.hongxi.whatsmars.ai.chatmemory;

import org.hongxi.whatsmars.ai.common.SseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 多轮对话控制器
 * <p>
 * 演示基于 @MemoryId 的会话记忆能力，流式输出。
 * 同一个 sessionId 的多次请求会保留上下文，实现连续对话。
 * </p>
 * <p>
 * 测试示例（使用相同 sessionId 进行多轮对话）：
 * <ul>
 *   <li>GET /ai/memory/chat?sessionId=user1&message=你好，我叫张三</li>
 *   <li>GET /ai/memory/chat?sessionId=user1&message=我叫什么名字？</li>
 *   <li>GET /ai/memory/chat?sessionId=user2&message=你好，我叫李四</li>
 *   <li>GET /ai/memory/chat?sessionId=user2&message=我叫什么名字？</li>
 * </ul>
 * 不同 sessionId 的对话互相隔离
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/memory")
public class ChatMemoryController {

    private static final Logger log = LoggerFactory.getLogger(ChatMemoryController.class);

    private final ChatMemoryAssistant assistant;
    private final ChatMemoryJpaRepository chatMemoryJpaRepository;

    public ChatMemoryController(ChatMemoryAssistant assistant, ChatMemoryJpaRepository chatMemoryJpaRepository) {
        this.assistant = assistant;
        this.chatMemoryJpaRepository = chatMemoryJpaRepository;
    }

    /**
     * 带会话记忆的流式聊天接口
     *
     * @param sessionId 会话 ID，相同 ID 共享对话上下文
     * @param message   用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = "text/event-stream")
    public SseEmitter chat(
            @RequestParam String sessionId,
            @RequestParam String message) {
        log.info("会话 [{}] 消息: {}", sessionId, message);
        SseEmitter emitter = new SseEmitter(0L);
        SseHelper.stream(assistant.chat(sessionId, message), emitter);
        return emitter;
    }

    /**
     * 清除指定会话的所有对话记忆
     *
     * @param sessionId 会话 ID
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<String> deleteMemory(@RequestParam String sessionId) {
        log.info("清除会话 [{}] 的对话记忆", sessionId);
        chatMemoryJpaRepository.deleteByMemoryId(sessionId);
        return ResponseEntity.ok("会话 [" + sessionId + "] 已清空");
    }
}
