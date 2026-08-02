package org.hongxi.whatsmars.ai.guardrails;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 输出护栏示例：检查回复质量
 * <p>
 * 检查 AI 回复是否过短（可能是错误或拒绝回答），
 * 如果回复过短则触发 reprompt，要求 AI 重新生成更详细的回答。
 * </p>
 *
 * @author hongxi
 */
public class QualityOutputGuardrail implements OutputGuardrail {

    private static final Logger log = LoggerFactory.getLogger(QualityOutputGuardrail.class);

    /**
     * 最小回复长度（字符数）
     */
    private static final int MIN_LENGTH = 50;

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        String text = responseFromLLM.text();

        if (text == null || text.length() < MIN_LENGTH) {
            log.warn("AI 回复过短 ({} 字符)，触发 reprompt", text == null ? 0 : text.length());
            return reprompt(
                "回复过于简短，请提供更详细、更有价值的回答",
                "请用至少 3-4 句话详细回答用户的问题，确保提供有价值的信息。"
            );
        }

        return success();
    }
}
