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

package com.glaf.core.config.distributed;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;

public class ZooKeeperConfig {
	protected static final Log logger = LogFactory
			.getLog(ZooKeeperConfig.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static volatile CuratorFramework zkClient = null;

	protected static void checkRoot(String regionName) {
		String path = "/" + regionName.replace('.', '_');
		try {
			Stat stat = getClient().checkExists().forPath(path);
			if (stat == null) {
				getClient().create().forPath(path);
				logger.debug("zk create root path:" + path);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
	}

	public static void clear(String regionName) {
		checkRoot(regionName);
		String path = "/" + regionName.replace('.', '_');
		try {
			List<String> children = getClient().getChildren().forPath(path);
			if (children != null && !children.isEmpty()) {
				for (String child : children) {
					logger.debug("delete cache key:" + child);
					getClient().delete().inBackground()
							.forPath(path + "/" + child);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
	}

	protected static synchronized CuratorFramework getClient() {
		if (zkClient == null) {
			String servers = conf.get("zookeeper.servers", "localhost:2181");
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(2000,
					Integer.MAX_VALUE);
			zkClient = CuratorFrameworkFactory.newClient(servers, retryPolicy);
			// zkClient =
			// CuratorFrameworkFactory.builder().connectString(servers)
			// .retryPolicy(retryPolicy).connectionTimeoutMs(5000)
			// .sessionTimeoutMs(60 * 1000).build();
			zkClient.start();
		}
		return zkClient;
	}

	public static String getString(String regionName, String key) {
		checkRoot(regionName);
		String path = "/" + regionName.replace('.', '_') + "/" + key;
		try {
			Stat stat = getClient().checkExists().forPath(path);
			if (stat != null) {
				byte[] data = getClient().getData().forPath(path);
				return new String(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
		return null;
	}

	public static void put(String regionName, String key, String value) {
		checkRoot(regionName);
		String path = "/" + regionName.replace('.', '_') + "/" + key;
		remove(regionName, key);
		try {
			getClient().create().withMode(CreateMode.PERSISTENT)
					.forPath(path, value.getBytes());
			logger.debug("put key:" + key);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
	}

	public static void remove(String regionName, String key) {
		checkRoot(regionName);
		String path = "/" + regionName.replace('.', '_') + "/" + key;
		try {
			Stat stat = getClient().checkExists().forPath(path);
			if (stat != null) {
				getClient().delete().inBackground().forPath(path);
				logger.debug("remove key:" + key);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
	}

	private ZooKeeperConfig() {

	}

}
