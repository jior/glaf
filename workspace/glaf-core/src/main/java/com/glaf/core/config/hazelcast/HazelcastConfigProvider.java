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
package com.glaf.core.config.hazelcast;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.glaf.core.config.Config;
import com.glaf.core.config.ConfigProvider;

public class HazelcastConfigProvider implements ConfigProvider {

	protected final ConcurrentMap<String, Config> caches = new ConcurrentHashMap<String, Config>();
	protected HazelcastInstance hazelcastInstance;
	protected com.hazelcast.config.Config config;

	public HazelcastConfigProvider() {
		config = new com.hazelcast.config.Config();
		hazelcastInstance = Hazelcast.newHazelcastInstance(config);
	}

	public HazelcastConfigProvider(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}

	@Override
	public Config buildConfig(String regionName, boolean autoCreate) {
		Config cache = this.getConfig(regionName);
		return cache;
	}

	public Config getConfig(String regionName) {
		Config cache = caches.get(regionName);
		if (cache == null) {
			final IMap<Object, Object> map = hazelcastInstance
					.getMap(regionName);
			cache = new HazelcastConfig(map);
			final Config currentConfig = caches.putIfAbsent(regionName, cache);
			if (currentConfig != null) {
				cache = currentConfig;
			}
		}
		return cache;
	}

	public Collection<String> getConfigNames() {
		return Collections.unmodifiableCollection(caches.keySet());
	}

	public String name() {
		return "hazelcast";
	}

	public void setHazelcastInstance(final HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}

	@SuppressWarnings("unchecked")
	public void start(Properties props) {
		final Collection<DistributedObject> distributedObjects = hazelcastInstance
				.getDistributedObjects();
		for (DistributedObject distributedObject : distributedObjects) {
			if (distributedObject instanceof IMap) {
				final IMap<Object, Object> map = (IMap<Object, Object>) distributedObject;
				caches.put(map.getName(), new HazelcastConfig(map));
			}
		}
	}

	public void stop() {
		hazelcastInstance.shutdown();
	}
}
