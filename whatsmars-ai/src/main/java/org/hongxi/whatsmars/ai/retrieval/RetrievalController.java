package org.hongxi.whatsmars.ai.retrieval;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 知识库问答控制器
 * <p>
 * 演示高级 RAG 流水线：
 * 用户提问 → 查询压缩 → 智能路由 → 向量检索 → LLM 重排序 → 注入上下文 → LLM 流式生成回答
 * 回答完成后通过 SSE 发送来源列表，增强可信度。
 * </p>
 * <p>
 * 测试示例：
 * <ul>
 *   <li>GET /ai/retrieval/chat?message=Spring Boot 有哪些核心特性？</li>
 *   <li>GET /ai/retrieval/chat?message=什么是 ConcurrentHashMap？</li>
 *   <li>GET /ai/retrieval/chat?message=你好（闲聊，跳过检索）</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/retrieval")
public class RetrievalController {

    private static final Logger log = LoggerFactory.getLogger(RetrievalController.class);
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private final RetrievalAssistant assistant;

    public RetrievalController(RetrievalAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * 高级 RAG 流式问答接口
     * <p>
     * SSE 事件类型：
     * <ul>
     *   <li>默认事件：流式 token（回答内容）</li>
     *   <li>event: sources — 回答完成后的来源列表（JSON 数组）</li>
     * </ul>
     *
     * @param message 用户问题
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = "text/event-stream")
    public SseEmitter chat(@RequestParam String message) {
        log.info("高级 RAG 流式问答 - 问题: {}", message);
        SseEmitter emitter = new SseEmitter(0L);

        // 清理上一次请求的上下文
        RetrievalContext.clear();

        EXECUTOR.execute(() -> {
            try {
                assistant.chat(message)
                        .onPartialResponse(token -> {
                            try {
                                emitter.send(SseEmitter.event().data(token));
                            } catch (IOException e) {
                                log.error("发送 token 失败", e);
                                emitter.completeWithError(e);
                            }
                        })
                        .onCompleteResponse(response -> {
                            try {
                                // 回答完成后发送来源信息
                                List<SourceInfo> sources = RetrievalContext.getSources();
                                if (!sources.isEmpty()) {
                                    emitter.send(SseEmitter.event().name("sources").data(sources));
                                }
                                emitter.complete();
                            } catch (IOException e) {
                                log.error("发送来源或完成 SSE 失败", e);
                                emitter.completeWithError(e);
                            } finally {
                                RetrievalContext.clear();
                            }
                        })
                        .onError(error -> {
                            log.error("流式响应异常", error);
                            emitter.completeWithError(error);
                            RetrievalContext.clear();
                        })
                        .start();
            } catch (Exception e) {
                log.error("启动流式响应失败", e);
                emitter.completeWithError(e);
                RetrievalContext.clear();
            }
        });

        return emitter;
    }
}
