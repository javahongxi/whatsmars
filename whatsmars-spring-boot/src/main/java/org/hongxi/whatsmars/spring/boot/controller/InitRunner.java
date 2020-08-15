package org.hongxi.whatsmars.spring.boot.controller;

import org.hongxi.whatsmars.spring.boot.async.MessageService;
import org.hongxi.whatsmars.spring.boot.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2017/6/29.
 */
@Component
@Order(value = 1)
public class InitRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageService messageService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("init......createIfNotExistsTable");
        userMapper.createIfNotExistsTable();

        for (int i = 0; i < 10; i++) {
            logger.info("send result: {}", messageService.send("message" + i).get());
        }
    }
}
