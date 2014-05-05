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
package com.glaf.shiro.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {

	public static JedisPool getJedisPool() {
		return jedisPool;
	}

	public static void setJedisPool(JedisPool jedisPool) {
		RedisManager.jedisPool = jedisPool;
	}

	private String host = "127.0.0.1";

	private String minConn = "5";

	private String maxConn = "100";

	private int port = 6379;

	// 0 - never expire
	private int expire = 0;

	private static JedisPool jedisPool = null;

	public RedisManager() {

	}

	/**
	 * size
	 */
	public Long dbSize() {
		Long dbSize = 0L;
		Jedis jedis = jedisPool.getResource();
		try {
			dbSize = jedis.dbSize();
		} finally {
			jedisPool.returnResource(jedis);
		}
		return dbSize;
	}

	/**
	 * del
	 * 
	 * @param key
	 */
	public void del(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * flush
	 */
	public void flushDB() {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.flushDB();
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * get value from redis
	 * 
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.get(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}

	public int getExpire() {
		return expire;
	}

	public String getHost() {
		return host;
	}

	public String getMaxConn() {
		return maxConn;
	}

	public String getMinConn() {
		return minConn;
	}

	public int getPort() {
		return port;
	}

	/**
	 * 初始化方法
	 */
	public void init() {
		if (jedisPool == null) {
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(Integer.parseInt(maxConn));
			poolConfig.setMaxIdle(Integer.parseInt(maxConn));
			poolConfig.setMinIdle(Integer.parseInt(minConn));
			poolConfig.setMaxWaitMillis(1000L * 10L);

			poolConfig.setTestOnBorrow(false);
			poolConfig.setTestOnReturn(false);
			poolConfig.setTestWhileIdle(true);
			poolConfig.setMinEvictableIdleTimeMillis(1000L * 60L * 10L); // 空闲对象,空闲多长时间会被驱逐出池里
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 30L); // 驱逐线程30秒执行一次
			poolConfig.setNumTestsPerEvictionRun(-1); // -1,表示在驱逐线程执行时,测试所有的空闲对象
			jedisPool = new JedisPool(poolConfig, host, port);
		}
	}

	/**
	 * keys
	 * 
	 * @param regex
	 * @return
	 */
	public Set<byte[]> keys(String pattern) {
		Set<byte[]> keys = null;
		Jedis jedis = jedisPool.getResource();
		try {
			keys = jedis.keys(pattern.getBytes());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return keys;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key, byte[] value, int expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setMaxConn(String maxConn) {
		this.maxConn = maxConn;
	}

	public void setMinConn(String minConn) {
		this.minConn = minConn;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
