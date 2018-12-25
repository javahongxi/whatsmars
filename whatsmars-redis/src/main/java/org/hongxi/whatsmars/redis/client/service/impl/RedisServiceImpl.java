package org.hongxi.whatsmars.redis.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.redis.client.service.RedisService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class RedisServiceImpl<T> implements RedisService<T> {

	private static final String CHARSET = "UTF8";

	@Resource
	private RedisTemplate<String, Serializable> redisTemplate;

	@Override
	public boolean set(byte[] key, byte[] value, long activeTime) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				boolean rs = true;
				connection.set(key, value);
				if (activeTime > 0) {
					rs = connection.expire(key, activeTime);
				}
				return rs;
			}
		});
	}

	@Override
	public boolean set(String key, String value, long activeTime) {
		return this.set(key.getBytes(), value.getBytes(), activeTime);
	}

	@Override
	public boolean set(String key, String value) {
		return this.set(key, value, 0L);
	}

	@Override
	public boolean set(byte[] key, byte[] value) {
		return this.set(key, value, 0L);
	}

	@Override
	public boolean set(byte[] key, T value, long activeTime){
		ObjectMapper objectMapper = new ObjectMapper();
		byte[] b = null;
		try {
			b = objectMapper.writeValueAsBytes(value);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return this.set(key, b, activeTime);
	}

	@Override
	public boolean set(String key, T value, long activeTime) {
		return this.set(key.getBytes(), value, activeTime);
	}

	@Override
	public boolean set(String key, T value) {
		return this.set(key, value, 0L);
	}

	@Override
	public String get(String key) {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] value = connection.get(key.getBytes());
					return value == null ? null : new String(value, CHARSET);
				} catch (UnsupportedEncodingException e) {
					log.error(e.getMessage(), e);
				}
				return null;
			}
		});
	}

	@Override
	public T getObject(String key, Class<T> c) {
		return redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] value = connection.get(key.getBytes());
				if(null == value){
					return null;
				}
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					return objectMapper.readValue(value, c);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return null;
			}
		});
	}

	@Override
	public Set<String> matchKeys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	@Override
	public boolean exists(String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
	}

	@Override
	public boolean flushDB() {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return true;
			}
		});
	}

	@Override
	public long delete(Collection<String> keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				for (String key : keys) {
					result += connection.del(key.getBytes());
				}
				return result;
			}
		});
	}

	@Override
	public long delete(String... keys) {
		Collection<String> cols = new ArrayList<String>();
		for (String key : keys) {
			cols.add(key);
		}
		return this.delete(cols);
	}

	@Override
	public byte[] getBytes(String key) {
		return redisTemplate.execute(new RedisCallback<byte[]>() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
	}

	@Override
	public List<T> multiGet(Collection keys) {
		return redisTemplate.opsForValue().multiGet(keys);
	}

}
