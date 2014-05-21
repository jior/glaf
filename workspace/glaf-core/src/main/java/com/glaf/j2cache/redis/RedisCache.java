/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.j2cache.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.glaf.j2cache.Cache;
import com.glaf.j2cache.CacheException;
import com.glaf.core.util.SerializationUtils;

/**
 * Redis 缓存实现
 * 
 * @author oschina.net
 */
public class RedisCache implements Cache {

	protected final static Logger log = LoggerFactory
			.getLogger(RedisCache.class);

	protected String region;

	protected int expireMinutes;

	public RedisCache(String region, int expireMinutes) {
		this.region = region;
		this.expireMinutes = expireMinutes;
	}

	@Override
	public void clear() throws CacheException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = RedisUtils.getResource();
			jedis.del(region + ":*");
			log.debug("###################################");
			log.debug(region + " clear from redis ");
			log.debug("###################################");
		} catch (Exception e) {
			broken = true;
			throw new CacheException(e);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	@Override
	public void destroy() throws CacheException {
		this.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.glaf.j2cache.Cache#batchRemove(java.util.List)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys == null || keys.size() == 0) {
			return;
		}
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource();
			String[] okeys = new String[keys.size()];
			for (int i = 0; i < okeys.length; i++) {
				okeys[i] = getKeyName(keys.get(i));
			}
			jedis.del(okeys);
		} catch (Exception e) {
			broken = true;
			throw new CacheException(e);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	@Override
	public void evict(Object key) throws CacheException {
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource();
			jedis.del(getKeyName(key));
			log.debug(key + " remove from redis cache");
		} catch (Exception e) {
			broken = true;
			throw new CacheException(e);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	@Override
	public Object get(Object key) throws CacheException {
		if (null == key) {
			return null;
		}
		Object object = null;
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource();
			byte[] b = jedis.get(getKeyName(key).getBytes());
			if (b != null) {
				object = SerializationUtils.unserialize(b);
			}
		} catch (Exception e) {
			log.error("Error occured when get data from L2 cache", e);
			broken = true;
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
		return object;
	}

	/**
	 * 生成缓存的 key
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getKeyName(Object key) {
		if (key instanceof Number) {
			return region + ":I:" + key;
		} else {
			Class keyClass = key.getClass();
			if (String.class.equals(keyClass)
					|| StringBuffer.class.equals(keyClass)
					|| StringBuilder.class.equals(keyClass))
				return region + ":S:" + key;
		}
		return region + ":O:" + key;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = RedisUtils.getResource();
			List<String> keys = new ArrayList<String>();
			keys.addAll(jedis.keys(region + ":*"));
			for (int i = 0; i < keys.size(); i++) {
				keys.set(i, keys.get(i).substring(region.length() + 3));
			}
			return keys;
		} catch (Exception e) {
			broken = true;
			throw new CacheException(e);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		if (value == null) {
			evict(key);
		} else {
			boolean broken = false;
			Jedis jedis = null;
			try {
				jedis = RedisUtils.getResource();
				jedis.set(getKeyName(key).getBytes(),
						SerializationUtils.serialize(value));
			} catch (Exception ex) {
				broken = true;
				ex.printStackTrace();
				throw new CacheException(ex);
			} finally {
				RedisUtils.returnResource(jedis, broken);
			}
		}
	}

	@Override
	public void update(Object key, Object value) throws CacheException {
		put(key, value);
	}

}
