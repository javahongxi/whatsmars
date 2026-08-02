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
            
            关于网络搜索工具的使用原则：
            - 仅当主题涉及近期事件、实时数据或你知道截止日期之后的变化时才使用搜索工具
            - 对于已有充分知识的成熟技术概念和框架特性，直接基于自身知识撰写报告即可
            - 判断标准：如果该主题在2026年之前已经广泛存在且相对稳定，则无需搜索
            
            用中文撰写报告。
            """)
    @UserMessage("请研究以下主题：{{topic}}")
    String research(@V("topic") String topic);
}
