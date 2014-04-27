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

package com.glaf.j2cache.spymemcache;

import java.io.IOException;
import java.util.*;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.SerializingTranscoder;
import net.spy.memcached.transcoders.Transcoder;
import net.spy.memcached.DefaultHashAlgorithm;

import com.glaf.j2cache.CacheException;

public class SpyMemCache implements com.glaf.j2cache.Cache {

	private final ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();

	protected MemcachedClient memcachedClient;

	protected String servers;

	protected int cacheSize = 100000;

	protected int expireMinutes = 30;

	public SpyMemCache(String servers, int cacheSize, int expireMinutes) {
		this.servers = servers;
		this.cacheSize = cacheSize;
		this.expireMinutes = expireMinutes;
		this.buildClient();
	}

	protected void buildClient() {
		try {
			Transcoder<Object> transcoder = new SerializingTranscoder();
			connectionFactoryBuilder.setOpTimeout(5000L);
			connectionFactoryBuilder.setUseNagleAlgorithm(false);
			connectionFactoryBuilder.setFailureMode(FailureMode.Redistribute);
			connectionFactoryBuilder.setTimeoutExceptionThreshold(2000);
			connectionFactoryBuilder
					.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH);
			connectionFactoryBuilder.setTranscoder(transcoder);
			memcachedClient = new MemcachedClient(
					connectionFactoryBuilder.build(),
					AddrUtil.getAddresses(servers));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void clear() throws CacheException {

	}

	public void destroy() throws CacheException {
		getCache().shutdown();
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys != null && !keys.isEmpty()) {
			for (Object key : keys) {
				getCache().delete(key.toString());
			}
		}
	}

	public void evict(Object key) throws CacheException {
		getCache().delete(key.toString());
	}

	public Object get(Object key) throws CacheException {
		return getCache().get(key.toString());
	}

	public MemcachedClient getCache() {
		if (memcachedClient == null) {
			this.buildClient();
		}
		return memcachedClient;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public int getExpireMinutes() {
		return expireMinutes;
	}

	public String getServers() {
		return servers;
	}

	public List<?> keys() throws CacheException {
		return null;
	}

	public void put(Object key, Object value) throws CacheException {
		getCache().set(key.toString(), expireMinutes * 60, value);
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setExpireMinutes(int expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public void update(Object key, Object value) throws CacheException {
		getCache().set(key.toString(), expireMinutes * 60, value);
	}

}
