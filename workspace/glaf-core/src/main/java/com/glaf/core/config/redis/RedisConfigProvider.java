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

import java.util.Properties;

import com.glaf.core.config.Config;
import com.glaf.core.config.ConfigProvider;
import com.glaf.core.config.redis.RedisConfig;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis 分布式配置实现
 */
public class RedisConfigProvider implements ConfigProvider {
	private String host;
	private int port;
	private int timeout;
	private String password;
	private int database;

	@Override
	public Config buildConfig(String regionName, boolean autoCreate) {
		return new RedisConfig(regionName);
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

	@Override
	public String name() {
		return "redis";
	}

	@Override
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

	@Override
	public void stop() {
		RedisUtils.destroy();
	}
}
