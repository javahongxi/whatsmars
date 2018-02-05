package org.hongxi.whatsmars.earth.dao.impl;

import org.hongxi.whatsmars.earth.dao.UserDao;
import org.hongxi.whatsmars.earth.domain.pojo.User;

/**
 * Created by javahongxi on 2017/12/24.
 */
public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public void createIfNotExistsTable() {
        this.sqlSession.update("User.createIfNotExistsTable");
    }

    @Override
    public User findByUsername(String username) {
        return this.sqlSession.selectOne("User.findByUsername", username);
    }

    @Override
    public void insert(User user) {
        this.sqlSession.insert("User.insert", user);
    }
}
