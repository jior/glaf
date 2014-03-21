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

package com.glaf.core.jdbc.datasource;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.Environment;
import com.glaf.core.jdbc.connection.ConnectionProvider;
import com.glaf.core.jdbc.connection.ConnectionProviderFactory;

public class MultiRoutingDataSource extends AbstractRoutingDataSource {
	protected final static Log logger = LogFactory
			.getLog(MultiRoutingDataSource.class);

	private static volatile ConcurrentMap<Object, Object> targetDataSources = new ConcurrentHashMap<Object, Object>();

	private static volatile Object defaultTargetDataSource;

	private static volatile boolean LOAD_DATASOURCE_OK = false;

	static {
		reloadDS();
	}

	public MultiRoutingDataSource() {

	}

	private static void reloadDS() {
		if (!LOAD_DATASOURCE_OK) {
			logger.info("--------------MultiRoutingDataSource reloadDS()------------");
			Map<Object, Object> dataSourceMap = new java.util.HashMap<Object, Object>();
			Map<String, Properties> dataSourceProperties = DBConfiguration
					.getDataSourceProperties();
			Set<Entry<String, Properties>> entrySet = dataSourceProperties
					.entrySet();
			for (Entry<String, Properties> entry : entrySet) {
				String name = entry.getKey();
				Properties props = entry.getValue();
				if (props != null && StringUtils.isNotEmpty(name)) {
					try {
						ConnectionProvider provider = ConnectionProviderFactory
								.createProvider(name);
						dataSourceMap.put(name, provider.getDataSource());

						if (StringUtils.equals(name,
								Environment.DEFAULT_SYSTEM_NAME)) {
							defaultTargetDataSource = provider.getDataSource();
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex);
					}
				}
			}

			if (defaultTargetDataSource == null) {
				Properties props = DBConfiguration
						.getProperties(Environment.DEFAULT_SYSTEM_NAME);
				if (props != null) {
					ConnectionProvider provider = ConnectionProviderFactory
							.createProvider(Environment.DEFAULT_SYSTEM_NAME,
									props);
					dataSourceMap.put(Environment.DEFAULT_SYSTEM_NAME,
							provider.getDataSource());
				}
			}

			targetDataSources.clear();
			targetDataSources.putAll(dataSourceMap);
			LOAD_DATASOURCE_OK = true;
			DBConfiguration.successReloadDataSource();

			logger.info("##datasources:" + targetDataSources.keySet());

		}
	}

	@Override
	public void afterPropertiesSet() {
		reloadDS();
		setDefaultTargetDataSource(defaultTargetDataSource);
		setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	@Override
	protected synchronized Object determineCurrentLookupKey() {
		if (DBConfiguration.requireReloadDataSource()) {
			reloadDS();
		}
		logger.debug("currentSystemName:" + Environment.getCurrentSystemName());
		return Environment.getCurrentSystemName();
	}

}