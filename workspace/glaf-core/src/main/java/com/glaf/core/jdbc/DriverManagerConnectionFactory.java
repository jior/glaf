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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class DriverManagerConnectionFactory implements ConnectionFactory {
	protected final static Log logger = LogFactory
			.getLog(DriverManagerConnectionFactory.class);
	protected String _connectUri;
	protected String _user;
	protected String _password;
	protected Properties _props;
	protected String _dbSessionConfig;
	protected String _rdbms;

	public DriverManagerConnectionFactory(String connectUri, Properties props) {
		_connectUri = null;
		_user = null;
		_password = null;
		_props = null;
		_connectUri = connectUri;
		_props = props;
	}

	public DriverManagerConnectionFactory(String connectUri, String user,
			String password, String dbSessionConfig, String rdbms) {
		_props = null;
		_dbSessionConfig = null;
		_connectUri = connectUri;
		_user = user;
		_password = password;
		_dbSessionConfig = dbSessionConfig;
		_rdbms = rdbms;
	}

	public Connection createConnection() throws SQLException {
		Connection conn = null;
		if (null == _props) {
			if (_user == null) {
				conn = DriverManager.getConnection(_connectUri);
			} else {
				logger.debug("url:" + _connectUri);
				logger.debug("user:" + _user);
				conn = DriverManager.getConnection(_connectUri, _user,
						_password);
			}
		} else {
			conn = DriverManager.getConnection(_connectUri, _props);
		}
		if (conn != null && _dbSessionConfig != null) {
			executeDefaultSQL(conn);
		}
		return conn;
	}

	private void executeDefaultSQL(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		try {
			stmt = conn.createStatement();
			if (_dbSessionConfig != null
					&& _dbSessionConfig.trim().length() > 0) {
				rset = stmt.executeQuery(_dbSessionConfig);
			}
			// set infrastructure for auditing
			SessionInfo.initDB(conn, _rdbms);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
			} catch (Exception e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				conn.commit();
			} catch (Exception e) {
			}
		}
	}
}
