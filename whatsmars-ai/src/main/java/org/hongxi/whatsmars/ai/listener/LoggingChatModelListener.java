package org.hongxi.whatsmars.ai.listener;

import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ChatModel 监听器示例
 * <p>
 * 通过实现 {@link ChatModelListener} 接口，监听所有 ChatModel 的请求、响应和异常事件。
 * 注册为 Spring Bean 后，langchain4j-open-ai-spring-boot-starter 会自动将其注入到所有 ChatModel 中。
 * </p>
 * <p>
 * 典型用途：
 * <ul>
 *     <li>记录请求/响应日志（可观测性）</li>
 *     <li>统计 Token 消耗（成本监控）</li>
 *     <li>记录请求耗时（性能分析）</li>
 *     <li>通过 attributes 在多个 Listener 之间传递数据</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@Component
public class LoggingChatModelListener implements ChatModelListener {

    private static final Logger log = LoggerFactory.getLogger(LoggingChatModelListener.class);

    private static final String ATTR_START_TIME = "startTime";

    /**
     * 请求发送前回调
     * <p>
     * 可在此记录请求开始时间，用于计算耗时。
     * 通过 attributes 传递数据给 onResponse / onError。
     * </p>
     */
    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        long startTime = System.currentTimeMillis();
        requestContext.attributes().put(ATTR_START_TIME, startTime);

        log.info("[LLM Request] provider={}, messages={}",
                requestContext.modelProvider(),
                requestContext.chatRequest().messages().size());

        // 打印消息角色分布
        requestContext.chatRequest().messages().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        msg -> msg.type().name(), java.util.stream.Collectors.counting()))
                .forEach((type, count) -> log.debug("  {} : {} messages", type, count));
    }

    /**
     * 响应接收后回调
     * <p>
     * 可在此记录响应内容、Token 用量和请求耗时。
     * </p>
     */
    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        ChatResponse chatResponse = responseContext.chatResponse();

        // Token 用量统计
        String tokenInfo = "N/A";
        if (chatResponse.tokenUsage() != null) {
            TokenUsage usage = chatResponse.tokenUsage();
            tokenInfo = String.format("prompt=%d, completion=%d, total=%d",
                    usage.inputTokenCount(),
                    usage.outputTokenCount(),
                    usage.totalTokenCount());
        }

        // 计算请求耗时
        long elapsed = System.currentTimeMillis()
                - (long) responseContext.attributes().getOrDefault(ATTR_START_TIME, System.currentTimeMillis());

        log.info("[LLM Response] provider={}, tokens=[{}], elapsed={}ms",
                responseContext.modelProvider(),
                tokenInfo,
                elapsed);
        log.debug("  AI : {}", truncate(chatResponse.aiMessage().text(), 500));
    }

    /**
     * 交互异常时回调
     * <p>
     * 可在此记录错误信息，用于告警和排查。
     * </p>
     */
    @Override
    public void onError(ChatModelErrorContext errorContext) {
        long elapsed = System.currentTimeMillis()
                - (long) errorContext.attributes().getOrDefault(ATTR_START_TIME, System.currentTimeMillis());

        log.error("[LLM Error] provider={}, elapsed={}ms, error={}",
                errorContext.modelProvider(),
                elapsed,
                errorContext.error().getMessage(),
                errorContext.error());
    }

    /**
     * 截断文本，避免日志过长
     */
    private static String truncate(String text, int maxLength) {
        if (text == null) {
            return "null";
        }
        // 将换行替换为空格，便于日志阅读
        String cleaned = text.replace("\n", " ").replace("\r", "");
        if (cleaned.length() <= maxLength) {
            return cleaned;
        }
        return cleaned.substring(0, maxLength) + "...";
    }
}
