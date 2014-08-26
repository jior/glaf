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
import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;
import org.hibernate.util.PropertiesHelper;
import org.hibernate.util.ReflectHelper;

import com.glaf.core.util.JdbcUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory
			.getLogger(HikariCPConnectionProvider.class);

	private final static String MIN_POOL_SIZE = "minPoolSize";

	private final static String MAX_POOL_SIZE = "maxPoolSize";

	private final static String MAX_IDLE_TIME = "maxIdleTime";

	private final static String MAX_STATEMENTS = "maxStatements";

	private final static String ACQUIRE_INCREMENT = "acquireIncrement";

	private final static String IDLE_CONNECTION_TEST_PERIOD = "idleConnectionTestPeriod";

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
		String jdbcDriverClass = props.getProperty(Environment.DRIVER);
		String jdbcUrl = props.getProperty(Environment.URL);
		Properties connectionProps = ConnectionProviderFactory
				.getConnectionProperties(props);

		log.info("HikariCP using driver: " + jdbcDriverClass + " at URL: "
				+ jdbcUrl);
		log.info("Connection properties: "
				+ PropertiesHelper.maskOut(connectionProps, "password"));

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
					ReflectHelper.classForName(jdbcDriverClass);
				} catch (ClassNotFoundException e) {
					String msg = "JDBC Driver class not found: "
							+ jdbcDriverClass;
					log.error(msg, e);
					throw new HibernateException(msg, e);
				}
			}
		}

		try {
			Integer minPoolSize = PropertiesHelper.getInteger(MIN_POOL_SIZE,
					props);
			Integer maxPoolSize = PropertiesHelper.getInteger(MAX_POOL_SIZE,
					props);
			Integer maxIdleTime = PropertiesHelper.getInteger(MAX_IDLE_TIME,
					props);
			Integer maxStatements = PropertiesHelper.getInteger(MAX_STATEMENTS,
					props);
			Integer acquireIncrement = PropertiesHelper.getInteger(
					ACQUIRE_INCREMENT, props);
			Integer idleTestPeriod = PropertiesHelper.getInteger(
					IDLE_CONNECTION_TEST_PERIOD, props);

			Properties properties = new Properties();

			for (Iterator<Object> ii = props.keySet().iterator(); ii.hasNext();) {
				String key = (String) ii.next();
				if (key.startsWith("hibernate.hikari.")) {
					String newKey = key.substring(17);
					properties.put(newKey, props.get(key));
				}
				if (key.startsWith("hikari.")) {
					String newKey = key.substring(7);
					properties.put(newKey, props.get(key));
				}
			}

			String dbUser = props.getProperty(Environment.USER);
			String dbPassword = props.getProperty(Environment.PASS);

			if (dbUser == null) {
				dbUser = "";
			}

			if (dbPassword == null) {
				dbPassword = "";
			}

			if (minPoolSize == null) {
				minPoolSize = 10;
			}

			if (maxPoolSize == null) {
				maxPoolSize = 100;
			}

			if (maxIdleTime == null) {
				maxIdleTime = 60;
			}

			if (maxStatements == null) {
				maxStatements = 200;
			}

			if (acquireIncrement == null) {
				acquireIncrement = 1;
			}

			if (idleTestPeriod == null) {
				idleTestPeriod = 120;
			}

			Properties allProps = (Properties) props.clone();
			allProps.putAll(properties);

			HikariConfig config = new HikariConfig();
			config.setDriverClassName(jdbcDriverClass);
			config.setJdbcUrl(jdbcUrl);
			config.setUsername(dbUser);
			config.setPassword(dbPassword);
			config.setMaximumPoolSize(maxPoolSize);
			config.setDataSourceProperties(allProps);

			ds = new HikariDataSource(config);

		} catch (Exception e) {
			log.error("could not instantiate HikariCP connection pool", e);
			throw new RuntimeException(
					"Could not instantiate HikariCP connection pool", e);
		}

		Connection conn = null;
		try {
			conn = ds.getConnection();
			if (conn == null) {
				throw new RuntimeException(
						"HikariCP connection pool can't get jdbc connection");
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}

		String i = props.getProperty(Environment.ISOLATION);
		if (i == null) {
			isolation = null;
		} else {
			isolation = new Integer(i);
			log.info("JDBC isolation level: "
					+ Environment.isolationLevelToString(isolation.intValue()));
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