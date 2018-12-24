package org.hongxi.whatsmars.redis.client.service.impl;

import com.alibaba.fastjson.JSON;
import org.hongxi.whatsmars.redis.client.service.RedisStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenhongxi on 2018/12/24.
 */
@Service
public class RedisStringServiceImpl implements RedisStringService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long activeTime) {
        stringRedisTemplate.opsForValue().set(key, value, activeTime, TimeUnit.SECONDS);
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
    public long addSet(String key, String... values) {
        return stringRedisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Set<String> getSet(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    @Override
    public void pubMsg(String channel, Object obj) {
        assert obj != null;
        String msg = obj instanceof String ? String.valueOf(obj) : JSON.toJSONString(obj);
        stringRedisTemplate.convertAndSend(channel, msg);
    }
}
