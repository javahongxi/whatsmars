/**
 * Created by shenhongxi on 2017/6/21.
 */
package com.whatsmars.dubbo.demo.provider;

import com.alibaba.dubbo.rpc.RpcContext;
import com.whatsmars.dubbo.demo.api.DemoService;
import com.whatsmars.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Autowired
    private UserService userService;

    public String sayHello(String name) {
        boolean registerSuccess = userService.register(name);
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", registerSuccess:" + registerSuccess + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }
    
}