package org.hongxi.whatsmars.ai.guardrails;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.guardrail.InputGuardrails;

/**
 * 带护栏保护的流式 AI 助手接口
 * <p>
 * 通过注解方式为接口方法绑定输入护栏：
 * <ul>
 *   <li>{@link InputGuardrails} - 在消息发送给 LLM 前执行输入检查</li>
 * </ul>
 * 返回 TokenStream 实现流式输出。
 * </p>
 *
 * @author hongxi
 */
public interface GuardrailsAssistant {

    /**
     * 进行受保护的流式对话
     * <p>
     * 输入护栏：过滤敏感词汇<br>
     * 返回 TokenStream 实现流式输出
     * </p>
     *
     * @param message 用户消息
     * @return 流式响应
     */
    @SystemMessage("你是一个专业的 Java 技术专家，回答要详细、准确、有深度。")
    @InputGuardrails(ProfanityInputGuardrail.class)
    TokenStream chat(String message);
}
