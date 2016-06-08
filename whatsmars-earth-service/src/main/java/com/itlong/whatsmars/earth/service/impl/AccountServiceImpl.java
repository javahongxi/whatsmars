package com.itlong.whatsmars.earth.service.impl;

import com.itlong.whatsmars.common.pojo.Result;
import com.itlong.whatsmars.earth.dao.AccountDao;
import com.itlong.whatsmars.earth.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * Created by shenhongxi on 2016/4/1.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    //@Autowired
    //private TransactionTemplate transactionTemplate;

    public Result hello(String name) {
        Result result = new Result();
        String hello = accountDao.hello(name);
        result.addModel("hello", hello);
        result.setSuccess(true);

        //事务操作
//        boolean isSuccess = transactionTemplate.execute(new TransactionCallback<Boolean>() {
//            @Override
//            public Boolean doInTransaction(TransactionStatus transactionStatus) {
//
//                return false;
//            }
//        });

        return result;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
