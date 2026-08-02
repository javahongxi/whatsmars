package org.hongxi.whatsmars.ai.agentic.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 条件工作流 - 法律专家 Agent（流式）
 * <p>
 * 从法律专业角度分析并回答用户问题，返回 {@link TokenStream} 支持 SSE 流式输出。
 * </p>
 *
 * @author hongxi
 */
public interface StreamingLegalExpertAgent {

    @Agent(description = "法律专家", outputKey = "response")
    @SystemMessage("""
            你是一位资深的法律专家。请从法律专业角度分析用户的问题，给出专业、准确的回答。
            回答应包括：相关法律条文引用、法律风险分析、建议的应对措施。
            请注意：你的回答仅供参考，不能替代专业法律咨询。
            用中文回复。
            """)
    @UserMessage("用户问题：{{request}}")
    TokenStream legal(@V("request") String request);
}
