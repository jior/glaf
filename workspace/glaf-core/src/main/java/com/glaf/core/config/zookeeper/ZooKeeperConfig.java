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
package com.glaf.core.config.zookeeper;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.glaf.core.config.Config;

public class ZooKeeperConfig implements Config {

	protected static final Log logger = LogFactory
			.getLog(ZooKeeperConfig.class);

	private static final Charset CHARSET = Charset.forName("UTF-8");

	protected CuratorFramework zkClient = null;

	protected String regionName;

	public ZooKeeperConfig(String regionName, CuratorFramework zkClient) {
		this.regionName = regionName;
		this.zkClient = zkClient;
	}

	protected CuratorFramework getClient() {
		return zkClient;
	}

	protected void checkRoot(String regionName) {
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

	public void clear() {
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

	public String getString(String key) {
		checkRoot(regionName);
		String path = "/" + regionName.replace('.', '_') + "/" + key;
		try {
			Stat stat = getClient().checkExists().forPath(path);
			if (stat != null) {
				byte[] data = getClient().getData().forPath(path);
				return new String(data, CHARSET);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
		return null;
	}

	public void put(final String key, final String value) {
		checkRoot(regionName);
		String path = "/" + regionName.replace('.', '_') + "/" + key;
		try {
			Stat stat = getClient().checkExists().forPath(path);
			if (stat == null) {
				getClient().create().withMode(CreateMode.PERSISTENT)
						.inBackground().forPath(path, value.getBytes(CHARSET));
				logger.debug("add key:" + key);
			} else {
				getClient().setData().inBackground()
						.forPath(path, value.getBytes(CHARSET));
				logger.debug("update key:" + key);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(ex);
			}
		}
	}

	public void remove(String key) {
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

}
