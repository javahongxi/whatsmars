package org.hongxi.whatsmars.dubbo.demo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by javahongxi on 2017/12/4.
 */
@SpringBootApplication
@ComponentScan(value = {"org.hongxi.whatsmars.dubbo"})
@EnableDubbo(scanBasePackages = "org.hongxi.whatsmars.dubbo.demo.provider.resource", multipleConfig = true)
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
