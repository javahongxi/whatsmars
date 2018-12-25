package org.hongxi.whatsmars.redis.client.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RedisService<T> {
	/**
	 * <pre>
	 *  
	 * 通过key删除 
	 * &#64;param keys 
	 * &#64;return 被删除的记录数
	 * </pre>
	 */
	long delete(String... keys);

	/**
	 * <pre>
	 *  
	 * 通过keys删除 
	 * &#64;param keys 
	 * &#64;return 被删除的记录数
	 * </pre>
	 */
	long delete(Collection<String> keys);

	/**
	 * <pre>
	 *  
	 *  &#64;param key 
	 *  &#64;param value 
	 *  &#64;param activeTime 秒 
	 *  &#64;return 添加key value 并且设置存活时间
	 * </pre>
	 */
	boolean set(byte[] key, byte[] value, long activeTime);

	/**
	 * <pre>
	 *  
	 * &#64;param key 
	 * &#64;param value 
	 * &#64;param activeTime 秒 
	 * &#64;return 添加key value 并且设置存活时间
	 * </pre>
	 */
	boolean set(String key, String value, long activeTime);

	/**
	 * <pre>
	 *  
	 *  &#64;param key 
	 *  &#64;param value 
	 *  &#64;return 添加key value
	 * </pre>
	 */
	boolean set(String key, String value);

	/**
	 * <pre>
	 *  
	 *  &#64;param key 
	 *  &#64;param value 
	 *  &#64;return 添加key value
	 * </pre>
	 */
	boolean set(byte[] key, byte[] value);
	
	boolean set(byte[] key, T value, long activeTime);

	boolean set(String key, T value, long activeTime);

	boolean set(String key, T value);

	/**
	 * <pre>
	 *  
	 * &#64;param key 
	 * &#64;return 获得value
	 * </pre>
	 */
	String get(String key);
	
	T getObject(String key, Class<T> c);
	
	byte[] getBytes(String key);

	/**
	 * <pre>
	 *  
	 * &#64;param pattern 
	 * &#64;return 通过正则匹配keys
	 * </pre>
	 */
	Set<String> matchKeys(String pattern);

	/**
	 * <pre>
	 *  
	 * &#64;param key 
	 * &#64;return 检查key是否已经存在
	 * </pre>
	 */
	boolean exists(String key);

	/**
	 * <pre>
	 *  
	 * &#64;return 清空所有数据
	 * </pre>
	 */
	boolean flushDB();

	/**
	 * <pre>
	 *
	 * &#64;param keys
	 * &#64;return 批量获取key的值
	 * </pre>
	 */
    List<T> multiGet(Collection keys);
}
