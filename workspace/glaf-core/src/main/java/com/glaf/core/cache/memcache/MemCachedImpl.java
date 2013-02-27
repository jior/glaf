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
package com.glaf.core.cache.memcache;

import java.util.Date;
import java.util.Properties;
import com.danga.MemCached.MemCachedClient;
import com.glaf.core.cache.Cache;

public class MemCachedImpl implements Cache {

	private MemCachedClient cacheProvider;

	private Properties properties;

	public MemCachedImpl() {

	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void init() {
		if (properties == null) {
			properties = new Properties();
		}
	}

	public boolean add(String key, Object value) {
		return cacheProvider.add(String.valueOf(key.hashCode()), value);
	}

	public boolean add(String key, Object value, long expiry) {
		return cacheProvider.add(String.valueOf(key.hashCode()), value,
				new Date(expiry));
	}

	public boolean add(String key, Object value, long expiry, Integer hashCode) {
		return cacheProvider.add(String.valueOf(key.hashCode()), value,
				new Date(expiry), hashCode);
	}

	public boolean add(String key, Object value, Integer hashCode) {
		return cacheProvider.add(String.valueOf(key.hashCode()), value,
				hashCode);
	}

	public boolean delete(String key) {
		return cacheProvider.delete(String.valueOf(key.hashCode()));
	}

	public boolean delete(String key, long expiry) {
		return cacheProvider.delete(String.valueOf(key.hashCode()));
	}

	public boolean delete(String key, Integer hashCode, long expiry) {
		return cacheProvider.delete(String.valueOf(key.hashCode()));
	}

	public Object get(String key) {
		return cacheProvider.get(String.valueOf(key.hashCode()));
	}

	public Object get(String key, Integer hashCode) {
		return cacheProvider.get(String.valueOf(key.hashCode()), hashCode);
	}

	public Object get(String key, Integer hashCode, boolean asString) {
		return cacheProvider.get(String.valueOf(key.hashCode()), hashCode,
				asString);
	}

	public boolean keyExists(String key) {
		return cacheProvider.keyExists(String.valueOf(key.hashCode()));
	}

	public boolean replace(String key, Object value) {
		return cacheProvider.replace(String.valueOf(key.hashCode()), value);
	}

	public boolean replace(String key, Object value, long expiry) {
		return cacheProvider.replace(String.valueOf(key.hashCode()), value,
				new Date(expiry));
	}

	public boolean replace(String key, Object value, long expiry,
			Integer hashCode) {
		return cacheProvider.replace(String.valueOf(key.hashCode()), value,
				new Date(expiry), hashCode);
	}

	public boolean replace(String key, Object value, Integer hashCode) {
		return cacheProvider.replace(String.valueOf(key.hashCode()), value,
				hashCode);
	}

	public boolean set(String key, Object value) {
		return cacheProvider.set(String.valueOf(key.hashCode()), value);
	}

	public boolean set(String key, Object value, long expiry) {
		return cacheProvider.set(String.valueOf(key.hashCode()), value,
				new Date(expiry));
	}

	public boolean set(String key, Object value, long expiry, Integer hashCode) {
		return cacheProvider.set(String.valueOf(key.hashCode()), value,
				new Date(expiry), hashCode);
	}

	public boolean set(String key, Object value, Integer hashCode) {
		return cacheProvider.set(String.valueOf(key.hashCode()), value,
				hashCode);
	}

	public MemCachedClient getCacheProvider() {
		return cacheProvider;
	}

	public void setCacheProvider(MemCachedClient memCachedClient) {
		cacheProvider = memCachedClient;
	}

	public void clear() {

	}

	public void shutdown() {

	}

	public void put(String key, Object value) {
		cacheProvider.set(String.valueOf(key.hashCode()), value);
	}

	public void remove(String key) {
		cacheProvider.delete(String.valueOf(key.hashCode()));
	}

	public int size() {
		return -1;
	}

}
