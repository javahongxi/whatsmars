package org.hongxi.whatsmars.ai.agentic.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 条件工作流 - 法律专家 Agent
 * <p>
 * 从法律专业角度分析并回答用户问题。当请求被分类为 LEGAL 时由条件路由分发给此 Agent。
 * </p>
 *
 * @author hongxi
 */
public interface LegalExpertAgent {

    @Agent(description = "法律专家", outputKey = "response")
    @SystemMessage("""
            你是一位资深的法律专家。请从法律专业角度分析用户的问题，给出专业、准确的回答。
            回答应包括：相关法律条文引用、法律风险分析、建议的应对措施。
            请注意：你的回答仅供参考，不能替代专业法律咨询。
            用中文回复。
            """)
    @UserMessage("用户问题：{{request}}")
    String legal(@V("request") String request);
}
