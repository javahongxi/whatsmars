package org.hongxi.whatsmars.spring.data.service;

import org.elasticsearch.client.transport.TransportClient;
import org.hongxi.whatsmars.spring.data.repository.CustomerRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DataService implements InitializingBean {

    @Resource
    private RedisTemplate<Integer, String> redisTemplate;
    @Resource
    private TransportClient transportClient;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private CustomerRepository customerRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(redisTemplate);
        System.out.println(transportClient);
        System.out.println(mongoTemplate);
        System.out.println(customerRepository);
    }
}
