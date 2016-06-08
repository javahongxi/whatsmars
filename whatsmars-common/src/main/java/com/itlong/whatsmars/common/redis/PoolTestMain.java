package com.itlong.whatsmars.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by shenhongxi on 16/1/25.
 */
public class PoolTestMain {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        //config.setMaxActive(32);
        config.setMaxTotal(32);
        config.setMaxIdle(6);
        config.setMinIdle(0);
        //config.setMaxWait(15000);
        config.setMaxWaitMillis(15000);
        config.setMinEvictableIdleTimeMillis(300000);
        config.setSoftMinEvictableIdleTimeMillis(-1);
        config.setNumTestsPerEvictionRun(3);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        config.setTestWhileIdle(false);
        config.setTimeBetweenEvictionRunsMillis(60000);//一分钟
        //config.setWhenExhaustedAction((byte)1);
        config.setBlockWhenExhausted(true);
        JedisPool pool = new JedisPool(config,"127.0.0.1",6379,15000,"123456",12);
        Jedis client = pool.getResource();//从pool中获取资源
        try{
            client.select(0);
            client.set("k1", "v1");
            System.out.println(client.get("k1"));
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            pool.returnResource(client);//向连接池“归还”资源，千万不要忘记。
        }

    }

}
