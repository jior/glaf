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

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.StackKeyedObjectPoolFactory;

import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.exceptions.ConnectionPoolException;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesHelper;

public class DbcpConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory
			.getLogger(DbcpConnectionProvider.class);

	private final static String MIN_POOL_SIZE = "minPoolSize";
	private final static String MAX_POOL_SIZE = "maxPoolSize";
	private final static String INITIAL_POOL_SIZE = "initialPoolSize";
	private final static String MAX_IDLE_TIME = "maxIdleTime";
	private final static String MAX_STATEMENTS = "maxStatements";
	private final static String ACQUIRE_INCREMENT = "acquireIncrement";
	private final static String IDLE_CONNECTION_TEST_PERIOD = "idleConnectionTestPeriod";

	private volatile DataSource ds;
	private volatile Integer isolation;
	private volatile boolean autocommit;

	public DbcpConnectionProvider() {
		log.info("----------------------------DbcpConnectionProvider-----------------");
	}

	public void close() {

	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void configure(Properties props) throws RuntimeException {
		String jdbcDriverClass = props.getProperty(Environment.DRIVER);
		String jdbcUrl = props.getProperty(Environment.URL);
		Properties connectionProps = ConnectionProviderFactory
				.getConnectionProperties(props);

		log.info("DBCP using driver: " + jdbcDriverClass + " at URL: "
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
					ClassUtils.classForName(jdbcDriverClass);
				} catch (Exception e) {
					String msg = "JDBC Driver class not found: "
							+ jdbcDriverClass;
					log.error(msg, e);
					throw new RuntimeException(msg, e);
				}
			}
		}

		String dbUser = props.getProperty(Environment.USER);
		String dbPassword = props.getProperty(Environment.PASS);

		if (dbUser == null) {
			dbUser = ""; // Some RDBMS (e.g Postgresql) don't like null
							// usernames
		}

		if (dbPassword == null) {
			dbPassword = ""; // Some RDBMS (e.g Postgresql) don't like null
								// passwords
		}

		Integer minPoolSize = PropertiesHelper.getInteger(MIN_POOL_SIZE, props);
		Integer maxPoolSize = PropertiesHelper.getInteger(MAX_POOL_SIZE, props);
		Integer maxIdleTime = PropertiesHelper.getInteger(MAX_IDLE_TIME, props);
		Integer maxStatements = PropertiesHelper.getInteger(MAX_STATEMENTS,
				props);
		Integer acquireIncrement = PropertiesHelper.getInteger(
				ACQUIRE_INCREMENT, props);
		Integer idleTestPeriod = PropertiesHelper.getInteger(
				IDLE_CONNECTION_TEST_PERIOD, props);

		Properties properties = new Properties();

		for (Iterator<Object> ii = props.keySet().iterator(); ii.hasNext();) {
			String key = (String) ii.next();
			if (key.startsWith("hibernate.dbcp.")) {
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
			initialPoolSize = 2;
		}
		if (minPoolSize == null) {
			minPoolSize = 2;
		}
		if (maxPoolSize == null) {
			maxPoolSize = 20;
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

		// Create the actual pool of connections
		ObjectPool connectionPool = new GenericObjectPool(null);

		// Apply any properties
		if (props.getProperty("hibernate.connection.maxIdle") != null) {
			int value = PropertiesHelper.getInt("hibernate.connection.maxIdle",
					props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMaxIdle(value);
			}
		}

		if (props.getProperty("hibernate.connection.minIdle") != null) {
			int value = PropertiesHelper.getInt("hibernate.connection.minIdle",
					props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMinIdle(value);
			}
		}

		if (props.getProperty("hibernate.connection.maxActive") != null) {
			int value = PropertiesHelper.getInt(
					"hibernate.connection.maxActive", props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMaxActive(value);
			}
		}

		if (props.getProperty("hibernate.connection.maxWait") != null) {
			int value = PropertiesHelper.getInt("hibernate.connection.maxWait",
					props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMaxWait(value);
			}
		}

		// how often should the evictor run (if ever, default is -1 = off)
		if (props
				.getProperty("hibernate.connection.timeBetweenEvictionRunsMillis") != null) {
			int value = PropertiesHelper.getInt(
					"hibernate.connection.timeBetweenEvictionRunsMillis",
					props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool)
						.setTimeBetweenEvictionRunsMillis(value);

				// in each eviction run, ecict at least a fourth of "maxIdle"
				// connections
				int maxIdle = ((GenericObjectPool) connectionPool).getMaxIdle();
				int numTestsPerEvictionRun = (int) Math
						.ceil(((double) maxIdle / 4));
				((GenericObjectPool) connectionPool)
						.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
			}
		}
		// how long may a connection sit idle in the pool before it may be
		// evicted
		if (props
				.getProperty("hibernate.connection.minEvictableIdleTimeMillis") != null) {
			int value = PropertiesHelper
					.getInt("hibernate.connection.minEvictableIdleTimeMillis",
							props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool)
						.setMinEvictableIdleTimeMillis(value);
			}
		}

		// Create a factory to be used by the pool to create the connections
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				jdbcUrl, dbUser, dbPassword);

		// Create a factory for caching the PreparedStatements
		KeyedObjectPoolFactory kpf = null;

		if (maxStatements > 0) {
			kpf = new StackKeyedObjectPoolFactory(null, maxStatements);
		}

		// Wrap the connections and statements with pooled variants
		try {
			String testSQL = null;
			if (props.getProperty("hibernate.connection.testSQL") != null) {
				testSQL = PropertiesHelper.getString(
						"hibernate.connection.testSQL", props, null);
			}
			new PoolableConnectionFactory(connectionFactory, connectionPool,
					kpf, testSQL, false, false);
			if (testSQL != null) {
				((GenericObjectPool) connectionPool).setTestOnBorrow(true);
			}
		} catch (Exception e) {
			throw new ConnectionPoolException("DBCP", jdbcDriverClass, jdbcUrl,
					e);
		}

		ds = new PoolingDataSource(connectionPool);

		Connection conn = null;
		try {
			conn = ds.getConnection();
			if (conn == null) {
				throw new RuntimeException(
						"DBCP connection pool can't get jdbc connection");
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