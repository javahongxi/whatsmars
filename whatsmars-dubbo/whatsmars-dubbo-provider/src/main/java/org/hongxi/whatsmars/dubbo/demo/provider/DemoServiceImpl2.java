/**
 * Created by shenhongxi on 2017/6/21.
 */
package org.hongxi.whatsmars.dubbo.demo.provider;

import org.hongxi.whatsmars.dubbo.demo.api.DemoService;
import org.springframework.stereotype.Service;

@Service("demoService2")
public class DemoServiceImpl2 implements DemoService {

    public String sayHello(String name) {
        return "Hello " + name;
    }
    
}