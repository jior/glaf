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

import java.util.Iterator;
import java.util.List;

import com.hazelcast.core.IMap;

import com.glaf.j2cache.Cache;
import com.glaf.j2cache.CacheException;

public class HazelcastCache implements Cache {

	private final IMap<Object, Object> map;

	public HazelcastCache(final IMap<Object, Object> map) {
		this.map = map;
	}

	public void clear() {
		map.clear();
	}

	public void destroy() throws CacheException {
		map.clear();
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys != null && !keys.isEmpty()) {
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				map.delete(key);
			}
		}
	}

	public void evict(final Object key) {
		if (key != null) {
			map.delete(key);
		}
	}

	public Object get(final Object key) {
		if (key == null) {
			return null;
		}
		final Object value = map.get(key);
		return value != null;
	}

	public String getName() {
		return map.getName();
	}

	public Object getNativeCache() {
		return map;
	}

	public List<?> keys() throws CacheException {
		return null;
	}

	public void put(final Object key, final Object value) {
		if (key != null) {
			map.set(key, value);
		}
	}

	public void update(Object key, Object value) throws CacheException {
		this.put(key, value);
	}
}
