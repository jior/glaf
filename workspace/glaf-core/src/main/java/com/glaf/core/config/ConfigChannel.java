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

package com.glaf.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式配置通道
 * 
 */
public class ConfigChannel {

	protected final static Logger log = LoggerFactory
			.getLogger(ConfigChannel.class);

	private static class ConfigChannelHolder {
		public static ConfigChannel instance = new ConfigChannel();
	}

	public static ConfigChannel getInstance() {
		return ConfigChannelHolder.instance;
	}

	/**
	 * 初始化分布式配置通道
	 * 
	 */
	private ConfigChannel() {
		try {
			ConfigManager.initConfigProvider();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Clear the config
	 */
	public void clear(String region) {
		ConfigManager.clear(region);
	}

	/**
	 * 关闭到通道的连接
	 */
	public void close() {
		ConfigManager.shutdown();
	}

	/**
	 * 获取分布式配置中的数据
	 * 
	 * @param region
	 * @param key
	 * @return
	 */
	public String getString(String region, String key) {
		if (region != null && key != null) {
			return ConfigManager.getString(region, key);
		}
		return null;
	}

	/**
	 * 写入分布式配置
	 * 
	 * @param region
	 * @param key
	 * @param value
	 */
	public void put(String region, String key, String value) {
		if (region != null && key != null) {
			if (value == null)
				remove(region, key);
			else {
				ConfigManager.put(region, key, value);
			}
		}
	}

	/**
	 * 删除分布式配置
	 * 
	 * @param region
	 * @param key
	 */
	public void remove(String region, String key) {
		ConfigManager.remove(region, key);
	}

}
