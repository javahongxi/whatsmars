package org.hongxi.whatsmars.ai.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础 AI 服务控制器
 *
 * @author hongxi
 */
@RestController
public class SimpleController {

    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);

    private final SimpleAssistant assistant;

    public SimpleController(SimpleAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * 简单的聊天接口
     *
     * @param message 用户消息
     * @return AI 回复
     */
    @GetMapping("/ai/chat")
    public String chat(@RequestParam String message) {
        log.info("收到消息: {}", message);
        String response = assistant.chat(message);
        log.info("AI 回复: {}", response);
        return response;
    }
}
