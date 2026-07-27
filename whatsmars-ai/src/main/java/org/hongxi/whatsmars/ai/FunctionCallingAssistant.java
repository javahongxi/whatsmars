package org.hongxi.whatsmars.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.spring.AiService;

/**
 * 支持函数调用的 AI 助手
 * <p>
 * AI 可以自动调用 ToolService 中 marked with @Tool 的方法
 * </p>
 *
 * @author hongxi
 */
@AiService
public interface FunctionCallingAssistant {

    /**
     * 与 AI 对话，AI 可以根据需要调用工具
     *
     * @param userMessage 用户消息
     * @return AI 回复（可能包含工具调用结果）
     */
    @SystemMessage("你是一个智能助手，可以使用提供的工具来帮助用户。如果工具能提供准确信息，优先使用工具。")
    TokenStream chat(String userMessage);
}
