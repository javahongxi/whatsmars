package com.whatsmars.redis.client.service;

import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;

import java.util.Set;

public interface RedisSortedSetService {

	public long zadd(String key, Set<Tuple> tuples);
	
	public long zadd(byte[] key, Set<Tuple> tuples);
	
	public Set<byte[]> zrange(byte[] key, int start, int end);
	
	public Set<byte[]> zrange(String key);
	
	public Set<byte[]> zrange(String key, int start, int end);
}
