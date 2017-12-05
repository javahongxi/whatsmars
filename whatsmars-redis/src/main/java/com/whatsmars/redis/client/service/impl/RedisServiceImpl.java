package com.whatsmars.redis.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsmars.redis.client.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service("redisService")
public class RedisServiceImpl<T> implements RedisService<T> {

	private static final String CHARSET = "UTF8";

	@Resource
	private RedisTemplate<String, Serializable> redisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public boolean set(final byte[] key, final byte[] value, final long activeTime) {
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
	
	public boolean set(byte[] key, T value, long activeTime){
		ObjectMapper objectMapper = new ObjectMapper();
		byte[] b = null;
		try {
			b = objectMapper.writeValueAsBytes(value);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		return this.set(key, b, activeTime);
	}

	@Override
	public String get(final String key) {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] value = connection.get(key.getBytes());
					return value == null ? "" : new String(value, CHARSET);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return "";
			}
		});
	}

	@Override
	public T getObject(final String key, final Class<T> c) {
		return redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] value = connection.get(key.getBytes());
				if(null == value){
					return null;
				}
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					return objectMapper.readValue(value, c);
				} catch (JsonParseException e) {

					e.printStackTrace();
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
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
	public boolean exists(final String key) {
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
	public long delete(final Collection<String> keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				for (String key : keys) {
					result = connection.del(key.getBytes());
				}
				return result;
			}
		});
	}

	@Override
	public long delete(final String... keys) {
		Collection<String> cols = new ArrayList<String>();
		for (String key : keys) {
			cols.add(key);
		}
		return this.delete(cols);
	}

	@Override
	public byte[] getB(final String key) {
		return redisTemplate.execute(new RedisCallback<byte[]>() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
	}

	@Override
	public void pubMsg(String channel, Object obj) {
		assert null != obj;
		String msg = obj instanceof String ? String.valueOf(obj) : JSON.toJSONString(obj);
		stringRedisTemplate.convertAndSend(channel, msg);
	}

	@Override
	public Set<String> getSet(String key) {
		return stringRedisTemplate.opsForSet().members(key);
	}

}
