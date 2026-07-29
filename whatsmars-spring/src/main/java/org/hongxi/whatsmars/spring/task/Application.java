package org.hongxi.whatsmars.spring.task;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;

/**
 * Demonstrates Spring's async and scheduling support:
 * <ul>
 *   <li>{@code @EnableAsync} — enables {@code @Async} method execution</li>
 *   <li>{@code @EnableScheduling} — enables {@code @Scheduled} periodic tasks</li>
 * </ul>
 */
@Configuration(proxyBeanMethods = false)
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = "org.hongxi.whatsmars.spring.task")
public class Application {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Application.class);
        ctx.refresh();

        System.out.println("===== @Async demo =====");
        AsyncService asyncService = ctx.getBean(AsyncService.class);

        // Fire-and-forget
        asyncService.doAsyncWork("task-A");
        asyncService.doAsyncWork("task-B");

        // Get result asynchronously
        CompletableFuture<String> future = asyncService.computeAsync(21);
        System.out.println("Main thread continues while async work runs...");
        String result = future.get(); // blocks until result is ready
        System.out.println("Async result: " + result);

        System.out.println("\n===== @Scheduled demo (runs for 3 seconds) =====");
        Thread.sleep(3500); // let scheduled task fire a few times

        ctx.close();
    }
}
