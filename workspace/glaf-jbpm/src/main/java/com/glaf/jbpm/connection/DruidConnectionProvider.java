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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;
import org.hibernate.util.PropertiesHelper;
import org.hibernate.util.ReflectHelper;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory
			.getLogger(DruidConnectionProvider.class);

	private final static String MIN_POOL_SIZE = "minPoolSize";
	private final static String MAX_POOL_SIZE = "maxPoolSize";
	private final static String INITIAL_POOL_SIZE = "initialPoolSize";
	private final static String MAX_IDLE_TIME = "maxIdleTime";
	private final static String MAX_STATEMENTS = "maxStatements";
	private final static String ACQUIRE_INCREMENT = "acquireIncrement";
	private final static String IDLE_CONNECTION_TEST_PERIOD = "idleConnectionTestPeriod";

	private DruidDataSource ds;
	private Integer isolation;
	private boolean autocommit;

	public void close() {
		try {
			ds.close();
		} catch (Exception sqle) {
			log.warn("could not destroy Druid connection pool", sqle);
		}
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	public void configure(Properties props) throws HibernateException {
		String jdbcDriverClass = props.getProperty(Environment.DRIVER);
		String jdbcUrl = props.getProperty(Environment.URL);
		Properties connectionProps = ConnectionProviderFactory
				.getConnectionProperties(props);

		log.info("Druid using driver: " + jdbcDriverClass + " at URL: "
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
				if (key.startsWith("hibernate.c3p0.")) {
					String newKey = key.substring(10);
					properties.put(newKey, props.get(key));
				}
			}

			for (Iterator<Object> ii = props.keySet().iterator(); ii.hasNext();) {
				String key = (String) ii.next();
				if (key.startsWith("hibernate.druid.")) {
					String newKey = key.substring(16);
					properties.put(newKey, props.get(key));
				}
			}

			Integer initialPoolSize = PropertiesHelper.getInteger(
					INITIAL_POOL_SIZE, props);
			if (initialPoolSize == null && minPoolSize != null) {
				properties.put(INITIAL_POOL_SIZE, String.valueOf(minPoolSize)
						.trim());
			}

			if (initialPoolSize == null) {
				initialPoolSize = 5;
			}
			if (minPoolSize == null) {
				minPoolSize = 5;
			}
			if (maxPoolSize == null) {
				maxPoolSize = 50;
			}
			if (maxIdleTime == null) {
				maxIdleTime = 25;
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

			ds = new DruidDataSource();

			DruidDataSourceFactory.config(ds, allProps);

			ds.setInitialSize(initialPoolSize);
			ds.setMinIdle(minPoolSize);
			ds.setMaxActive(maxPoolSize);

			ds.setDefaultAutoCommit(true);
			ds.setLogAbandoned(false);
			ds.setTestOnReturn(false);
			ds.setTestOnBorrow(false);

			ds.setTimeBetweenEvictionRunsMillis(((long) idleTestPeriod) * 1000L);
			ds.setMaxOpenPreparedStatements(maxStatements);
			ds.setMinEvictableIdleTimeMillis(((long) maxIdleTime) * 1000L);

			ds.setConnectProperties(allProps);
			ds.setUrl(jdbcUrl);
			ds.setDriverClassName(jdbcDriverClass);
			ds.setUsername(props.getProperty(Environment.USER));
			ds.setPassword(props.getProperty(Environment.PASS));
		} catch (Exception e) {
			log.error("could not instantiate Druid connection pool", e);
			throw new HibernateException(
					"Could not instantiate Druid connection pool", e);
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
		final Connection connection = ds.getConnection();
		if (isolation != null) {
			connection.setTransactionIsolation(isolation.intValue());
		}
		if (connection.getAutoCommit() != autocommit) {
			connection.setAutoCommit(autocommit);
		}
		return connection;
	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

}
