package org.hongxi.whatsmars.cloud.consumer.controller;

import org.hongxi.whatsmars.cloud.consumer.feign.DemoFeign;
import org.hongxi.whatsmars.cloud.consumer.rpc.DemoRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shenhongxi on 2017/9/14.
 */
@RestController
public class DemoController {

    @Autowired
    private DemoRpc demoRpc;

    @Autowired
    private DemoFeign demoFeign;

    @RequestMapping("/hello")
    public String hello(String name) {
        return demoRpc.hello(name);
    }

    @RequestMapping("/hi")
    public String hi(String name) {
        return demoFeign.hello(name);
    }
}
