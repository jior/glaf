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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is used to maintain session information which will be used for
 * audit purposes.
 * 
 */
public class SessionInfo {
	protected final static Log logger = LogFactory.getLog(SessionInfo.class);

	/**
	 * updated on context start and via SL_AuditTable. used to switch on/off the
	 * audit trail system
	 */
	private static boolean isAuditActive = false;
	private static boolean usageAuditActive = false;

	/*
	 * The following variables track per thread the information about the
	 * current 'user' of the thread (this info is later at getConnection() time
	 * passed into a temporary AD_CONTEXT_INFO table to be available to the
	 * generated audit triggers.
	 */
	private static ThreadLocal<String> sessionId = new ThreadLocal<String>();
	private static ThreadLocal<String> userId = new ThreadLocal<String>();
	private static ThreadLocal<String> processType = new ThreadLocal<String>();
	private static ThreadLocal<String> processId = new ThreadLocal<String>();
	private static ThreadLocal<String> command = new ThreadLocal<String>();
	private static ThreadLocal<String> queryProfile = new ThreadLocal<String>();

	/*
	 * To optimize updating of the AD_CONTEXT_INFO information, getConnection()
	 * is changed to return the same connection on all getConnection() calls
	 * done inside the same request when possible. Then the ad_context_info does
	 * not need to be updated so often (as the data doesn't change so often for
	 * a specific connection).
	 */
	private static ThreadLocal<Connection> sessionConnection = new ThreadLocal<Connection>();
	private static ThreadLocal<Boolean> changedInfo = new ThreadLocal<Boolean>();

	/*
	 * Maintain artifact's module id. This element is not persisted in auxiliary
	 * session table, it is intended to be used in the usage audit.
	 */
	private static ThreadLocal<String> moduleId = new ThreadLocal<String>();

	/**
	 * Sets all session information to null. Called at the end of http-request
	 * handling, to reset the audit information for that thread.
	 */
	public static void init() {
		sessionId.set(null);
		userId.set(null);
		processType.set(null);
		processId.set(null);
		changedInfo.set(null);
		moduleId.set(null);
		command.set(null);
		queryProfile.set(null);
		// if there is an open connection associated to get current request,
		// close it
		Connection conn = sessionConnection.get();
		try {
			if (conn != null && !conn.isClosed()) {
				logger.debug("Close session's connection");
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("Error closing sessionConnection", e);
		}
		sessionConnection.set(null);
	}

	/**
	 * Creates the needed infrastructure for audit. Which is temporary session
	 * table for PostgreSQL connections.
	 * 
	 * Called whenever a new physical db-connection is created.
	 * 
	 * @param conn
	 *            Connection to database
	 * @param rdbms
	 *            Database, only action is take for POSTGRESQL
	 */
	public static void initDB(Connection conn, String rdbms) {

		if (rdbms != null && rdbms.equals("postgresql")) {
			// Create temporary table
			PreparedStatement psQuery = null;
			PreparedStatement psCreate = null;
			try {
				psQuery = getPreparedStatement(
						conn,
						"select count(*) from information_schema.tables where table_name='ad_context_info' and table_type = 'LOCAL TEMPORARY'");
				ResultSet rs = psQuery.executeQuery();

				if (rs.next() && rs.getString(1).equals("0")) {
					StringBuffer sql = new StringBuffer();
					sql.append("CREATE TEMPORARY TABLE AD_CONTEXT_INFO");
					sql.append("(AD_USER_ID VARCHAR(32), ");
					sql.append("  AD_SESSION_ID VARCHAR(32),");
					sql.append("  PROCESSTYPE VARCHAR(60), ");
					sql.append("  PROCESSID VARCHAR(32)) on commit preserve rows");
					psCreate = getPreparedStatement(conn, sql.toString());
					psCreate.execute();
				}
			} catch (Exception e) {
				logger.error("Error initializating audit infrastructure", e);
			} finally {
				releasePreparedStatement(psQuery);
				releasePreparedStatement(psCreate);
			}
		}
	}

	/**
	 * Inserts in the session table the information about the Openbravo session.
	 * 
	 * This methods optimizes the ad_context_info update away, if the
	 * 'user'-info associated with a connection didn't change
	 * 
	 * @param conn
	 *            Connection where the session information will be stored in
	 * @param onlyIfChanged
	 *            Updates database info only in case there are changes since the
	 *            last time it was set
	 */
	static void setDBSessionInfo(Connection conn, boolean onlyIfChanged) {
		if (!isAuditActive
				|| (onlyIfChanged && (changedInfo.get() == null || !changedInfo
						.get()))) {
			if (logger.isDebugEnabled()) {
				logger.debug("No session info set isAuditActive: "
						+ isAuditActive + " - changes in info: "
						+ changedInfo.get());
			}
			return;
		}
		setDBSessionInfo(conn);
	}

	/**
	 * Inserts in the session table the information about the Openbravo session.
	 * 
	 * @param conn
	 *            Connection where the session information will be stored in
	 */
	public static void setDBSessionInfo(Connection conn) {
		if (!isAuditActive) {
			return;
		}
		logger.debug("set session info");
		// Clean up temporary table
		PreparedStatement psCleanUp = null;
		PreparedStatement psInsert = null;
		try {
			psCleanUp = getPreparedStatement(conn,
					"delete from ad_context_info");
			psCleanUp.executeUpdate();

			psInsert = getPreparedStatement(
					conn,
					"insert into ad_context_info (ad_user_id, ad_session_id, processType, processId) values (?, ?, ?, ?)");
			psInsert.setString(1, SessionInfo.getUserId());
			psInsert.setString(2, SessionInfo.getSessionId());
			psInsert.setString(3, SessionInfo.getProcessType());
			psInsert.setString(4, SessionInfo.getProcessId());
			psInsert.executeUpdate();
			changedInfo.set(false);
		} catch (Exception e) {
			logger.error("Error setting audit info", e);
		} finally {
			releasePreparedStatement(psCleanUp);
			releasePreparedStatement(psInsert);
		}
	}

	/**
	 * Initialized DB with temporary table and sets session information on it.
	 * 
	 * @param conn
	 *            Connection where the session information will be stored in
	 * @param rdbms
	 *            Database type
	 */
	public static void setDBSessionInfo(Connection conn, String rdbms) {
		if (!isAuditActive) {
			return;
		}
		initDB(conn, rdbms);
		setDBSessionInfo(conn);
	}

	/**
	 * Return the connection associated with the current session, if there is
	 * one.
	 */
	static Connection getSessionConnection() {
		Connection conn = sessionConnection.get();
		try {
			if (conn == null || conn.isClosed()) {
				return null;
			}
		} catch (SQLException e) {
			logger.error("Error checking connection", e);
			return null;
		}
		logger.debug("Reuse session's connection");
		return conn;
	}

	private static PreparedStatement getPreparedStatement(Connection conn,
			String SQLPreparedStatement) throws SQLException {
		if (conn == null || SQLPreparedStatement == null
				|| SQLPreparedStatement.equals(""))
			return null;
		PreparedStatement ps = null;

		try {
			if (logger.isDebugEnabled())
				logger.debug("preparedStatement requested");
			ps = conn.prepareStatement(SQLPreparedStatement,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (logger.isDebugEnabled())
				logger.debug("preparedStatement received");
		} catch (SQLException e) {
			logger.error("getPreparedStatement: " + SQLPreparedStatement + "\n"
					+ e);
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return (ps);
	}

	private static void releasePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
				logger.error("Error closing PreparedStatement", e);
			}
		}
	}

