package org.hongxi.whatsmars.dubbo.demo.consumer.controller;

import org.hongxi.whatsmars.dubbo.demo.consumer.rpc.DemoRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shenhongxi on 2019/4/17.
 */
@RestController
public class DemoController {

    @Autowired
    private DemoRpc demoRpc;

    @GetMapping("/hello")
    public String hello() {
        return demoRpc.sayHello("hongxi");
    }
}
