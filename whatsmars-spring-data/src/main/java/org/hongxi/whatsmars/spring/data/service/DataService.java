package org.hongxi.whatsmars.spring.data.service;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DataService {

    @Resource
    private RedisTemplate<Integer, String> redisTemplate;
    @Resource
    private TransportClient transportClient;
}
