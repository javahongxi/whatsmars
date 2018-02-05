package org.hongxi.whatsmars.earth.dao;

import org.hongxi.whatsmars.earth.domain.pojo.User;

/**
 * Created by javahongxi on 2017/12/24.
 */
public interface UserDao {

    void createIfNotExistsTable();

    User findByUsername(String username);

    void insert(User user);
}
