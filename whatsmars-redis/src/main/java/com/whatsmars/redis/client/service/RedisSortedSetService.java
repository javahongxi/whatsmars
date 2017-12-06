package com.whatsmars.redis.client.service;

import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;

import java.util.Set;

public interface RedisSortedSetService {

	long zadd(String key, Set<Tuple> tuples);
	
	long zadd(byte[] key, Set<Tuple> tuples);
	
	Set<byte[]> zrange(byte[] key, int start, int end);
	
	Set<byte[]> zrange(String key);
	
	Set<byte[]> zrange(String key, int start, int end);
}
