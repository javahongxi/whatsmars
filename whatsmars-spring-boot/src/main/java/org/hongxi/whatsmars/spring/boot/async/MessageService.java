package org.hongxi.whatsmars.spring.boot.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Created by shenhongxi on 2018/5/8.
 */
@Service
public class MessageService {

    @Async
    public CompletableFuture<String> send(String message) {
        System.out.println(message);
        return CompletableFuture.completedFuture(message);
    }
}
