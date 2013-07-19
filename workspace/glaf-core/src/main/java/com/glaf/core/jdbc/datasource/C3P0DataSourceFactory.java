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

import java.sql.*;
import java.util.Iterator;
import java.util.Properties;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.exceptions.ConnectionPoolException;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesHelper;

public class C3P0DataSourceFactory implements IDataSourceFactory {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public DataSource makePooledDataSource(Properties props) {
		String dbDriver = props.getProperty(DBConfiguration.JDBC_DRIVER);
		String dbURL = props.getProperty(DBConfiguration.JDBC_URL);
		String dbUser = props.getProperty(DBConfiguration.JDBC_USER);
		String dbPassword = props.getProperty(DBConfiguration.JDBC_PASSWORD);

		// Load the database driver
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException cnfe) {
			try {
				ClassUtils.loadClass(dbDriver);
			} catch (RuntimeException ex) {
				// JDBC driver not found
				throw new RuntimeException(dbDriver + " not found");
			}
		}

		// Create the actual pool of connections
		com.mchange.v2.c3p0.ComboPooledDataSource ds = new com.mchange.v2.c3p0.ComboPooledDataSource();

		Properties c3props = new Properties();

		for (Iterator ii = props.keySet().iterator(); ii.hasNext();) {
			String key = (String) ii.next();
			if (key.startsWith("hibernate.c3p0.")) {
				String newKey = key.substring(10);
				c3props.put(newKey, props.get(key));
			} else if (key.startsWith("connection.c3p0.")) {
				String newKey = key.substring(11);
				c3props.put(newKey, props.get(key));
			}
		}

		Integer initialPoolSize = PropertiesHelper.getInteger(
				DBConfiguration.POOL_INIT_SIZE, props);
		Integer minPoolSize = PropertiesHelper.getInteger(
				DBConfiguration.POOL_MIN_SIZE, props);
		if (initialPoolSize == null && minPoolSize != null) {
			c3props.put(DBConfiguration.POOL_INIT_SIZE,
					String.valueOf(minPoolSize).trim());
		}

		Properties allProps = (Properties) props.clone();
		allProps.putAll(c3props);
		ds.setProperties(allProps);

		// Apply any properties
		if (props.getProperty(DBConfiguration.POOL_MAX_STATEMENTS) != null) {
			int size = PropertiesHelper.getInt(
					DBConfiguration.POOL_MAX_STATEMENTS, props, 0);
			if (size >= 0) {
				ds.setMaxStatements(size);
			}
		}
		if (props.getProperty(DBConfiguration.POOL_MAX_SIZE) != null) {
			int size = PropertiesHelper.getInt(DBConfiguration.POOL_MAX_SIZE,
					props, 0);
			if (size > 0) {
				ds.setMaxPoolSize(size);
			}
		}
		if (props.getProperty(DBConfiguration.POOL_MIN_SIZE) != null) {
			int size = PropertiesHelper.getInt(DBConfiguration.POOL_MIN_SIZE,
					props, 0);
			if (size >= 0) {
				ds.setMinPoolSize(size);
			}
		}

		if (props.getProperty(DBConfiguration.POOL_INIT_SIZE) != null) {
			int size = PropertiesHelper.getInt(DBConfiguration.POOL_INIT_SIZE,
					props, 0);
			if (size >= 0) {
				ds.setInitialPoolSize(size);
			}
		}

		if (props.getProperty(DBConfiguration.POOL_TIMEOUT) != null) {
			int maxIdleTime = PropertiesHelper.getInt(
					DBConfiguration.POOL_TIMEOUT, props, 0);
			if (maxIdleTime > 0) {
				ds.setMaxIdleTime(maxIdleTime);
			}
		}

		if (props.getProperty(DBConfiguration.POOL_ACQUIRE_INCREMENT) != null) {
			int size = PropertiesHelper.getInt(
					DBConfiguration.POOL_ACQUIRE_INCREMENT, props, 0);
			if (size > 0) {
				ds.setAcquireIncrement(size);
			}
		}

		if (props.getProperty(DBConfiguration.POOL_IDLE_TEST_PERIOD) != null) {
			int size = PropertiesHelper.getInt(
					DBConfiguration.POOL_IDLE_TEST_PERIOD, props, 0);
			if (size > 0) {
				ds.setIdleConnectionTestPeriod(size);
			}
		}

		// Load the database driver
		try {
			ds.setDriverClass(dbDriver);
		} catch (PropertyVetoException pve) {
			throw new ConnectionPoolException("C3P0", dbDriver, dbURL, pve);
		}

		ds.setJdbcUrl(dbURL);
		ds.setUser(dbUser);
		ds.setPassword(dbPassword);

		Connection conn = null;
		try {
			conn = ds.getConnection();
			if (conn == null) {
				throw new RuntimeException("can't get jdbc connection");
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}

		return ds;
	}
}