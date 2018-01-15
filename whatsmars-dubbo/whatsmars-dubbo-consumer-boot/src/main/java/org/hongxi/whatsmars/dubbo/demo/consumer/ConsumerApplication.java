package org.hongxi.whatsmars.dubbo.demo.consumer;

import org.hongxi.whatsmars.dubbo.demo.consumer.rpc.DemoRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by javahongxi on 2017/12/4.
 */
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConsumerApplication.class, args);
        DemoRpc demoRpc = context.getBean(DemoRpc.class);
        System.out.println(demoRpc.sayHello("Lily"));
    }
}
