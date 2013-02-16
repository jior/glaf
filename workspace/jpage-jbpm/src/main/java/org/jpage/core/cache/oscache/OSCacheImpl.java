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


package org.jpage.core.cache.oscache;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.cache.Cache;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class OSCacheImpl implements Cache {
	protected final static Log logger = LogFactory.getLog(OSCacheImpl.class);
	private GeneralCacheAdministrator cacheProvider;
	private int refreshPeriod;
	private String regionName;
	private Properties properties;

	public OSCacheImpl() {

	}

	public void clear() {
		cacheProvider.flushAll();
	}

	public Object get(String key) {
		if (refreshPeriod <= 0) {
			refreshPeriod = 600;// 600 seconds
		}
		Object object = null;
		try {
			object = cacheProvider.getFromCache(toString(key), refreshPeriod);
		} catch (NeedsRefreshException ex) {
			logger.debug(ex);
		}
		return object;
	}

	public GeneralCacheAdministrator getCacheProvider() {
		return cacheProvider;
	}

	public long getElementCountInMemory() {
		return -1;
	}

	public long getElementCountOnDisk() {
		return -1;
	}

	public Properties getProperties() {
		return properties;
	}

	public int getRefreshPeriod() {
		return refreshPeriod;
	}

	public String getRegionName() {
		if (regionName == null) {
			regionName = "default";
		}
		return regionName;
	}

	public long getSizeInMemory() {
		return -1;
	}

	public void init() {
		if (properties == null) {
			properties = new Properties();
		}
	}

	public void put(String key, Object value) {
		cacheProvider.putInCache(toString(key), value);
	}

	public void remove(String key) {
		cacheProvider.flushEntry(toString(key));
	}

	public void setCacheCapacity(int cacheCapacity) {
		cacheProvider.setCacheCapacity(cacheCapacity);
	}

	public void setCacheProvider(GeneralCacheAdministrator cacheProvider) {
		this.cacheProvider = cacheProvider;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setRefreshPeriod(int refreshPeriod) {
		this.refreshPeriod = refreshPeriod;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public void shutdown() {
		cacheProvider.destroy();
	}

	public String toString() {
		return "OSCache(" + regionName + ')';
	}

	private String toString(Object key) {
		return String.valueOf(key) + '.' + regionName;
	}

}
