package org.hongxi.whatsmars.earth.service;

import org.hongxi.whatsmars.earth.domain.pojo.User;

/**
 * Created by javahongxi on 2017/12/24.
 */
public interface UserService {

    User findByUsername(String username);

    void add(User user);
}
