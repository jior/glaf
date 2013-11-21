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

package com.glaf.core.cache.ehcache;

import java.util.Properties;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.glaf.core.cache.Cache;
import com.glaf.core.cache.CacheException;
import com.glaf.core.context.ContextFactory;

public class EHCacheImpl implements Cache {

	private Ehcache cache;

	private Properties properties;

	private int timeToLive;

	public EHCacheImpl() {

	}

	public void clear() {
		try {
			getCache().removeAll();
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public Object get(String key) {
		try {
			if (key == null) {
				return null;
			} else {
				Element element = getCache().get(key);
				if (element == null) {
					return null;
				} else {
					return element.getObjectValue();
				}
			}
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public Ehcache getCache() {
		if (cache == null) {
			cache = ContextFactory.getBean("ehCache");
		}
		return cache;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getRegionName() {
		return getCache().getName();
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public void init() {
		if (properties == null) {
			properties = new Properties();
		}
	}

	public void put(String key, Object value) {
		try {
			Element element = new Element(key, value);
			if (timeToLive > 0) {
				element.setTimeToLive(timeToLive);
			}
			getCache().put(element);
		} catch (IllegalArgumentException e) {
			throw new CacheException(e);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}

	}

	public void remove(String key) {
		try {
			getCache().remove(key);
		} catch (ClassCastException e) {
			throw new CacheException(e);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public void shutdown() {
		try {
			getCache().getCacheManager().shutdown();
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public String toString() {
		return "EHCache(" + getRegionName() + ')';
	}

}
