package com.itlong.whatsmars.redis.client.cluster;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by javahongxi on 2017/6/22.
 */
public class RedisClusterClient implements FactoryBean<JedisCluster>,InitializingBean {
    private JedisCluster jedisCluster;

    private int maxTotal = 128;

    //最大空闲连接数
    private int maxIdle = 6;

    //最小空闲连接数
    private int minIdle = 1;
    //如果连接池耗尽，最大阻塞的时间，默认为3秒
    private long maxWait = 3000;//单位毫秒

    private int timeout = 3000;//connectionTimeout，soTimeout，默认为3秒

    private boolean testOnBorrow = true;
    private boolean testOnReturn = true;

    private String addresses;//ip:port,ip:port

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

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
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

    private Set<HostAndPort> buildHostAndPorts() {
        String[] hostPorts = addresses.split(",");
        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        for(String item : hostPorts) {
            String[] hostPort = item.split(":");
            HostAndPort hostAndPort = new HostAndPort(hostPort[0],Integer.valueOf(hostPort[1]));
            hostAndPorts.add(hostAndPort);
        }
        return hostAndPorts;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisPoolConfig config = buildConfig();
        Set<HostAndPort> hostAndPorts = buildHostAndPorts();
        jedisCluster = new JedisCluster(hostAndPorts,timeout,config);
    }

    @Override
    public JedisCluster getObject() throws Exception {
        return jedisCluster;
    }

    @Override
    public Class<?> getObjectType() {
        return JedisCluster.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
