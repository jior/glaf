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
package com.glaf.j2cache.ehcache;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.j2cache.CacheException;
import com.glaf.j2cache.CacheExpiredListener;
import com.glaf.j2cache.CacheProvider;
import com.glaf.j2cache.ehcache.EhCache;
import com.glaf.core.util.FileUtils;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;

/**
 * Cache Provider plugin
 * 
 * Taken from EhCache 1.3 distribution
 */
public class EhCacheProvider implements CacheProvider {

	private final static Logger log = LoggerFactory
			.getLogger(EhCacheProvider.class);

	public static final String EHCACHE_CONFIGURATION_RESOURCE_NAME = "ehcache.configurationResourceName";

	private final static String CONFIG_XML = "/conf/j2cache/ehcache.xml";

	private static final AtomicInteger REFERENCE_COUNT = new AtomicInteger();

	private volatile CacheManager cacheManager;

	private volatile ConcurrentMap<String, EhCache> cacheMap;

	@Override
	public String name() {
		return "ehcache";
	}

	/**
	 * Builds a Cache.
	 * <p>
	 * Even though this method provides properties, they are not used.
	 * Properties for EHCache are specified in the ehcache.xml file.
	 * Configuration will be read from ehcache.xml for a cache declaration where
	 * the name attribute matches the name parameter in this builder.
	 * 
	 * @param name
	 *            the name of the cache. Must match a cache configured in
	 *            ehcache.xml
	 * @param properties
	 *            not used
	 * @return a newly built cache will be built and initialised
	 * @throws CacheException
	 *             inter alia, if a cache of the same name already exists
	 */
	public synchronized EhCache buildCache(String name, boolean autoCreate,
			CacheExpiredListener listener) throws CacheException {
		EhCache ehcache = cacheMap.get(name);
		if (ehcache == null && autoCreate) {
			try {
				ehcache = cacheMap.get(name);
				if (ehcache == null) {
					net.sf.ehcache.Cache cache = cacheManager.getCache(name);
					if (cache == null) {
						log.warn("Could not find configuration [" + name
								+ "]; using defaults.");
						cacheManager.addCache(name);
						cache = cacheManager.getCache(name);
						log.debug("started EHCache region: " + name);
					}
					ehcache = new EhCache(cache, listener);
					cacheMap.put(name, ehcache);
				}
			} catch (net.sf.ehcache.CacheException ex) {
				ex.printStackTrace();
				throw new CacheException(ex);
			}
		}
		return ehcache;
	}

	/**
	 * Callback to perform any necessary initialization of the underlying cache
	 * implementation during SessionFactory construction.
	 * 
	 * @param properties
	 *            current configuration settings.
	 */
	public void start(Properties props) throws CacheException {
		if (cacheManager != null) {
			log.warn("Attempt to restart an already started EhCacheProvider. Use sessionFactory.close() "
					+ " between repeated calls to buildSessionFactory. Using previously created EhCacheProvider."
					+ " If this behaviour is required, consider using net.sf.ehcache.hibernate.SingletonEhCacheProvider.");
			return;
		}

		log.info("load cacheManager from spring ioc ");
		if (ContextFactory.hasBean("ehCacheManager")) {
			cacheManager = ContextFactory.getBean("ehCacheManager");
		}

		if (cacheManager == null) {
			String configurationResourceName = null;
			if (props != null) {
				configurationResourceName = (String) props
						.get(EHCACHE_CONFIGURATION_RESOURCE_NAME);
			}

			if (configurationResourceName == null
					|| configurationResourceName.length() == 0) {
				configurationResourceName = CONFIG_XML;
			}

			String filename = SystemProperties.getConfigRootPath()
					+ configurationResourceName;
			InputStream configStream = FileUtils.getInputStream(filename);

			log.info("load ehcahce config:" + filename);
			Configuration config = ConfigurationFactory
					.parseConfiguration(configStream);
			config.setName(this.name());
			cacheManager = CacheManager.newInstance(config);
			cacheManager.setName(this.name());
			REFERENCE_COUNT.incrementAndGet();
		}
		cacheMap = new ConcurrentHashMap<String, EhCache>();
	}

	/**
	 * Callback to perform any necessary cleanup of the underlying cache
	 * implementation during SessionFactory.close().
	 */
	public void stop() {
		if (cacheManager != null) {
			if (REFERENCE_COUNT.decrementAndGet() == 0) {
				cacheManager.shutdown();
			}
			cacheManager = null;
		}
	}

}
