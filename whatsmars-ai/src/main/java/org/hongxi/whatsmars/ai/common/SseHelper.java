package org.hongxi.whatsmars.ai.common;

import dev.langchain4j.service.TokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * SSE 流式响应工具类
 * <p>
 * 封装 TokenStream → SseEmitter 的通用桥接逻辑，
 * 避免在每个 Controller 中重复编写相同的流式模板代码。
 * </p>
 *
 * @author hongxi
 */
public final class SseHelper {

    private static final Logger log = LoggerFactory.getLogger(SseHelper.class);

    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private SseHelper() {
    }

    /**
     * 将 TokenStream 桥接到 SseEmitter，每个 token 以纯文本形式发送
     *
     * @param stream  TokenStream（来自 AiService 接口返回值）
     * @param emitter SSE 发射器
     */
    public static void stream(TokenStream stream, SseEmitter emitter) {
        stream(stream, emitter, null);
    }

    /**
     * 将 TokenStream 桥接到 SseEmitter，支持自定义 token 映射
     * <p>
     * 例如将 token 包装为 DTO 对象后以 JSON 格式发送。
     * </p>
     *
     * @param stream      TokenStream
     * @param emitter     SSE 发射器
     * @param tokenMapper 可选的 token 转换函数，为 null 时直接发送原始 token 字符串
     */
    public static void stream(TokenStream stream, SseEmitter emitter, Function<String, Object> tokenMapper) {
        EXECUTOR.execute(() -> {
            try {
                stream.onPartialResponse(token -> {
                    try {
                        Object data = tokenMapper != null ? tokenMapper.apply(token) : token;
                        emitter.send(SseEmitter.event().data(data));
                    } catch (IOException e) {
                        log.error("发送 token 失败", e);
                        emitter.completeWithError(e);
                    }
                }).onCompleteResponse(response -> {
                    emitter.complete();
                }).onError(error -> {
                    emitter.completeWithError(error);
                }).start();
            } catch (Exception e) {
                log.error("流式响应异常", e);
                emitter.completeWithError(e);
            }
        });
    }
}
