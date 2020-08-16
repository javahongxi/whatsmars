package org.hongxi.whatsmars.boot.sample.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by shenhongxi on 2018/5/8.
 */
@Service
public class MessageService {

    @Async("taskExecutor")
    public CompletableFuture<String> hello(String message) {
        return CompletableFuture.completedFuture("Hello, " + message);
    }

    @Async("taskExecutor")
    public void send(String message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send " + message);
    }
}
