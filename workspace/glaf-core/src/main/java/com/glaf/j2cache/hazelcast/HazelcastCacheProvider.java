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
package com.glaf.j2cache.hazelcast;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.hazelcast.config.Config;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.glaf.j2cache.Cache;
import com.glaf.j2cache.CacheException;
import com.glaf.j2cache.CacheExpiredListener;
import com.glaf.j2cache.CacheProvider;

public class HazelcastCacheProvider implements CacheProvider {

	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	private HazelcastInstance hazelcastInstance;
	private Config config;

	public HazelcastCacheProvider() {
		config = new Config();
		hazelcastInstance = Hazelcast.newHazelcastInstance(config);
	}

	public HazelcastCacheProvider(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}

	@Override
	public Cache buildCache(String regionName, boolean autoCreate,
			CacheExpiredListener listener) throws CacheException {
		Cache cache = this.getCache(regionName);
		return cache;
	}

	public Cache getCache(String regionName) {
		Cache cache = caches.get(regionName);
		if (cache == null) {
			final IMap<Object, Object> map = hazelcastInstance
					.getMap(regionName);
			cache = new HazelcastCache(map);
			final Cache currentCache = caches.putIfAbsent(regionName, cache);
			if (currentCache != null) {
				cache = currentCache;
			}
		}
		return cache;
	}

	public Collection<String> getCacheNames() {
		return Collections.unmodifiableCollection(caches.keySet());
	}

	public String name() {
		return "hazelcast";
	}

	public void setHazelcastInstance(final HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}

	@SuppressWarnings("unchecked")
	public void start(Properties props) throws CacheException {
		final Collection<DistributedObject> distributedObjects = hazelcastInstance
				.getDistributedObjects();
		for (DistributedObject distributedObject : distributedObjects) {
			if (distributedObject instanceof IMap) {
				final IMap<Object, Object> map = (IMap<Object, Object>) distributedObject;
				caches.put(map.getName(), new HazelcastCache(map));
			}
		}
	}

	public void stop() {
		hazelcastInstance.shutdown();
	}
}
