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

package com.glaf.core.jdbc.connection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.Environment;
import com.glaf.core.util.ClassUtils;

public final class ConnectionProviderFactory {

	private static final Logger log = LoggerFactory
			.getLogger(ConnectionProviderFactory.class);

	private static final ConcurrentMap<String, ConnectionProvider> providerCache = new ConcurrentHashMap<String, ConnectionProvider>();

	private static final Set<String> SPECIAL_PROPERTIES;

	static {
		SPECIAL_PROPERTIES = new HashSet<String>();
		SPECIAL_PROPERTIES.add(Environment.DATASOURCE);
		SPECIAL_PROPERTIES.add(Environment.URL);
		SPECIAL_PROPERTIES.add(Environment.CONNECTION_PROVIDER);
		SPECIAL_PROPERTIES.add(Environment.POOL_SIZE);
		SPECIAL_PROPERTIES.add(Environment.ISOLATION);
		SPECIAL_PROPERTIES.add(Environment.DRIVER);
		SPECIAL_PROPERTIES.add(Environment.USER);
	}

	private static boolean c3p0ConfigDefined(Properties properties) {
		Iterator<?> iter = properties.keySet().iterator();
		while (iter.hasNext()) {
			String property = (String) iter.next();
			if (property.startsWith("c3p0")) {
				return true;
			}
		}
		return false;
	}

	private static boolean c3p0ProviderPresent() {
		try {
			ClassUtils
					.classForName("com.glaf.core.jdbc.connection.C3P0ConnectionProvider");
		} catch (Exception e) {
			log.warn("c3p0 properties is specificed, but could not find com.glaf.core.jdbc.connection.C3P0ConnectionProvider from the classpath, "
					+ "these properties are going to be ignored.");
			return false;
		}
		return true;
	}

	public static ConnectionProvider createProvider(Properties properties) {
		return createProvider(properties, null);
	}

	@SuppressWarnings("rawtypes")
	private static ConnectionProvider createProvider(Properties properties,
			Map connectionProviderInjectionData) {
		log.debug("---------------------------ConnectionProvider create----------------");
		ConnectionProvider provider = null;
		String providerClass = properties
				.getProperty(Environment.CONNECTION_PROVIDER);
		if (providerClass != null) {
			provider = initializeConnectionProviderFromConfig(providerClass);
		} else if (c3p0ConfigDefined(properties) && c3p0ProviderPresent()) {
			provider = initializeConnectionProviderFromConfig("com.glaf.core.jdbc.connection.C3P0ConnectionProvider");
		}

		if (provider == null) {
			provider = initializeConnectionProviderFromConfig("com.glaf.core.jdbc.connection.DruidConnectionProvider");
			if (StringUtils.equals(properties.getProperty(Environment.DRIVER),
					"org.sqlite.JDBC")) {
				provider = initializeConnectionProviderFromConfig("com.glaf.core.jdbc.connection.C3P0ConnectionProvider");
			}
		}

		if (connectionProviderInjectionData != null
				&& connectionProviderInjectionData.size() != 0) {
			try {
				BeanInfo info = Introspector.getBeanInfo(provider.getClass());
				PropertyDescriptor[] descritors = info.getPropertyDescriptors();
				int size = descritors.length;
				for (int index = 0; index < size; index++) {
					String propertyName = descritors[index].getName();
					if (connectionProviderInjectionData
							.containsKey(propertyName)) {
						Method method = descritors[index].getWriteMethod();
						method.invoke(provider,
								new Object[] { connectionProviderInjectionData
										.get(propertyName) });
					}
				}
			} catch (IntrospectionException e) {
				throw new RuntimeException(
						"Unable to inject objects into the connection provider",
						e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(
						"Unable to inject objects into the connection provider",
						e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(
						"Unable to inject objects into the connection provider",
						e);
			}
		}
		provider.configure(properties);
		log.debug("---------------------------ConnectionProvider end----------------");
		return provider;
	}

	public static ConnectionProvider createProvider(String systemName) {
		if (providerCache.get(systemName) != null) {
			return providerCache.get(systemName);
		}
		ConnectionProvider model = createProvider(Environment
				.getDataSourcePropertiesByName(systemName));
		providerCache.put(systemName, model);
		return model;
	}

	protected static Properties getConnectionProperties(Properties properties) {
		Iterator<?> iter = properties.keySet().iterator();
		Properties result = new Properties();
		while (iter.hasNext()) {
			String prop = (String) iter.next();
			if (prop.startsWith(Environment.CONNECTION_PREFIX)
					&& !SPECIAL_PROPERTIES.contains(prop)) {
				result.setProperty(prop.substring(Environment.CONNECTION_PREFIX
						.length() + 1), properties.getProperty(prop));
			}
		}
		String userName = properties.getProperty(Environment.USER);
		if (userName != null) {
			result.setProperty("user", userName);
		}
		return result;
	}

	private static ConnectionProvider initializeConnectionProviderFromConfig(
			String providerClass) {
		ConnectionProvider connections;
		try {
			log.info("Initializing connection provider: " + providerClass);
			connections = (ConnectionProvider) ClassUtils.classForName(
					providerClass).newInstance();
		} catch (Exception e) {
			log.error("Could not instantiate connection provider", e);
			throw new RuntimeException(
					"Could not instantiate connection provider: "
							+ providerClass);
		}
		return connections;
	}

	private ConnectionProviderFactory() {

	}

}