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

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis 缓存实现
 */
public class RedisCacheProvider implements CacheProvider {
	private String host;
	private int port;
	private int timeout;
	private String password;
	private int database;
	protected int expireMinutes;

	@Override
	public Cache buildCache(String regionName, boolean autoCreate,
			CacheExpiredListener listener) throws CacheException {
		return new RedisCache(regionName, expireMinutes);
	}

	public int getExpireMinutes() {
		return expireMinutes;
	}

	private boolean getProperty(Properties props, String key,
			boolean defaultValue) {
		return "true".equalsIgnoreCase(props.getProperty(key,
				String.valueOf(defaultValue)).trim());
	}

	private int getProperty(Properties props, String key, int defaultValue) {
		try {
			return Integer.parseInt(props.getProperty(key,
					String.valueOf(defaultValue)).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	private String getProperty(Properties props, String key, String defaultValue) {
		return props.getProperty(key, defaultValue).trim();
	}

	@Override
	public String name() {
		return "redis";
	}

	public void setExpireMinutes(int expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	@Override
	public void start(Properties props) throws CacheException {
		JedisPoolConfig config = new JedisPoolConfig();

		host = getProperty(props, "host", "127.0.0.1");
		password = props.getProperty("password", null);

		port = getProperty(props, "port", 6379);
		timeout = getProperty(props, "timeout", 2000);
		database = getProperty(props, "database", 0);
		expireMinutes = getProperty(props, "expireMinutes", 30);

		config.setMaxTotal(getProperty(props, "maxTotal", 50));
		config.setMaxIdle(getProperty(props, "maxIdle", 10));
		config.setMinIdle(getProperty(props, "minIdle", 5));
		config.setMaxWaitMillis(getProperty(props, "maxWait", 10) * 1000);
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
		config.setLifo(getProperty(props, "lifo", false));

		JedisPool pool = new JedisPool(config, host, port, timeout, password,
				database);
		RedisUtils.setPool(pool);
	}

	@Override
	public void stop() {
		RedisUtils.destroy();
	}
}
