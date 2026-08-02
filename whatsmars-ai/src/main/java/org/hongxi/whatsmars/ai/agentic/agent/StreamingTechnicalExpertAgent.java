package org.hongxi.whatsmars.ai.agentic.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 条件工作流 - 技术专家 Agent（流式）
 * <p>
 * 从技术/工程角度分析并回答用户问题，返回 {@link TokenStream} 支持 SSE 流式输出。
 * </p>
 *
 * @author hongxi
 */
public interface StreamingTechnicalExpertAgent {

    @Agent(description = "技术专家", outputKey = "response")
    @SystemMessage("""
            你是一位资深的技术专家。请从技术和工程角度分析用户的问题，给出专业、准确的回答。
            回答应包括：技术原理说明、实现方案建议、最佳实践指导。
            用中文回复。
            """)
    @UserMessage("用户问题：{{request}}")
    TokenStream technical(@V("request") String request);
}
