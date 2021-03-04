package org.hongxi.whatsmars.boot.sample.async;

import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.boot.sample.async.circular.BeanA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by shenhongxi on 2018/5/8.
 */
@Slf4j
@Service
public class MessageService {

    @Autowired
    private BeanA a;

    @Async("taskExecutor")
    public CompletableFuture<String> hello(String message) {
        return CompletableFuture.completedFuture("Hello, " + message);
    }

    @Async("taskExecutor")
    public void send(String message) {
        log.info("send {}", message);
    }

    public void send() {
        a.send();
    }
}
