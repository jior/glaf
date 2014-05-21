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

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisUtils {

	private static JedisPool pool;

	protected static void destroy() {
		pool.destroy();
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

	protected static void setPool(JedisPool jedisPool) {
		if (jedisPool != null) {
			pool = jedisPool;
		}
	}

	private RedisUtils() {

	}

}
