package org.hongxi.whatsmars.redis.client.sharded;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhongxi on 2018/6/27.
 */
public class ShardedRedisClient implements FactoryBean<ShardedJedisPool>, InitializingBean, DisposableBean {

    private ShardedJedisPool shardedJedisPool;

    private JedisPoolConfig jedisPoolConfig;

    private String address;

    private int timeout = 3000;

    @Override
    public ShardedJedisPool getObject() throws Exception {
        return shardedJedisPool;
    }

    @Override
    public Class<?> getObjectType() {
        return ShardedJedisPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<JedisShardInfo> shardInfos = new ArrayList<>();
        String[] addressArr = address.split(",");
        for (String internalAddress : addressArr) {
            String[] infoParams = internalAddress.split(":");
            shardInfos.add(new JedisShardInfo(infoParams[1], Integer.parseInt(infoParams[2]), timeout, infoParams[0]));
        }
        shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shardInfos);
    }

    @Override
    public void destroy() throws Exception {
        if (shardedJedisPool != null) {
            shardedJedisPool.close();
        }
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
