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
import java.util.Properties;
import javax.sql.DataSource;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.exceptions.ConnectionPoolException;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesHelper;

public class DBCPDataSourceFactory implements IDataSourceFactory {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataSource makePooledDataSource(Properties props) {
		String dbDriver = props.getProperty(DBConfiguration.JDBC_DRIVER);
		String dbURL = props.getProperty(DBConfiguration.JDBC_URL);
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
		org.apache.commons.pool.ObjectPool connectionPool = new org.apache.commons.pool.impl.GenericObjectPool(
				null);

		// Apply any properties
		if (props.getProperty(DBConfiguration.POOL_TIMEOUT) != null) {
			int value = PropertiesHelper.getInt(DBConfiguration.POOL_TIMEOUT,
					props, 0);
			if (value > 0) {
				((org.apache.commons.pool.impl.GenericObjectPool) connectionPool)
						.setMaxIdle(value);
			}
		}
		if (props.getProperty("connectionPool.minIdle") != null) {
			int value = PropertiesHelper.getInt("connectionPool.minIdle",
					props, 0);
			if (value > 0) {
				((org.apache.commons.pool.impl.GenericObjectPool) connectionPool)
						.setMinIdle(value);
			}
		}
		if (props.getProperty("connectionPool.maxActive") != null) {
			int value = PropertiesHelper.getInt("connectionPool.maxActive",
					props, 0);
			if (value > 0) {
				((org.apache.commons.pool.impl.GenericObjectPool) connectionPool)
						.setMaxActive(value);
			}
		}
		if (props.getProperty("connectionPool.maxWait") != null) {
			int value = PropertiesHelper.getInt("connectionPool.maxWait",
					props, 0);
			if (value > 0) {
				((org.apache.commons.pool.impl.GenericObjectPool) connectionPool)
						.setMaxWait(value);
			}
		}
		// how often should the evictor run (if ever, default is -1 = off)
		if (props.getProperty("connectionPool.timeBetweenEvictionRunsMillis") != null) {
			int value = PropertiesHelper.getInt(
					"connectionPool.timeBetweenEvictionRunsMillis", props, 0);
			if (value > 0) {
				((org.apache.commons.pool.impl.GenericObjectPool) connectionPool)
						.setTimeBetweenEvictionRunsMillis(value);

				// in each eviction run, ecict at least a fourth of "maxIdle"
				// connections
				int maxIdle = ((org.apache.commons.pool.impl.GenericObjectPool) connectionPool)
						.getMaxIdle();
				int numTestsPerEvictionRun = (int) Math
						.ceil(((double) maxIdle / 4));
				((org.apache.commons.pool.impl.GenericObjectPool) connectionPool)
						.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
			}
		}
		// how long may a connection sit idle in the pool before it may be
		// evicted
		if (props.getProperty("connectionPool.minEvictableIdleTimeMillis") != null) {
			int value = PropertiesHelper.getInt(
					"connectionPool.minEvictableIdleTimeMillis", props, 0);
			if (value > 0) {
				((org.apache.commons.pool.impl.GenericObjectPool) connectionPool)
						.setMinEvictableIdleTimeMillis(value);
			}
		}

		// Create a factory to be used by the pool to create the connections
		org.apache.commons.dbcp.ConnectionFactory connectionFactory = new org.apache.commons.dbcp.DriverManagerConnectionFactory(
				dbURL, dbUser, dbPassword);

		// Create a factory for caching the PreparedStatements
		org.apache.commons.pool.KeyedObjectPoolFactory kpf = null;
		if (props.getProperty(DBConfiguration.POOL_MAX_STATEMENTS) != null) {
			int value = PropertiesHelper.getInt(
					DBConfiguration.POOL_MAX_STATEMENTS, props, 0);
			if (value > 0) {
				kpf = new org.apache.commons.pool.impl.StackKeyedObjectPoolFactory(
						null, value);
			}
		}

		// Wrap the connections and statements with pooled variants
		try {
			String testSQL = null;
			if (props.getProperty("connectionPool.testSQL") != null) {
				testSQL = PropertiesHelper.getString("connectionPool.testSQL",
						props, null);
			}
			new org.apache.commons.dbcp.PoolableConnectionFactory(
					connectionFactory, connectionPool, kpf, testSQL, false,
					false);
			if (testSQL != null) {
				((org.apache.commons.pool.impl.GenericObjectPool) connectionPool)
						.setTestOnBorrow(true);
			}
		} catch (Exception e) {
			throw new ConnectionPoolException("DBCP", dbDriver, dbURL, e);
		}

		// Create the datasource
		DataSource ds = new org.apache.commons.dbcp.PoolingDataSource(
				connectionPool);

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