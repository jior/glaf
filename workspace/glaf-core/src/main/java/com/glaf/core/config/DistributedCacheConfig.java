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

package com.glaf.core.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.cache.CacheFactory;

public class DistributedCacheConfig {
	protected static final Log logger = LogFactory
			.getLog(DistributedCacheConfig.class);

	private DistributedCacheConfig() {

	}

	public static void clear(String sid) {

	}

	public static String getString(String sid, String key) {
		String cacheKey = sid + "_" + key;
		return CacheFactory.getString(cacheKey);
	}

	public static void put(String sid, String key, String value) {
		String cacheKey = sid + "_" + key;
		CacheFactory.put(cacheKey, value);
		logger.debug("put key:" + key);
	}

	public static void remove(String sid, String key) {
		String cacheKey = sid + "_" + key;
		CacheFactory.remove(cacheKey);
		logger.debug("remove key:" + key);
	}

}
