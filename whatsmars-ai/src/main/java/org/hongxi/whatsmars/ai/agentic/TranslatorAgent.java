package org.hongxi.whatsmars.ai.agentic;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 顺序工作流 - 翻译 Agent
 * <p>
 * 接收摘要，翻译为英文（演示顺序工作流的最后一环）。
 * </p>
 *
 * @author hongxi
 */
public interface TranslatorAgent {

    @Agent("将中文技术文档翻译为英文")
    @SystemMessage("""
            You are a professional technical translator.
            Translate the following Chinese technical summary into clear, professional English.
            Maintain technical accuracy and use appropriate industry terminology.
            Output only the translated text, no explanations.
            """)
    @UserMessage("请翻译以下内容：\n\n{{summary}}")
    String translate(@V("summary") String summary);
}
