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

package com.glaf.j2cache;

import java.util.List;
import java.util.Map;

import com.glaf.core.util.SoftHashMap;

public class MapCache implements Cache {

	protected Map<Object, Object> cache;

	protected int cacheSize = 100000;

	public MapCache(int cacheSize) {
		this.cacheSize = cacheSize;
		cache = new SoftHashMap<Object, Object>(cacheSize);
	}

	public void clear() throws CacheException {
		getCache().clear();
	}

	public void destroy() throws CacheException {
		getCache().clear();
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys != null && !keys.isEmpty()) {
			for (Object key : keys) {
				getCache().remove(key);
			}
		}
	}

	public void evict(Object key) throws CacheException {
		getCache().remove(key);
	}

	public Object get(Object key) throws CacheException {
		return getCache().get(key);
	}

	public Map<Object, Object> getCache() {
		if (cache == null) {
			cache = new SoftHashMap<Object, Object>(getCacheSize());
		}
		return cache;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public List<?> keys() throws CacheException {
		return null;
	}

	public void put(Object key, Object value) throws CacheException {
		getCache().put(key, value);
	}

	public void setCache(Map<Object, Object> cache) {
		this.cache = cache;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void update(Object key, Object value) throws CacheException {
		getCache().put(key, value);
	}

}
