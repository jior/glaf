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

package org.jpage.jbpm.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.jpage.jbpm.service.ProcessContainer;

public class DataSourceConfig {

	private static Properties properties = new Properties();

	private static String databaseType;

	protected static Properties dialetTypeMappings = getDialectMappings();

	protected static Properties hibernateDialetTypeMappings = getHibernateDialectMappings();

	protected static Properties databaseTypeMappings = getDefaultDatabaseTypeMappings();

	static {

	}

	public static boolean eq(String key, String value) {
		if (key != null && value != null) {
			String x = properties.getProperty(key);
			if (StringUtils.equals(value, x)) {
				return true;
			}
		}
		return false;
	}

	public static boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public static String getDatabaseDialect() {
		if (getDatabaseType() != null) {
			return dialetTypeMappings.getProperty(getDatabaseType());
		}
		return null;
	}

	public static String getDatabaseType() {
		if (databaseType == null) {
			initDatabaseType();
		}
		return databaseType;
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

	protected static Properties getDefaultDatabaseTypeMappings() {
		Properties databaseTypeMappings = new Properties();
		databaseTypeMappings.setProperty("H2", "h2");
		databaseTypeMappings.setProperty("MySQL", "mysql");
		databaseTypeMappings.setProperty("Oracle", "oracle");
		databaseTypeMappings.setProperty("PostgreSQL", "postgres");
		databaseTypeMappings.setProperty("Microsoft SQL Server", "mssql");
		databaseTypeMappings.setProperty("DB2", "db2");
		databaseTypeMappings.setProperty("DB2/NT", "db2");
		databaseTypeMappings.setProperty("DB2/NT64", "db2");
		databaseTypeMappings.setProperty("DB2 UDP", "db2");
		databaseTypeMappings.setProperty("DB2/LINUX", "db2");
		databaseTypeMappings.setProperty("DB2/LINUX390", "db2");
		databaseTypeMappings.setProperty("DB2/LINUXZ64", "db2");
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

	protected static Properties getDialectMappings() {
		Properties dialectMappings = new Properties();
		dialectMappings.setProperty("h2",
				"com.gzgi.framework.core.dialect.H2Dialect");
		dialectMappings.setProperty("mysql",
				"com.gzgi.framework.core.dialect.MySQLDialect");
		dialectMappings.setProperty("oracle",
				"com.gzgi.framework.core.dialect.OracleDialect");
		dialectMappings.setProperty("postgres",
				"com.gzgi.framework.core.dialect.PostgreSQLDialect");
		dialectMappings.setProperty("mssql",
				"com.gzgi.framework.core.dialect.SQLServerDialect");
		dialectMappings.setProperty("db2",
				"com.gzgi.framework.core.dialect.DB2Dialect");
		return dialectMappings;
	}

	public static double getDouble(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Double.valueOf(value).doubleValue();
		}
		return 0;
	}

	public static String getHibernateDialect() {
		if (getDatabaseType() != null) {
			return hibernateDialetTypeMappings.getProperty(getDatabaseType());
		}
		return null;
	}

	protected static Properties getHibernateDialectMappings() {
		Properties dialectMappings = new Properties();
		dialectMappings.setProperty("h2", "org.hibernate.dialect.H2Dialect");
		dialectMappings.setProperty("mysql",
				"org.hibernate.dialect.MySQL5Dialect");
		dialectMappings.setProperty("oracle",
				"org.hibernate.dialect.Oracle10gDialect");
		dialectMappings.setProperty("postgres",
				"org.hibernate.dialect.PostgreSQLDialect");
		dialectMappings.setProperty("mssql",
				"org.hibernate.dialect.SQLServerDialect");
		dialectMappings.setProperty("db2", "org.hibernate.dialect.DB2Dialect");
		return dialectMappings;
	}

	public static int getInt(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Integer.valueOf(value).intValue();
		}
		return 0;
	}

	public static String getJdbcConnectionURL() {
		return getString("jdbc.url");
	}

	public static String getJdbcDriverClass() {
		return getString("jdbc.driver");
	}

	public static String getJdbcPassword() {
		return getString("jdbc.password");
	}

	public static String getJdbcUsername() {
		return getString("jdbc.user");
	}

	public static String getJndiName() {
		return getString("jdbc.datasource");
	}

	public static long getLong(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Long.valueOf(value).longValue();
		}
		return 0;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static String getString(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			if (value == null) {
				value = properties.getProperty(key.toUpperCase());
			}
			return value;
		}
		return null;
	}

	public static boolean hasObject(String key) {
		if (properties == null || key == null) {
			return false;
		}
		String value = properties.getProperty(key);
		if (value != null) {
			return true;
		}
		return false;
	}

	public static void initDatabaseType() {
		Connection connection = null;
		try {
			connection = ProcessContainer.getContainer().createJbpmContext()
					.getConnection();

			if (connection != null) {
				DatabaseMetaData databaseMetaData = connection.getMetaData();
				String databaseProductName = databaseMetaData
						.getDatabaseProductName();
				System.out.println("database product name: '"
						+ databaseProductName + "'");
				databaseType = databaseTypeMappings
						.getProperty(databaseProductName);
				if (databaseType == null) {
					throw new RuntimeException(
							"couldn't deduct database type from database product name '"
									+ databaseProductName + "'");
				}
				System.out.println("using database type: " + databaseType);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isBooleanDatabase() {
		String databaseType = getDatabaseType();
		if ("postgres".equalsIgnoreCase(databaseType)) {
			return true;
		}
		return false;
	}

	private DataSourceConfig() {

	}

}
