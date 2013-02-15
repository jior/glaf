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

package org.jpage.core.cache.ehcache;

import java.util.Properties;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.jpage.core.cache.Cache;
import org.jpage.core.cache.CacheException;

public class EHCacheImpl implements Cache {

	private Ehcache cache;

	private Properties properties;

	private int timeToLive;

	public EHCacheImpl() {

	}

	public void clear() {
		try {
			cache.removeAll();
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
				Element element = cache.get(key);
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
		return cache;
	}

	public long getElementCountInMemory() {
		try {
			return cache.getMemoryStoreSize();
		} catch (net.sf.ehcache.CacheException ce) {
			throw new CacheException(ce);
		}
	}

	public long getElementCountOnDisk() {
		return cache.getDiskStoreSize();
	}

	public Properties getProperties() {
		return properties;
	}

	public String getRegionName() {
		return cache.getName();
	}

	public long getSizeInMemory() {
		return cache.calculateInMemorySize();
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
			cache.put(element);
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
			cache.remove(key);
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
			cache.getCacheManager().shutdown();
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
