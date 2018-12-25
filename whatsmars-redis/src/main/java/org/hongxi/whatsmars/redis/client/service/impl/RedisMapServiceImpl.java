package org.hongxi.whatsmars.redis.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.redis.client.service.RedisMapService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class RedisMapServiceImpl implements RedisMapService {

	private static final String CHARSET = "UTF8";

	@Resource
	private RedisTemplate<String, Map<byte[], byte[]>> redisTemplate;
	
	@Override
	public boolean hMSet(String redisKey, Map<byte[], byte[]> valueMap) {
		if(CollectionUtils.isEmpty(valueMap)){
			return false;
		}
		redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.hMSet(redisKey.getBytes(), valueMap);
				return true;
			}
		});
		return false;
	}

	@Override
	public Set<byte[]> hKeys(String redisKey) {
		return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hKeys(redisKey.getBytes());
			}
		});
	}

	@Override
	public Map<byte[], byte[]> hGetAll(String redisKey) {
		return redisTemplate.execute(new RedisCallback<Map<byte[], byte[]>>() {

			@Override
			public Map<byte[], byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hGetAll(redisKey.getBytes());
			}
		});
	}

	@Override
	public List<byte[]> hMGet(String redisKey, List<String> mapKeys) {
		return redisTemplate.execute(new RedisCallback<List<byte[]>>() {

			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[][] keys = new byte[mapKeys.size()][];
				
				for (int i = 0; i < mapKeys.size(); i++) {
					keys[i] = mapKeys.get(i).getBytes();
				}
				
				return connection.hMGet(redisKey.getBytes(), keys);
			}
		});
	}

	@Override
	public byte[] hGet(String redisKey, String mapKey) {
		return redisTemplate.execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hGet(redisKey.getBytes(), mapKey.getBytes());
			}
		});
	}

	@Override
	public long hDel(String redisKey, List<String> mapKeys) {
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[][] keys = new byte[mapKeys.size()][];
				
				for (int i = 0; i < mapKeys.size(); i++) {
					keys[i] = mapKeys.get(i).getBytes();
				}
				return connection.hDel(redisKey.getBytes(), keys);
			}
		});
	}

	@Override
	public boolean hSet(String redisKey, String mapKey, String mapValue) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hSet(redisKey.getBytes(), mapKey.getBytes(), mapValue.getBytes());
			}
		});
	}

	@Override
	public String hGetString(String redisKey, String mapKey) {
		byte[] value = hGet(redisKey, mapKey);
		try {
			return value == null ? null : new String(value, CHARSET);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String hGet(String redisKey, byte[] mapKey) {
		return redisTemplate.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] value = connection.hGet(redisKey.getBytes(), mapKey);
				if(null == value){
					return null;
				}
				return new String(value);
			}
		});
	}

	@Override
	public long hDel(String redisKey, byte[] mapKey) {
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hDel(redisKey.getBytes(), mapKey);
			}
		});
	}

}
