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

package com.glaf.core.config.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.glaf.core.config.Config;
import com.glaf.core.util.SerializationUtils;

/**
 * Redis 分布式配置实现
 * 
 * @author oschina.net
 */
public class RedisConfig implements Config {

	private final static Logger log = LoggerFactory
			.getLogger(RedisConfig.class);

	public static void main(String[] args) {
		RedisConfig cache = new RedisConfig("User");
		System.out.println(cache.getKeyName("Hello"));
		System.out.println(cache.getKeyName(2));
		System.out.println(cache.getKeyName((byte) 2));
		System.out.println(cache.getKeyName(2L));
	}

	private String region;

	public RedisConfig(String region) {
		this.region = region;
	}

	@Override
	public void clear() {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = RedisUtils.getResource();
			jedis.del(region + ":*");
		} catch (Exception e) {
			broken = true;
			throw new RuntimeException(e);
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
			String[] okeys = new String[keys.size()];
			for (int i = 0; i < okeys.length; i++) {
				okeys[i] = getKeyName(keys.get(i));
			}
			jedis.del(okeys);
		} catch (Exception e) {
			broken = true;
			throw new RuntimeException(e);
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
		} catch (Exception e) {
			broken = true;
			throw new RuntimeException(e);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	public Object get(Object key) {
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

	public String getString(String key) {
		return (String) this.get(key);
	}

	@SuppressWarnings("rawtypes")
	public List keys() {
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
			throw new RuntimeException(e);
		} finally {
			RedisUtils.returnResource(jedis, broken);
		}
	}

	public void put(Object key, Object value) {
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
				throw new RuntimeException(ex);
			} finally {
				RedisUtils.returnResource(jedis, broken);
			}
		}
	}

	public void put(String key, String value) {
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
				throw new RuntimeException(ex);
			} finally {
				RedisUtils.returnResource(jedis, broken);
			}
		}
	}

	public void remove(String key) {
		this.evict(key);
	}

	public void update(Object key, Object value) {
		put(key, value);
	}
}
