package com.itlong.whatsmars.spring.boot.service.impl;

import com.itlong.whatsmars.spring.boot.dao.UserMapper;
import com.itlong.whatsmars.spring.boot.model.User;
import com.itlong.whatsmars.spring.boot.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shenhongxi on 2017/6/21.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    public UserServiceImpl() {
        logger.info("UserServiceImpl init...");
        System.out.println("UserServiceImpl init...");
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
