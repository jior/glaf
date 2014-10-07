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

import java.util.Properties;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis 分布式缓存实现
 */
public class RedisCacheManager implements CacheManager, Initializable,
		Destroyable {
	protected String host;
	protected int port;
	protected int timeout;
	protected String password;
	protected int database;
	protected Properties properties = new Properties();

	public void destroy() throws Exception {
		RedisUtils.destroy();
	}

	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new RedisCache<K, V>(name);
	}

	public int getDatabase() {
		return database;
	}

	public String getHost() {
		return host;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
		}
		return properties;
	}

	protected boolean getProperty(Properties props, String key,
			boolean defaultValue) {
		return "true".equalsIgnoreCase(props.getProperty(key,
				String.valueOf(defaultValue)).trim());
	}

	protected int getProperty(Properties props, String key, int defaultValue) {
		try {
			return Integer.parseInt(props.getProperty(key,
					String.valueOf(defaultValue)).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	protected String getProperty(Properties props, String key,
			String defaultValue) {
		return props.getProperty(key, defaultValue).trim();
	}

	public int getTimeout() {
		return timeout;
	}

	public void init() throws ShiroException {
		JedisPoolConfig config = new JedisPoolConfig();

		config.setMaxTotal(getProperty(properties, "maxTotal", 50));
		config.setMaxIdle(getProperty(properties, "maxIdle", 10));
		config.setMinIdle(getProperty(properties, "minIdle", 5));
		config.setMaxWaitMillis(getProperty(properties, "maxWait", 10) * 1000);
		config.setTestWhileIdle(getProperty(properties, "testWhileIdle", false));
		config.setTestOnBorrow(getProperty(properties, "testOnBorrow", true));
		config.setTestOnReturn(getProperty(properties, "testOnReturn", false));
		config.setNumTestsPerEvictionRun(getProperty(properties,
				"numTestsPerEvictionRun", 10));
		config.setMinEvictableIdleTimeMillis(getProperty(properties,
				"minEvictableIdleTimeMillis", 1000));
		config.setSoftMinEvictableIdleTimeMillis(getProperty(properties,
				"softMinEvictableIdleTimeMillis", 10));
		config.setTimeBetweenEvictionRunsMillis(getProperty(properties,
				"timeBetweenEvictionRunsMillis", 10));
		config.setLifo(getProperty(properties, "lifo", false));

		JedisPool jedisPool = new JedisPool(config, host, port, timeout,
				password, database);
		RedisUtils.setPool(jedisPool);
	}

	public String name() {
		return "redis";
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void start(Properties props) {
		JedisPoolConfig config = new JedisPoolConfig();

		host = getProperty(props, "host", "127.0.0.1");
		password = props.getProperty("password", null);

		port = getProperty(props, "port", 6379);
		timeout = getProperty(props, "timeout", 2000);
		database = getProperty(props, "database", 0);

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

		JedisPool jedisPool = new JedisPool(config, host, port, timeout,
				password, database);
		RedisUtils.setPool(jedisPool);
	}

	public void stop() {
		RedisUtils.destroy();
	}
}
