package com.itlong.whatsmars.redis.client;

import com.itlong.whatsmars.redis.client.readwrite.ReadWriteRedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * Created by javahongxi on 2017/6/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class Demo {

    @Autowired
    @Qualifier("singletonRedisClient")
    private JedisPool singletonRedisClient;

    @Autowired
    private ReadWriteRedisClient readWriteRedisClient;

    @Autowired
    @Qualifier("redisClusterClient")
    private JedisCluster jedisCluster;

    @Test
    public void testSingleton() {
        Jedis jedis = singletonRedisClient.getResource();
        String cacheContent = null;
        try {
            cacheContent = jedis.get("hello_world");
        }finally {
            singletonRedisClient.close();
        }
        // 获取redis数据之后，立即释放连接，然后开始进行业务处理
        if(cacheContent == null) {
            // DB operation
        }
        // ..
    }

    @Test
    public void testReadWrite() {
        String cacheContent = null;
        try {
            cacheContent = readWriteRedisClient.get("hello_world");
        } catch (Exception e) {
            //如果异常，你可以决定是否忽略
        }
        if(cacheContent == null) {
            //如果cache中不存在，或者redis异常
        }
    }

    @Test
    public void testCluster() {
        String cacheContent = null;
        try {
            cacheContent = jedisCluster.get("hello_world");
        } catch (Exception e) {
            //如果异常，你可以决定是否忽略
        }
        if(cacheContent == null) {
            //如果cache中不存在，或者redis异常
        }
    }
}
