package org.hongxi.whatsmars.ai.simple;

import org.hongxi.whatsmars.ai.common.SseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 流式响应控制器
 * <p>
 * 基于 TokenStream + SSE 实现真正的流式输出
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/stream")
public class StreamingController {

    private static final Logger log = LoggerFactory.getLogger(StreamingController.class);

    private final StreamingAssistant assistant;

    public StreamingController(StreamingAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * 流式对话接口
     * <p>
     * 返回 text/event-stream 格式，浏览器可以实时显示 AI 回复
     * </p>
     *
     * @param message 用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamChat(@RequestParam String message) {
        log.info("开始流式对话: {}", message);
        SseEmitter emitter = new SseEmitter(0L);
        SseHelper.stream(assistant.chat(message), emitter);
        return emitter;
    }

    /**
     * 流式对话接口（JSON 格式）
     * <p>
     * 返回 application/json 格式的流，每个元素包含 token 和时间戳
     * </p>
     *
     * @param message 用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public SseEmitter streamChatJson(@RequestParam String message) {
        log.info("开始流式对话 (JSON): {}", message);
        SseEmitter emitter = new SseEmitter(0L);
        SseHelper.stream(assistant.chat(message), emitter, TokenResponse::new);
        return emitter;
    }

    /**
     * Token 响应 DTO
     */
    public record TokenResponse(String token, long timestamp) {
        public TokenResponse(String token) {
            this(token, System.currentTimeMillis());
        }
    }
}
