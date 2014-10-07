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

package com.glaf.shiro.cache.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.glaf.core.util.SerializationUtils;

/**
 * Redis 分布式缓存实现
 * 
 * @author oschina.net
 */
public class RedisCache<K, V> implements org.apache.shiro.cache.Cache<K, V> {

	private final static Logger log = LoggerFactory.getLogger(RedisCache.class);

	private String region;

	public RedisCache(String region) {
		this.region = region;
	}

	@Override
	public void clear() {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = RedisUtils.getResource();
			jedis.del(region + ":*");
		} catch (Exception ex) {
			broken = true;
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) {
		if (keys == null || keys.size() == 0) {
			return;
		}
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource();
			String[] newkeys = new String[keys.size()];
			for (int i = 0; i < newkeys.length; i++) {
				newkeys[i] = getKeyName(keys.get(i));
			}
			jedis.del(newkeys);
		} catch (Exception ex) {
			broken = true;
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	public void evict(Object key) {
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource();
			jedis.del(getKeyName(key));
		} catch (Exception ex) {
			broken = true;
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	@SuppressWarnings("unchecked")
	public V get(K key) throws CacheException {
		if (null == key) {
			return null;
		}
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource();
			byte[] raw = jedis.get(getKeyName(key).getBytes());
			return (V) SerializationUtils.unserialize(raw);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Error occured when get data from redis", ex);
			broken = true;
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
		return null;
	}

	public byte[] getData(String key) {
		if (null == key) {
			return null;
		}
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource();
			byte[] raw = jedis.get(getKeyName(key).getBytes());
			return raw;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Error occured when get data from redis", ex);
			broken = true;
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
		return null;
	}

	/**
	 * 生成分布式配置的 key
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

	@SuppressWarnings("unchecked")
	public Set<K> keys() {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = RedisUtils.getResource();
			Set<String> keys = new HashSet<String>();
			List<String> list = new ArrayList<String>();
			list.addAll(jedis.keys(region + ":*"));
			for (int i = 0; i < list.size(); i++) {
				list.set(i, list.get(i).substring(region.length() + 3));
				keys.add(list.get(i));
			}
			return (Set<K>) keys;
		} catch (Exception ex) {
			broken = true;
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	public V put(K key, V value) throws CacheException {
		if (value == null) {
			evict(key);
		} else {
			byte[] name = getKeyName(key).getBytes();
			byte[] data = SerializationUtils.serialize(value);
			boolean broken = false;
			Jedis jedis = null;
			try {
				jedis = RedisUtils.getResource();
				jedis.set(name, data);
			} catch (Exception ex) {
				broken = true;
				ex.printStackTrace();
				throw new RuntimeException(
						"Error occured when put data into redis", ex);
			} finally {
				RedisUtils.returnResource(jedis, broken);
			}
		}
		return null;
	}

	public void put(String key, byte[] value) {
		if (value == null) {
			evict(key);
		} else {
			boolean broken = false;
			Jedis jedis = null;
			try {
				jedis = RedisUtils.getResource();
				jedis.set(getKeyName(key).getBytes(), value);
			} catch (Exception ex) {
				broken = true;
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				RedisUtils.returnResource(jedis, broken);
			}
		}
	}

	public V remove(K key) throws CacheException {
		this.evict(key);
		return null;
	}

	public void remove(String key) {
		this.evict(key);
	}

	public int size() {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = RedisUtils.getResource();
			Long size = jedis.dbSize();
			return size.intValue();
		} catch (Exception ex) {
			isBroken = true;
			ex.printStackTrace();
		} finally {
			RedisUtils.returnResource(jedis, isBroken);
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = RedisUtils.getResource();
			Collection<V> values = new HashSet<V>();
			List<String> list = new ArrayList<String>();
			list.addAll(jedis.keys(region + ":*"));
			for (int i = 0; i < list.size(); i++) {
				list.set(i, list.get(i).substring(region.length() + 3));
				String key = list.get(i);
				byte[] raw = jedis.get(getKeyName(key).getBytes());
				V value = (V) SerializationUtils.unserialize(raw);
				values.add(value);
			}
			return values;
		} catch (Exception ex) {
			broken = true;
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

}
