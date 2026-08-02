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
            
            关于网络搜索工具的使用原则：
            - 仅当主题涉及近期事件、实时数据或你知道截止日期之后的变化时才使用搜索工具
            - 对于已有充分知识的成熟技术概念和框架特性，直接基于自身知识撰写报告即可
            - 判断标准：如果该主题在2026年之前已经广泛存在且相对稳定，则无需搜索
            
            用中文撰写报告。
            """)
    @UserMessage("请研究以下主题：{{topic}}")
    TokenStream research(@V("topic") String topic);
}
