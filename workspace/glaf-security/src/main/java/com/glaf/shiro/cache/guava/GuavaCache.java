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

package com.glaf.shiro.cache.guava;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.CacheException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCache<K, V> implements org.apache.shiro.cache.Cache<K, V> {

	protected static final ConcurrentMap<Object, Integer> cacheMap = new ConcurrentHashMap<Object, Integer>();

	protected Cache<Object, Object> cache;

	protected int cacheSize = 100000;

	protected int expireMinutes = 720;

	public GuavaCache(int cacheSize, int expireMinutes) {
		this.cacheSize = cacheSize;
		this.expireMinutes = expireMinutes;
		cache = CacheBuilder.newBuilder().maximumSize(cacheSize)
				.expireAfterAccess(expireMinutes, TimeUnit.MINUTES).build();
	}

	public void clear() throws CacheException {
		getCache().invalidateAll();
		getCache().cleanUp();
		cacheMap.clear();
	}

	public void destroy() throws CacheException {
		getCache().invalidateAll();
		getCache().cleanUp();
		cacheMap.clear();
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys != null && !keys.isEmpty()) {
			for (Object key : keys) {
				getCache().invalidate(key);
				cacheMap.remove(key);
			}
		}
	}

	public void evict(Object key) throws CacheException {
		getCache().invalidate(key);
		cacheMap.remove(key);
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public Set<K> keys() {
		Set<K> keys = new HashSet<K>();
		Set<Object> set = cacheMap.keySet();
		Iterator<Object> iterator = set.iterator();
		while (iterator.hasNext()) {
			K key = (K) iterator.next();
			keys.add(key);
		}
		return keys;
	}

	public V put(K key, V value) throws CacheException {
		getCache().put(key, value);
		cacheMap.put(key, 1);
		return null;
	}

	public V remove(K key) throws CacheException {
		getCache().invalidate(key);
		cacheMap.remove(key);
		return null;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setExpireMinutes(int expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public int size() {
		return (int) getCache().size();
	}

	public void update(Object key, Object value) throws CacheException {
		getCache().put(key, value);
		cacheMap.remove(key);
	}

	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		Collection<V> values = new HashSet<V>();
		Set<Object> set = cacheMap.keySet();
		Iterator<Object> iterator = set.iterator();
		while (iterator.hasNext()) {
			K key = (K) iterator.next();
			V value = (V) getCache().getIfPresent(key);
			values.add(value);
		}
		return values;
	}

}
