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

package com.glaf.core.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.base.ConnectionDefinition;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.startup.ConnectionConfig;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.domain.Database;

public class DatabaseConnectionConfig implements ConnectionConfig {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected IDatabaseService databaseService;

	public DatabaseConnectionConfig() {

	}

	public boolean checkConfig(Database database) {
		String name = database.getName();
		if (DBConnectionFactory.checkConnection(name)) {
			return true;
		}
		String dbType = database.getType();
		String host = database.getHost();
		int port = database.getPort();
		String databaseName = database.getDbname();
		String user = database.getUser();
		try {
			String password = SecurityUtils.decode(database.getKey(),
					database.getPassword());
			DBConfiguration.addDataSourceProperties(name, dbType, host, port,
					databaseName, user, password);
			logger.debug("->name:" + name);
			if (DBConnectionFactory.checkConnection(name)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(database.getTitle() + " config error", ex);
		}
		return false;
	}

	public boolean checkConnection(ConnectionDefinition connectionDefinition) {
		String name = connectionDefinition.getName();
		if (DBConnectionFactory.checkConnection(name)) {
			return true;
		}
		String dbType = connectionDefinition.getType();
		String host = connectionDefinition.getHost();
		int port = connectionDefinition.getPort();
		String databaseName = connectionDefinition.getDatabase();
		String user = connectionDefinition.getUser();
		try {
			String password = connectionDefinition.getPassword();
			DBConfiguration.addDataSourceProperties(name, dbType, host, port,
					databaseName, user, password);
			logger.debug("->name:" + name);
			if (DBConnectionFactory.checkConnection(name)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(connectionDefinition.getSubject() + " config error",
					ex);
		}
		return false;
	}

	public List<Database> getActiveDatabases(LoginContext loginContext,
			long databaseId) {
		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		List<Database> activeDatabases = new ArrayList<Database>();

		List<Database> databases = null;
		if (loginContext.isSystemAdministrator()) {
			databases = getDatabaseService().list(query);
		} else {
			databases = getDatabaseService().getDatabases(
					loginContext.getActorId());
		}

		if (databases != null && !databases.isEmpty()) {
			for (Database database : databases) {
				if ("1".equals(database.getActive())) {
					if (this.checkConfig(database)) {
						activeDatabases.add(database);
						logger.debug(database.getName()
								+ " check connection ok.");
					}
				}
			}
		}
		return activeDatabases;
	}

	public List<ConnectionDefinition> getConnectionDefinitions() {
		List<ConnectionDefinition> list = new ArrayList<ConnectionDefinition>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery(" select * from SYS_DATABASE where ACTIVE_ = '1' and TYPE_ <> 'mongodb' ");
			while (rs.next()) {
				ConnectionDefinition model = new ConnectionDefinition();
				String key = rs.getString("KEY_");
				String password = rs.getString("PASSWORD_");
				String pass = SecurityUtils.decode(key, password);
				model.setName(rs.getString("NAME_"));
				model.setSubject(rs.getString("TITLE_"));
				model.setHost(rs.getString("HOST_"));
				model.setPort(rs.getInt("PORT_"));
				model.setType(rs.getString("TYPE_"));
				model.setDatabase(rs.getString("DBNAME_"));
				model.setUser(rs.getString("USER_"));
				model.setPassword(pass);
				list.add(model);
			}
			rs.close();
			stmt.close();
			rs = null;
			stmt = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("get databases error", ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(stmt);
			JdbcUtils.close(conn);
		}

		logger.debug("connection size:" + list.size());
		return list;
	}

	public Database getDatabase(LoginContext loginContext, Long databaseId) {
		Database currentDB = null;
		if (databaseId != null && databaseId > 0) {
			DatabaseQuery query = new DatabaseQuery();
			query.active("1");
			List<Database> activeDatabases = new ArrayList<Database>();

			List<Database> databases = null;
			if (loginContext != null) {
				if (loginContext.isSystemAdministrator()) {
					databases = getDatabaseService().list(query);
				} else {
					databases = getDatabaseService().getDatabases(
							loginContext.getActorId());
				}
			}

			if (databases != null && !databases.isEmpty()) {
				for (Database database : databases) {
					if ("1".equals(database.getActive())) {
						if (this.checkConfig(database)) {
							activeDatabases.add(database);
							logger.debug(database.getName()
									+ " check connection ok.");
						}
					}
				}
			}

			if (!activeDatabases.isEmpty()) {
				if (databaseId > 0) {
					currentDB = databaseService.getDatabaseById(databaseId);
					if (!activeDatabases.contains(currentDB)) {
						currentDB = null;
					}
				}
			}
		}

		return currentDB;
	}

	public IDatabaseService getDatabaseService() {
		if (databaseService == null) {
			databaseService = ContextFactory.getBean("databaseService");
		}
		return databaseService;
	}

	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

}