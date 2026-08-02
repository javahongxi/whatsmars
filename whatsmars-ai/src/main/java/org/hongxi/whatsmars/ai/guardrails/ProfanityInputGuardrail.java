package org.hongxi.whatsmars.ai.guardrails;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 输入护栏示例：过滤敏感词汇
 * <p>
 * 检查用户消息是否包含敏感词汇，如果包含则拒绝请求。
 * 这是一个简单的关键词匹配示例，实际生产环境可以使用更复杂的 NLP 模型。
 * </p>
 *
 * @author hongxi
 */
public class ProfanityInputGuardrail implements InputGuardrail {

    private static final Logger log = LoggerFactory.getLogger(ProfanityInputGuardrail.class);

    /**
     * 敏感词列表（示例）
     */
    private static final String[] BLOCKED_WORDS = {"fuck", "shit", "damn", "ass"};

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        String text = userMessage.singleText().toLowerCase();

        for (String word : BLOCKED_WORDS) {
            if (text.contains(word)) {
                log.warn("检测到敏感词汇: {}", word);
                return failure("消息包含不当内容，请重新表述您的问题");
            }
        }

        return success();
    }
}
