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

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesHelper;

public class TomcatJdbcConnectionProvider implements ConnectionProvider {

	private static final Logger log = LoggerFactory
			.getLogger(TomcatJdbcConnectionProvider.class);

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

	protected static final String PROP_INITSQL = "initSQL";
	protected static final String PROP_INTERCEPTORS = "jdbcInterceptors";
	protected static final String PROP_VALIDATIONINTERVAL = "validationInterval";
	protected static final String PROP_JMX_ENABLED = "jmxEnabled";
	protected static final String PROP_FAIR_QUEUE = "fairQueue";

	protected static final String PROP_USE_EQUALS = "useEquals";
	protected static final String PROP_USE_CON_LOCK = "useLock";

	protected static final String PROP_SUSPECT_TIMEOUT = "suspectTimeout";

	protected static final String PROP_ALTERNATE_USERNAME_ALLOWED = "alternateUsernameAllowed";

	public static final int UNKNOWN_TRANSACTIONISOLATION = -1;

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
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(50);
		p.setMaxIdle(20);
		p.setInitialSize(5);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
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
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}

	}

	public Connection getConnection() throws SQLException {
		final Connection conn = ds.getConnection();
		if (isolation != null) {
			conn.setTransactionIsolation(isolation.intValue());
		}
		if (conn.getAutoCommit() != autocommit) {
			conn.setAutoCommit(autocommit);
		}
		return conn;
	}

	public DataSource getDataSource() {
		return ds;
	}

	protected void parsePoolProperties(PoolConfiguration poolProperties,
			Properties properties) {

		String value = null;

		value = properties.getProperty(PROP_DEFAULTAUTOCOMMIT);
		if (value != null) {
			poolProperties.setDefaultAutoCommit(Boolean.valueOf(value));
		}

		value = properties.getProperty(PROP_DEFAULTREADONLY);
		if (value != null) {
			poolProperties.setDefaultReadOnly(Boolean.valueOf(value));
		}

		value = properties.getProperty(PROP_DEFAULTTRANSACTIONISOLATION);
		if (value != null) {
			int level = UNKNOWN_TRANSACTIONISOLATION;
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
					level = UNKNOWN_TRANSACTIONISOLATION;
				}
			}
			poolProperties.setDefaultTransactionIsolation(level);
		}

		value = properties.getProperty(PROP_DEFAULTCATALOG);
		if (value != null) {
			poolProperties.setDefaultCatalog(value);
		}

		value = properties.getProperty(PROP_MAXACTIVE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMaxActive(Integer.parseInt(value));
		}

		value = properties.getProperty(PROP_MAXIDLE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMaxIdle(Integer.parseInt(value));
		}

		value = properties.getProperty(PROP_MINIDLE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMinIdle(Integer.parseInt(value));
		}

		value = properties.getProperty(PROP_INITIALSIZE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setInitialSize(Integer.parseInt(value));
		}

		value = properties.getProperty(PROP_MAXWAIT);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMaxWait(Integer.parseInt(value));
		}

		value = properties.getProperty(PROP_TESTONBORROW);
		if (value != null) {
			poolProperties.setTestOnBorrow(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties.getProperty(PROP_TESTONRETURN);
		if (value != null) {
			poolProperties.setTestOnReturn(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties.getProperty(PROP_TESTONCONNECT);
		if (value != null) {
			poolProperties.setTestOnConnect(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties.getProperty(PROP_TIMEBETWEENEVICTIONRUNSMILLIS);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setTimeBetweenEvictionRunsMillis(Integer
					.parseInt(value));
		}

		value = properties.getProperty(PROP_NUMTESTSPEREVICTIONRUN);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setNumTestsPerEvictionRun(Integer.parseInt(value));
		}

		value = properties.getProperty(PROP_MINEVICTABLEIDLETIMEMILLIS);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMinEvictableIdleTimeMillis(Integer
					.parseInt(value));
		}

		value = properties.getProperty(PROP_TESTWHILEIDLE);
		if (value != null) {
			poolProperties.setTestWhileIdle(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties.getProperty(PROP_VALIDATIONQUERY);
		if (value != null) {
			poolProperties.setValidationQuery(value);
		}

		value = properties.getProperty(PROP_VALIDATOR_CLASS_NAME);
		if (value != null) {
			poolProperties.setValidatorClassName(value);
		}

		value = properties.getProperty(PROP_VALIDATIONINTERVAL);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setValidationInterval(Long.parseLong(value));
		}

		value = properties
				.getProperty(PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED);
		if (value != null) {
			poolProperties.setAccessToUnderlyingConnectionAllowed(Boolean
					.valueOf(value).booleanValue());
		}

		value = properties.getProperty(PROP_REMOVEABANDONED);
		if (value != null) {
			poolProperties.setRemoveAbandoned(Boolean.valueOf(value)
					.booleanValue());
		}

		value = properties.getProperty(PROP_REMOVEABANDONEDTIMEOUT);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setRemoveAbandonedTimeout(Integer.parseInt(value));
		}

		value = properties.getProperty(PROP_LOGABANDONED);
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

		value = properties.getProperty(PROP_INITSQL);
		if (value != null) {
			poolProperties.setInitSQL(value);
		}

		value = properties.getProperty(PROP_INTERCEPTORS);
		if (value != null) {
			poolProperties.setJdbcInterceptors(value);
		}

		value = properties.getProperty(PROP_JMX_ENABLED);
		if (value != null) {
			poolProperties.setJmxEnabled(Boolean.parseBoolean(value));
		}

		value = properties.getProperty(PROP_FAIR_QUEUE);
		if (value != null) {
			poolProperties.setFairQueue(Boolean.parseBoolean(value));
		}

		value = properties.getProperty(PROP_USE_EQUALS);
		if (value != null) {
			poolProperties.setUseEquals(Boolean.parseBoolean(value));
		}

		value = properties.getProperty(PROP_ABANDONWHENPERCENTAGEFULL);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties
					.setAbandonWhenPercentageFull(Integer.parseInt(value));
		}

		value = properties.getProperty(PROP_MAXAGE);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setMaxAge(Long.parseLong(value));
		}

		value = properties.getProperty(PROP_USE_CON_LOCK);
		if (value != null) {
			poolProperties.setUseLock(Boolean.parseBoolean(value));
		}

		value = properties.getProperty(PROP_SUSPECT_TIMEOUT);
		if (value != null && StringUtils.isNumeric(value)) {
			poolProperties.setSuspectTimeout(Integer.parseInt(value));
		}

		value = properties.getProperty(PROP_ALTERNATE_USERNAME_ALLOWED);
		if (value != null) {
			poolProperties.setAlternateUsernameAllowed(Boolean
					.parseBoolean(value));
		}

	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

}