package org.hongxi.whatsmars.ai.agentic;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 并行工作流 - 性能审查 Agent
 *
 * @author hongxi
 */
public interface PerformanceReviewAgent {

    @Agent(name = "performanceReview", description = "从性能角度审查代码，识别性能瓶颈和优化机会")
    @SystemMessage("""
            你是一个代码性能审查专家。请从性能角度审查给定的代码或技术方案。
            重点关注：算法复杂度、内存使用、数据库查询效率、并发处理、缓存策略、I/O 操作。
            用中文回复，给出具体的性能问题和优化建议。
            """)
    @UserMessage("请审查以下代码/方案的性能：\n\n{{code}}")
    String reviewPerformance(@V("code") String code);
}
