package com.whatsmars.cloud.consumer.rpc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by javahongxi on 2017/12/28.
 */
@Component
public class DemoRpc {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback")
    public String hello(String name) {
        return restTemplate.getForObject("http://demo-provider/hello?name=" + name, String.class);
    }

    public String helloFallback(String name) {
        return "Hi, " + name + ", Here is Fallback";
    }
}
