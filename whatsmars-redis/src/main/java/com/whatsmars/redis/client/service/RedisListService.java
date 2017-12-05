package com.whatsmars.redis.client.service;

import java.util.List;

public interface RedisListService {

	public long rpush(byte[] key, byte[]... values);

	public long rpush(String key, byte[]... values);
	
	public List<byte[]> lrange(byte[] key, long begin, long end);
	
	public List<byte[]> lrange(String key, long begin, long end);
	
	public List<byte[]> lrange(String key);
	
	public long del(byte[] key);
	
	public long del(String key);
}
