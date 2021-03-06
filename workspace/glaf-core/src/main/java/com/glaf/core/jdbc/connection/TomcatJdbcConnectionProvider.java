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

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesHelper;

public class TomcatJdbcConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory
			.getLogger(TomcatJdbcConnectionProvider.class);

	protected static Configuration conf = BaseConfiguration.create();

	private volatile DataSource ds;
	private volatile Integer isolation;
	private volatile boolean autocommit;

	public TomcatJdbcConnectionProvider() {
		log.info("----------------------------TomcatJdbcConnectionProvider-----------------");
	}

	public void close() {

	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	public void configure(Properties props) throws RuntimeException {
		String jdbcDriverClass = props.getProperty(DBConfiguration.JDBC_DRIVER);
		String jdbcUrl = props.getProperty(DBConfiguration.JDBC_URL);
		Properties connectionProps = ConnectionProviderFactory
				.getConnectionProperties(props);

		log.info("TomcatJdbc using driver: " + jdbcDriverClass + " at URL: "
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

		Integer maxIdle = PropertiesHelper.getInteger(
				ConnectionConstants.PROP_MAXIDLE, props);
		Integer minIdle = PropertiesHelper.getInteger(
				ConnectionConstants.PROP_MINIDLE, props);
		Integer maxActive = PropertiesHelper.getInteger(
				ConnectionConstants.PROP_MAXACTIVE, props);

		Integer timeBetweenEvictionRuns = PropertiesHelper.getInteger(
				ConnectionConstants.PROP_TIMEBETWEENEVICTIONRUNSMILLIS, props);

		Integer maxWait = PropertiesHelper.getInteger(
				ConnectionConstants.PROP_MAXWAIT, props);

		String validationQuery = props
				.getProperty(ConnectionConstants.PROP_VALIDATIONQUERY);

		if (maxIdle == null) {
			maxIdle = 20;
		}

		if (minIdle == null) {
			minIdle = 20;
		}

		if (maxActive == null) {
			maxActive = 50;
		}

		if (timeBetweenEvictionRuns == null) {
			timeBetweenEvictionRuns = 600;
		}

		if (maxWait == null) {
			maxWait = 60;
		}

		String dbUser = props.getProperty(DBConfiguration.JDBC_USER);
		String dbPassword = props.getProperty(DBConfiguration.JDBC_PASSWORD);

		if (dbUser == null) {
			dbUser = "";
		}

		if (dbPassword == null) {
			dbPassword = "";
		}

		PoolConfiguration p = new PoolProperties();
		p.setUrl(jdbcUrl);
		p.setDriverClassName(jdbcDriverClass);
		p.setUsername(dbUser);
		p.setPassword(dbPassword);
		p.setJmxEnabled(false);

		p.setTestOnBorrow(false);
		p.setTestOnReturn(false);

		p.setValidationInterval(30000);
		p.setMinEvictableIdleTimeMillis(300000);// 配置一个连接在池中最小生存的时间，单位是毫秒
		p.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRuns * 1000);// 间隔多久才进行一次检测，检测需要关闭的空闲连接
		p.setMaxActive(maxActive);
		p.setMaxIdle(maxIdle);
		p.setMinIdle(minIdle);
		p.setInitialSize(1);
		p.setMaxWait(maxWait);

		// p.setRemoveAbandonedTimeout(600);// 超过10分钟开始关闭空闲连接
		// p.setRemoveAbandoned(true);
		// p.setLogAbandoned(true);

		if (validationQuery != null) {
			p.setTestWhileIdle(true);
			p.setValidationQuery(validationQuery);
		}

		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

		this.parsePoolProperties(p, props);

		org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
		datasource.setPoolProperties(p);

		ds = (DataSource) datasource;

		Connection conn = null;
		try {
			conn = ds.getConnection();
			if (conn == null) {
				throw new RuntimeException(
						"TomcatJdbc connection pool can't get jdbc connection");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
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

	protected void parsePoolProperties(PoolConfiguration poolProperties,
			Properties properties) {

		String value = null;

		value = properties
				.getProperty(ConnectionConstants.PROP_DEFAULTAUTOCOMMIT);
		if (value != null) {
			poolProperties.setDefaultAutoCommit(Boolean.valueOf(value));
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_DEFAULTREADONLY);
		if (value != null) {
			poolProperties.setDefaultReadOnly(Boolean.valueOf(value));
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_DEFAULTTRANSACTIONISOLATION);
		if (value != null) {
			int level = ConnectionConstants.UNKNOWN_TRANSACTIONISOLATION;
			if ("NONE".equalsIgnoreCase(value)) {
				level = Connection.TRANSACTION_NONE;
			} else if ("READ_COMMITTED".equalsIgnoreCase(value)) {
				level = Connection.TRANSACTION_READ_COMMITTED;
			} else if ("READ_UNCOMMITTED".equalsIgnoreCase(value)) {
				level = Connection.TRANSACTION_READ_UNCOMMITTED;
			} else if ("REPEATABLE_READ".equalsIgnoreCase(value)) {
				level = Connection.TRANSACTION_REPEATABLE_READ;
			} else if ("SERIALIZABLE".equalsIgnoreCase(value)) {
				level = Connection.TRANSACTION_SERIALIZABLE;
			} else {
				try {
					level = Integer.parseInt(value);
				} catch (NumberFormatException e) {
					System.err
							.println("Could not parse defaultTransactionIsolation: "
									+ value);
					System.err
							.println("WARNING: defaultTransactionIsolation not set");
					System.err
							.println("using default value of database driver");
					level = ConnectionConstants.UNKNOWN_TRANSACTIONISOLATION;
				}
			}
			poolProperties.setDefaultTransactionIsolation(level);
		}

		value = properties.getProperty(ConnectionConstants.PROP_DEFAULTCATALOG);
		if (value != null) {
			poolProperties.setDefaultCatalog(value);
		}

		value = properties.getProperty(ConnectionConstants.PROP_MAXACTIVE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMaxActive(Integer.parseInt(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_MAXIDLE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMaxIdle(Integer.parseInt(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_MINIDLE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMinIdle(Integer.parseInt(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_INITIALSIZE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setInitialSize(Integer.parseInt(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_MAXWAIT);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMaxWait(Integer.parseInt(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_TESTONBORROW);
		if (value != null) {
			poolProperties.setTestOnBorrow(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties.getProperty(ConnectionConstants.PROP_TESTONRETURN);
		if (value != null) {
			poolProperties.setTestOnReturn(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties.getProperty(ConnectionConstants.PROP_TESTONCONNECT);
		if (value != null) {
			poolProperties.setTestOnConnect(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_TIMEBETWEENEVICTIONRUNSMILLIS);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setTimeBetweenEvictionRunsMillis(Integer
					.parseInt(value));
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_NUMTESTSPEREVICTIONRUN);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setNumTestsPerEvictionRun(Integer.parseInt(value));
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_MINEVICTABLEIDLETIMEMILLIS);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMinEvictableIdleTimeMillis(Integer
					.parseInt(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_TESTWHILEIDLE);
		if (value != null) {
			poolProperties.setTestWhileIdle(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_VALIDATOR_CLASS_NAME);
		if (value != null) {
			poolProperties.setValidatorClassName(value);
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_VALIDATIONINTERVAL);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setValidationInterval(Long.parseLong(value));
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED);
		if (value != null) {
			poolProperties.setAccessToUnderlyingConnectionAllowed(Boolean
					.valueOf(value).booleanValue());
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_REMOVEABANDONED);
		if (value != null) {
			poolProperties.setRemoveAbandoned(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_REMOVEABANDONEDTIMEOUT);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setRemoveAbandonedTimeout(Integer.parseInt(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_LOGABANDONED);
		if (value != null) {
			poolProperties.setLogAbandoned(Boolean.valueOf(value)
					.booleanValue());
		}

		if (poolProperties.getUsername() != null) {
			poolProperties.getDbProperties().setProperty("user",
					poolProperties.getUsername());
		}
		if (poolProperties.getPassword() != null) {
			poolProperties.getDbProperties().setProperty("password",
					poolProperties.getPassword());
		}

		value = properties.getProperty(ConnectionConstants.PROP_INITSQL);
		if (value != null) {
			poolProperties.setInitSQL(value);
		}

		value = properties.getProperty(ConnectionConstants.PROP_INTERCEPTORS);
		if (value != null) {
			poolProperties.setJdbcInterceptors(value);
		}

		value = properties.getProperty(ConnectionConstants.PROP_JMX_ENABLED);
		if (value != null) {
			poolProperties.setJmxEnabled(Boolean.parseBoolean(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_FAIR_QUEUE);
		if (value != null) {
			poolProperties.setFairQueue(Boolean.parseBoolean(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_USE_EQUALS);
		if (value != null) {
			poolProperties.setUseEquals(Boolean.parseBoolean(value));
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_ABANDONWHENPERCENTAGEFULL);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties
					.setAbandonWhenPercentageFull(Integer.parseInt(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_MAXAGE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMaxAge(Long.parseLong(value));
		}

		value = properties.getProperty(ConnectionConstants.PROP_USE_CON_LOCK);
		if (value != null) {
			poolProperties.setUseLock(Boolean.parseBoolean(value));
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_SUSPECT_TIMEOUT);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setSuspectTimeout(Integer.parseInt(value));
		}

		value = properties
				.getProperty(ConnectionConstants.PROP_ALTERNATE_USERNAME_ALLOWED);
		if (value != null) {
			poolProperties.setAlternateUsernameAllowed(Boolean
					.parseBoolean(value));
		}

	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

}