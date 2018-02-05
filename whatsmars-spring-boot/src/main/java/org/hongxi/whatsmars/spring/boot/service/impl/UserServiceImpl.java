package org.hongxi.whatsmars.spring.boot.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.hongxi.whatsmars.spring.boot.dao.UserMapper;
import org.hongxi.whatsmars.spring.boot.model.User;
import org.hongxi.whatsmars.spring.boot.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shenhongxi on 2017/6/21.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
        logger.info("add user success, username: {}", user.getUsername());
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Long id) {
        userMapper.delete(id);
    }

    @Override
    public Page<User> query(int offset, int limit) {
        return PageHelper.offsetPage(offset, limit).doSelectPage(() -> userMapper.query());
    }

    @Override
    public void insertBatch(List<User> users) {
        userMapper.insertBatch(users);
    }

    @Transactional
    @Override
    public void add(List<User> users) {
        for (User user : users) {
            userMapper.insert(user);
        }
    }
}
