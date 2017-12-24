package com.whatsmars.earth.dao;

import com.whatsmars.earth.domain.pojo.User;

/**
 * Created by javahongxi on 2017/12/24.
 */
public interface UserDao {

    User findByUsername(String username);

    void insert(User user);
}
