package org.hongxi.whatsmars.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    
    // 用于异步处理流式响应
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private StreamingAssistant assistant;

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
        
        SseEmitter emitter = new SseEmitter(0L); // 0 表示无超时
        
        executor.execute(() -> {
            try {
                assistant.chat(message)
                        .onPartialResponse(token -> {
                            try {
                                log.debug("发送 token: {}", token);
                                emitter.send(SseEmitter.event().data(token));
                            } catch (IOException e) {
                                log.error("发送 token 失败", e);
                                emitter.completeWithError(e);
                            }
                        })
                        .onCompleteResponse(response -> {
                            log.info("流式对话完成");
                            emitter.complete();
                        })
                        .onError(error -> {
                            log.error("流式对话出错", error);
                            emitter.completeWithError(error);
                        })
                        .start();
            } catch (Exception e) {
                log.error("流式对话异常", e);
                emitter.completeWithError(e);
            }
        });
        
        return emitter;
    }

    /**
     * 流式对话接口（JSON 格式）
     * <p>
     * 返回 application/json 格式的流，每个元素包含 token 信息
     * </p>
     *
     * @param message 用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public SseEmitter streamChatJson(@RequestParam String message) {
        log.info("开始流式对话 (JSON): {}", message);
        
        SseEmitter emitter = new SseEmitter(0L);
        
        executor.execute(() -> {
            try {
                assistant.chat(message)
                        .onPartialResponse(token -> {
                            try {
                                TokenResponse response = new TokenResponse(token);
                                log.debug("发送 JSON token: {}", response.getToken());
                                emitter.send(SseEmitter.event().data(response));
                            } catch (IOException e) {
                                log.error("发送 JSON token 失败", e);
                                emitter.completeWithError(e);
                            }
                        })
                        .onCompleteResponse(response -> {
                            log.info("流式对话完成 (JSON)");
                            emitter.complete();
                        })
                        .onError(error -> {
                            log.error("流式对话出错 (JSON)", error);
                            emitter.completeWithError(error);
                        })
                        .start();
            } catch (Exception e) {
                log.error("流式对话异常 (JSON)", e);
                emitter.completeWithError(e);
            }
        });
        
        return emitter;
    }

    /**
     * Token 响应 DTO
     */
    public static class TokenResponse {
        private String token;
        private long timestamp;

        public TokenResponse(String token) {
            this.token = token;
            this.timestamp = System.currentTimeMillis();
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
