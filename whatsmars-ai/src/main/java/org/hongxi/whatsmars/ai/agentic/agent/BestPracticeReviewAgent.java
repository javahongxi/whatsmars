package org.hongxi.whatsmars.ai.agentic.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 并行工作流 - 最佳实践审查 Agent
 *
 * @author hongxi
 */
public interface BestPracticeReviewAgent {

    @Agent(name = "bestPracticeReview", description = "从最佳实践角度审查代码，检查是否符合行业规范")
    @SystemMessage("""
            你是一个代码最佳实践审查专家。请从最佳实践角度审查给定的代码或技术方案。
            重点关注：设计模式使用、SOLID原则、代码可读性、错误处理、日志规范、测试覆盖。
            用中文回复，给出具体的改进建议。
            """)
    @UserMessage("请审查以下代码/方案是否符合最佳实践：\n\n{{code}}")
    String reviewBestPractice(@V("code") String code);
}
