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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.glaf.core.exceptions.NoConnectionAvailableException;

public interface ConnectionProvider {
	void destroy() throws Exception;

	CallableStatement getCallableStatement(Connection conn, String strSql)
			throws SQLException;

	CallableStatement getCallableStatement(String strSql) throws Exception;

	CallableStatement getCallableStatement(String poolName, String strSql)
			throws Exception;

	Connection getConnection() throws SQLException;

	PreparedStatement getPreparedStatement(Connection conn, String strSql)
			throws SQLException;

	PreparedStatement getPreparedStatement(String strSql) throws Exception;

	PreparedStatement getPreparedStatement(String poolName, String strSql)
			throws Exception;

	String getRDBMS();

	Statement getStatement() throws Exception;

	Statement getStatement(Connection conn) throws SQLException;

	Statement getStatement(String poolName) throws Exception;

	String getStatus();

	void closeConnection(Connection conn) throws SQLException;

	Connection getTransactionConnection()
			throws NoConnectionAvailableException, SQLException;

	void releaseCallableStatement(CallableStatement callableStatement)
			throws SQLException;

	void releaseCommitConnection(Connection conn) throws SQLException;

	void releasePreparedStatement(PreparedStatement preparedStatement)
			throws SQLException;

	void releaseRollbackConnection(Connection conn) throws SQLException;

	void releaseStatement(Statement statement) throws SQLException;

	void releaseTransactionalPreparedStatement(
			PreparedStatement preparedStatement) throws SQLException;

	void releaseTransactionalStatement(Statement statement) throws SQLException;
}
