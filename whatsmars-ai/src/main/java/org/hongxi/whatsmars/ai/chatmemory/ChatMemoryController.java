package org.hongxi.whatsmars.ai.chatmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private final ChatMemoryAssistant assistant;

    public ChatMemoryController(ChatMemoryAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * 带会话记忆的流式聊天接口
     *
     * @param sessionId 会话 ID，相同 ID 共享对话上下文
     * @param message   用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(
            @RequestParam String sessionId,
            @RequestParam String message) {
        log.info("会话 [{}] 消息: {}", sessionId, message);

        SseEmitter emitter = new SseEmitter(0L);

        executor.execute(() -> {
            try {
                assistant.chat(sessionId, message)
                        .onPartialResponse(token -> {
                            try {
                                log.debug("会话 [{}] 发送 token: {}", sessionId, token);
                                emitter.send(SseEmitter.event().data(token));
                            } catch (IOException e) {
                                log.error("会话 [{}] 发送 token 失败", sessionId, e);
                                emitter.completeWithError(e);
                            }
                        })
                        .onCompleteResponse(response -> {
                            log.info("会话 [{}] 流式完成", sessionId);
                            emitter.complete();
                        })
                        .onError(error -> {
                            log.error("会话 [{}] 流式出错", sessionId, error);
                            emitter.completeWithError(error);
                        })
                        .start();
            } catch (Exception e) {
                log.error("会话 [{}] 流式异常", sessionId, e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
