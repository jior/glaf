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

package com.glaf.jbpm.connection;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.util.PropertiesHelper;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.exceptions.PoolNotFoundException;
import com.glaf.core.jdbc.ConnectionProviderImpl;

public class DbcpConnectionProvider extends ConnectionProviderImpl implements
		ConnectionProvider {
	protected final static Log logger = LogFactory
			.getLog(DbcpConnectionProvider.class);

	private final static String MIN_POOL_SIZE = "minPoolSize";
	private final static String MAX_POOL_SIZE = "maxPoolSize";

	public DbcpConnectionProvider() {

	}

	public DbcpConnectionProvider(Properties properties)
			throws PoolNotFoundException {
		super(properties);
		this.configure(properties);
	}

	@Override
	public void close() throws HibernateException {
		try {
			super.close();
		} catch (Exception ex) {
			throw new HibernateException(ex);
		}
	}

	@Override
	public void configure(Properties properties) throws HibernateException {
		logger.info("jbpm properties:" + properties);
		try {
			if (properties == null) {
				properties = DBConfiguration.getDefaultDataSourceProperties();
			}
			Integer minPoolSize = PropertiesHelper.getInteger(MIN_POOL_SIZE,
					properties);
			Integer maxPoolSize = PropertiesHelper.getInteger(MAX_POOL_SIZE,
					properties);
			if (minPoolSize == null) {
				minPoolSize = 1;
			}
			if (maxPoolSize == null) {
				maxPoolSize = 20;
			}
			if (minPoolSize < 1) {
				minPoolSize = 1;
			}
			if (maxPoolSize < 10) {
				maxPoolSize = 10;
			}
			if (maxPoolSize > 100) {
				maxPoolSize = 100;
			}
			if (minPoolSize > maxPoolSize) {
				minPoolSize = maxPoolSize;
			}
			String jdbcDriverClass = properties.getProperty(Environment.DRIVER);
			String jdbcUrl = properties.getProperty(Environment.URL);
			String user = properties.getProperty(Environment.USER);
			String password = properties.getProperty(Environment.PASS);

			properties.setProperty(DBConfiguration.JDBC_NAME, "jbpm");

			if (jdbcDriverClass != null) {
				properties.setProperty(DBConfiguration.JDBC_DRIVER,
						jdbcDriverClass);
			}
			if (jdbcUrl != null) {
				String type = DBConfiguration.getDatabaseType(jdbcUrl);
				properties.setProperty(DBConfiguration.JDBC_URL, jdbcUrl);
				properties.setProperty(DBConfiguration.JDBC_TYPE, type);
			}
			if (user != null) {
				properties.setProperty(DBConfiguration.JDBC_USER, user);
			}
			if (password != null) {
				properties.setProperty(DBConfiguration.JDBC_PASSWORD, password);
			}

			properties.setProperty(DBConfiguration.POOL_MIN_SIZE,
					String.valueOf(minPoolSize));
			properties.setProperty(DBConfiguration.POOL_MAX_SIZE,
					String.valueOf(maxPoolSize));

			super.create(properties, false, "jbpm");
		} catch (PoolNotFoundException ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new HibernateException(ex);
		}
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

}
