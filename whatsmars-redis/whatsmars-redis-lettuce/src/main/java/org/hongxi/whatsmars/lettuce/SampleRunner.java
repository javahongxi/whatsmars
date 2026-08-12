package org.hongxi.whatsmars.lettuce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SampleRunner implements CommandLineRunner {

    private final RedisTemplate<String, Object> cacheRedisTemplate;
    private final RedisTemplate<String, Object> sessionRedisTemplate;

    public SampleRunner(RedisTemplate<String, Object> cacheRedisTemplate,
                        RedisTemplate<String, Object> sessionRedisTemplate) {
        this.cacheRedisTemplate = cacheRedisTemplate;
        this.sessionRedisTemplate = sessionRedisTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Lily", 20, new Date());

        cacheRedisTemplate.opsForValue().set("user", user);
        User user1 = (User) cacheRedisTemplate.opsForValue().get("user");
        if (user1 != null) {
            System.out.println(user1);
        }

        sessionRedisTemplate.opsForValue().set("user", user);
        User user2 = (User) sessionRedisTemplate.opsForValue().get("user");
        if (user2 != null) {
            System.out.println(user2);
        }
    }
}
