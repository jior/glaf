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

class DriverManagerConnectionFactory implements ConnectionFactory {
	protected String _connectUri;
	protected String _uname;
	protected String _passwd;
	protected Properties _props;
	protected String _dbSessionConfig;
	protected String _rdbsm;

	public DriverManagerConnectionFactory(String connectUri, Properties props) {
		_connectUri = null;
		_uname = null;
		_passwd = null;
		_props = null;
		_connectUri = connectUri;
		_props = props;
	}

	public DriverManagerConnectionFactory(String connectUri, String uname,
			String passwd, String dbSessionConfig, String rdbsm) {
		_props = null;
		_dbSessionConfig = null;
		_connectUri = connectUri;
		_uname = uname;
		_passwd = passwd;
		_dbSessionConfig = dbSessionConfig;
		_rdbsm = rdbsm;
	}

	public Connection createConnection() throws SQLException {
		Connection conn = null;
		if (null == _props) {
			if (_uname == null) {
				conn = DriverManager.getConnection(_connectUri);
			} else {
				conn = DriverManager
						.getConnection(_connectUri, _uname, _passwd);
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
			if (!_dbSessionConfig.equals("")) {
				rset = stmt.executeQuery(_dbSessionConfig);
			}
			// set infrastructure for auditing
			SessionInfo.initDB(conn, _rdbsm);
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
