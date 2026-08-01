package org.hongxi.whatsmars.ai.retrieval;

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
 * 基于自定义 RetrievalAugmentor 的知识库问答控制器
 * <p>
 * 演示 RetrievalAugmentor 的完整 RAG 流水线：
 * 用户提问 → 查询扩展(1→3) → 向量检索知识库 → 合并去重 → 拼接上下文 → LLM 流式生成回答
 * </p>
 * <p>
 * 测试示例：
 * <ul>
 *   <li>GET /ai/retrieval/chat?message=Spring Boot 有哪些核心特性？</li>
 *   <li>GET /ai/retrieval/chat?message=什么是 ConcurrentHashMap？</li>
 *   <li>GET /ai/retrieval/chat?message=线程池的核心参数有哪些？</li>
 * </ul>
 * </p>
 * <p>
 * 与 /ai/rag/chat 的区别：
 * 该接口使用 ExpandingQueryTransformer 将用户查询扩展为 3 条变体查询，
 * 分别检索后合并去重，通常能获得更高的召回率和更准确的回答。
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/retrieval")
public class RetrievalController {

    private static final Logger log = LoggerFactory.getLogger(RetrievalController.class);

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private final RetrievalAssistant assistant;

    public RetrievalController(RetrievalAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * 基于自定义 RetrievalAugmentor 的流式问答接口
     *
     * @param message 用户问题
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestParam String message) {
        log.info("RetrievalAugmentor 流式问答 - 问题: {}", message);

        SseEmitter emitter = new SseEmitter(0L);

        executor.execute(() -> {
            try {
                assistant.chat(message)
                        .onPartialResponse(token -> {
                            try {
                                log.debug("Retrieval 发送 token: {}", token);
                                emitter.send(SseEmitter.event().data(token));
                            } catch (IOException e) {
                                log.error("Retrieval 发送 token 失败", e);
                                emitter.completeWithError(e);
                            }
                        })
                        .onCompleteResponse(response -> {
                            log.info("RetrievalAugmentor 流式问答完成");
                            emitter.complete();
                        })
                        .onError(error -> {
                            log.error("RetrievalAugmentor 流式问答出错", error);
                            emitter.completeWithError(error);
                        })
                        .start();
            } catch (Exception e) {
                log.error("RetrievalAugmentor 流式问答异常", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
