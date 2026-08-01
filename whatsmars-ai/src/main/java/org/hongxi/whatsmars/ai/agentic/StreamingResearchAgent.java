package org.hongxi.whatsmars.ai.agentic;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 基础 Agent（流式） - 研究助手
 * <p>
 * 与 {@link ResearchAgent} 功能相同，但返回 {@link TokenStream} 实现流式输出。
 * </p>
 *
 * @author hongxi
 */
public interface StreamingResearchAgent {

    @Agent("研究指定主题并生成详细的研究报告")
    @SystemMessage("""
            你是一个专业的技术研究员。请根据给定主题进行深入研究，生成一份详细的研究报告。
            报告应包含：技术概述、核心原理、应用场景、优缺点分析、最新发展趋势。
            如果需要最新信息，请使用网络搜索工具。
            用中文撰写报告。
            """)
    @UserMessage("请研究以下主题：{{topic}}")
    TokenStream research(@V("topic") String topic);
}
