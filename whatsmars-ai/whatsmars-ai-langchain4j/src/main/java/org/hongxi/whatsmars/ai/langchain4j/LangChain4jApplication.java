package org.hongxi.whatsmars.ai.langchain4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * LangChain4j 示例应用启动类
 *
 * @author hongxi
 */
@SpringBootApplication
public class LangChain4jApplication {
    public static void main(String[] args) {
        SpringApplication.run(LangChain4jApplication.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventListener(Environment environment) {
        return event -> {
            String port = environment.getProperty("server.port", "8080");
            String contextPath = environment.getProperty("server.servlet.context-path", "");
            String accessUrl = "http://localhost:" + port + contextPath;
            System.out.println("\n========================================");
            System.out.println("Application is ready!");
            System.out.println("Open in browser: " + accessUrl);
            System.out.println("========================================\n");
        };
    }
}
