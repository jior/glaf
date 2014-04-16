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

package com.glaf.core.cache.guava;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCache implements com.glaf.core.cache.Cache {
	protected static final Log logger = LogFactory.getLog(GuavaCache.class);

	protected Cache<Object, Object> cache;

	protected int cacheSize = 1000000;

	protected int expireMinutes = 30;

	public GuavaCache() {

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

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public int getExpireMinutes() {
		return expireMinutes;
	}

	public void setExpireMinutes(int expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public Object get(String key) {
		Object value = getCache().getIfPresent(key);
		if (value != null) {
			// logger.debug("get object from guava cache.");
		}
		return value;
	}

	public void put(String key, Object value) {
		getCache().put(key, value);
	}

	public void remove(String key) {
		getCache().invalidate(key);
	}

	public void clear() {
		getCache().invalidateAll();
		getCache().cleanUp();
	}

	public void shutdown() {
		getCache().invalidateAll();
		getCache().cleanUp();
	}
}
