package org.hongxi.whatsmars.dubbo.demo.consumer.rpc;

import com.alibaba.dubbo.config.annotation.Reference;
import org.hongxi.whatsmars.dubbo.demo.api.DemoService;
import org.hongxi.whatsmars.dubbo.demo.api.OtherService;
import org.springframework.stereotype.Component;

/**
 * Created by javahongxi on 2017/12/4.
 * 将服务调用包装，可以方便进行调用监控
 */
@Component
public class DemoRpc {

    @Reference
    private DemoService demoService;

    @Reference(registry = "otherRegistry")
    private OtherService otherService;

    public String sayHello(String name) {
        String result = null;
        try {
            result = demoService.sayHello(name);
        } catch (Exception e) {
            // log
            e.printStackTrace();
        }
        return result;
    }

    public String sayHello2(String name) {
        String result = null;
        try {
            result = otherService.sayHello(name);
        } catch (Exception e) {
            // log
            e.printStackTrace();
        }
        return result;
    }
}
