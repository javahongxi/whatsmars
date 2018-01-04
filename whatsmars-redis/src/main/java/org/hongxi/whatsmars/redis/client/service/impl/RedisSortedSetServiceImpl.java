package org.hongxi.whatsmars.redis.client.service.impl;

import org.hongxi.whatsmars.redis.client.service.RedisSortedSetService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service("redisSortedSetService")
public class RedisSortedSetServiceImpl implements RedisSortedSetService {

	@Resource
	private RedisTemplate<String, Set<Object>> redisTemplate;
	
	@Override
	public long zadd(final byte[] key, final Set<Tuple> tuples) {
		redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.zAdd(key, tuples);
			}
		});
		return 0;
	}

	@Override
	public Set<byte[]> zrange(final byte[] key, final int start, final int end) {
		return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.zRange(key, start, end);
			}
		});
	}

	@Override
	public long zadd(String key, Set<Tuple> tuples) {
		return this.zadd(key.getBytes(), tuples);
	}

	@Override
	public Set<byte[]> zrange(String key) {
		return this.zrange(key, 0, -1);
	}

	@Override
	public Set<byte[]> zrange(String key, int start, int end) {
		return this.zrange(key.getBytes(), start, end);
	}


}
