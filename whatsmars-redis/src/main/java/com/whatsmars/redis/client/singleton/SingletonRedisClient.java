package com.whatsmars.redis.client.singleton;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by javahongxi on 2017/6/22.
 */
public class SingletonRedisClient implements FactoryBean<JedisPool>,InitializingBean {
    private JedisPool jedisPool;

    private int maxTotal = 128;

    //最大空闲连接数
    private int maxIdle = 2;

    //最小空闲连接数
    private int minIdle = 1;
    //如果连接池耗尽，最大阻塞的时间，默认为3秒
    private long maxWait = 3000;//单位毫秒
    private String host;
    private int port;
    private int database = 0;//选择数据库，默认为0
    private int timeout = 3000;//connectionTimeout，soTimeout，默认为3秒

    private boolean testOnBorrow = true;
    private boolean testOnReturn = true;

    private String password;

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected JedisPoolConfig buildConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);
        config.setMaxWaitMillis(maxWait);
        config.setFairness(false);

        return config;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisPoolConfig config = buildConfig();
        jedisPool = new JedisPool(config,host,port,timeout, password, database,null);
    }

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

    public static void main(String[] args) throws Exception {
        SingletonRedisClient client = new SingletonRedisClient();
        client.setHost("10.142.103.52");
        client.setPort(1251);
        client.setPassword("71b5d8fe3c826c80");
        client.afterPropertiesSet();
        Jedis jedis = client.getObject().getResource();
        jedis.get("hello_world");
    }
}
