package org.hongxi.whatsmars.redis.client.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shenhongxi on 2018/12/24.
 */
public interface RedisStringService {

    void set(String key, String value);

    void set(String key, String value, long activeTime);

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

    long addSet(String key, String... values);

    Set<String> getSet(String key);

    void pubMsg(String channel, Object obj);
}
