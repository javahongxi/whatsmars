package org.hongxi.whatsmars.ai.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * 必须先启动whatsmars-ai-spring，因为依赖了它 的 MCP Server
 */
@SpringBootApplication
public class AiAlibabaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiAlibabaApplication.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventListener(Environment environment) {
        return event -> {
            String port = environment.getProperty("server.port", "8080");
            String contextPath = environment.getProperty("server.servlet.context-path", "");
            String accessUrl = "http://localhost:" + port + contextPath + "/chatui/index.html";
            System.out.println("\n========================================");
            System.out.println("Application is ready!");
            System.out.println("Chat with your agent: " + accessUrl);
            System.out.println("========================================\n");
        };
    }
}
