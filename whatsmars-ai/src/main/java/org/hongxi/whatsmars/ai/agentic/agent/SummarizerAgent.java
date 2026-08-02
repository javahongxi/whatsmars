package org.hongxi.whatsmars.ai.agentic.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 顺序工作流 - 摘要提取 Agent
 * <p>
 * 接收研究报告，生成精炼摘要。
 * 在顺序工作流中，它的输入 {{researchResult}} 来自上游 Agent 的输出。
 * </p>
 *
 * @author hongxi
 */
public interface SummarizerAgent {

    @Agent("对研究报告进行摘要提取")
    @SystemMessage("""
            你是一个专业的技术文档摘要专家。请对给定的研究报告提取关键信息，生成一份精炼的摘要。
            摘要应包含：核心要点（3-5条）、关键数据、重要结论。
            保持简洁，摘要长度控制在原文的 20% 以内。
            """)
    @UserMessage("请对以下报告进行摘要：\n\n{{researchResult}}")
    String summarize(@V("researchResult") String researchResult);
}
