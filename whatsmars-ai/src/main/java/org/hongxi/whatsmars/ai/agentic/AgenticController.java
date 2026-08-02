package org.hongxi.whatsmars.ai.agentic;

import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.service.TokenStream;
import org.hongxi.whatsmars.ai.common.SseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Agentic Patterns 控制器
 * <p>
 * 提供 5 种 Agent 编排模式的 REST API，每种模式提供同步和流式（SSE）端点：
 * <ul>
 *   <li>POST /ai/agentic/basic           - 基础 Agent（同步）</li>
 *   <li>GET  /ai/agentic/basic/stream    - 基础 Agent（流式）</li>
 *   <li>POST /ai/agentic/sequential      - 顺序工作流（同步）</li>
 *   <li>GET  /ai/agentic/sequential/stream - 顺序工作流（流式）</li>
 *   <li>POST /ai/agentic/loop            - 循环工作流（同步）</li>
 *   <li>GET  /ai/agentic/loop/stream     - 循环工作流（SSE 进度事件 + 最终文档）</li>
 *   <li>POST /ai/agentic/parallel        - 并行工作流（同步）</li>
 *   <li>POST /ai/agentic/supervisor      - 监督者编排（同步）</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/agentic")
public class AgenticController {

    private static final Logger log = LoggerFactory.getLogger(AgenticController.class);
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private final ResearchAgent researchAgent;
    private final StreamingResearchAgent streamingResearchAgent;
    private final UntypedAgent sequentialWorkflow;
    private final UntypedAgent streamingSequentialWorkflow;
    private final UntypedAgent loopWorkflow;
    private final UntypedAgent parallelWorkflow;
    private final SupervisorAgent supervisorAgent;
    private final WriterAgent writerAgent;
    private final QualityReviewerAgent qualityReviewerAgent;

    public AgenticController(ResearchAgent researchAgent,
                             StreamingResearchAgent streamingResearchAgent,
                             UntypedAgent sequentialWorkflow,
                             UntypedAgent streamingSequentialWorkflow,
                             UntypedAgent loopWorkflow,
                             UntypedAgent parallelWorkflow,
                             SupervisorAgent supervisorAgent,
                             WriterAgent writerAgent,
                             QualityReviewerAgent qualityReviewerAgent) {
        this.researchAgent = researchAgent;
        this.streamingResearchAgent = streamingResearchAgent;
        this.sequentialWorkflow = sequentialWorkflow;
        this.streamingSequentialWorkflow = streamingSequentialWorkflow;
        this.loopWorkflow = loopWorkflow;
        this.parallelWorkflow = parallelWorkflow;
        this.supervisorAgent = supervisorAgent;
        this.writerAgent = writerAgent;
        this.qualityReviewerAgent = qualityReviewerAgent;
    }

    /**
     * 基础 Agent：单 Agent + 工具调用
     * <p>
     * Agent 可以自动调用 web_search 工具获取实时信息。
     * </p>
     *
     * @param message 研究主题
     * @return 研究报告
     */
    @PostMapping("/basic")
    public String basicAgent(@RequestParam String message) {
        return researchAgent.research(message);
    }

    /**
     * 顺序工作流：研究 → 摘要 → 翻译
     * <p>
     * 三个 Agent 依次执行，每个 Agent 的输出作为下一个 Agent 的输入。
     * </p>
     *
     * @param message 研究主题
     * @return 最终翻译结果
     */
    @PostMapping("/sequential")
    public String sequentialWorkflow(@RequestParam String message) {
        return (String) sequentialWorkflow.invoke(Map.of("topic", message));
    }

    /**
     * 循环工作流：写作 → 评审 → 达标退出
     * <p>
     * WriterAgent 和 QualityReviewerAgent 循环执行，
     * 当评审分数 >= 0.7 或达到最大迭代次数（3次）时退出。
     * </p>
     *
     * @param message 写作主题
     * @return 最终文档
     */
    @PostMapping("/loop")
    public String loopWorkflow(@RequestParam String message) {
        return (String) loopWorkflow.invoke(Map.of("topic", message, "feedback", "无"));
    }

