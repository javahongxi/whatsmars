package org.hongxi.whatsmars.dubbo.demo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by javahongxi on 2017/12/4.
 */
@SpringBootApplication
@ComponentScan(value = {"org.hongxi.whatsmars.dubbo"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
