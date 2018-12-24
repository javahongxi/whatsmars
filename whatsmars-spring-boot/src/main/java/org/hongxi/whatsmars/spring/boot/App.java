package org.hongxi.whatsmars.spring.boot;

import org.hongxi.whatsmars.spring.boot.config.UserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({UserConfig.class})
@ImportResource(locations={"classpath*:spring/*.xml"})
@ComponentScan({"org.hongxi.whatsmars.spring.boot", "org.hongxi.whatsmars.redis.client"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}