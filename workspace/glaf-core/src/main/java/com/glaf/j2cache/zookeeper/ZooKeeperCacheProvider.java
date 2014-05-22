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
package com.glaf.j2cache.zookeeper;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.glaf.j2cache.Cache;
import com.glaf.j2cache.CacheException;
import com.glaf.j2cache.CacheExpiredListener;
import com.glaf.j2cache.CacheProvider;

public class ZooKeeperCacheProvider implements CacheProvider {

	private final static ConcurrentMap<String, Cache> concurrentMap = new ConcurrentHashMap<String, Cache>();

	protected CuratorFramework zkClient;

	protected String servers;

	protected int connectionTimeoutMs;

	public ZooKeeperCacheProvider() {

	}

	public Cache buildCache(String regionName, boolean autoCreate,
			CacheExpiredListener listener) throws CacheException {
		Cache config = concurrentMap.get(regionName);
		if (config == null) {
			zkClient = getClient();
			config = new ZooKeeperCache(regionName, zkClient);
			final Cache currentCache = concurrentMap.putIfAbsent(regionName,
					config);
			if (currentCache != null) {
				config = currentCache;
			}
		}
		return config;
	}

	protected CuratorFramework getClient() {
		if (zkClient == null) {
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(
					connectionTimeoutMs, Integer.MAX_VALUE);
			zkClient = CuratorFrameworkFactory.newClient(servers, retryPolicy);
			zkClient.start();
		}
		return zkClient;
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

	public String name() {
		return "zookeeper";
	}

	public void start(Properties props) {
		servers = getProperty(props, "servers", "127.0.0.1:2181");
		connectionTimeoutMs = getProperty(props, "connectionTimeoutMs", 2000);
	}

	public void stop() {
		getClient().close();
	}
}
