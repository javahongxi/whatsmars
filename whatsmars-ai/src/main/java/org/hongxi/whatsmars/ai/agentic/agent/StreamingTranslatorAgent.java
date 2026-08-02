package org.hongxi.whatsmars.ai.agentic.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 顺序工作流 - 翻译 Agent（流式）
 * <p>
 * 作为顺序工作流的最后一步，返回 {@link TokenStream} 使整个工作流支持流式输出。
 * 中间步骤（研究、摘要）仍使用同步 ChatModel，只有最终翻译结果流式返回给用户。
 * </p>
 *
 * @author hongxi
 */
public interface StreamingTranslatorAgent {

    @Agent("将中文技术文档翻译为英文")
    @SystemMessage("""
            You are a professional technical translator.
            Translate the following Chinese technical summary into clear, professional English.
            Maintain technical accuracy and use appropriate industry terminology.
            Output only the translated text, no explanations.
            """)
    @UserMessage("请翻译以下内容：\n\n{{summary}}")
    TokenStream translate(@V("summary") String summary);
}
