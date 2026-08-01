package org.hongxi.whatsmars.ai.agentic;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 并行工作流 - 安全审查 Agent
 * <p>
 * 专注于代码/架构的安全性分析，在并行工作流中与其他审查 Agent 同时运行。
 * </p>
 *
 * @author hongxi
 */
public interface SecurityReviewAgent {

    @Agent(name = "securityReview", description = "从安全角度审查代码，识别潜在安全风险")
    @SystemMessage("""
            你是一个代码安全审查专家。请从安全角度审查给定的代码或技术方案。
            重点关注：注入攻击、认证授权漏洞、数据泄露风险、依赖安全、敏感信息暴露。
            用中文回复，给出具体的安全问题和修复建议。
            """)
    @UserMessage("请审查以下代码/方案的安全性：\n\n{{code}}")
    String reviewSecurity(@V("code") String code);
}
