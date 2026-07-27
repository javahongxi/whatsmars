package org.hongxi.whatsmars.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 函数调用控制器
 * <p>
 * 演示 AI 如何自动调用工具函数，流式输出回答
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/function")
public class FunctionCallingController {

    private static final Logger log = LoggerFactory.getLogger(FunctionCallingController.class);

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private FunctionCallingAssistant assistant;

    /**
     * 发送消息，AI 会根据需要调用工具，流式输出回答
     * <p>
     * 测试示例：
     * - "现在几点了？" -> 调用 getCurrentTime()
     * - "今天日期是多少？" -> 调用 getCurrentDate()
     * - "计算 123 + 456" -> 调用 add(123, 456)
     * - "北京天气怎么样？" -> 调用 getWeather("北京")
     * </p>
     *
     * @param message 用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestParam String message) {
        log.info("函数调用 - 收到消息: {}", message);

        SseEmitter emitter = new SseEmitter(0L);

        executor.execute(() -> {
            try {
                assistant.chat(message)
                        .onPartialResponse(token -> {
                            try {
                                log.debug("函数调用发送 token: {}", token);
                                emitter.send(SseEmitter.event().data(token));
                            } catch (IOException e) {
                                log.error("函数调用发送 token 失败", e);
                                emitter.completeWithError(e);
                            }
                        })
                        .onCompleteResponse(response -> {
                            log.info("函数调用流式完成");
                            emitter.complete();
                        })
                        .onError(error -> {
                            log.error("函数调用流式出错", error);
                            emitter.completeWithError(error);
                        })
                        .start();
            } catch (Exception e) {
                log.error("函数调用流式异常", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
