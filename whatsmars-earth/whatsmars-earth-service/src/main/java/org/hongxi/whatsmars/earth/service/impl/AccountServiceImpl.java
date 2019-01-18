package org.hongxi.whatsmars.earth.service.impl;

import org.hongxi.whatsmars.earth.common.pojo.Result;
import org.hongxi.whatsmars.earth.dao.AccountDao;
import org.hongxi.whatsmars.earth.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by shenhongxi on 2016/4/1.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    //@Autowired
//    private TransactionTemplate transactionTemplate;

    public Result hello(String name) {
        Result result = new Result();
        String hello = accountDao.hello(name);
        result.addModel("hello", hello);
        result.setSuccess(true);

        //事务操作
//        boolean isSuccess = transactionTemplate.execute(new TransactionCallback<Boolean>() {
//            @Override
//            public Boolean doInTransaction(TransactionStatus transactionStatus) {
//                try {
//                    // ...
//                    return true;
//                } catch (Exception e) {
//                    transactionStatus.setRollbackOnly();
//                }
//                return false;
//            }
//        });

        return result;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
