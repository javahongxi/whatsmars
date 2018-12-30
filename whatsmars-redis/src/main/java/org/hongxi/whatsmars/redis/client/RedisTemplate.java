package org.hongxi.whatsmars.redis.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.*;

/**
 * Created by shenhongxi on 2018/12/27.
 * 单个redis模式作为sharding模式的特例(配一个host:port即可)
 * JedisCluster不用封装，直接用即可
 */
@Slf4j
public class RedisTemplate {

    private ShardedJedisPool shardedJedisPool;

    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T execute(RedisCallback<T> action) {
        ShardedJedis jedis = fetchJedis();
        try {
            return action.doInRedis(jedis);
        } finally {
            release(jedis);
        }
    }

    public void set(String key, String value) {
        ShardedJedis jedis = fetchJedis();
        try {
            jedis.set(key, value);
        } finally {
            release(jedis);
        }
    }

    public void set(String key, String value, int seconds) {
        ShardedJedis jedis = fetchJedis();
        try {
            jedis.setex(key, seconds, value);
        } finally {
            release(jedis);
        }
    }

    public void setIfAbsent(String key, String value) {
        ShardedJedis jedis = fetchJedis();
        try {
            jedis.setnx(key, value);
        } finally {
            release(jedis);
        }
    }

    public String get(String key) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.get(key);
        } finally {
            release(jedis);
        }
    }

    public void del(String key) {
        ShardedJedis jedis = fetchJedis();
        try {
            jedis.del(key);
        } finally {
            release(jedis);
        }
    }

    public List<String> multiGet(Collection<String> keys) {
        List<String> list = new ArrayList<>();
        keys.stream().forEach(key -> list.add(get(key)));
        return list;
    }

    public Long increment(String key, long delta) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.incrBy(key, delta);
        } finally {
            release(jedis);
        }
    }

    public Double increment(String key, double delta) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.incrByFloat(key, delta);
        } finally {
            release(jedis);
        }
    }

    public Long append(String key, String value) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.append(key, value);
        } finally {
            release(jedis);
        }
    }

    public String get(String key, long start, long end) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.getrange(key, start, end);
        } finally {
            release(jedis);
        }
    }

    public Long size(String key) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.strlen(key);
        } finally {
            release(jedis);
        }
    }

    public Boolean setBit(String key, long offset, boolean value) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.setbit(key, offset, value);
        } finally {
            release(jedis);
        }
    }

    public Boolean getBit(String key, long offset) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.getbit(key, offset);
        } finally {
            release(jedis);
        }
    }

    public long sadd(String key, String... values) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.sadd(key, values);
        } finally {
            release(jedis);
        }
    }

    public Set<String> smembers(String key) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.smembers(key);
        } finally {
            release(jedis);
        }
    }

    public boolean sismember(String key, String value) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.sismember(key, value);
        } finally {
            release(jedis);
        }
    }

    public long lpush(String key, String... values) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.lpush(key, values);
        } finally {
            release(jedis);
        }
    }

    public String rpop(String key) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.rpop(key);
        } finally {
            release(jedis);
        }
    }

    public List<String> lrange(String key, long start, long end) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.lrange(key, start, end);
        } finally {
            release(jedis);
        }
    }

    public Long hset(String key, String field, String value) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.hset(key, field, value);
        } finally {
            release(jedis);
        }
    }

    public String hget(String key, String field) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.hget(key, field);
        } finally {
            release(jedis);
        }
    }

    public boolean hexists(String key, String field) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.hexists(key, field);
        } finally {
            release(jedis);
        }
    }

    public long hdel(String key, String... fields) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.hdel(key, fields);
        } finally {
            release(jedis);
        }
    }

    public <T> void set(String key, T value, int seconds) throws Exception {
        ShardedJedis jedis = fetchJedis();
        try {
            jedis.setex(key.getBytes(), seconds, this.serialize(value));
        } finally {
            release(jedis);
        }
    }

    public <T> void set(String key, T value) throws Exception {
        ShardedJedis jedis = fetchJedis();
        try {
            jedis.set(key.getBytes(), this.serialize(value));
        } finally {
            release(jedis);
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        byte[] value = this.getBytes(key);
        return this.deserialize(value, clazz);
    }

    public byte[] getBytes(String key) {
        ShardedJedis jedis = fetchJedis();
        try {
            return jedis.get(key.getBytes());
        } finally {
            release(jedis);
        }
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    private ShardedJedis fetchJedis() {
        return shardedJedisPool.getResource();
    }

    private void release(ShardedJedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private <T> byte[] serialize(T value) throws Exception {
        return objectMapper.writeValueAsBytes(value);
    }

    private <T> T deserialize(byte[] value, Class<T> clazz) {
        try {
            return objectMapper.readValue(value, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
