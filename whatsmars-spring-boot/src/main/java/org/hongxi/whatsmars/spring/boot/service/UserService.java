package org.hongxi.whatsmars.spring.boot.service;

import com.github.pagehelper.Page;
import org.hongxi.whatsmars.spring.boot.model.User;

/**
 * Created by shenhongxi on 2017/6/26.
 */
public interface UserService {

    User findByUsername(String username);

    void add(User user);

    Page<User> query(int offset, int limit);
}
