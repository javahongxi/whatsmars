package org.hongxi.whatsmars.boot.sample.datasource.service;

import com.github.pagehelper.Page;
import org.hongxi.whatsmars.boot.sample.datasource.model.User;

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

    void addBatch(List<User> users);

    void add(List<User> users);

    List<User> findByNicknameAndGender(String nickname, Integer gender);

    void testTransaction(List<User> users);

}
