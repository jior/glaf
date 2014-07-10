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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
import com.glaf.core.exceptions.NoConnectionAvailableException;
import com.glaf.core.exceptions.PoolNotFoundException;

public class ConnectionProviderImpl implements ConnectionProvider {
	protected final static Log logger = LogFactory
			.getLog(ConnectionProviderImpl.class);
	protected String contextName = "glaf";
	protected String defaultPoolName = "";
	protected String dbServer = "";
	protected String rdbms = "";

	private static ExternalConnectionPool externalConnectionPool;

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
			String dbPassword, int minConns, int maxConns, double maxConnTime,
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

	/**
	 * Close for transactional connections
	 */
	public void closeConnection(Connection conn) throws SQLException {
		if (conn == null) {
			return;
		}
		try {
			conn.setAutoCommit(true);
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error on closeConnection", ex);
			throw ex;
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
		double maxConnTime = 0.5;
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
		maxConnTime = Double.parseDouble(properties.getProperty(
				DBConfiguration.POOL_TIMEOUT, "0.5"));
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

		if (externalPoolClassName != null) {
			try {
				externalConnectionPool = ExternalConnectionPool
						.getInstance(externalPoolClassName);
			} catch (Throwable e) {
				externalConnectionPool = null;
			}
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

	public void destroy() throws Exception {
		destroy(defaultPoolName);
	}

	public void destroy(String name) throws Exception {
		PoolingDriver driver = (PoolingDriver) DriverManager
				.getDriver("jdbc:apache:commons:dbcp:");
		driver.closePool(name);
	}

	public CallableStatement getCallableStatement(Connection conn,
			String SQLCallableStatement) throws SQLException {
		if (conn == null || SQLCallableStatement == null
				|| SQLCallableStatement.equals("")) {
			return null;
		}
		CallableStatement cs = null;
		try {
			cs = conn.prepareCall(SQLCallableStatement);
		} catch (SQLException ex) {
			logger.error("getCallableStatement: " + SQLCallableStatement + "\n"
					+ ex);
			releaseConnection(conn);
			throw ex;
		}
		return (cs);
	}

	public CallableStatement getCallableStatement(String SQLCallableStatement)
			throws Exception {
		return getCallableStatement(defaultPoolName, SQLCallableStatement);
	}

	public CallableStatement getCallableStatement(String poolName,
			String SQLCallableStatement) throws Exception {
		if (poolName == null || poolName.equals("")) {
			throw new PoolNotFoundException(
					"Can't get the pool. No pool name specified");
		}
		Connection conn = getConnection(poolName);
		return getCallableStatement(conn, SQLCallableStatement);
	}

	public Connection getConnection() {
		return getConnection(defaultPoolName);
	}

	/*
	 * Optimization, try to get the connection associated with the current
	 * thread, to always get the same connection for all getConnection() calls
	 * inside a request.
	 */
	public Connection getConnection(String poolName)
			throws NoConnectionAvailableException {
		if (poolName == null || poolName.equals("")) {
			throw new NoConnectionAvailableException(
					"Couldn´t get a connection for an unnamed pool");
		}
		logger.debug("poolName:" + poolName);
		// try to get the connection from the session to use a single connection
		// for the whole request
		Connection conn = SessionInfo.getSessionConnection();
		if (conn == null) {
			// No connection in the session, take a new one and attach it to the
			// session
			if (externalConnectionPool != null) {
				conn = externalConnectionPool.getConnection();
			} else {
				conn = getNewConnection(poolName);
			}
			SessionInfo.setSessionConnection(conn);
		} else {
			// Update session info if needed
			SessionInfo.setDBSessionInfo(conn, true);
		}
		return conn;
	}

	private Connection getNewConnection() throws NoConnectionAvailableException {
		return getNewConnection(defaultPoolName);
	}

	/**
	 * Gets a new connection without trying to obtain the sessions's one
	 */
	private Connection getNewConnection(String poolName)
			throws NoConnectionAvailableException {
		if (poolName == null || poolName.equals("")) {
			throw new NoConnectionAvailableException(
					"Couldn´t get a connection for an unnamed pool");
		}
		logger.debug("poolName:" + poolName);
		logger.debug("name:" + (contextName + "_" + poolName));
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:"
					+ contextName + "_" + poolName);
			// Set session info for the connection, but do not attach the
			// connection to the session since
			// it shouldn't be reused
			SessionInfo.setDBSessionInfo(conn);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error getting connection", ex);
			throw new NoConnectionAvailableException(
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

	public PreparedStatement getPreparedStatement(Connection conn,
			String SQLPreparedStatement) throws SQLException {
		if (conn == null || SQLPreparedStatement == null
				|| SQLPreparedStatement.equals("")) {
			return null;
		}
		PreparedStatement ps = null;
		try {
			logger.debug("preparedStatement requested");
			ps = conn.prepareStatement(SQLPreparedStatement,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			logger.debug("preparedStatement received");
		} catch (SQLException ex) {
			logger.error("getPreparedStatement: " + SQLPreparedStatement + "\n"
					+ ex);
			releaseConnection(conn);
			throw ex;
		}
		return (ps);
	}

	public PreparedStatement getPreparedStatement(String SQLPreparedStatement)
			throws Exception {
		return getPreparedStatement(defaultPoolName, SQLPreparedStatement);
	}

	public PreparedStatement getPreparedStatement(String poolName,
			String SQLPreparedStatement) throws Exception {
		if (poolName == null || poolName.equals("")) {
			throw new PoolNotFoundException(
					"Can't get the pool. No pool name specified");
		}
		logger.debug("connection requested");
		Connection conn = getConnection(poolName);
		logger.debug("connection established");
		return getPreparedStatement(conn, SQLPreparedStatement);
	}

	public String getRDBMS() {
		return rdbms;
	}

	public Statement getStatement() throws Exception {
		return getStatement(defaultPoolName);
	}

	public Statement getStatement(Connection conn) throws SQLException {
		if (conn == null) {
			return null;
		}
		try {
			return (conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY));
		} catch (SQLException e) {
			logger.error("getStatement: " + e);
			releaseConnection(conn);
			throw e;
		}
	}

	public Statement getStatement(String poolName) throws Exception {
		if (poolName == null || poolName.equals("")) {
			throw new PoolNotFoundException(
					"Can't get the pool. No pool name specified");
		}
		Connection conn = getConnection(poolName);
		return getStatement(conn);
	}

	/**
	 * Returns the actual status of the dynamic pool.
	 */
	public String getStatus() {
		StringBuffer strResultado = new StringBuffer();
		strResultado.append("Not implemented yet");
		return strResultado.toString();
	}// End getStatus()

	public Connection getTransactionConnection()
			throws NoConnectionAvailableException, SQLException {
		Connection conn = getNewConnection();
		if (conn == null) {
			throw new NoConnectionAvailableException(
					"Couldn´t get an available connection");
		}
		conn.setAutoCommit(false);
		return conn;
	}

	public void releaseCallableStatement(CallableStatement callableStatement)
			throws SQLException {
		if (callableStatement == null) {
			return;
		}
		Connection conn = null;
		try {
			conn = callableStatement.getConnection();
			callableStatement.close();
			releaseConnection(conn);
		} catch (SQLException ex) {
			logger.error("releaseCallableStatement: " + ex);
			releaseConnection(conn);
			throw ex;
		}
	}

	public void releaseCommitConnection(Connection conn) throws SQLException {
		if (conn == null) {
			return;
		}
		conn.commit();
		closeConnection(conn);
	}

	public boolean releaseConnection(Connection conn) {
		if (conn == null) {
			return false;
		}
		try {
			// Set autocommit, this makes not necessary to explicitly commit,
			// all prepared statements are
			// commited
			conn.setAutoCommit(true);
			if (SessionInfo.getSessionConnection() == null) {
				// close connection if it's not attached to session, other case
				// it will be closed when the
				// request is done
				logger.debug("close connection directly (no connection in session)");
				if (!conn.isClosed()) {
					conn.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error on releaseConnection", ex);
			return false;
		}
		return true;
	}

	public void releasePreparedStatement(PreparedStatement preparedStatement)
			throws SQLException {
		if (preparedStatement == null) {
			return;
		}
		Connection conn = null;
		try {
			conn = preparedStatement.getConnection();
			preparedStatement.close();
			releaseConnection(conn);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("releasePreparedStatement: " + ex);
			releaseConnection(conn);
			throw ex;
		}
	}

	public void releaseRollbackConnection(Connection conn) throws SQLException {
		if (conn == null) {
			return;
		}
		// prevent extra exception if the connection is already closed
		// especially needed here because rollback occurs in case of
		// application exceptions also. If the conn.isClosed and a rollback
		// is done then the real app exception is hidden.
		if (conn.isClosed()) {
			return;
		}
		conn.rollback();
		closeConnection(conn);
	}

	public void releaseStatement(Statement statement) throws SQLException {
		if (statement == null) {
			return;
		}
		Connection conn = null;
		try {
			conn = statement.getConnection();
			statement.close();
			releaseConnection(conn);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("releaseStatement: " + ex);
			releaseConnection(conn);
			throw ex;
		}
	}

	public void releaseTransactionalPreparedStatement(
			PreparedStatement preparedStatement) throws SQLException {
		if (preparedStatement == null) {
			return;
		}
		preparedStatement.close();
	}

	public void releaseTransactionalStatement(Statement statement)
			throws SQLException {
		if (statement == null) {
			return;
		}
		statement.close();
	}

	public void reload(String file, boolean isRelative, String _context)
			throws Exception {
		destroy();
		create(file, isRelative, _context);
	}
}
