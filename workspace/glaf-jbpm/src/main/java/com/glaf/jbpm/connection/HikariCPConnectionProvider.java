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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;
import org.hibernate.util.PropertiesHelper;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.jdbc.connection.ConnectionConstants;
import com.glaf.core.util.ClassUtils;

public class HikariCPConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory
			.getLogger(HikariCPConnectionProvider.class);

	protected static Configuration conf = BaseConfiguration.create();

	private volatile HikariDataSource ds;

	private volatile Integer isolation;

	private volatile boolean autocommit;

	public HikariCPConnectionProvider() {
		log.info("----------------------------HikariCPConnectionProvider-----------------");
	}

	public void close() {
		if (ds != null) {
			ds.close();
		}
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	public void configure(Properties props) throws RuntimeException {
		Properties properties = new Properties();
		properties.putAll(props);

		for (Iterator<?> ii = props.keySet().iterator(); ii.hasNext();) {
			String key = (String) ii.next();
			if (key.startsWith("hikari.")) {
				String newKey = key.substring(7);
				properties.put(newKey, props.get(key));
			}
		}

		String jdbcDriverClass = properties.getProperty(Environment.DRIVER);
		String jdbcUrl = properties.getProperty(Environment.URL);

		Properties connectionProps = ConnectionProviderFactory
				.getConnectionProperties(properties);

		log.info("HikariCP using driver: " + jdbcDriverClass + " at URL: "
				+ jdbcUrl);
		log.info("Connection properties: "
				+ PropertiesHelper.maskOut(connectionProps, Environment.PASS));

		autocommit = PropertiesHelper.getBoolean(Environment.AUTOCOMMIT, props);
		log.info("autocommit mode: " + autocommit);

		if (jdbcDriverClass == null) {
			log.warn("No JDBC Driver class was specified by property "
					+ Environment.DRIVER);
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

			Integer initialPoolSize = PropertiesHelper.getInteger(
					ConnectionConstants.PROP_INITIALSIZE, properties);
			Integer minPoolSize = PropertiesHelper.getInteger(
					ConnectionConstants.PROP_MINACTIVE, properties);
			Integer maxPoolSize = PropertiesHelper.getInteger(
					ConnectionConstants.PROP_MAXACTIVE, properties);
			if (initialPoolSize == null && minPoolSize != null) {
				properties.put(ConnectionConstants.PROP_INITIALSIZE, String
						.valueOf(minPoolSize).trim());
			}

			if (maxPoolSize == null) {
				maxPoolSize = 50;
			}

			String dbUser = properties.getProperty(Environment.USER);
			String dbPassword = properties.getProperty(Environment.PASS);

			if (dbUser == null) {
				dbUser = "";
			}

			if (dbPassword == null) {
				dbPassword = "";
			}

			HikariConfig config = new HikariConfig();
			config.setDriverClassName(jdbcDriverClass);
			config.setJdbcUrl(jdbcUrl);
			config.setUsername(dbUser);
			config.setPassword(dbPassword);
			config.setMaximumPoolSize(maxPoolSize);
			config.setDataSourceProperties(properties);

			ds = new HikariDataSource(config);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("could not instantiate HikariCP connection pool", ex);
			throw new RuntimeException(
					"Could not instantiate HikariCP connection pool", ex);
		}
		String i = properties.getProperty(Environment.ISOLATION);
		if (i == null) {
			isolation = null;
		} else {
			isolation = new Integer(i);
			log.info("JDBC isolation level: "
					+ Environment.isolationLevelToString(isolation.intValue()));
		}

	}

	public Connection getConnection() throws SQLException {
		Connection connection = null;
		int count = 0;
		while (count < conf.getInt("jdbc.connection.retryCount", 10)) {
			try {
				connection = ds.getConnection();
				if (connection != null) {
					if (isolation != null) {
						connection
								.setTransactionIsolation(isolation.intValue());
					}
					if (connection.getAutoCommit() != autocommit) {
						connection.setAutoCommit(autocommit);
					}
					return connection;
				} else {
					count++;
					try {
						Thread.sleep(conf.getInt("jdbc.connection.retryTimeMs",
								500));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException ex) {
				count++;
				try {
					Thread.sleep(conf
							.getInt("jdbc.connection.retryTimeMs", 500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (count >= conf.getInt("jdbc.connection.retryCount", 10)) {
					ex.printStackTrace();
					throw ex;
				}
			}
		}
		return connection;
	}

	public DataSource getDataSource() {
		return ds;
	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

}