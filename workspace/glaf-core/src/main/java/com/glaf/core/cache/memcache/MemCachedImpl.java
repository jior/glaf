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
import com.whalin.MemCached.MemCachedClient;
import com.glaf.core.cache.Cache;

public class MemCachedImpl implements Cache {

	private MemCachedClient cacheProvider;

	private Properties properties;

	public MemCachedImpl() {

	}

	public boolean add(String key, Object value) {
		return cacheProvider.add(key, value);
	}

	public boolean add(String key, Object value, Integer hashCode) {
		return cacheProvider.add(key, value, hashCode);
	}

	public boolean add(String key, Object value, long expiry) {
		return cacheProvider.add(key, value, new Date(expiry));
	}

	public boolean add(String key, Object value, long expiry, Integer hashCode) {
		return cacheProvider.add(key, value, new Date(expiry), hashCode);
	}

	public void clear() {

	}

	public boolean delete(String key) {
		return cacheProvider.delete(key);
	}

	public boolean delete(String key, Integer hashCode, long expiry) {
		return cacheProvider.delete(key);
	}

	public boolean delete(String key, long expiry) {
		return cacheProvider.delete(key);
	}

	public Object get(String key) {
		return cacheProvider.get(key);
	}

	public Object get(String key, Integer hashCode) {
		return cacheProvider.get(key, hashCode);
	}

	public Object get(String key, Integer hashCode, boolean asString) {
		return cacheProvider.get(key, hashCode, asString);
	}

	public MemCachedClient getCacheProvider() {
		return cacheProvider;
	}

	public Properties getProperties() {
		return properties;
	}

	public void init() {
		if (properties == null) {
			properties = new Properties();
		}
	}

	public boolean keyExists(String key) {
		return cacheProvider.keyExists(key);
	}

	public void put(String key, Object value) {
		cacheProvider.set(key, value);
	}

	public void remove(String key) {
		cacheProvider.delete(key);
	}

	public boolean replace(String key, Object value) {
		return cacheProvider.replace(key, value);
	}

	public boolean replace(String key, Object value, Integer hashCode) {
		return cacheProvider.replace(key, value, hashCode);
	}

	public boolean replace(String key, Object value, long expiry) {
		return cacheProvider.replace(key, value, new Date(expiry));
	}

	public boolean replace(String key, Object value, long expiry,
			Integer hashCode) {
		return cacheProvider.replace(key, value, new Date(expiry), hashCode);
	}

	public boolean set(String key, Object value) {
		return cacheProvider.set(key, value);
	}

	public boolean set(String key, Object value, Integer hashCode) {
		return cacheProvider.set(key, value, hashCode);
	}

	public boolean set(String key, Object value, long expiry) {
		return cacheProvider.set(key, value, new Date(expiry));
	}

	public boolean set(String key, Object value, long expiry, Integer hashCode) {
		return cacheProvider.set(key, value, new Date(expiry), hashCode);
	}

	public void setCacheProvider(MemCachedClient memCachedClient) {
		cacheProvider = memCachedClient;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void shutdown() {

	}

	public int size() {
		return -1;
	}

}
