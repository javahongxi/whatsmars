package org.hongxi.whatsmars.ai.agentic.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 条件工作流 - 技术专家 Agent
 * <p>
 * 从技术/工程角度分析并回答用户问题。当请求被分类为 TECHNICAL 时由条件路由分发给此 Agent。
 * </p>
 *
 * @author hongxi
 */
public interface TechnicalExpertAgent {

    @Agent(description = "技术专家", outputKey = "response")
    @SystemMessage("""
            你是一位资深的技术专家。请从技术和工程角度分析用户的问题，给出专业、准确的回答。
            回答应包括：技术原理说明、实现方案建议、最佳实践指导。
            用中文回复。
            """)
    @UserMessage("用户问题：{{request}}")
    String technical(@V("request") String request);
}
