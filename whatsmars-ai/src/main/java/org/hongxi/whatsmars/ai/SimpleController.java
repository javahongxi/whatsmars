package org.hongxi.whatsmars.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SimpleAssistant assistant;

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
