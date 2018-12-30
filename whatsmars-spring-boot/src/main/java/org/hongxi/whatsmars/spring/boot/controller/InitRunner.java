package org.hongxi.whatsmars.spring.boot.controller;

import org.hongxi.whatsmars.redis.client.service.RedisService;
import org.hongxi.whatsmars.spring.boot.async.MessageService;
import org.hongxi.whatsmars.spring.boot.dao.UserMapper;
import org.hongxi.whatsmars.spring.boot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2017/6/29.
 */
@Component
@Order(value = 1)
public class InitRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {
        logger.info("init......createIfNotExistsTable");
        userMapper.createIfNotExistsTable();

        for (int i = 0; i < 10; i++) {
            messageService.send("message" + i);
        }

        String key = "domain";
        redisService.set(key, "hongxi.org", 60); // 60s
        System.out.println(redisService.get(key));

        User user = new User();
        user.setUsername("javahongxi");
        user.setNickname("红喜");
        redisService.set("user", user);
        System.out.println(redisService.get("user"));
        System.out.println(redisService.get("user", User.class));
        redisService.sadd("countries", "China", "America", "Japan");
        System.out.println(redisTemplate.opsForSet().isMember("countries", "China")); // fasle
        System.out.println(stringRedisTemplate.opsForSet().isMember("countries", "China")); // true

        redisService.hset("menu", "A", "a");
        System.out.println(redisService.hget("menu", "A"));

        redisService.leftPush("message", "haha");
        redisService.leftPush("message", user);
        System.out.println(redisService.rightPop("message"));
        System.out.println(redisService.rightPop("message"));
    }
}
