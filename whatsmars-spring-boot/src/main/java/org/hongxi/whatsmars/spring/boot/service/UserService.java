package org.hongxi.whatsmars.spring.boot.service;

import com.github.pagehelper.Page;
import org.hongxi.whatsmars.spring.boot.model.User;

import java.util.List;

/**
 * Created by shenhongxi on 2017/6/26.
 */
public interface UserService {

    User findByUsername(String username);

    void add(User user);

    void update(User user);

    void delete(Long id);

    Page<User> query(int offset, int limit);

    void insertBatch(List<User> users);

    void add(List<User> users);

}
