package org.hongxi.whatsmars.ai.mcp;

import org.hongxi.whatsmars.ai.condition.ConditionalOnMcpStatusUp;
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
 * MCP 对话控制器
 * <p>
 * 演示通过 MCP 协议调用 whatsmars-mcp Server 提供的工具进行对话。
 * 调用 {@code GET /ai/mcp/chat?message=...}，流式输出。
 * </p>
 * <p>
 * AI 通过 MCP Client 连接 whatsmars-mcp Server，
 * 可以调用天气查询、地图服务等工具来回答用户问题。
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/mcp")
@ConditionalOnMcpStatusUp
public class McpController {

    private static final Logger log = LoggerFactory.getLogger(McpController.class);

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private McpAssistant mcpAssistant;

    /**
     * MCP 工具调用对话接口（流式输出）
     *
     * @param message 用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestParam String message) {
        log.info("MCP 对话: {}", message);

        SseEmitter emitter = new SseEmitter(0L);

        executor.execute(() -> {
            try {
                mcpAssistant.chat(message)
                        .onPartialResponse(token -> {
                            try {
                                emitter.send(SseEmitter.event().data(token));
                            } catch (IOException e) {
                                log.error("发送 token 失败", e);
                                emitter.completeWithError(e);
                            }
                        })
                        .onCompleteResponse(response -> {
                            log.info("MCP 对话完成");
                            emitter.complete();
                        })
                        .onError(error -> {
                            log.error("MCP 对话出错", error);
                            emitter.completeWithError(error);
                        })
                        .start();
            } catch (Exception e) {
                log.error("MCP 对话异常", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
