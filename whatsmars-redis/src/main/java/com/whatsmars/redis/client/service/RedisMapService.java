package com.whatsmars.redis.client.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisMapService {

	boolean hMSet(String redisKey, Map<byte[], byte[]> valueMap);
	
	Set<byte[]> hKeys(String redisKey);
	
	Map<byte[], byte[]> hGetAll(String redisKey);
	
	List<byte[]> hMGet(String redisKey, List<String> mapKeys);
	
	byte[] hGet(String redisKey, String mapKey);
	
	long hDel(String redisKey, List<String> mapKeys);
	
	long hDel(String redisKey, byte[] mapKey);
	
	boolean hSet(String redisKey, String mapKey, String mapValue);
	
	String hGet(String redisKey, byte[] mapKey);
}
