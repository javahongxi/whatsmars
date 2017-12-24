package com.whatsmars.earth.dao.impl;

import com.whatsmars.earth.dao.UserDao;
import com.whatsmars.earth.domain.pojo.User;

/**
 * Created by javahongxi on 2017/12/24.
 */
public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public User findByUsername(String username) {
        return this.sqlSession.selectOne("User.findByUsername", username);
    }

    @Override
    public void insert(User user) {
        this.sqlSession.insert("User.insert", user);
    }
}
