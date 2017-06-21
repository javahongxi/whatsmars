package com.itlong.whatsmars.spring.boot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by shenhongxi on 2017/6/21.
 */
public class UserService {

    final Logger logger = LogManager.getLogger(getClass());

    public UserService() {
        logger.info("UserService init...");
        System.out.println("UserService init...");
    }
}
