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

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.exceptions.ConnectionPoolException;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesHelper;

public class DBCPConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory
			.getLogger(DBCPConnectionProvider.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static final String PROP_DEFAULTAUTOCOMMIT = "defaultAutoCommit";
	protected static final String PROP_DEFAULTREADONLY = "defaultReadOnly";
	protected static final String PROP_DEFAULTTRANSACTIONISOLATION = "defaultTransactionIsolation";
	protected static final String PROP_DEFAULTCATALOG = "defaultCatalog";

	protected static final String PROP_MAXACTIVE = "maxActive";
	protected static final String PROP_MAXIDLE = "maxIdle";
	protected static final String PROP_MINIDLE = "minIdle";
	protected static final String PROP_INITIALSIZE = "initialSize";
	protected static final String PROP_MAXWAIT = "maxWait";
	protected static final String PROP_MAXAGE = "maxAge";

	protected static final String PROP_TESTONBORROW = "testOnBorrow";
	protected static final String PROP_TESTONRETURN = "testOnReturn";
	protected static final String PROP_TESTWHILEIDLE = "testWhileIdle";
	protected static final String PROP_TESTONCONNECT = "testOnConnect";
	protected static final String PROP_VALIDATIONQUERY = "validationQuery";
	protected static final String PROP_VALIDATOR_CLASS_NAME = "validatorClassName";

	protected static final String PROP_NUMTESTSPEREVICTIONRUN = "numTestsPerEvictionRun";
	protected static final String PROP_TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";
	protected static final String PROP_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";

	protected static final String PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED = "accessToUnderlyingConnectionAllowed";

	protected static final String PROP_REMOVEABANDONED = "removeAbandoned";
	protected static final String PROP_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";
	protected static final String PROP_LOGABANDONED = "logAbandoned";
	protected static final String PROP_ABANDONWHENPERCENTAGEFULL = "abandonWhenPercentageFull";

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

		autocommit = PropertiesHelper.getBoolean(
				DBConfiguration.JDBC_AUTOCOMMIT, props);
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
		if (props.getProperty(PROP_MAXIDLE) != null) {
			int value = PropertiesHelper.getInt(PROP_MAXIDLE, props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMaxIdle(value);
			}
		}
		if (props.getProperty(PROP_MINIDLE) != null) {
			int value = PropertiesHelper.getInt(PROP_MINIDLE, props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMinIdle(value);
			}
		}
		if (props.getProperty(PROP_MAXACTIVE) != null) {
			int value = PropertiesHelper.getInt(PROP_MAXACTIVE, props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMaxActive(value);
			}
		}
		if (props.getProperty(PROP_MAXWAIT) != null) {
			int value = PropertiesHelper.getInt(PROP_MAXWAIT, props, 0);
			if (value > 0) {
				((GenericObjectPool) connectionPool).setMaxWait(value);
			}
		}
		// how often should the evictor run (if ever, default is -1 = off)
		if (props.getProperty(PROP_TIMEBETWEENEVICTIONRUNSMILLIS) != null) {
			int value = PropertiesHelper.getInt(
					PROP_TIMEBETWEENEVICTIONRUNSMILLIS, props, 0);
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
		if (props.getProperty(PROP_MINEVICTABLEIDLETIMEMILLIS) != null) {
			int value = PropertiesHelper.getInt(
					PROP_MINEVICTABLEIDLETIMEMILLIS, props, 0);
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
			if (props.getProperty("validationQuery") != null) {
				testSQL = PropertiesHelper.getString("validationQuery", props,
						null);
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