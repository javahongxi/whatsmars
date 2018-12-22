package org.hongxi.whatsmars.redis.client.singleton;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by javahongxi on 2017/6/22.
 */
public class SingletonRedisClient implements FactoryBean<JedisPool>, InitializingBean, DisposableBean {

    private JedisPool jedisPool;

    private JedisPoolConfig jedisPoolConfig;

    private String host;

    private int port;

    private int timeout = 3000;

    @Override
    public JedisPool getObject() throws Exception {
        return jedisPool;
    }

    @Override
    public Class<?> getObjectType() {
        return JedisPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
    }

    @Override
    public void destroy() throws Exception {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
