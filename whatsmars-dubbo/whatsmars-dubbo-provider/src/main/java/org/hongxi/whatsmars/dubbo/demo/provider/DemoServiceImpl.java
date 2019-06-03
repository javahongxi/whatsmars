/**
 * Created by shenhongxi on 2017/6/21.
 */
package org.hongxi.whatsmars.dubbo.demo.provider;

import org.apache.dubbo.rpc.RpcContext;
import org.hongxi.whatsmars.dubbo.demo.api.DemoService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }
    
}