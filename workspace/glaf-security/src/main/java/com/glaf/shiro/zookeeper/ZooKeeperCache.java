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

package com.glaf.shiro.zookeeper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.zookeeper.CreateMode;
import org.springframework.util.SerializationUtils;

public class ZooKeeperCache<K, V> implements Cache<K, V> {

	private static final Log logger = LogFactory.getLog(ZooKeeperCache.class);

	private volatile CuratorFramework zkClient;

	private volatile String groupName;

	public ZooKeeperCache(String groupName, CuratorFramework zkClient) {
		if (zkClient == null) {
			throw new IllegalArgumentException(
					"zkClient argument cannot be null.");
		}
		if (groupName == null) {
			throw new IllegalArgumentException(
					"groupName argument cannot be null.");
		}
		this.zkClient = zkClient;
		this.groupName = groupName;
	}

	public void clear() throws CacheException {
		try {
			List<String> children = zkClient.getChildren().forPath(groupName);
			if (children != null && !children.isEmpty()) {
				for (String child : children) {
					logger.debug("cache key:" + child);
					zkClient.delete().inBackground().forPath(child);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public V get(K key) throws CacheException {
		try {
			String path = groupName + "/" + key;
			logger.debug("cache key:" + path);
			byte[] bytes = zkClient.getData().forPath(path);
			return (V) SerializationUtils.deserialize(bytes);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
		return null;
	}

	public Set<K> keys() {
		return null;
	}

	public V put(K key, V value) throws CacheException {
		byte[] data = SerializationUtils.serialize(value);
		try {
			String path = groupName + "/" + key;
			logger.debug("cache key:" + path);
			zkClient.create().withMode(CreateMode.PERSISTENT)
					.forPath(path, data);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
		return null;
	}

	public V remove(K key) throws CacheException {
		try {
			String path = groupName + "/" + key;
			logger.debug("cache key:" + path);
			zkClient.delete().inBackground().forPath(path);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
		return null;
	}

	public int size() {
		return 0;
	}

	public Collection<V> values() {
		return null;
	}

}
