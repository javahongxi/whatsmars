package org.hongxi.whatsmars.cloud.consumer.rpc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by javahongxi on 2017/12/28.
 */
@Component
public class DemoRpc {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;

    @HystrixCommand(fallbackMethod = "helloFallback")
    public String hello(String name) {
        return restTemplate.getForObject("http://" + env.getProperty("provider.name") + "/hello?name=" + name, String.class);
    }

    public String helloFallback(String name) {
        return "Hi, " + name + ", Here is Fallback";
    }
}
