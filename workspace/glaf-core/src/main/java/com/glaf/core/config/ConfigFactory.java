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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.distributed.HazelcastConfig;
import com.glaf.core.config.distributed.ZooKeeperConfig;

/**
 * 分布式配置工厂类 如果需要进行分布式部署，需要将配置项写入分布式存储中
 */
public class ConfigFactory {
	protected static final Log logger = LogFactory.getLog(ConfigFactory.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static final ConfigChannel channel = ConfigChannel.getInstance();

	protected static final String CONFIG_PROVIDER = "distributed.config.provider";

	public static void clear(String region) {
		if (conf.getBoolean("distributed.config.enabled", false)) {
			channel.clear(region);
		} else {
			if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "hazelcast")) {
				HazelcastConfig.clear(region);
			} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER),
					"zookeeper")) {
				ZooKeeperConfig.clear(region);
			}
		}
	}

	public static JSONObject getJSONObject(String region, String key) {
		String text = getString(region, key);
		if (StringUtils.isNotEmpty(text)) {
			JSONObject jsonObject = JSON.parseObject(text);
			return jsonObject;
		}
		return null;
	}

	public static String getString(String region, String key) {
		if (conf.getBoolean("distributed.config.enabled", false)) {
			return channel.getString(region, key);
		} else {
			if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "hazelcast")) {
				return HazelcastConfig.getString(region, key);
			} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER),
					"zookeeper")) {
				return ZooKeeperConfig.getString(region, key);
			}
		}
		return null;
	}

	public static void put(String region, String key, String value) {
		if (conf.getBoolean("distributed.config.enabled", false)) {
			channel.put(region, key, value);
		} else {
			if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "hazelcast")) {
				HazelcastConfig.put(region, key, value);
			} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER),
					"zookeeper")) {
				ZooKeeperConfig.put(region, key, value);
			}
		}
	}

	public static void remove(String region, String key) {
		if (conf.getBoolean("distributed.config.enabled", false)) {
			channel.remove(region, key);
		} else {
			if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "hazelcast")) {
				HazelcastConfig.remove(region, key);
			} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER),
					"zookeeper")) {
				ZooKeeperConfig.remove(region, key);
			}
		}
	}

	private ConfigFactory() {

	}

}
