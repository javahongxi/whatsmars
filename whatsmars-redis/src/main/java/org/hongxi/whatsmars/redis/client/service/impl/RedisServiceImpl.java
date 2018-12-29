package org.hongxi.whatsmars.redis.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.redis.client.service.RedisService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenhongxi on 2018/12/24.
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long seconds) {
        stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void setIfAbsent(String key, String value) {
        stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public String getAndSet(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public void multiSet(Map<String, String> map) {
        stringRedisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public List<String> multiGet(Collection<String> keys) {
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Double increment(String key, double delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Integer append(String key, String value) {
        return stringRedisTemplate.opsForValue().append(key, value);
    }

    @Override
    public String get(String key, long start, long end) {
        return stringRedisTemplate.opsForValue().get(key, start, end);
    }

    @Override
    public Long size(String key) {
        return stringRedisTemplate.opsForValue().size(key);
    }

    @Override
    public Boolean setBit(String key, long offset, boolean value) {
        return stringRedisTemplate.opsForValue().setBit(key, offset, value);
    }

    @Override
    public Boolean getBit(String key, long offset) {
        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    @Override
    public long sadd(String key, String... values) {
        return stringRedisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Set<String> smembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    @Override
    public void convertAndSend(String channel, Object obj) {
        assert obj != null;
        String msg = obj instanceof String ? String.valueOf(obj) : JSON.toJSONString(obj);
        stringRedisTemplate.convertAndSend(channel, msg);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    @Override
    public Set<String> matchKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public <T> void set(String key, T value, long seconds) throws Exception {
        this.set(key.getBytes(), serialize(value), seconds);
    }

    @Override
    public <T> void set(String key, T value) throws Exception {
        this.set(key.getBytes(), serialize(value));
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] value = connection.get(key.getBytes());
                if (value == null) {
                    return null;
                }
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    return objectMapper.readValue(value, clazz);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public boolean set(byte[] key, byte[] value, long seconds) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(key, seconds, value);
                return true;
            }
        });
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                return true;
            }
        });
    }

    @Override
    public byte[] getBytes(String key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
    }

    private <T> byte[] serialize(T value) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsBytes(value);
    }

}
