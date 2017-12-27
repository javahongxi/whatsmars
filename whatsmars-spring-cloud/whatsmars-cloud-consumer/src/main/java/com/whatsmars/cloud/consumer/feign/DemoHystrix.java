package com.whatsmars.cloud.consumer.feign;

import org.springframework.stereotype.Component;

/**
 * Created by javahongxi on 2017/12/28.
 */
@Component
public class DemoHystrix implements DemoFeign {
    @Override
    public String hello(String name) {
        return "Hi, " + name + ", Here is Hystrix";
    }
}
