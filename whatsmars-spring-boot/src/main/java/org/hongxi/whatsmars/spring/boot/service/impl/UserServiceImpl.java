package org.hongxi.whatsmars.spring.boot.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.hongxi.whatsmars.spring.boot.dao.UserMapper;
import org.hongxi.whatsmars.spring.boot.model.User;
import org.hongxi.whatsmars.spring.boot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shenhongxi on 2017/6/21.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
    public void addBatch(List<User> users) {
        userMapper.insertBatch(users);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(List<User> users) {
        int i = 0;
        for (User user : users) {
//            if (i++ == 1) throw new RuntimeException("TEST TX");
            userMapper.insert(user);
        }
    }

    @Override
    public List<User> findByNicknameAndGender(String nickname, Integer gender) {
        return userMapper.findByNicknameAndGender(nickname, gender);
    }

    @Override
    public void testTransaction(List<User> users) {
        // 同一个类中调有事务的方法，无事务，因为事务的本质是调代理
        add(users);
    }
}
