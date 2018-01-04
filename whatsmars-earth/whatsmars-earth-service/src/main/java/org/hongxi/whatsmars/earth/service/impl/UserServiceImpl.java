package org.hongxi.whatsmars.earth.service.impl;

import org.hongxi.whatsmars.earth.dao.UserDao;
import org.hongxi.whatsmars.earth.domain.pojo.User;
import org.hongxi.whatsmars.earth.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by javahongxi on 2017/12/24.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void add(User user) {
        userDao.insert(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
