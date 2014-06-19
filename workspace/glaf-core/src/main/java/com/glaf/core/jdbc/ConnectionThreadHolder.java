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
import java.util.ArrayList;
import java.util.List;

public class ConnectionThreadHolder {

	private static ThreadLocal<List<Connection>> connectionThreadLocalHolder = new ThreadLocal<List<Connection>>();

	public static void addConnection(Connection connection) {
		List<Connection> connections = connectionThreadLocalHolder.get();
		if (connections == null) {
			connections = new ArrayList<Connection>();
			connectionThreadLocalHolder.set(connections);
		}
		connections.add(connection);
	}

	public static void closeAndClear() {
		if (connectionThreadLocalHolder.get() != null) {
			List<Connection> connections = connectionThreadLocalHolder.get();
			if (connections != null && !connections.isEmpty()) {
				for (Connection connection : connections) {
					if (connection != null) {
						try {
							if (!connection.isClosed()) {
								connection.close();
							}
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
			connectionThreadLocalHolder.remove();
		}
	}

	private ConnectionThreadHolder() {

	}
}
