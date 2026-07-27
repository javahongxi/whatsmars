package org.hongxi.whatsmars.ai.rag;

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
 * RAG 知识库问答控制器
 * <p>
 * 演示检索增强生成（RAG）的核心流程：
 * 用户提问 → 向量检索知识库 → 拼接上下文 → LLM 流式生成回答
 * </p>
 * <p>
 * 测试示例：
 * <ul>
 *   <li>GET /ai/rag/chat?message=Spring Boot 有哪些核心特性？</li>
 *   <li>GET /ai/rag/chat?message=什么是 ConcurrentHashMap？</li>
 *   <li>GET /ai/rag/chat?message=线程池的核心参数有哪些？</li>
 *   <li>GET /ai/rag/chat?message=阿里巴巴规范为什么不推荐用 Executors？</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/rag")
public class RagController {

    private static final Logger log = LoggerFactory.getLogger(RagController.class);

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private final RagAssistant assistant;

    public RagController(RagAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * 基于知识库的流式问答接口
     * <p>
     * 返回 text/event-stream 格式，浏览器可以实时显示 AI 逐字回复
     * </p>
     *
     * @param message 用户问题
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestParam String message) {
        log.info("RAG 流式问答 - 问题: {}", message);

        SseEmitter emitter = new SseEmitter(0L);

        executor.execute(() -> {
            try {
                assistant.chat(message)
                        .onPartialResponse(token -> {
                            try {
                                log.debug("RAG 发送 token: {}", token);
                                emitter.send(SseEmitter.event().data(token));
                            } catch (IOException e) {
                                log.error("RAG 发送 token 失败", e);
                                emitter.completeWithError(e);
                            }
                        })
                        .onCompleteResponse(response -> {
                            log.info("RAG 流式问答完成");
                            emitter.complete();
                        })
                        .onError(error -> {
                            log.error("RAG 流式问答出错", error);
                            emitter.completeWithError(error);
                        })
                        .start();
            } catch (Exception e) {
                log.error("RAG 流式问答异常", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
