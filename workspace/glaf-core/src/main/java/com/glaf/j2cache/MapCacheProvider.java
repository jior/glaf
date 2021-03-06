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

import java.util.Properties;

public class MapCacheProvider implements CacheProvider {

	private static MapCache cache;

	protected int cacheSize = 100000;

	@Override
	public Cache buildCache(String regionName, boolean autoCreate,
			CacheExpiredListener listener) throws CacheException {
		if (cache == null) {
			cache = new MapCache(getCacheSize());
		}
		return cache;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	private int getProperty(Properties props, String key, int defaultValue) {
		try {
			return Integer.parseInt(props.getProperty(key,
					String.valueOf(defaultValue)).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	@Override
	public String name() {
		return "map";
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	@Override
	public void start(Properties props) throws CacheException {
		cacheSize = getProperty(props, "cacheSize", 100000);
	}

	@Override
	public void stop() {
		cache.clear();
	}

}
