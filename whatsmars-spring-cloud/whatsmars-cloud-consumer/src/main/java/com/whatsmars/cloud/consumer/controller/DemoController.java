package com.whatsmars.cloud.consumer.controller;

import com.whatsmars.cloud.consumer.feign.DemoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by shenhongxi on 2017/9/14.
 */
@RestController
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DemoFeign demoFeign;

    @RequestMapping("/hello")
    public String hello(String name) {
        return restTemplate.getForObject("http://demo-provider/hello?name=" + name, String.class);
    }

    @RequestMapping("/hi")
    public String hi(String name) {
        return demoFeign.hello(name);
    }
}
