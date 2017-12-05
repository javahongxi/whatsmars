package com.whatsmars.redis.client.service;

import java.util.Collection;
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
	public long delete(String... keys);

	/**
	 * <pre>
	 *  
	 * 通过keys删除 
	 * &#64;param keys 
	 * &#64;return 被删除的记录数
	 * </pre>
	 */
	public long delete(Collection<String> keys);

	/**
	 * <pre>
	 *  
	 *  &#64;param key 
	 *  &#64;param value 
	 *  &#64;param activeTime 秒 
	 *  &#64;return 添加key value 并且设置存活时间
	 * </pre>
	 */
	public boolean set(byte[] key, byte[] value, long activeTime);

	/**
	 * <pre>
	 *  
	 * &#64;param key 
	 * &#64;param value 
	 * &#64;param activeTime 秒 
	 * &#64;return 添加key value 并且设置存活时间
	 * </pre>
	 */
	public boolean set(String key, String value, long activeTime);

	/**
	 * <pre>
	 *  
	 *  &#64;param key 
	 *  &#64;param value 
	 *  &#64;return 添加key value
	 * </pre>
	 */
	public boolean set(String key, String value);

	/**
	 * <pre>
	 *  
	 *  &#64;param key 
	 *  &#64;param value 
	 *  &#64;return 添加key value
	 * </pre>
	 */
	public boolean set(byte[] key, byte[] value);
	
	public boolean set(byte[] key, T value, long activeTime);

	/**
	 * <pre>
	 *  
	 * &#64;param key 
	 * &#64;return 获得value
	 * </pre>
	 */
	public String get(String key);
	
	public T getObject(String key, Class<T> c);
	
	public byte[] getB(String key);

	/**
	 * <pre>
	 *  
	 * &#64;param pattern 
	 * &#64;return 通过正则匹配keys
	 * </pre>
	 */
	public Set<String> matchKeys(String pattern);

	/**
	 * <pre>
	 *  
	 * &#64;param key 
	 * &#64;return 检查key是否已经存在
	 * </pre>
	 */
	public boolean exists(String key);

	/**
	 * <pre>
	 *  
	 * &#64;return 清空所有数据
	 * </pre>
	 */
	public boolean flushDB();

    void pubMsg(String channel, Object obj);

    public Set<String> getSet(String key);
}
