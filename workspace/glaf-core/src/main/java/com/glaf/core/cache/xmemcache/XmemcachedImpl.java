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
package com.glaf.core.cache.xmemcache;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import com.glaf.core.cache.Cache;
import com.glaf.core.cache.CacheException;

import net.rubyeye.xmemcached.*;
import net.rubyeye.xmemcached.exception.*;

public class XmemcachedImpl implements Cache {

	private MemcachedClient cacheProvider;

	private Properties properties;

	public XmemcachedImpl() {

	}

	public MemcachedClient getCacheProvider() {
		return cacheProvider;
	}

	public void setCacheProvider(MemcachedClient cacheProvider) {
		this.cacheProvider = cacheProvider;
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

	public Object get(String key) {
		try {
			return cacheProvider.get(key);
		} catch (TimeoutException e) {
			throw new CacheException(e);
		} catch (InterruptedException e) {
			throw new CacheException(e);
		} catch (MemcachedException e) {
			throw new CacheException(e);
		}
	}

	public void put(String key, Object value) {
		try {
			cacheProvider.set(key, 3600, value);
		} catch (TimeoutException e) {
			throw new CacheException(e);
		} catch (InterruptedException e) {
			throw new CacheException(e);
		} catch (MemcachedException e) {
			throw new CacheException(e);
		}
	}

	public void remove(String key) {
		try {
			cacheProvider.delete(key);
		} catch (TimeoutException e) {
			throw new CacheException(e);
		} catch (InterruptedException e) {
			throw new CacheException(e);
		} catch (MemcachedException e) {
			throw new CacheException(e);
		}
	}

	public void clear() {

	}

	public void shutdown() {
		try {
			cacheProvider.shutdown();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public int size() {
		return -1;
	}

}
