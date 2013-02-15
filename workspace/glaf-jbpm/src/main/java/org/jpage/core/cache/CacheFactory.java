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


package org.jpage.core.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CacheFactory {
	protected static final Log logger = LogFactory.getLog(CacheFactory.class);
	private final static String DEFAULT_CONFIG = "org/jpage/core/cache/cache-context.xml";
	private final static Map<String, Cache> cacheMap = new HashMap<String, Cache>();
	private static ApplicationContext ctx;
	private static String CACHE_PREFIX = "JPX_";

	protected static void clearAll() {
		try {
			Cache cache = getCache();
			if (cache != null) {
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
				String cacheKey = CACHE_PREFIX + key;
				cacheKey = DigestUtils.md5Hex(cacheKey.getBytes());
				Object value = cache.get(cacheKey);
				if (value != null) {
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

	public static void put(String key, Object value) {
		try {
			Cache cache = getCache();
			if (cache != null) {
				String cacheKey = CACHE_PREFIX + key;
				cacheKey = DigestUtils.md5Hex(cacheKey.getBytes());
				cache.put(cacheKey, value);
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

			ctx = new ClassPathXmlApplicationContext(configLocation);
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
				String cacheKey = CACHE_PREFIX + key;
				cacheKey = DigestUtils.md5Hex(cacheKey.getBytes());
				cache.remove(cacheKey);
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
