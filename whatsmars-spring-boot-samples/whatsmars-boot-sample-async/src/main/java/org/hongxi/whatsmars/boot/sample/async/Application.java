package org.hongxi.whatsmars.boot.sample.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@Slf4j
@EnableAsync
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(Application.class, args);
        MessageService messageService = applicationContext.getBean(MessageService.class);
        CompletableFuture<String> future = messageService.hello("world");
        messageService.send("world");
        try {
            log.info(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        messageService.send();
        log.info("......end");
    }
}
