package com.itlong.whatsmars.redis.client.singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by javahongxi on 2017/6/22.
 */
@Component
public class Demo {

    @Autowired
    @Qualifier("singletonRedisClient")
    private JedisPool redisClient;

    public void test() {
        Jedis jedis = redisClient.getResource();
        String cacheContent = null;
        try {
            cacheContent = jedis.get("hello_world");
        }finally {
            redisClient.close();
        }
        // 获取redis数据之后，立即释放连接，然后开始进行业务处理
        if(cacheContent == null) {
            // DB operation
        }
        // ..
    }
}
