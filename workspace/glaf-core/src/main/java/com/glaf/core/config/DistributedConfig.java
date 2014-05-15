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

public class DistributedConfig {
	protected static final Log logger = LogFactory
			.getLog(DistributedConfig.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static final String CONFIG_PROVIDER = "distributed.config.provider";

	private DistributedConfig() {

	}

	public static void clear(String sid) {
		if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "hazelcast")) {
			HazelcastConfig.clear(sid);
		} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "zookeeper")) {
			ZooKeeperConfig.clear(sid);
		} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "cache")) {
			DistributedCacheConfig.clear(sid);
		}
	}

	public static String getString(String sid, String key) {
		if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "hazelcast")) {
			return HazelcastConfig.getString(sid, key);
		} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "zookeeper")) {
			return ZooKeeperConfig.getString(sid, key);
		} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "cache")) {
			return DistributedCacheConfig.getString(sid, key);
		}
		return null;
	}

	public static void put(String sid, String key, String value) {
		if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "hazelcast")) {
			HazelcastConfig.put(sid, key, value);
		} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "zookeeper")) {
			ZooKeeperConfig.put(sid, key, value);
		} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "cache")) {
			DistributedCacheConfig.put(sid, key, value);
		}
	}

	public static void remove(String sid, String key) {
		if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "hazelcast")) {
			HazelcastConfig.remove(sid, key);
		} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "zookeeper")) {
			ZooKeeperConfig.remove(sid, key);
		} else if (StringUtils.equals(conf.get(CONFIG_PROVIDER), "cache")) {
			DistributedCacheConfig.remove(sid, key);
		}
	}

}
