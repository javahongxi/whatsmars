package com.whatsmars.earth.service;

import com.whatsmars.earth.domain.pojo.User;

/**
 * Created by javahongxi on 2017/12/24.
 */
public interface UserService {

    User findByUsername(String username);

    void add(User user);
}
