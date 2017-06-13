package com.itlong.whatsmars.worker.base;

/**
 * Created by shenhongxi on 2016/7/12.
 */
public interface CacheService {

    void set(String key, String value, long seconds);

    String get(String key);
}
