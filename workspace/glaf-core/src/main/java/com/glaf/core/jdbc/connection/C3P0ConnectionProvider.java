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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.DataSources;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesHelper;

public class C3P0ConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory
			.getLogger(C3P0ConnectionProvider.class);

	private volatile DataSource ds;
	private volatile Integer isolation;
	private volatile boolean autocommit;

	public C3P0ConnectionProvider() {
		log.info("----------------------------C3P0ConnectionProvider-----------------");
	}

	public void close() {
		try {
			DataSources.destroy(ds);
		} catch (SQLException sqle) {
			log.warn("could not destroy C3P0 connection pool", sqle);
		}
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	public void configure(Properties props) throws RuntimeException {
		String jdbcDriverClass = props.getProperty(DBConfiguration.JDBC_DRIVER);
		String jdbcUrl = props.getProperty(DBConfiguration.JDBC_URL);
		Properties connectionProps = ConnectionProviderFactory
				.getConnectionProperties(props);

		log.info("C3P0 using driver: " + jdbcDriverClass + " at URL: "
				+ jdbcUrl);
		log.info("Connection properties: "
				+ PropertiesHelper.maskOut(connectionProps, "password"));

		autocommit = PropertiesHelper.getBoolean(DBConfiguration.JDBC_AUTOCOMMIT,
				props);
		log.info("autocommit mode: " + autocommit);

		if (jdbcDriverClass == null) {
			log.warn("No JDBC Driver class was specified by property "
					+ DBConfiguration.JDBC_DRIVER);
		} else {
			try {
				Class.forName(jdbcDriverClass);
			} catch (ClassNotFoundException cnfe) {
				try {
					ClassUtils.classForName(jdbcDriverClass);
				} catch (Exception e) {
					String msg = "JDBC Driver class not found: "
							+ jdbcDriverClass;
					log.error(msg, e);
					throw new RuntimeException(msg, e);
				}
			}
		}

		try {

			Properties c3props = new Properties();

			for (Iterator<?> ii = props.keySet().iterator(); ii.hasNext();) {
				String key = (String) ii.next();
				if (key.startsWith("hibernate.c3p0.")) {
					String newKey = key.substring(10);
					c3props.put(newKey, props.get(key));
				} else if (key.startsWith("connection.c3p0.")) {
					String newKey = key.substring(11);
					c3props.put(newKey, props.get(key));
				} else if (key.startsWith("c3p0.")) {
					String newKey = key;
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

			DataSource unpooled = DataSources.unpooledDataSource(jdbcUrl,
					connectionProps);

			Properties allProps = (Properties) props.clone();
			allProps.putAll(c3props);

			ds = DataSources.pooledDataSource(unpooled, allProps);
		} catch (Exception e) {
			log.error("could not instantiate C3P0 connection pool", e);
			throw new RuntimeException(
					"Could not instantiate C3P0 connection pool", e);
		}

		Connection conn = null;
		try {
			conn = ds.getConnection();
			if (conn == null) {
				throw new RuntimeException(
						"C3P0 connection pool can't get jdbc connection");
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}

		String i = props.getProperty(DBConfiguration.JDBC_ISOLATION);
		if (i == null) {
			isolation = null;
		} else {
			isolation = new Integer(i);
			log.info("JDBC isolation level: "
					+ DBConfiguration.isolationLevelToString(isolation
							.intValue()));
		}

	}

	public Connection getConnection() throws SQLException {
		final Connection c = ds.getConnection();
		if (isolation != null) {
			c.setTransactionIsolation(isolation.intValue());
		}
		if (c.getAutoCommit() != autocommit) {
			c.setAutoCommit(autocommit);
		}
		return c;
	}

	public DataSource getDataSource() {
		return ds;
	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

}