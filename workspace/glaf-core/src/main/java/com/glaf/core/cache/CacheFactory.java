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

package com.glaf.core.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.Environment;

public class CacheFactory {
	protected static final Log logger = LogFactory.getLog(CacheFactory.class);
	protected static final String DEFAULT_CONFIG = "com/glaf/core/cache/cache-context.xml";
	protected static final Map<String, Cache> cacheMap = new HashMap<String, Cache>();
	protected static final Map<String, CacheItem> items = new LinkedHashMap<String, CacheItem>();
	protected static Configuration conf = BaseConfiguration.create();

	private static ApplicationContext ctx;

	private static String CACHE_PREFIX = "GLAF_";

	protected static void clearAll() {
		try {
			Cache cache = getCache();
			if (cache != null) {
				for (String cacheKey : items.keySet()) {
					cache.remove(cacheKey);
				}
				items.clear();
				cache.clear();
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
		}
	}

	public static Object get(String key) {
		try {
			Cache cache = getCache();
			if (cache != null) {
				String cacheKey = Environment.getCurrentSystemName() + "_"
						+ CACHE_PREFIX + key;
				// cacheKey = DigestUtils.md5Hex(cacheKey.getBytes());
				Object value = cache.get(cacheKey);
				if (value != null) {
					logger.debug("get object'" + key + "' from cache");
					return value;
				}
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
		}
		return null;
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	public static Object getBean(Object name) {
		if (ctx == null) {
			ctx = reload();
		}
		return ctx.getBean((String) name);
	}

	protected static Cache getCache() {
		String beanId = "cache";
		Cache cache = (Cache) getBean(beanId);
		if (cache != null) {
			cacheMap.put(beanId, cache);
		}
		return cache;
	}

	public static Collection<CacheItem> getItems() {
		return items.values();
	}

	public static String getString(String key) {
		try {
			Cache cache = getCache();
			if (cache != null) {
				String cacheKey = Environment.getCurrentSystemName() + "_"
						+ CACHE_PREFIX + key;
				// cacheKey = DigestUtils.md5Hex(cacheKey.getBytes());
				Object value = cache.get(cacheKey);
				if (value != null) {
					logger.debug("get object'" + key + "' from cache");
					return value.toString();
				}
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
		}
		return null;
	}

	public static void put(String key, String value) {
		try {
			Cache cache = getCache();
			if (cache != null && key != null && value != null) {
				String cacheKey = Environment.getCurrentSystemName() + "_"
						+ CACHE_PREFIX + key;
				// cacheKey = DigestUtils.md5Hex(cacheKey.getBytes());
				int limitSize = conf.getInt("cache.limitSize", 1024000);// 1024KB
				if (value.length() < limitSize) {
					cache.put(cacheKey, value);
					CacheItem item = new CacheItem();
					item.setKey(key);
					item.setLastModified(System.currentTimeMillis());
					item.setSize(value.length());
					items.put(cacheKey, item);
				}
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
		}
	}

	protected static ApplicationContext reload() {
		try {
			if (null != ctx) {
				Cache cache = getCache();
				if (cache != null) {
					shutdown();
				}
				ctx = null;
			}

			String configLocation = DEFAULT_CONFIG;

			if (CustomProperties.getString("sys.cache.context") != null) {
				configLocation = CustomProperties
						.getString("sys.cache.context");
			}

			ctx = new ClassPathXmlApplicationContext(configLocation);
			logger.info("################# CacheFactory ##############");
			logger.info("start spring ioc from: " + configLocation);
			return ctx;
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
			throw new CacheException("can't reload cache", ex);
		}
	}

	public static void remove(String key) {
		try {
			Cache cache = getCache();
			if (cache != null) {
				String cacheKey = Environment.getCurrentSystemName() + "_"
						+ CACHE_PREFIX + key;
				// cacheKey = DigestUtils.md5Hex(cacheKey.getBytes());
				cache.remove(cacheKey);
				items.remove(cacheKey);
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
		}
	}

	private static void shutdown() {
		try {
			Cache cache = getCache();
			if (cache != null) {
				items.clear();
				cache.clear();
				cache.shutdown();
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
		}
	}

}
