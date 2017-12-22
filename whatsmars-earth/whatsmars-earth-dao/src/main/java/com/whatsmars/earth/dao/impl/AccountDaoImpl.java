package com.whatsmars.earth.dao.impl;

import com.whatsmars.earth.dao.AccountDao;

/**
 * Created by shenhongxi on 2016/4/1.
 */
public class AccountDaoImpl extends BaseDao implements AccountDao {

    public String hello(String name) {
        return "Hello, " + name;
    }
}
