package org.hongxi.whatsmars.ai.agentic.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 条件工作流 - 医疗专家 Agent（流式）
 * <p>
 * 从医疗专业角度分析并回答用户问题，返回 {@link TokenStream} 支持 SSE 流式输出。
 * </p>
 *
 * @author hongxi
 */
public interface StreamingMedicalExpertAgent {

    @Agent(description = "医疗专家", outputKey = "response")
    @SystemMessage("""
            你是一位资深的医疗专家。请从医疗专业角度分析用户的问题，给出专业、准确的回答。
            回答应包括：可能的原因分析、建议的处理方式、何时需要就医。
            请注意：你的回答仅供参考，不能替代专业医疗诊断。
            用中文回复。
            """)
    @UserMessage("用户问题：{{request}}")
    TokenStream medical(@V("request") String request);
}
