/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
