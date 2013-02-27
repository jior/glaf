package com.glaf.core.cache.redis;

import java.nio.charset.Charset;

import org.codehaus.jackson.map.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.glaf.core.cache.Cache;

public class RedisCacheImpl implements Cache {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private static final byte[] EMPTY_ARRAY = new byte[0];

	protected ObjectMapper objectMapper = new ObjectMapper();

	protected JedisPool redisPool;

	public RedisCacheImpl() {

	}

	public void clear() {

	}

	public Object deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		try {
			return this.objectMapper.readValue(bytes, 0, bytes.length,
					Object.class);
		} catch (Exception ex) {
			throw new RuntimeException("Could not read JSON: "
					+ ex.getMessage(), ex);
		}
	}

	public Object get(String key) {
		Jedis jedis = redisPool.getResource();
		byte[] bytes = jedis.get(key.getBytes());
		redisPool.returnResource(jedis);
		if (!isEmpty(bytes)) {
			return this.deserialize(bytes);
		}
		return null;
	}

	public JedisPool getRedisPool() {
		return redisPool;
	}

	protected boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

	public void put(String key, Object value) {
		byte[] bytes = this.serialize(value);
		if (!isEmpty(bytes)) {
			Jedis jedis = redisPool.getResource();
			jedis.set(key.getBytes(), bytes);
			redisPool.returnResource(jedis);
		}
	}

	public void remove(String key) {
		Jedis jedis = redisPool.getResource();
		jedis.del(key);
		redisPool.returnResource(jedis);
	}

	public byte[] serialize(Object t) {
		if (t == null) {
			return EMPTY_ARRAY;
		}
		try {
			return this.objectMapper.writeValueAsBytes(t);
		} catch (Exception ex) {
			throw new RuntimeException("Could not write JSON: "
					+ ex.getMessage(), ex);
		}
	}

	public void setRedisPool(JedisPool redisPool) {
		this.redisPool = redisPool;
	}

	public void shutdown() {
		Jedis jedis = redisPool.getResource();
		jedis.shutdown();
		redisPool.returnResource(jedis);
	}

	public int size() {
		Jedis jedis = redisPool.getResource();
		Long size = jedis.dbSize();
		redisPool.returnResource(jedis);
		return size.intValue();
	}

}
