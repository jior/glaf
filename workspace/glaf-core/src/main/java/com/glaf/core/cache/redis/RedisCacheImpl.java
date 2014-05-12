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
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = redisPool.getResource();
			byte[] bytes = jedis.get(key.getBytes());
			if (!isEmpty(bytes)) {
				return this.deserialize(bytes);
			}
		} catch (Exception ex) {
			isBroken = true;
		} finally {
			returnResource(jedis, isBroken);
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
			Jedis jedis = null;
			boolean isBroken = false;
			try {
				jedis = redisPool.getResource();
				jedis.set(key.getBytes(), bytes);
			} catch (Exception ex) {
				isBroken = true;
			} finally {
				returnResource(jedis, isBroken);
			}
		}
	}

	public void remove(String key) {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = redisPool.getResource();
			jedis.del(key);
		} catch (Exception ex) {
			isBroken = true;
		} finally {
			returnResource(jedis, isBroken);
		}
	}

	/**
	 * 释放资源
	 * 
	 * @param jedis
	 * @param isBrokenResource
	 */
	public void returnResource(Jedis jedis, boolean isBrokenResource) {
		if (null == jedis) {
			return;
		}
		if (isBrokenResource) {
			redisPool.returnBrokenResource(jedis);
			jedis = null;
		} else {
			redisPool.returnResource(jedis);
		}
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
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = redisPool.getResource();
			jedis.shutdown();
		} catch (Exception ex) {
			isBroken = true;
		} finally {
			returnResource(jedis, isBroken);
		}
	}

	public int size() {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = redisPool.getResource();
			Long size = jedis.dbSize();
			return size.intValue();
		} catch (Exception ex) {
			isBroken = true;
		} finally {
			returnResource(jedis, isBroken);
		}
		return -1;
	}

}
