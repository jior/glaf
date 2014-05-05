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

package com.glaf.j2cache;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存管理器
 * 
 * @author oschina.net
 */
class CacheManager {

	private final static Logger log = LoggerFactory
			.getLogger(CacheManager.class);
	private final static String CONFIG_FILE = "/conf/j2cache/j2cache.properties";

	private static CacheProvider l1_provider;
	private static CacheProvider l2_provider;

	private static CacheExpiredListener listener;

	private final static Cache _GetCache(int level, String cache_name,
			boolean autoCreate) {
		return ((level == 1) ? l1_provider : l2_provider).buildCache(
				cache_name, autoCreate, listener);
	}

	/**
	 * 批量删除缓存中的一些数据
	 * 
	 * @param level
	 * @param name
	 * @param keys
	 */
	@SuppressWarnings("rawtypes")
	public final static void batchEvict(int level, String name, List keys) {
		if (name != null && keys != null && keys.size() > 0) {
			Cache cache = _GetCache(level, name, false);
			if (cache != null) {
				cache.evict(keys);
			}
		}
	}

	/**
	 * Clear the cache
	 */
	public final static void clear(int level, String name)
			throws CacheException {
		Cache cache = _GetCache(level, name, false);
		if (cache != null) {
			cache.clear();
		}
	}

	/**
	 * 清除缓存中的某个数据
	 * 
	 * @param level
	 * @param name
	 * @param key
	 */
	public final static void evict(int level, String name, Object key) {
		// batchEvict(level, name, java.util.Arrays.asList(key));
		if (name != null && key != null) {
			Cache cache = _GetCache(level, name, false);
			if (cache != null) {
				cache.evict(key);
			}
		}
	}

	/**
	 * 获取缓存中的数据
	 * 
	 * @param <T>
	 * @param level
	 * @param resultClass
	 * @param name
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final static <T> T get(int level, Class<T> resultClass, String name,
			Object key) {
		// System.out.println("GET2 => " + name+":"+key);
		if (name != null && key != null) {
			Cache cache = _GetCache(level, name, false);
			if (cache != null) {
				return (T) cache.get(key);
			}
		}
		return null;
	}

	/**
	 * 获取缓存中的数据
	 * 
	 * @param level
	 * @param name
	 * @param key
	 * @return
	 */
	public final static Object get(int level, String name, Object key) {
		// System.out.println("GET1 => " + name+":"+key);
		if (name != null && key != null) {
			Cache cache = _GetCache(level, name, false);
			if (cache != null) {
				return cache.get(key);
			}
		}
		return null;
	}

	private final static CacheProvider getProviderInstance(String value)
			throws Exception {
		String className = value;
		if ("ehcache".equalsIgnoreCase(value)) {
			className = "com.glaf.j2cache.ehcache.EhCacheProvider";
		}
		if ("redis".equalsIgnoreCase(value)) {
			className = "com.glaf.j2cache.redis.RedisCacheProvider";
		}
		if ("guava".equalsIgnoreCase(value)) {
			className = "com.glaf.j2cache.guava.GuavaCacheProvider";
		}
		if ("hazelcast".equalsIgnoreCase(value)) {
			className = "com.glaf.j2cache.hazelcast.HazelcastCacheProvider";
		}
		if ("mongodb".equalsIgnoreCase(value)) {
			className = "com.glaf.j2cache.mongodb.MongodbCacheProvider";
		}
		if ("spymemcached".equalsIgnoreCase(value)) {
			className = "com.glaf.j2cache.spymemcache.SpyMemCacheProvider";
		}
		if ("xmemcached".equalsIgnoreCase(value)) {
			className = "com.glaf.j2cache.xmemcache.XMemCacheProvider";
		}
		if ("map".equalsIgnoreCase(value)) {
			className = "com.glaf.j2cache.MapCacheProvider";
		}
		if ("none".equalsIgnoreCase(value)) {
			className = "com.glaf.j2cache.NullCacheProvider";
		}
		return (CacheProvider) Class.forName(className).newInstance();
	}

	private final static Properties getProviderProperties(Properties props,
			CacheProvider provider) {
		Properties new_props = new Properties();
		Enumeration<Object> keys = props.keys();
		String prefix = provider.name() + '.';
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix))
				new_props.setProperty(key.substring(prefix.length()),
						props.getProperty(key));
		}
		return new_props;
	}

	public static void initCacheProvider(CacheExpiredListener listener) {
		InputStream configStream = null;
		Properties props = new Properties();
		CacheManager.listener = listener;
		try {
			configStream = FileUtils.getInputStream(SystemProperties
					.getConfigRootPath() + CONFIG_FILE);
			props.load(configStream);

			CacheManager.l1_provider = getProviderInstance(props
					.getProperty("cache.L1.provider_class"));
			CacheManager.l1_provider.start(getProviderProperties(props,
					CacheManager.l1_provider));
			log.info("Using L1 CacheProvider : "
					+ l1_provider.getClass().getName());

			CacheManager.l2_provider = getProviderInstance(props
					.getProperty("cache.L2.provider_class"));
			CacheManager.l2_provider.start(getProviderProperties(props,
					CacheManager.l2_provider));
			log.info("Using L2 CacheProvider : "
					+ l2_provider.getClass().getName());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CacheException("Unabled to initialize cache providers",
					ex);
		} finally {
			IOUtils.closeStream(configStream);
		}
	}

	@SuppressWarnings("rawtypes")
	public final static List keys(int level, String name) throws CacheException {
		Cache cache = _GetCache(level, name, false);
		return (cache != null) ? cache.keys() : null;
	}

	/**
	 * 写入缓存
	 * 
	 * @param level
	 * @param name
	 * @param key
	 * @param value
	 */
	public final static void set(int level, String name, Object key,
			Object value) {
		// System.out.println("SET => " + name+":"+key+"="+value);
		if (name != null && key != null && value != null) {
			Cache cache = _GetCache(level, name, true);
			if (cache != null) {
				cache.put(key, value);
			}
		}
	}

}
