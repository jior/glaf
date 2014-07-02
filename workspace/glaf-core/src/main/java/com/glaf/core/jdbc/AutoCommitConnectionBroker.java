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
import java.sql.SQLException;

/**
 * This class managed the sharing of non-transactional connection per thread.
 * 
 */
public class AutoCommitConnectionBroker {

	private static class ConnectionReference {
		protected Connection connection;
		protected int referenceCount;

		protected ConnectionReference(Connection conn) {
			connection = conn;
			referenceCount = 1;
		}
	}

	private static ThreadLocal<ConnectionReference> threadLocalConnection = new ThreadLocal<ConnectionReference>() {
		protected ConnectionReference initialValue() {
			return null;
		}
	};

	/**
	 * Retrieve non-transactional connection for current thread. If none have
	 * been allocated yet, a new one will be created from the connection pool.
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		ConnectionReference connReference = threadLocalConnection.get();
		try {
			if (connReference != null && !connReference.connection.isClosed()) {
				connReference.referenceCount++;
				return connReference.connection;
			}
		} catch (SQLException ex) {
		}

		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection();
			connection
					.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(true);
		} catch (SQLException ex) {
		}
		connReference = new ConnectionReference(connection);
		threadLocalConnection.set(connReference);
		return connection;
	}

	/**
	 * Release connection. The connection goes back to pool if reference count
	 * is zero.
	 * 
	 * @param conn
	 */
	public static void releaseConnection(Connection conn) {
		ConnectionReference connReference = threadLocalConnection.get();
		if (connReference != null && connReference.connection == conn) {
			connReference.referenceCount--;
			if (connReference.referenceCount <= 0) {
				threadLocalConnection.set(null);
				try {
					connReference.connection.close();
				} catch (SQLException ex) {
				}
			}
		} else {
			try {
				conn.close();
			} catch (SQLException ex) {
			}
		}
	}
}
