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
