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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.exceptions.ConnectionPoolException;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesHelper;

public class DBCPConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory
			.getLogger(DBCPConnectionProvider.class);

	private volatile DataSource ds;
	private volatile Integer isolation;
	private volatile boolean autocommit;

	public DBCPConnectionProvider() {
		log.info("----------------------------DBCPConnectionProvider-----------------");
	}

	public void close() {

	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void configure(Properties props) throws RuntimeException {
		String jdbcDriverClass = props.getProperty(DBConfiguration.JDBC_DRIVER);
		String jdbcUrl = props.getProperty(DBConfiguration.JDBC_URL);
		Properties connectionProps = ConnectionProviderFactory
				.getConnectionProperties(props);

		log.info("DBCP using driver: " + jdbcDriverClass + " at URL: "
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

		String dbUser = props.getProperty(DBConfiguration.JDBC_USER);
		String dbPassword = props.getProperty(DBConfiguration.JDBC_PASSWORD);

		if (dbUser == null) {
			dbUser = ""; // Some RDBMS (e.g Postgresql) don't like null
							// usernames
		}

		if (dbPassword == null) {
			dbPassword = ""; // Some RDBMS (e.g Postgresql) don't like null
								// passwords
		}

		// Create the actual pool of connections
		ObjectPool connectionPool = new GenericObjectPool(null);

		// Apply any properties
		if (props.getProperty(DBConfiguration.POOL_TIMEOUT) != null) {
			int value = PropertiesHelper.getInt(DBConfiguration.POOL_TIMEOUT,
					props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMaxIdle(value);
			}
		}
		if (props.getProperty("connectionPool.minIdle") != null) {
			int value = PropertiesHelper.getInt("connectionPool.minIdle",
					props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMinIdle(value);
			}
		}
		if (props.getProperty("connectionPool.maxActive") != null) {
			int value = PropertiesHelper.getInt("connectionPool.maxActive",
					props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMaxActive(value);
			}
		}
		if (props.getProperty("connectionPool.maxWait") != null) {
			int value = PropertiesHelper.getInt("connectionPool.maxWait",
					props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMaxWait(value);
			}
		}
		// how often should the evictor run (if ever, default is -1 = off)
		if (props.getProperty("connectionPool.timeBetweenEvictionRunsMillis") != null) {
			int value = PropertiesHelper.getInt(
					"connectionPool.timeBetweenEvictionRunsMillis", props, 0);
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
		if (props.getProperty("connectionPool.minEvictableIdleTimeMillis") != null) {
			int value = PropertiesHelper.getInt(
					"connectionPool.minEvictableIdleTimeMillis", props, 0);
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
		if (props.getProperty(DBConfiguration.POOL_MAX_STATEMENTS) != null) {
			int value = PropertiesHelper.getInt(
					DBConfiguration.POOL_MAX_STATEMENTS, props, 0);
			if (value > 0) {
				kpf = new StackKeyedObjectPoolFactory(null, value);
			}
		}

		// Wrap the connections and statements with pooled variants
		try {
			String testSQL = null;
			if (props.getProperty("connectionPool.testSQL") != null) {
				testSQL = PropertiesHelper.getString("connectionPool.testSQL",
						props, null);
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