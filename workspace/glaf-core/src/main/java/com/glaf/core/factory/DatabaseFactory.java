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

package com.glaf.core.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.JdbcUtils;

public class DatabaseFactory {
	private static class DatabaseHolder {
		public static DatabaseFactory instance = new DatabaseFactory();
	}

	protected static List<Database> databases = new CopyOnWriteArrayList<Database>();

	protected static List<Database> activeDatabases = new CopyOnWriteArrayList<Database>();

	public static List<Database> getActiveDatabases() {
		return activeDatabases;
	}

	public static DatabaseFactory getInstance() {
		return DatabaseHolder.instance;
	}

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private DatabaseFactory() {

	}

	public synchronized void clearDatabases() {
		databases.clear();
	}

	public synchronized List<Database> getDatabases() {
		if (!databases.isEmpty()) {
			return databases;
		}
		List<Database> list = new ArrayList<Database>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery(" select * from SYS_DATABASE where ACTIVE_ = '1' and TYPE_ <> 'mongodb' ");
			while (rs.next()) {
				Database model = new Database();
				model.setId(rs.getLong("ID_"));
				model.setName(rs.getString("NAME_"));
				model.setCode(rs.getString("CODE_"));
				model.setTitle(rs.getString("TITLE_"));
				model.setHost(rs.getString("HOST_"));
				model.setPort(rs.getInt("PORT_"));
				model.setKey(rs.getString("KEY_"));
				model.setType(rs.getString("TYPE_"));
				model.setDbname(rs.getString("DBNAME_"));
				model.setUser(rs.getString("USER_"));
				model.setPassword(rs.getString("PASSWORD_"));
				model.setActive(rs.getString("ACTIVE_"));
				model.setVerify(rs.getString("VERIFY_"));
				model.setInitFlag(rs.getString("INITFLAG_"));
				model.setLevel(rs.getInt("LEVEL_"));
				model.setProviderClass(rs.getString("PROVIDERCLASS_"));
				list.add(model);
			}
			rs.close();
			stmt.close();
			rs = null;
			stmt = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("get repositories error", ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(stmt);
			JdbcUtils.close(conn);
		}

		if (list != null && !list.isEmpty()) {
			DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
			Collections.sort(list);
			for (Database repo : list) {
				if (cfg.checkConfig(repo)) {
					databases.add(repo);
				}
			}
		}
		logger.debug("databases size:" + databases.size());
		return databases;
	}

	public void reload() {
		try {
			List<Database> databases = getDatabases();
			if (databases != null && !databases.isEmpty()) {
				for (Database database : databases) {
					if ("1".equals(database.getActive())) {
						DatabaseConnectionConfig config = new DatabaseConnectionConfig();
						if (config.checkConfig(database)) {
							activeDatabases.add(database);
							logger.debug(database.getName()
									+ " check connection ok.");
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("load database list error", ex);
		}
	}

}