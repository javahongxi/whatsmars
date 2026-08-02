package org.hongxi.whatsmars.ai.tc;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;

/**
 * 支持工具调用的 AI 助手
 * <p>
 * AI 可以根据用户问题自动调用已注册的工具：
 * <ul>
 *   <li>{@link org.hongxi.whatsmars.ai.tool.TimeTool} - 实时时间查询、日期差计算</li>
 *   <li>{@link org.hongxi.whatsmars.ai.tool.HttpRequestTool} - HTTP 请求（GET/POST）</li>
 *   <li>{@link org.hongxi.whatsmars.ai.tool.WebSearchTool} - 网络搜索（Tavily API）</li>
 *   <li>{@link org.hongxi.whatsmars.ai.tool.SystemInfoTool} - 系统环境信息查询</li>
 * </ul>
 * </p>
 * <p>
 * 设计原则：只提供 LLM 自身无法完成的能力（实时数据、网络访问、环境信息、精确计算），
 * LLM 本身就能回答的问题（如简单数学运算、常识问答）不注册为工具。
 * </p>
 *
 * @author hongxi
 */
public interface ToolCallingAssistant {

    /**
     * 与 AI 对话，AI 可以根据需要调用工具
     *
     * @param message 用户消息
     * @return AI 回复（可能包含工具调用结果）
     */
    @SystemMessage("""
            你是一个智能助手，可以根据用户的问题自动调用合适的工具来获取信息。
            你有以下工具能力：实时时间查询、日期差计算、HTTP 接口调用、网络搜索、系统环境信息查询。

            重要规则：
            1. 你的训练数据有截止日期，无法感知当前时间。搜索时效性信息时（如「最近」「最新」「当前」等），
               必须先调用时间工具获取当前完整时间，再基于准确的时间上下文构建搜索关键词。
               例如：获取到当前是 2026年7月，则搜索「2026年7月最新电影」，绝不能用训练数据中的旧时间。
            2. 涉及日期计算、当前时间等问题时，调用时间工具获取准确值。
            3. 不要编造数据，如果不确定就使用工具验证。
            4. 用中文回答。
            """)
    TokenStream chat(String message);
}
