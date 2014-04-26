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

package com.glaf.j2cache.guava;

import java.util.List;

import java.util.concurrent.TimeUnit;

import com.glaf.j2cache.CacheException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCache implements com.glaf.j2cache.Cache {

	protected Cache<Object, Object> cache;

	protected int cacheSize = 100000;

	protected int expireMinutes = 30;

	public GuavaCache(int cacheSize, int expireMinutes) {
		this.cacheSize = cacheSize;
		this.expireMinutes = expireMinutes;
		cache = CacheBuilder.newBuilder().maximumSize(cacheSize)
				.expireAfterAccess(expireMinutes, TimeUnit.MINUTES).build();
	}

	public void clear() throws CacheException {
		getCache().invalidateAll();
		getCache().cleanUp();
	}

	public void destroy() throws CacheException {
		getCache().invalidateAll();
		getCache().cleanUp();
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys != null && !keys.isEmpty()) {
			for (Object key : keys) {
				getCache().invalidate(key);
			}
		}
	}

	public void evict(Object key) throws CacheException {
		getCache().invalidate(key);
	}

	public Object get(Object key) throws CacheException {
		return getCache().getIfPresent(key);
	}

	public Cache<Object, Object> getCache() {
		if (cache == null) {
			cache = CacheBuilder.newBuilder().maximumSize(getCacheSize())
					.expireAfterAccess(getExpireMinutes(), TimeUnit.MINUTES)
					.build();
		}
		return cache;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public int getExpireMinutes() {
		return expireMinutes;
	}

	public List<?> keys() throws CacheException {
		return null;
	}

	public void put(Object key, Object value) throws CacheException {
		getCache().put(key, value);
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setExpireMinutes(int expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public void update(Object key, Object value) throws CacheException {
		getCache().put(key, value);
	}

}
