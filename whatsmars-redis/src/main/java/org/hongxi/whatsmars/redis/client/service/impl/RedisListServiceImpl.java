package org.hongxi.whatsmars.redis.client.service.impl;

import org.hongxi.whatsmars.redis.client.service.RedisListService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RedisListServiceImpl implements RedisListService {

	@Resource
	private RedisTemplate<String, List<Object>> redisTemplate;
	
	@Override
	public long rpush(byte[] key, byte[]... values) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.rPush(key, values);
			}
		});
	}

	@Override
	public long rpush(String key, byte[]... values) {
		if(values == null || values.length <= 0){
			return 0;
		}
		return this.rpush(key.getBytes(), values);
	}

	@Override
	public List<byte[]> lrange(byte[] key, long begin, long end) {
		return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lRange(key, begin, end);
			}
		});
	}

	@Override
	public List<byte[]> lrange(String key, long begin, long end) {
		return this.lrange(key.getBytes(), begin, end);
	}

	@Override
	public List<byte[]> lrange(String key) {
		return this.lrange(key, 0, -1);
	}

	@Override
	public long del(byte[] key) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.del(key);
			}
		});
	}

	@Override
	public long del(String key) {
		return this.del(key.getBytes());
	}

}
