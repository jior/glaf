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

package com.glaf.core.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.StackKeyedObjectPoolFactory;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.exceptions.PoolNotFoundException;
import com.glaf.core.jdbc.connection.ConnectionProvider;

public class ConnectionProviderImpl implements ConnectionProvider {
	protected final static Log logger = LogFactory
			.getLog(ConnectionProviderImpl.class);
	protected String contextName = "glaf";
	protected String defaultPoolName = "";
	protected String dbServer = "";
	protected String rdbms = "";

	public ConnectionProviderImpl() {

	}

	public ConnectionProviderImpl(Properties properties)
			throws PoolNotFoundException {
		create(properties, false, "glaf");
	}

	public ConnectionProviderImpl(String file) throws PoolNotFoundException {
		this(file, false, "glaf");
	}

	public ConnectionProviderImpl(String file, boolean isRelative,
			String _context) throws PoolNotFoundException {
		create(file, isRelative, _context);
	}

	public ConnectionProviderImpl(String file, String _context)
			throws PoolNotFoundException {
		this(file, false, _context);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addNewPool(String dbDriver, String dbServer, String dbLogin,
			String dbPassword, int minConns, int maxConns, long maxConnTime,
			String dbSessionConfig, String rdbms, String name) throws Exception {
		logger.debug("Loading underlying JDBC driver.");
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			throw new Exception(e);
		}
		logger.debug("Done.");

		GenericObjectPool connectionPool = new GenericObjectPool(null);
		connectionPool
				.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
		connectionPool.setMaxActive(maxConns);
		connectionPool.setTestOnBorrow(false);
		connectionPool.setTestOnReturn(false);
		connectionPool.setTestWhileIdle(false);

		KeyedObjectPoolFactory keyedObject = new StackKeyedObjectPoolFactory();
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				dbServer, dbLogin, dbPassword, dbSessionConfig, rdbms);
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, connectionPool, keyedObject, null, false,
				true);
		if (poolableConnectionFactory != null) {
			logger.debug("poolableConnectionFactory:"
					+ poolableConnectionFactory.toString());
		}
		Class.forName("org.apache.commons.dbcp.PoolingDriver");
		PoolingDriver driver = (PoolingDriver) DriverManager
				.getDriver("jdbc:apache:commons:dbcp:");
		logger.debug(contextName + "_" + name);
		driver.registerPool(contextName + "_" + name, connectionPool);

		if (this.defaultPoolName == null || this.defaultPoolName.equals("")) {
			this.defaultPoolName = name;
			this.dbServer = dbServer;
			this.rdbms = rdbms;
		}
	}

	public void close() {
		close(defaultPoolName);
	}

	public void close(String name) {
		PoolingDriver driver = null;
		try {
			driver = (PoolingDriver) DriverManager
					.getDriver("jdbc:apache:commons:dbcp:");
			driver.closePool(name);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Close for transactional connections
	 */
	public void closeConnection(Connection conn) throws SQLException {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error on closeConnection", ex);
			throw ex;
		}
	}

	public void configure(Properties properties) {
		try {
			create(properties, false, "glaf");
		} catch (PoolNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	protected void create(Properties properties, boolean isRelative,
			String _context) throws PoolNotFoundException {
		logger.debug("Creating ConnectionProviderImpl");
		if (_context != null && !_context.equals("")) {
			contextName = _context;
		}
		String poolName = null;
		String externalPoolClassName;
		String dbDriver = null;
		String dbServer = null;
		String dbLogin = null;
		String dbPassword = null;
		int minConns = 1;
		int maxConns = 20;
		long maxConnTime = 30;
		String dbSessionConfig = null;
		String rdbms = null;

		poolName = properties.getProperty(DBConfiguration.JDBC_NAME, "default");
		externalPoolClassName = properties
				.getProperty("db.externalPoolClassName");
		dbDriver = properties.getProperty(DBConfiguration.JDBC_DRIVER);
		dbServer = properties.getProperty(DBConfiguration.JDBC_URL);
		dbLogin = properties.getProperty(DBConfiguration.JDBC_USER);
		dbPassword = properties.getProperty(DBConfiguration.JDBC_PASSWORD);
		minConns = Integer.parseInt(properties.getProperty(
				DBConfiguration.POOL_MIN_SIZE, "1"));
		maxConns = Integer.parseInt(properties.getProperty(
				DBConfiguration.POOL_MAX_SIZE, "20"));
		maxConnTime = Long.parseLong(properties.getProperty(
				DBConfiguration.POOL_TIMEOUT, "30"));
		dbSessionConfig = properties.getProperty("jdbc.sessionConfig");
		rdbms = properties.getProperty(DBConfiguration.JDBC_TYPE);

		if (logger.isDebugEnabled()) {
			logger.debug("poolName: " + poolName);
			logger.debug("externalPoolClassName: " + externalPoolClassName);
			logger.debug("dbDriver: " + dbDriver);
			logger.debug("dbServer: " + dbServer);
			logger.debug("dbLogin: " + dbLogin);
			logger.debug("minConns: " + minConns);
			logger.debug("maxConns: " + maxConns);
			logger.debug("maxConnTime: " + Double.toString(maxConnTime));
			logger.debug("dbSessionConfig: " + dbSessionConfig);
			logger.debug("rdbms: " + rdbms);
		}

		try {
			addNewPool(dbDriver, dbServer, dbLogin, dbPassword, minConns,
					maxConns, maxConnTime, dbSessionConfig, rdbms, poolName);
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new PoolNotFoundException(
					"Failed when creating database connections pool", ex);
		}
	}

	protected void create(String file, boolean isRelative, String _context)
			throws PoolNotFoundException {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
			create(properties, isRelative, _context);
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("Error loading properties", ex);
		}
	}

	public Connection getConnection() throws SQLException {
		return getConnection(defaultPoolName);
	}

	/*
	 * Optimization, try to get the connection associated with the current
	 * thread, to always get the same connection for all getConnection() calls
	 * inside a request.
	 */
	public Connection getConnection(String poolName) throws SQLException {
		if (poolName == null || poolName.equals("")) {
			throw new SQLException(
					"Couldn´t get a connection for an unnamed pool");
		}
		// try to get the connection from the session to use a single connection
		// for the whole request
		Connection conn = SessionInfo.getSessionConnection();
		try {
			if (conn == null || conn.isClosed()) {
				conn = getNewConnection(poolName);
				if (conn != null && !conn.isClosed()) {
					SessionInfo.setSessionConnection(conn);
				}
			}
		} catch (SQLException ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw ex;
		}
		return conn;
	}

	public DataSource getDataSource() {
		return null;
	}

	/**
	 * Gets a new connection without trying to obtain the sessions's one
	 */
	private Connection getNewConnection(String poolName) throws SQLException {
		if (poolName == null || poolName.equals("")) {
			throw new SQLException(
					"Couldn´t get a connection for an unnamed pool");
		}
		logger.debug("poolName:" + poolName);
		logger.debug("name:" + (contextName + "_" + poolName));
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:"
					+ contextName + "_" + poolName);
			logger.debug("get new connection:" + (contextName + "_" + poolName));
			if (conn != null && conn.isClosed()) {
				logger.error("get closed connection!");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error getting connection", ex);
			throw new SQLException(
					"There are no connections available in jdbc:apache:commons:dbcp:"
							+ contextName + "_" + poolName);
		}
		return conn;
	}

	@SuppressWarnings("rawtypes")
	public ObjectPool getPool() throws PoolNotFoundException {
		return getPool(defaultPoolName);
	}

	@SuppressWarnings("rawtypes")
	public ObjectPool getPool(String poolName) throws PoolNotFoundException {
		if (poolName == null || poolName.equals("")) {
			throw new PoolNotFoundException("Couldn´t get an unnamed pool");
		}
		ObjectPool connectionPool = null;
		try {
			PoolingDriver driver = (PoolingDriver) DriverManager
					.getDriver("jdbc:apache:commons:dbcp:");
			connectionPool = driver.getConnectionPool(contextName + "_"
					+ poolName);
		} catch (SQLException ex) {
			logger.error(ex);
		}
		if (connectionPool == null) {
			throw new PoolNotFoundException(poolName + " not found");
		} else {
			return connectionPool;
		}
	}

}
