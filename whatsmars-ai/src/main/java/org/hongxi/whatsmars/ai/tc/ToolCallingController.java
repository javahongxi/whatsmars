package org.hongxi.whatsmars.ai.tc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 工具调用控制器
 * <p>
 * 演示 AI 如何自动调用工具，流式输出回答。
 * </p>
 * <p>
 * 测试示例：
 * <ul>
 *   <li>"现在几点了？" → 调用 TimeTool.getCurrentDateTime()</li>
 *   <li>"距离国庆节还有多少天？" → 调用 TimeTool.daysUntil()</li>
 *   <li>"帮我请求一下 https://jsonplaceholder.typicode.com/posts/1" → 调用 HttpRequestTool.httpGet()</li>
 *   <li>"搜索一下最近有什么新上映的电影" → 调用 WebSearchTool.webSearch()</li>
 *   <li>"当前系统内存使用情况" → 调用 SystemInfoTool.getSystemInfo()</li>
 *   <li>"查看当前环境变量" → 调用 SystemInfoTool.getEnvironmentInfo()</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/tool")
public class ToolCallingController {

    private static final Logger log = LoggerFactory.getLogger(ToolCallingController.class);

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private final ToolCallingAssistant assistant;

    public ToolCallingController(ToolCallingAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * 发送消息，AI 会根据需要调用工具，流式输出回答
     *
     * @param message 用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestParam String message) {
        log.info("工具调用 - 收到消息: {}", message);

        SseEmitter emitter = new SseEmitter(0L);

        executor.execute(() -> {
            try {
                assistant.chat(message)
                        .onPartialResponse(token -> {
                            try {
                                log.debug("工具调用发送 token: {}", token);
                                emitter.send(SseEmitter.event().data(token));
                            } catch (IOException e) {
                                log.error("工具调用发送 token 失败", e);
                                emitter.completeWithError(e);
                            }
                        })
                        .onCompleteResponse(response -> {
                            log.info("工具调用流式完成");
                            emitter.complete();
                        })
                        .onError(error -> {
                            log.error("工具调用流式出错", error);
                            emitter.completeWithError(error);
                        })
                        .start();
            } catch (Exception e) {
                log.error("工具调用流式异常", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
