package org.hongxi.whatsmars.cloud.consumer.controller;

import org.hongxi.whatsmars.cloud.consumer.feign.DemoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by shenhongxi on 2017/9/14.
 */
@RestController
public class DemoController {

    @Autowired
    private DemoFeign demoFeign;

    @RequestMapping("/hi")
    public Mono<String> hi(String name) {
        return Mono.just(demoFeign.hello(name));
    }
}
