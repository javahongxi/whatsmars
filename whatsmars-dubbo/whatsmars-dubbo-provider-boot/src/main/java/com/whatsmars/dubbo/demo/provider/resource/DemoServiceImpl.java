/**
 * Created by shenhongxi on 2017/12/4.
 */
package com.whatsmars.dubbo.demo.provider.resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.itlong.whatsmars.dubbo.demo.api.DemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service(version = "1.0.0")
public class DemoServiceImpl implements DemoService {

    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }
    
}