package com.itlong.whatsmars.spring.boot.service;

import com.itlong.whatsmars.spring.boot.model.User;

/**
 * Created by shenhongxi on 2017/6/26.
 */
public interface UserService {

    User findByUsername(String username);
}
