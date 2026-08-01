package org.hongxi.whatsmars.ai.retrieval;

import org.hongxi.whatsmars.ai.common.SseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 知识库问答控制器
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
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/retrieval")
public class RetrievalController {

    private static final Logger log = LoggerFactory.getLogger(RetrievalController.class);

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
    @GetMapping(value = "/chat", produces = "text/event-stream")
    public SseEmitter chat(@RequestParam String message) {
        log.info("RetrievalAugmentor 流式问答 - 问题: {}", message);
        SseEmitter emitter = new SseEmitter(0L);
        SseHelper.stream(assistant.chat(message), emitter);
        return emitter;
    }
}
