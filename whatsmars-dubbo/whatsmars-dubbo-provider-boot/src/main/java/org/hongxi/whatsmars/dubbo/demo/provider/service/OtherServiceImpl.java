/**
 * Created by shenhongxi on 2017/12/4.
 */
package org.hongxi.whatsmars.dubbo.demo.provider.service;

import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.hongxi.whatsmars.dubbo.demo.api.OtherService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service(registry = "otherRegistry")
public class OtherServiceImpl implements OtherService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hi " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }
    
}