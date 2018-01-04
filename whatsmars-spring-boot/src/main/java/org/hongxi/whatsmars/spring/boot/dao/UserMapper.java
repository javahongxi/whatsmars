package org.hongxi.whatsmars.spring.boot.dao;

import org.hongxi.whatsmars.spring.boot.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by shenhongxi on 2017/6/26.
 */
@Mapper
public interface UserMapper {

    User findByUsername(String username);

    void insert(User user);

    void insertBatch(List<User> users);
}