    /**
     * 并行工作流：安全 + 性能 + 最佳实践 三路并行审查
     * <p>
     * 三个审查 Agent 并行执行，最终聚合为一份综合审查报告。
     * </p>
     *
     * @param message 待审查的代码
     * @return 综合审查报告
     */
    @PostMapping("/parallel")
    public String parallelWorkflow(@RequestParam String message) {
        return (String) parallelWorkflow.invoke(Map.of("code", message));
    }

    /**
     * 监督者编排：动态调度专家 Agent
     * <p>
     * Supervisor 根据用户问题自动决定调用哪些专家、以什么顺序调用。
     * 与确定性工作流不同，监督者模式是动态的、由 LLM 自主决策的。
     * 注：当前 SupervisorAgent 不支持流式，仅提供同步接口。
     * </p>
     *
     * @param message 用户问题
     * @return 监督者的综合回复
     */
    @PostMapping("/supervisor")
    public String supervisorAgent(@RequestParam String message) {
        return supervisorAgent.invoke(message);
    }

    // ==================== 流式 SSE 端点 ====================

    /**
     * 基础 Agent（流式）：单 Agent + 工具调用，SSE 流式输出
     *
     * @param message 研究主题
     */
    @PostMapping(value = "/basic/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamBasicAgent(@RequestParam String message) {
        log.info("流式基础 Agent，message: {}", message);
        SseEmitter emitter = new SseEmitter(0L);
        TokenStream tokenStream = streamingResearchAgent.research(message);
        SseHelper.stream(tokenStream, emitter);
        return emitter;
    }

    /**
     * 顺序工作流（流式）：研究 → 摘要 → 翻译，最终翻译结果 SSE 流式输出
     * <p>
     * 中间步骤（研究、摘要）同步执行，最后一步（翻译）流式返回。
     * </p>
     *
     * @param message 研究主题
     */
    @PostMapping(value = "/sequential/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSequentialWorkflow(@RequestParam String message) {
        log.info("流式顺序工作流，message: {}", message);
        SseEmitter emitter = new SseEmitter(0L);
        TokenStream tokenStream = (TokenStream) streamingSequentialWorkflow.invoke(Map.of("topic", message));
        SseHelper.stream(tokenStream, emitter);
        return emitter;
    }

    /**
     * 循环工作流（SSE）：每轮迭代发送进度事件，最终发送完整文档
     * <p>
     * SSE 事件格式：
     * <ul>
     *   <li>event: iteration，data: {"iteration": 1, "status": "writing"}</li>
     *   <li>event: iteration，data: {"iteration": 1, "status": "reviewing"}</li>
     *   <li>event: review，data: {"score": 0.8, "feedback": "..."}</li>
     *   <li>event: document，data: 最终文档内容</li>
     * </ul>
     * </p>
     *
     * @param message 写作主题
     */
    @PostMapping(value = "/loop/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamLoopWorkflow(@RequestParam String message) {
        log.info("流式循环工作流，message: {}", message);
        SseEmitter emitter = new SseEmitter(0L);

        EXECUTOR.execute(() -> {
            try {
                String feedback = "无";
                String document = null;
                int maxIterations = 3;

                for (int i = 1; i <= maxIterations; i++) {
                    // 发送写作进度事件
                    emitter.send(SseEmitter.event().name("iteration")
                            .data(Map.of("iteration", i, "status", "writing")));
                    document = writerAgent.writeDocument(message, feedback);

                    // 发送评审进度事件
                    emitter.send(SseEmitter.event().name("iteration")
                            .data(Map.of("iteration", i, "status", "reviewing")));
                    QualityReviewerAgent.QualityReview review = qualityReviewerAgent.reviewDocument(document);

                    // 发送评审结果事件
                    emitter.send(SseEmitter.event().name("review")
                            .data(Map.of("score", review.score(), "feedback", review.feedback())));

                    if (review.score() >= 0.7) {
                        break;
                    }
                    feedback = review.feedback();
                }

                // 发送最终文档
                emitter.send(SseEmitter.event().name("document").data(document));
                emitter.complete();
            } catch (Exception e) {
                log.error("流式循环工作流异常", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
