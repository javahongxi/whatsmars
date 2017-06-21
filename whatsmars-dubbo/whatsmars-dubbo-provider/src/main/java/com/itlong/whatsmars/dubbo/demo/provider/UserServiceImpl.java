/**
 * Created by shenhongxi on 2017/6/21.
 */
package com.itlong.whatsmars.dubbo.demo.provider;

import com.alibaba.dubbo.rpc.RpcContext;
import com.itlong.whatsmars.dubbo.demo.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserServiceImpl implements UserService {

    public String sayHi(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hi " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hi " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }
    
}