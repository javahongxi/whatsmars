package org.hongxi.whatsmars.redis.client;

import redis.clients.jedis.ShardedJedis;

/**
 * Created by shenhongxi on 2018/12/30.
 */
public interface RedisCallback<T> {

    T doInRedis(ShardedJedis jedis);
}
