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
package com.glaf.shiro.redis;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.glaf.core.util.SerializerUtils;
import com.glaf.shiro.Cacheable;

@SuppressWarnings("unchecked")
public class ShiroRedisCache<K, V> implements Cache<K, V> {
	protected final static Log logger = LogFactory
			.getLog(ShiroRedisCache.class);
	private String name;
	private Cacheable cacheable;

	public ShiroRedisCache(String name, Cacheable cacheable) {
		this.name = name;
		this.cacheable = cacheable;
	}

	@Override
	public void clear() throws CacheException {
		logger.debug("从redis中删除所有元素");
		try {
			cacheable.remove(getByteName());
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public V get(K key) throws CacheException {
		logger.debug("根据key从Redis中获取对象 key [" + key + "]");
		try {
			if (key == null) {
				return null;
			} else {
				V value = (V) cacheable.getHashCache(getByteName(),
						getByteKey(key));
				return value;
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}

	}

	/**
	 * 获得byte[]型的key
	 * 
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(K key) {
		if (key instanceof String) {
			String preKey = key.toString();
			return preKey.getBytes();
		} else {
			return SerializerUtils.serialize(key);
		}
	}

	private byte[] getByteName() {
		return name.getBytes();
	}

	public Cacheable getCacheable() {
		return cacheable;
	}

	public String getName() {
		return name;
	}

	@Override
	public Set<K> keys() {
		try {
			Set<K> keys = cacheable.getHashKeys(getByteName());
			return keys;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public V put(K key, V value) throws CacheException {
		logger.debug("根据key从存储 key [" + key + "]");
		try {
			cacheable.updateHashCache(getByteName(), getByteKey(key),
					SerializerUtils.serialize(value), null);
			return value;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public V remove(K key) throws CacheException {
		logger.debug("从redis中删除 key [" + key + "]");
		try {
			V previous = get(key);
			cacheable.deleteHashCache(getByteName(), getByteKey(key));
			return previous;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public void setCacheable(Cacheable cacheable) {
		this.cacheable = cacheable;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int size() {
		try {
			Long longSize = new Long(cacheable.getHashSize(getByteName()));
			return longSize.intValue();
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public Collection<V> values() {
		try {
			Collection<V> values = cacheable.getHashValues(getByteName());
			return values;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

}
