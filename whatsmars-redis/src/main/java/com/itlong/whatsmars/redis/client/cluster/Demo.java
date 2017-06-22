package com.itlong.whatsmars.redis.client.cluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * Created by javahongxi on 2017/6/22.
 */
@Component
public class Demo {

    @Autowired
    @Qualifier("redisClusterClient")
    private JedisCluster redisClient;

    public void test() {
        String cacheContent = null;
        try {
            cacheContent = redisClient.get("hello_world");
        } catch (Exception e) {
            //如果异常，你可以决定是否忽略
        }
        if(cacheContent == null) {
            //如果cache中不存在，或者redis异常
        }
    }
}
