package org.hongxi.whatsmars.ai.agentic;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.agentic.supervisor.SupervisorContextStrategy;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import org.hongxi.whatsmars.ai.tool.WebSearchTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Agentic Patterns 配置类
 * <p>
 * 演示 LangChain4j 的多种 Agent 编排模式：
 * <ul>
 *   <li>Basic Agent - 单个 Agent + 工具调用</li>
 *   <li>Sequential Workflow - 顺序流水线（研究 → 摘要 → 翻译）</li>
 *   <li>Loop Workflow - 循环改进（写作 → 评审 → 达标退出）</li>
 *   <li>Parallel Workflow - 并行审查（安全 + 性能 + 最佳实践）</li>
 *   <li>Supervisor Orchestration - 监督者动态编排</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class AgenticConfig {

    /**
     * 基础 Agent：研究助手（带工具调用）
     */
    @Bean
    public ResearchAgent researchAgent(ChatModel chatModel, WebSearchTool webSearchTool) {
        return AgenticServices.agentBuilder(ResearchAgent.class)
                .chatModel(chatModel)
                .tools(webSearchTool)
                .outputKey("researchResult")
                .build();
    }

    /**
     * 顺序工作流：研究 → 摘要 → 翻译
     * <p>
     * 每个 Agent 的输出通过 AgenticScope 传递给下一个 Agent。
     * ResearchAgent 输出 researchResult → SummarizerAgent 输出 summary → TranslatorAgent 输出 translation。
     * </p>
     */
    @Bean
    public UntypedAgent sequentialWorkflow(ChatModel chatModel, WebSearchTool webSearchTool) {
        // 研究 Agent（带搜索工具）
        var research = AgenticServices.agentBuilder(ResearchAgent.class)
                .chatModel(chatModel)
                .tools(webSearchTool)
                .outputKey("researchResult")
                .build();

        // 摘要 Agent
        var summarize = AgenticServices.agentBuilder(SummarizerAgent.class)
                .chatModel(chatModel)
                .outputKey("summary")
                .build();

        // 翻译 Agent
        var translate = AgenticServices.agentBuilder(TranslatorAgent.class)
                .chatModel(chatModel)
                .outputKey("translation")
                .build();

        // 构建顺序工作流
        return AgenticServices.sequenceBuilder()
                .subAgents(research, summarize, translate)
                .outputKey("translation")
                .build();
    }

    /**
     * 循环工作流 - 写作 Agent（同步）
     */
    @Bean
    public WriterAgent writerAgent(ChatModel chatModel) {
        return AgenticServices.agentBuilder(WriterAgent.class)
                .chatModel(chatModel)
                .outputKey("document")
                .build();
    }

    /**
     * 循环工作流 - 写作 Agent（流式）
     * <p>
     * 返回 TokenStream，支持 SSE 实时流式输出写作内容。
     * </p>
     */
    @Bean
    public StreamingWriterAgent streamingWriterAgent(StreamingChatModel streamingChatModel) {
        return AgenticServices.agentBuilder(StreamingWriterAgent.class)
                .streamingChatModel(streamingChatModel)
                .outputKey("document")
                .build();
    }

    /**
     * 循环工作流 - 评审 Agent（同步）
     */
    @Bean
    public QualityReviewerAgent qualityReviewerAgent(ChatModel chatModel) {
        return AgenticServices.agentBuilder(QualityReviewerAgent.class)
                .chatModel(chatModel)
                .outputKey("review")
                .build();
    }

    /**
     * 循环工作流：写作 → 评审 → 达标退出
     * <p>
     * WriterAgent 生成文档，QualityReviewerAgent 评审并打分。
     * 当分数 > 0.7 或达到最大迭代次数时退出循环。
     * </p>
     */
    @Bean
    public UntypedAgent loopWorkflow(WriterAgent writerAgent, QualityReviewerAgent qualityReviewerAgent) {
        return AgenticServices.loopBuilder()
                .subAgents(writerAgent, qualityReviewerAgent)
                .outputKey("document")
                .exitCondition(scope -> {
                    QualityReviewerAgent.QualityReview review =
                            (QualityReviewerAgent.QualityReview) scope.readState("review");
                    return review != null && review.score() >= 0.7;
                })
                .maxIterations(3)
                .build();
    }

    /**
     * 并行工作流：安全 + 性能 + 最佳实践 三路并行审查
     * <p>
     * 三个审查 Agent 并行执行，最终通过 output 回调聚合结果。
     * </p>
     */
    @Bean
    public UntypedAgent parallelWorkflow(ChatModel chatModel) {
        var securityReviewer = AgenticServices.agentBuilder(SecurityReviewAgent.class)
                .chatModel(chatModel)
                .outputKey("securityReview")
                .build();

        var performanceReviewer = AgenticServices.agentBuilder(PerformanceReviewAgent.class)
                .chatModel(chatModel)
                .outputKey("performanceReview")
                .build();

        var bestPracticeReviewer = AgenticServices.agentBuilder(BestPracticeReviewAgent.class)
                .chatModel(chatModel)
                .outputKey("bestPracticeReview")
                .build();

        return AgenticServices.parallelBuilder()
                .subAgents(securityReviewer, performanceReviewer, bestPracticeReviewer)
                .outputKey("fullReview")
                .output(scope -> {
                    String security = (String) scope.readState("securityReview");
                    String performance = (String) scope.readState("performanceReview");
                    String bestPractice = (String) scope.readState("bestPracticeReview");
                    return """
                            === 综合代码审查报告 ===
                            
                            【安全审查】
                            %s
                            
                            【性能审查】
                            %s
                            
                            【最佳实践审查】
                            %s
                            """.formatted(security, performance, bestPractice);
                })
                .build();
    }

    /**
     * 基础 Agent（流式）：研究助手，支持 SSE 流式输出
     */
    @Bean
    public StreamingResearchAgent streamingResearchAgent(StreamingChatModel streamingChatModel, WebSearchTool webSearchTool) {
        return AgenticServices.agentBuilder(StreamingResearchAgent.class)
                .streamingChatModel(streamingChatModel)
                .tools(webSearchTool)
                .outputKey("researchResult")
                .build();
    }

    /**
     * 顺序工作流（流式）：研究 → 摘要 → 翻译
     * <p>
     * 中间步骤（研究、摘要）使用同步 ChatModel，
     * 最后一步（翻译）使用 StreamingChatModel，使最终结果支持流式输出。
     * </p>
     */
    @Bean
    public UntypedAgent streamingSequentialWorkflow(ChatModel chatModel, StreamingChatModel streamingChatModel, WebSearchTool webSearchTool) {
        var research = AgenticServices.agentBuilder(ResearchAgent.class)
                .chatModel(chatModel)
                .tools(webSearchTool)
                .outputKey("researchResult")
                .build();

        var summarize = AgenticServices.agentBuilder(SummarizerAgent.class)
                .chatModel(chatModel)
                .outputKey("summary")
                .build();

        // 最后一步使用流式 Agent，使整个工作流返回 TokenStream
        var translate = AgenticServices.agentBuilder(StreamingTranslatorAgent.class)
                .streamingChatModel(streamingChatModel)
                .outputKey("translation")
                .build();

        return AgenticServices.sequenceBuilder()
                .subAgents(research, summarize, translate)
                .outputKey("translation")
                .build();
    }

    /**
     * 监督者编排：技术顾问动态决定调用哪些专家 Agent
     * <p>
     * Supervisor 接收用户问题，自动决定调用哪些子 Agent、以什么顺序调用。
     * 与确定性工作流不同，监督者模式是动态的、由 LLM 自主决策的。
     * </p>
     */
    @Bean
    public SupervisorAgent supervisorAgent(ChatModel chatModel, WebSearchTool webSearchTool) {
        // 注册专家 Agent
        var researcher = AgenticServices.agentBuilder(ResearchAgent.class)
                .chatModel(chatModel)
                .tools(webSearchTool)
                .outputKey("researchResult")
                .build();

        var summarizer = AgenticServices.agentBuilder(SummarizerAgent.class)
                .chatModel(chatModel)
                .outputKey("summary")
                .build();

        var securityReviewer = AgenticServices.agentBuilder(SecurityReviewAgent.class)
                .chatModel(chatModel)
                .outputKey("securityReview")
                .build();

        var performanceReviewer = AgenticServices.agentBuilder(PerformanceReviewAgent.class)
                .chatModel(chatModel)
                .outputKey("performanceReview")
                .build();

        var bestPracticeReviewer = AgenticServices.agentBuilder(BestPracticeReviewAgent.class)
                .chatModel(chatModel)
                .outputKey("bestPracticeReview")
                .build();

        return AgenticServices.supervisorBuilder()
                .chatModel(chatModel)
                .subAgents(researcher, summarizer, securityReviewer, performanceReviewer, bestPracticeReviewer)
                .contextGenerationStrategy(SupervisorContextStrategy.CHAT_MEMORY_AND_SUMMARIZATION)
                .responseStrategy(SupervisorResponseStrategy.SUMMARY)
                .supervisorContext("你是一个技术顾问主管。根据用户的问题，合理调度专家团队。" +
                        "如果需要研究，先调用研究员；如果需要代码审查，调用审查专家。" +
                        "用中文回答。")
                .build();
    }
}
