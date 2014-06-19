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
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.jdbc.connection.ConnectionProvider;
import com.glaf.core.jdbc.connection.ConnectionProviderFactory;
import com.glaf.core.util.JdbcUtils;

public class DBConnectionFactory {

	protected final static Log logger = LogFactory
			.getLog(DBConnectionFactory.class);

	protected static Properties databaseTypeMappings = getDatabaseTypeMappings();

	public static boolean checkConnection() {
		Connection connection = null;
		DataSource ds = null;
		try {
			Properties props = DBConfiguration.getDefaultDataSourceProperties();
			if (props != null) {
				connection = getConnection(props);
			} else {
				ds = ContextFactory.getBean("dataSource");
				connection = ds.getConnection();
			}

			if (connection != null) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(connection);
		}
		return false;
	}

	public static boolean checkConnection(java.util.Properties props) {
		Connection connection = null;
		try {
			if (StringUtils.isNotEmpty(props
					.getProperty(DBConfiguration.JDBC_DATASOURCE))) {
				InitialContext ctx = new InitialContext();
				DataSource ds = (DataSource) ctx.lookup(props
						.getProperty(DBConfiguration.JDBC_DATASOURCE));
				connection = ds.getConnection();
			} else {
				ConnectionProvider provider = ConnectionProviderFactory
						.createProvider(props);
				connection = provider.getConnection();
			}
			if (connection != null) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	public static Connection getConnection() {
		return getConnection(Environment.DEFAULT_SYSTEM_NAME);
	}

	public static Connection getConnection(java.util.Properties props) {
		Connection connection = null;
		try {
			if (props != null) {
				if (StringUtils.isNotEmpty(props
						.getProperty(DBConfiguration.JDBC_DATASOURCE))) {
					InitialContext ctx = new InitialContext();
					DataSource ds = (DataSource) ctx.lookup(props
							.getProperty(DBConfiguration.JDBC_DATASOURCE));
					connection = ds.getConnection();
				} else {
					String systemName = props
							.getProperty(DBConfiguration.JDBC_NAME);
					if (StringUtils.isNotEmpty(systemName)) {
						ConnectionProvider provider = ConnectionProviderFactory
								.createProvider(systemName);
						connection = provider.getConnection();
					} else {
						ConnectionProvider provider = ConnectionProviderFactory
								.createProvider(props);
						connection = provider.getConnection();
					}
				}
			}
			if (connection != null) {
				ConnectionThreadHolder.addConnection(connection);
			}
			return connection;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public static Connection getConnection(String systemName) {
		if (systemName == null) {
			throw new RuntimeException("systemName is required.");
		}
		logger.debug("systemName:" + systemName);
		Connection connection = null;
		try {
			Properties props = DBConfiguration
					.getDataSourcePropertiesByName(systemName);
			if (props != null) {
				if (StringUtils.isNotEmpty(props
						.getProperty(DBConfiguration.JDBC_DATASOURCE))) {
					InitialContext ctx = new InitialContext();
					DataSource ds = (DataSource) ctx.lookup(props
							.getProperty(DBConfiguration.JDBC_DATASOURCE));
					connection = ds.getConnection();
				} else {
					ConnectionProvider provider = ConnectionProviderFactory
							.createProvider(systemName);
					connection = provider.getConnection();
				}
			} else {
				DataSource ds = ContextFactory.getBean("dataSource");
				connection = ds.getConnection();
			}
			if (connection != null) {
				ConnectionThreadHolder.addConnection(connection);
			}
			return connection;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public static String getDatabaseType() {
		String systemName = Environment.getCurrentSystemName();
		return DBConfiguration.getDatabaseTypeByName(systemName);
	}

	public static String getDatabaseType(Connection connection) {
		if (connection != null) {
			String databaseProductName = null;
			try {
				DatabaseMetaData databaseMetaData = connection.getMetaData();
				databaseProductName = databaseMetaData.getDatabaseProductName();
			} catch (SQLException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
			String dbType = databaseTypeMappings
					.getProperty(databaseProductName);
			if (dbType == null) {
				throw new RuntimeException(
						"couldn't deduct database type from database product name '"
								+ databaseProductName + "'");
			}
			return dbType;
		}
		return null;
	}

	public static String getDatabaseType(DataSource dataSource) {
		String dbType = null;
		Connection con = null;
		try {
			con = dataSource.getConnection();
			dbType = getDatabaseType(con);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(con);
		}
		return dbType;
	}

	public static Properties getDatabaseTypeMappings() {
		Properties databaseTypeMappings = new Properties();
		databaseTypeMappings.setProperty("H2", "h2");
		databaseTypeMappings.setProperty("MySQL", "mysql");
		databaseTypeMappings.setProperty("Oracle", "oracle");
		databaseTypeMappings.setProperty("PostgreSQL", "postgresql");
		databaseTypeMappings.setProperty("Microsoft SQL Server", "sqlserver");
		databaseTypeMappings.setProperty("SQLite", "sqlite");
		databaseTypeMappings.setProperty("DB2", "db2");
		databaseTypeMappings.setProperty("DB2/NT", "db2");
		databaseTypeMappings.setProperty("DB2/NT64", "db2");
		databaseTypeMappings.setProperty("DB2 UDP", "db2");
		databaseTypeMappings.setProperty("DB2/LINUX", "db2");
		databaseTypeMappings.setProperty("DB2/LINUX390", "db2");
		databaseTypeMappings.setProperty("DB2/LINUXZ64", "db2");
		databaseTypeMappings.setProperty("DB2/LINUXX8664", "db2");
		databaseTypeMappings.setProperty("DB2/400 SQL", "db2");
		databaseTypeMappings.setProperty("DB2/6000", "db2");
		databaseTypeMappings.setProperty("DB2 UDB iSeries", "db2");
		databaseTypeMappings.setProperty("DB2/AIX64", "db2");
		databaseTypeMappings.setProperty("DB2/HPUX", "db2");
		databaseTypeMappings.setProperty("DB2/HP64", "db2");
		databaseTypeMappings.setProperty("DB2/SUN", "db2");
		databaseTypeMappings.setProperty("DB2/SUN64", "db2");
		databaseTypeMappings.setProperty("DB2/PTX", "db2");
		databaseTypeMappings.setProperty("DB2/2", "db2");

		return databaseTypeMappings;
	}

	private DBConnectionFactory() {

	}

}