	public static void setUserId(String user) {
		if (user == null || !user.equals(getUserId())) {
			userId.set(user);
			changedInfo.set(true);
		}
	}

	public static String getUserId() {
		return userId.get();
	}

	public static void setProcessId(String processId) {
		if (processId == null || !processId.equals(getProcessId())) {
			SessionInfo.processId.set(processId);
			changedInfo.set(true);
		}
	}

	public static String getProcessId() {
		return processId.get();
	}

	public static void setProcessType(String processType) {
		if (processType == null || !processType.equals(getProcessType())) {
			SessionInfo.processType.set(processType);
			changedInfo.set(true);
		}
	}

	public static String getProcessType() {
		return processType.get();
	}

	public static void setSessionId(String session) {
		if (session == null || !session.equals(getSessionId())) {
			sessionId.set(session);
			changedInfo.set(true);
		}
	}

	/**
	 * Forces changed info flag so next time it is checked it session info will
	 * be set in DB
	 */
	public static void infoChanged() {
		changedInfo.set(true);
	}

	public static String getCommand() {
		return command.get();
	}

	public static void setCommand(String comm) {
		command.set(comm);
	}

	public static String getQueryProfile() {
		return queryProfile.get();
	}

	public static void setQueryProfile(String profile) {
		queryProfile.set(profile);
	}

	public static String getSessionId() {
		return sessionId.get();
	}

	public static void setAuditActive(boolean isAuditActive) {
		SessionInfo.isAuditActive = isAuditActive;
	}

	static void setSessionConnection(Connection conn) {
		sessionConnection.set(conn);
	}

	public static String getModuleId() {
		return moduleId.get();
	}

	public static void setModuleId(String moduleId) {
		SessionInfo.moduleId.set(moduleId);
	}

	public static boolean isUsageAuditActive() {
		return usageAuditActive;
	}

	public static void setUsageAuditActive(boolean usageAuditActive) {
		SessionInfo.usageAuditActive = usageAuditActive;
	}
}
