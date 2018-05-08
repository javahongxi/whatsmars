package org.hongxi.whatsmars.spring.boot.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by shenhongxi on 2018/5/8.
 */
@Service
public class MessageService {

    @Async
    public void send(String message) {
        System.out.println(message);
    }
}
