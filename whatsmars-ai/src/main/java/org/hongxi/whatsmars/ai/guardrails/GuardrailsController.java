package org.hongxi.whatsmars.ai.guardrails;

import dev.langchain4j.guardrail.InputGuardrailException;
import org.hongxi.whatsmars.ai.common.SseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 护栏流式控制器
 * <p>
 * 演示护栏在流式请求中的工作原理：
 * <ul>
 *   <li>输入护栏拦截：发送包含敏感词的消息会被拒绝，抛出 InputGuardrailException</li>
 *   <li>流式输出：通过 TokenStream + SSE 实时输出 AI 回复</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/guardrails")
public class GuardrailsController {

    private static final Logger log = LoggerFactory.getLogger(GuardrailsController.class);

    private final GuardrailsAssistant assistant;

    public GuardrailsController(GuardrailsAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * 受护栏保护的流式聊天接口
     * <p>
     * 输入护栏在 TokenStream.start() 前执行，如果拦截则抛出 InputGuardrailException。
     * </p>
     *
     * @param message 用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamChat(@RequestParam String message) {
        log.info("收到消息: {}", message);
        SseEmitter emitter = new SseEmitter(0L);
        try {
            SseHelper.stream(assistant.chat(message), emitter);
        } catch (InputGuardrailException e) {
            log.warn("输入护栏拦截: {}", e.getMessage());
            try {
                emitter.send(SseEmitter.event().data("【输入拦截】" + e.getMessage()));
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        }
        return emitter;
    }

    /**
     * 全局异常处理：输入护栏异常
     */
    @ExceptionHandler(InputGuardrailException.class)
    public ResponseEntity<String> handleInputGuardrailException(InputGuardrailException e) {
        log.warn("输入护栏异常: {}", e.getMessage());
        return ResponseEntity.badRequest().body("输入检查未通过: " + e.getMessage());
    }
}
