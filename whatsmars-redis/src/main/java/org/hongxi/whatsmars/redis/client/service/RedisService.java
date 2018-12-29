package org.hongxi.whatsmars.redis.client.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shenhongxi on 2018/12/24.
 */
public interface RedisService {

    void set(String key, String value);

    void set(String key, String value, long seconds);

    void setIfAbsent(String key, String value);

    String get(String key);

    String getAndSet(String key, String value);

    void multiSet(Map<String, String> map);

    List<String> multiGet(Collection<String> keys);

    Long increment(String key, long delta);

    Double increment(String key, double delta);

    Integer append(String key, String value);

    String get(String key, long start, long end);

    Long size(String key);

    Boolean setBit(String key, long offset, boolean value);

    Boolean getBit(String key, long offset);

    long sadd(String key, String... values);

    Set<String> smembers(String key);

    void convertAndSend(String channel, Object obj);

    void delete(String key);

    boolean exists(String key);

    Set<String> matchKeys(String pattern);

    <T> void set(String key, T value, long seconds) throws Exception;

    <T> void set(String key, T value) throws Exception;

    <T> T get(String key, Class<T> clazz);

    boolean set(byte[] key, byte[] value, long seconds);

    boolean set(byte[] key, byte[] value);

    byte[] getBytes(String key);
}
