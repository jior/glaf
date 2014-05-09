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

import java.util.Properties;

import com.glaf.j2cache.Cache;
import com.glaf.j2cache.CacheException;
import com.glaf.j2cache.CacheExpiredListener;
import com.glaf.j2cache.CacheProvider;
import com.glaf.j2cache.redis.RedisCache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Redis 缓存实现
 */
public class RedisCacheProvider implements CacheProvider {

	private static String host;
	private static int port;
	private static int timeout;
	private static String password;
	private static int database;

	private static JedisPool pool;

	@Override
	public String name() {
		return "redis";
	}

	/**
	 * 释放资源
	 * 
	 * @param jedis
	 * @param isBrokenResource
	 */
	public static void returnResource(Jedis jedis, boolean isBrokenResource) {
		if (null == jedis) {
			return;
		}
		if (isBrokenResource) {
			pool.returnBrokenResource(jedis);
			jedis = null;
		} else {
			pool.returnResource(jedis);
		}
	}

	public static Jedis getResource() {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new JedisConnectionException(ex);
		}  
	}

	@Override
	public Cache buildCache(String regionName, boolean autoCreate,
			CacheExpiredListener listener) throws CacheException {
		return new RedisCache(regionName);
	}

	@Override
	public void start(Properties props) throws CacheException {
		JedisPoolConfig config = new JedisPoolConfig();

		host = getProperty(props, "host", "127.0.0.1");
		password = props.getProperty("password", null);

		port = getProperty(props, "port", 6379);
		timeout = getProperty(props, "timeout", 2000);
		database = getProperty(props, "database", 0);

		// config.setWhenExhaustedAction((byte)getProperty(props,
		// "whenExhaustedAction",1));
		config.setMaxIdle(getProperty(props, "maxIdle", 10));
		config.setMinIdle(getProperty(props, "minIdle", 5));
		// config.setMaxActive(getProperty(props, "maxActive", 50));
		// config.setMaxWait(getProperty(props, "maxWait", 100));
		config.setTestWhileIdle(getProperty(props, "testWhileIdle", false));
		config.setTestOnBorrow(getProperty(props, "testOnBorrow", true));
		config.setTestOnReturn(getProperty(props, "testOnReturn", false));
		config.setNumTestsPerEvictionRun(getProperty(props,
				"numTestsPerEvictionRun", 10));
		config.setMinEvictableIdleTimeMillis(getProperty(props,
				"minEvictableIdleTimeMillis", 1000));
		config.setSoftMinEvictableIdleTimeMillis(getProperty(props,
				"softMinEvictableIdleTimeMillis", 10));
		config.setTimeBetweenEvictionRunsMillis(getProperty(props,
				"timeBetweenEvictionRunsMillis", 10));
		// config.lifo = getProperty(props, "lifo", false);

		pool = new JedisPool(config, host, port, timeout, password, database);

	}

	@Override
	public void stop() {
		pool.destroy();
	}

	private static String getProperty(Properties props, String key,
			String defaultValue) {
		return props.getProperty(key, defaultValue).trim();
	}

	private static int getProperty(Properties props, String key,
			int defaultValue) {
		try {
			return Integer.parseInt(props.getProperty(key,
					String.valueOf(defaultValue)).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	private static boolean getProperty(Properties props, String key,
			boolean defaultValue) {
		return "true".equalsIgnoreCase(props.getProperty(key,
				String.valueOf(defaultValue)).trim());
	}
}
