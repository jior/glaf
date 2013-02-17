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

package com.glaf.base.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.glaf.base.utils.PropertiesTools;

public class DataSourceConfig {

	private final static String SYSTEM_CONFIG = "/jdbc.properties";

	private static Properties properties = new Properties();

	private static String databaseType;

	protected static Properties dialetTypeMappings = getDialectMappings();

	protected static Properties hibernateDialetTypeMappings = getHibernateDialectMappings();

	protected static Properties databaseTypeMappings = getDefaultDatabaseTypeMappings();

	static {
		try {
			reload();
		} catch (Exception ex) {
		}
	}

	public static boolean checkConnection() {
		Connection connection = null;
		DataSource ds = null;
		try {
			if (StringUtils.isNotEmpty(getJndiName())) {
				InitialContext ctx = new InitialContext();
				ds = (DataSource) ctx.lookup(getJndiName());
				connection = ds.getConnection();
			} else {
				BasicDataSource bds = new BasicDataSource();
				bds.setDriverClassName(getJdbcDriverClass());
				bds.setUrl(getJdbcConnectionURL());
				bds.setUsername(getJdbcUsername());
				bds.setPassword(getJdbcPassword());
				connection = bds.getConnection();
			}

			if (connection != null) {
				return true;
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
		return false;
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

	public static Connection getConnection() {
		Connection connection = null;
		DataSource ds = null;
		try {
			if (StringUtils.isNotEmpty(getJndiName())) {
				InitialContext ctx = new InitialContext();
				ds = (DataSource) ctx.lookup(getJndiName());
				connection = ds.getConnection();
			} else {
				BasicDataSource bds = new BasicDataSource();
				bds.setDriverClassName(getJdbcDriverClass());
				bds.setUrl(getJdbcConnectionURL());
				bds.setUsername(getJdbcUsername());
				bds.setPassword(getJdbcPassword());
				connection = bds.getConnection();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connection;
	}

	public static Connection getConnection(Properties props) {
		Connection connection = null;
		DataSource ds = null;
		try {
			if (StringUtils.isNotEmpty(props.getProperty("jdbc.datasource"))) {
				InitialContext ctx = new InitialContext();
				ds = (DataSource) ctx.lookup(props
						.getProperty("jdbc.datasource"));
				connection = ds.getConnection();
			} else {
				BasicDataSource bds = new BasicDataSource();
				bds.setDriverClassName(props.getProperty("jdbc.driver"));
				bds.setUrl(props.getProperty("jdbc.url"));
				bds.setUsername(props.getProperty("jdbc.user"));
				bds.setPassword(props.getProperty("jdbc.password"));
				connection = bds.getConnection();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connection;
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
		DataSource ds = null;
		try {
			if (StringUtils.isNotEmpty(getJndiName())) {
				InitialContext ctx = new InitialContext();
				ds = (DataSource) ctx.lookup(getJndiName());
				connection = ds.getConnection();
			} else {
				BasicDataSource bds = new BasicDataSource();
				bds.setDriverClassName(getJdbcDriverClass());
				bds.setUrl(getJdbcConnectionURL());
				bds.setUsername(getJdbcUsername());
				bds.setPassword(getJdbcPassword());
				connection = bds.getConnection();
			}

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
				if (SystemConfig.getBoolean("hibernate.cfg.update")) {
					reconfigHibernate();
				}
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

	public static void reconfigHibernate() {
		Resource resource = new ClassPathResource("/hibernate.properties");
		try {
			Properties p = PropertiesTools.loadProperties(resource
					.getInputStream());
			if (StringUtils.isNotEmpty(getJndiName())) {
				p.put("hibernate.connection.datasource", getJndiName());
				p.remove("hibernate.connection.driver_class");
				p.remove("hibernate.connection.url");
				p.remove("hibernate.connection.username");
				p.remove("hibernate.connection.password");
				p.remove("hibernate.connection.provider_class");
				p.remove("hibernate.connection.pool_size");
				p.remove("hibernate.connection.autocommit");
				p.remove("hibernate.c3p0.max_size");
				p.remove("hibernate.c3p0.min_size");
				p.remove("hibernate.c3p0.timeout");
				p.remove("hibernate.c3p0.max_statements");
				p.remove("hibernate.c3p0.acquire_increment");
				p.remove("hibernate.c3p0.idle_test_period");
				p.remove("hibernate.c3p0.validate");

			} else {
				if (getJdbcDriverClass() != null) {
					p.put("hibernate.connection.driver_class",
							getJdbcDriverClass());
				}

				if (getJdbcConnectionURL() != null) {
					p.put("hibernate.connection.url", getJdbcConnectionURL());
				}
				if (getJdbcUsername() != null) {
					p.put("hibernate.connection.username", getJdbcUsername());
				}
				if (getJdbcPassword() != null) {
					p.put("hibernate.connection.password", getJdbcPassword());
				} else {
					p.put("hibernate.connection.password", "");
				}

				p.put("hibernate.connection.provider_class",
						"org.hibernate.connection.C3P0ConnectionProvider");
				p.put("hibernate.connection.autocommit", "true");
				p.put("hibernate.c3p0.max_size", "50");
				p.put("hibernate.c3p0.min_size", "5");
				p.put("hibernate.c3p0.timeout", "5000");
				p.put("hibernate.c3p0.max_statements", "100");
				p.put("hibernate.c3p0.acquire_increment", "2");
				p.put("hibernate.c3p0.idle_test_period", "3000");
				p.put("hibernate.c3p0.validate", "false");

				p.remove("hibernate.connection.datasource");
			}

			if (getHibernateDialect() != null) {
				p.put("hibernate.dialect", getHibernateDialect());
			}

			Map<String, Object> treeMap = new TreeMap<String, Object>();

			Set<String> keys = p.stringPropertyNames();
			for (String key : keys) {
				treeMap.put(key, p.get(key));
			}

			PropertiesTools.save(resource, treeMap);

		} catch (IOException ex) {
			System.out.println("ÅäÖÃhibernate.propertiesÎÄ¼þ³ö´í¡£");
		}
	}

	public static Properties reload() {
		synchronized (DataSourceConfig.class) {
			InputStream inputStream = null;
			try {
				Resource resource = new ClassPathResource(SYSTEM_CONFIG);
				System.out.println("load jdbc config:"
						+ resource.getFile().getAbsolutePath());
				inputStream = new FileInputStream(resource.getFile()
						.getAbsolutePath());
				properties.clear();
				Properties p = PropertiesTools.loadProperties(inputStream);
				if (p != null) {
					Enumeration<?> e = p.keys();
					while (e.hasMoreElements()) {
						String key = (String) e.nextElement();
						String value = p.getProperty(key);
						properties.setProperty(key, value);
					}
				}
				inputStream.close();
				inputStream = null;
				return p;
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	private DataSourceConfig() {

	}

}
