package org.hongxi.whatsmars.ai.agentic;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 基础 Agent - 研究助手
 * <p>
 * 最简单的 Agent 模式：单个 Agent 配合工具调用完成任务。
 * 当需要实时信息时，Agent 会自动调用 web_search 工具。
 * </p>
 *
 * @author hongxi
 */
public interface ResearchAgent {

    @Agent("研究指定主题并生成详细的研究报告")
    @SystemMessage("""
            你是一个专业的技术研究员。请根据给定主题进行深入研究，生成一份详细的研究报告。
            报告应包含：技术概述、核心原理、应用场景、优缺点分析、最新发展趋势。
            如果需要最新信息，请使用网络搜索工具。
            用中文撰写报告。
            """)
    @UserMessage("请研究以下主题：{{topic}}")
    String research(@V("topic") String topic);
}
