package com.whatsmars.mars001.service.impl;

import com.whatsmars.mars001.common.pojo.Result;
import com.whatsmars.mars001.dao.AccountDao;
import com.whatsmars.mars001.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * Created by shenhongxi on 2016/4/1.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    public Result hello(String name) {
        Result result = new Result();
        String hello = accountDao.hello(name);
        result.addModel("hello", hello);
        result.setSuccess(true);
        return result;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
