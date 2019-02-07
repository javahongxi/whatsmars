package org.hongxi.whatsmars.dubbo.demo.consumer;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.hongxi.whatsmars.dubbo.demo.api.DemoService;

public class Consumer {

    public static void main(String[] args) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("demo-consumer");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(DemoService.class);
        reference.setVersion("1.0.0");

        DemoService demoService = reference.get();
        System.out.println(demoService.sayHello("hongxi"));
    }
}
