package com.itlong.whatsmars.common.redis;

import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by shenhongxi on 2016/1/21.
 */
public class RedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6380);
        //jedis.auth("admin"); // password
        String key = "t";
        jedis.set(key, "javared");
        System.out.println(jedis.get(key));
        jedis.append(key, "&wely");
        System.out.println(jedis.get(key));
        jedis.del(key);
        System.out.println(jedis.get(key));

        Jedis jedis2 = new Jedis("127.0.0.1", 6379);
        //jedis2.auth("admin");
        jedis2.set(key, "wely.com");
        System.out.println(jedis2.get(key));

        // 批量插入
        jedis.mset("name", "hongxi", "age", "23", "qq", "1037256085");
        jedis.incr("age");
        System.out.println(jedis.get("name") + "_" + jedis.get("age") + "_" + jedis.get("qq"));

        // map
        Map<String, String> user = new HashMap<String, String>();
        user.put("name", "hongxi");
        user.put("age", "23");
        user.put("qq", "1037256085");
        String hmKey = "user";
        jedis.hmset(hmKey, user);
        List<String> list = jedis.hmget(hmKey, "name", "age", "qq");
        System.out.println(list);
        jedis.hdel(hmKey, "name");
        list = jedis.hmget(hmKey, "name", "age", "qq");
        System.out.println(list);
        System.out.println(jedis.hlen(hmKey));
        System.out.println(jedis.exists(hmKey));
        System.out.println(jedis.hkeys(hmKey));
        System.out.println(jedis.hvals(hmKey));
        Iterator<String> iter=jedis.hkeys("user").iterator();
        while (iter.hasNext()){
            String _key = iter.next();
            System.out.println(_key + ":" + jedis.hmget("user", _key));
        }

        // list
        String listKey = "Java Framework";
        jedis.del(listKey);
        System.out.println(jedis.lrange(listKey, 0, -1));
        jedis.lpush(listKey, "spring");
        jedis.lpush(listKey, "restEasy");
        jedis.lpush(listKey, "mybatis");
        System.out.println(jedis.lrange(listKey, 0, -1));
        jedis.del(listKey);
        jedis.rpush(listKey, "spring");
        jedis.rpush(listKey, "restEasy");
        jedis.rpush(listKey, "mybatis");
        System.out.println(jedis.lrange(listKey, 0, -1));

        // set
        String setKey = "lang";
        Long size = jedis.sadd(setKey, "hongxi", "wely", "norip");
        System.out.println(size);
        jedis.srem(setKey, "norip");
        Set<String> set = jedis.smembers(setKey);
        System.out.println(set);
        System.out.println(jedis.sismember(setKey, "hongxi"));
        System.out.println(jedis.srandmember(setKey));
        System.out.println(jedis.scard(setKey));

        // 排序
        jedis.del("a");
        jedis.rpush("a", "1");
        jedis.lpush("a","6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1)); // [9, 3, 6, 1]
        System.out.println(jedis.sort("a")); // [1, 3, 6, 9]
        System.out.println(jedis.lrange("a",0,-1)); // [9, 3, 6, 1]
    }
}
