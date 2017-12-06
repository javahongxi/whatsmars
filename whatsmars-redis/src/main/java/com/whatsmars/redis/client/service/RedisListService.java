package com.whatsmars.redis.client.service;

import java.util.List;

public interface RedisListService {

	long rpush(byte[] key, byte[]... values);

	long rpush(String key, byte[]... values);
	
	List<byte[]> lrange(byte[] key, long begin, long end);
	
	List<byte[]> lrange(String key, long begin, long end);
	
	List<byte[]> lrange(String key);
	
	long del(byte[] key);
	
	long del(String key);
}
