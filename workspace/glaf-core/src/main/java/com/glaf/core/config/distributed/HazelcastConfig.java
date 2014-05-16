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

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HazelcastConfig {
	protected static final Log logger = LogFactory
			.getLog(HazelcastConfig.class);

	private static Config config = new Config();

	private static HazelcastInstance hazelcastInstance = Hazelcast
			.newHazelcastInstance(config);

	private HazelcastConfig() {

	}

	public static void clear(String sid) {
		ConcurrentMap<String, Object> concurrentMap = hazelcastInstance
				.getMap(sid);
		if (concurrentMap != null) {
			concurrentMap.clear();
		}
	}

	public static Object getObject(String sid, String key) {
		ConcurrentMap<String, Object> concurrentMap = hazelcastInstance
				.getMap(sid);
		if (concurrentMap != null && key != null) {
			return concurrentMap.get(key);
		}
		return null;
	}

	public static String getString(String sid, String key) {
		ConcurrentMap<String, String> concurrentMap = hazelcastInstance
				.getMap(sid);
		if (concurrentMap != null && key != null) {
			return concurrentMap.get(key);
		}
		return null;
	}

	public static void put(String sid, String key, Object value) {
		ConcurrentMap<String, Object> concurrentMap = hazelcastInstance
				.getMap(sid);
		if (concurrentMap != null && key != null) {
			concurrentMap.putIfAbsent(key, value);
			logger.debug("put key:" + key);
		}
	}

	public static void put(String sid, String key, String value) {
		ConcurrentMap<String, String> concurrentMap = hazelcastInstance
				.getMap(sid);
		if (concurrentMap != null && key != null) {
			concurrentMap.putIfAbsent(key, value);
			logger.debug("put key:" + key);
		}
	}

	public static void remove(String sid, String key) {
		ConcurrentMap<String, Object> concurrentMap = hazelcastInstance
				.getMap(sid);
		if (concurrentMap != null && key != null) {
			concurrentMap.remove(key);
			logger.debug("remove key:" + key);
		}
	}

}
