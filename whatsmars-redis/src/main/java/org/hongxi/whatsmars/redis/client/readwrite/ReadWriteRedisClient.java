package org.hongxi.whatsmars.redis.client.readwrite;

import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by javahongxi on 2017/6/22.
 */
public class ReadWriteRedisClient implements InitializingBean {
    //master:port,slave:port,slave:port...
    //master first
    private String hosts;
    private JedisPool master;
    private List<JedisPool> slaves = new ArrayList<JedisPool>();

    private int maxTotal = 128;

    //最大空闲连接数
    private int maxIdle = 2;

    //最小空闲连接数
    private int minIdle = 1;
    //如果连接池耗尽，最大阻塞的时间，默认为3秒
    private long maxWait = 3000;//单位毫秒
    private int database = 0;//选择数据库，默认为0
    private int timeout = 3000;//connectionTimeout，soTimeout，默认为3秒

    private boolean testOnBorrow = true;
    private boolean testOnReturn = true;

    private String password;

    private Random random = new Random();

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

    public void setHosts(String hosts) {
        this.hosts = hosts;
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
        String[] hostAndPorts = hosts.split(",");
        String masterHP = hostAndPorts[0];
        String[] ms = masterHP.split(":");
        master = new JedisPool(config,ms[0],Integer.valueOf(ms[1]),timeout, password, database,null);
        if(hostAndPorts.length > 1) {
            for(int i = 1; i < hostAndPorts.length; i++) {
                String[] ss = hostAndPorts[i].split(":");
                JedisPool slave = new JedisPool(config,ss[0],Integer.valueOf(ss[1]),timeout, password, database,null);
                slaves.add(slave);
            }
        }
        slaves.add(master);
    }

    public String get(String key) {
        Jedis jedis = fetchResource(true);
        try {
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }

    public List<String> mget(String... keys) {
        Jedis jedis = fetchResource(true);
        try {
            return jedis.mget(keys);
        } finally {
            jedis.close();
        }
    }

    public String setex(String key,int seconds,String value) {
        Jedis jedis = fetchResource(false);
        try {
            return jedis.setex(key,seconds,value);
        } finally {
            jedis.close();
        }
    }

    public Long setnx(String key,String value) {
        Jedis jedis = fetchResource(false);
        try {
            return jedis.setnx(key,value);
        } finally {
            jedis.close();
        }
    }

    public String set(String key,String value) {
        Jedis jedis = fetchResource(false);
        try {
            return jedis.set(key,value);
        } finally {
            jedis.close();
        }
    }

    public Long del(String key) {
        Jedis jedis = fetchResource(false);
        try {
            return jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public Long expire(String key,int seconds) {
        Jedis jedis = fetchResource(false);
        try {
            return jedis.expire(key,seconds);
        } finally {
            jedis.close();
        }
    }

    public Boolean exists(String key) {
        Jedis jedis = fetchResource(false);
        try {
            return jedis.exists(key);
        } finally {
            jedis.close();
        }
    }

    public Long exists(String... keys) {
        Jedis jedis = fetchResource(false);
        try {
            return jedis.exists(keys);
        } finally {
            jedis.close();
        }
    }

    private Jedis fetchResource(boolean read) {
        if(slaves.isEmpty() || !read) {
            return master.getResource();
        }
        int size = slaves.size();
        int i = random.nextInt(size);
        return slaves.get(i).getResource();
    }


    public static void main(String[] args) throws Exception {
        String prefix = "_test_";
        ReadWriteRedisClient client = new ReadWriteRedisClient();
        client.setHosts("127.0.0.1:6379,127.0.0.1:6379");

        client.afterPropertiesSet();

        client.set(prefix + "10001","test");
        System.out.println(client.get(prefix + "10001"));
    }
}
