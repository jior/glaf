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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class SimpleDataSource implements DataSource {
	private java.sql.Connection conn = null;

	public SimpleDataSource(java.sql.Connection conn) {
		this.conn = conn;
	}

	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {

	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {

	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return conn;
	}

	@Override
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		return conn;
	}

 
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

}