package com.whatsmars.redis.client;

import com.whatsmars.redis.client.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by javahongxi on 2017/12/5.
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private RedisService redisService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        redisService.set("count", "1");
        System.out.println(redisService.get("count"));
    }
}
