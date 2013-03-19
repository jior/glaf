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

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;

import com.glaf.core.dialect.DB2Dialect;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.dialect.H2Dialect;
import com.glaf.core.dialect.MySQLDialect;
import com.glaf.core.dialect.OracleDialect;
import com.glaf.core.dialect.PostgreSQLDialect;
import com.glaf.core.dialect.SQLiteDialect;

import com.glaf.core.security.LoginContext;
import com.glaf.core.util.Constants;
import com.glaf.core.util.PropertiesUtils;

public final class Environment {
	protected final static Log logger = LogFactory.getLog(Environment.class);

	public static final String AUTOCOMMIT = "jdbc.autocommit";

	public static final String CONNECTION_DATABASE_TYPE = "jdbc.type";

	public static final String CONNECTION_NAME = "jdbc.name";

	public static final String CONNECTION_PREFIX = "jdbc";

	public static final String CONNECTION_PROVIDER = "jdbc.provider_class";

	public static final String CONNECTION_TITLE = "jdbc.title";

	public static final String CURRENT_SYSTEM_NAME = "CURRENT_SYSTEM_NAME";

	public static final String CURRENT_USER = "CURRENT_USER";

	protected static Properties databaseTypeMappings = getDatabaseTypeMappings();

	public static final String DATASOURCE = "jdbc.datasource";

	private static ConcurrentMap<String, Properties> dataSourceProperties = new ConcurrentHashMap<String, Properties>();

	public static final String DEFAULT_SYSTEM_NAME = "default";

	public static final String DIALECT = "dialect";

	protected static Properties dialectTypeMappings = getDialectMappings();

	public static final String URL = "jdbc.url";

	public static final String USER = "jdbc.user";

	public static final String DRIVER = "jdbc.driver";

	public static final String PASS = "jdbc.password";

	protected static Properties hibernateDialectTypeMappings = getHibernateDialectMappings();

	public static final String ISOLATION = "jdbc.isolation";

	private static final ConcurrentMap<Integer, String> ISOLATION_LEVELS = new ConcurrentHashMap<Integer, String>();

	public static final String POOL_ACQUIRE_INCREMENT = "jdbc.acquire_increment";

	public static final String POOL_IDLE_TEST_PERIOD = "jdbc.idle_test_period";

	public static final String POOL_INIT_SIZE = "jdbc.init_size";

	public static final String POOL_MAX_SIZE = "jdbc.max_size";

	public static final String POOL_MAX_STATEMENTS = "jdbc.max_statements";

	public static final String POOL_MIN_SIZE = "jdbc.min_size";

	public static final String POOL_SIZE = "jdbc.pool_size";

	public static final String POOL_TIMEOUT = "jdbc.timeout";

	/**
	 * 连接池类型，支持Druid,C3P0,DBCP,TomcatJdbc,默认是Druid
	 */
	public static final String POOL_TYPE = "jdbc.pool_type";

	private static Properties properties = new Properties();

	private static boolean REQUIRE_RELOAD_JDBC_RESOURCE = false;

	private static ConcurrentMap<String, Properties> systemProperties = new ConcurrentHashMap<String, Properties>();

	protected static ThreadLocal<LoginContext> threadLocalContexts = new ThreadLocal<LoginContext>();

	protected static ThreadLocal<Map<String, String>> threadLocalVaribles = new ThreadLocal<Map<String, String>>();

	static {
		ISOLATION_LEVELS.put(new Integer(Connection.TRANSACTION_NONE), "NONE");
		ISOLATION_LEVELS.put(new Integer(
				Connection.TRANSACTION_READ_UNCOMMITTED), "READ_UNCOMMITTED");
		ISOLATION_LEVELS.put(
				new Integer(Connection.TRANSACTION_READ_COMMITTED),
				"READ_COMMITTED");
		ISOLATION_LEVELS.put(
				new Integer(Connection.TRANSACTION_REPEATABLE_READ),
				"REPEATABLE_READ");
		ISOLATION_LEVELS.put(new Integer(Connection.TRANSACTION_SERIALIZABLE),
				"SERIALIZABLE");
		try {
			reload();
			reloadDS();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getCurrentDatabaseType() {
		Properties props = Environment.getCurrentDataSourceProperties();
		if (props != null) {
			return props.getProperty(Environment.CONNECTION_DATABASE_TYPE);
		}
		return null;
	}

	public static Properties getCurrentDataSourceProperties() {
		String dsName = getCurrentSystemName();
		return dataSourceProperties.get(dsName);
	}

	public static Dialect getCurrentDialect() {
		Map<String, Dialect> dialects = getDialects();
		Properties props = getCurrentDataSourceProperties();
		if (props != null) {
			String dbType = props.getProperty(CONNECTION_DATABASE_TYPE);
			if (dbType == null) {
				dbType = DataSourceConfig.getDatabaseType();
			}
			logger.debug("databaseType:" + dbType);
			return dialects.get(dbType);
		}
		return null;
	}

	public static String getCurrentSystemName() {
		if (threadLocalVaribles.get() != null
				&& threadLocalVaribles.get().get(CURRENT_SYSTEM_NAME) != null) {
			return threadLocalVaribles.get().get(CURRENT_SYSTEM_NAME);
		}
		return DEFAULT_SYSTEM_NAME;
	}

	public static Properties getCurrentSystemProperties() {
		String dsName = getCurrentSystemName();
		return systemProperties.get(dsName);
	}

	public static String getCurrentUser() {
		if (threadLocalVaribles.get() != null) {
			return threadLocalVaribles.get().get(CURRENT_USER);
		}
		return null;
	}

	public static String getDatabaseType(String url) {
		String dbType = null;
		if (StringUtils.contains(url, "jdbc:mysql:")) {
			dbType = "mysql";
		} else if (StringUtils.contains(url, "jdbc:postgresql:")) {
			dbType = "postgresql";
		} else if (StringUtils.contains(url, "jdbc:h2:")) {
			dbType = "h2";
		} else if (StringUtils.contains(url, "jdbc:jtds:sqlserver:")) {
			dbType = "sqlserver";
		} else if (StringUtils.contains(url, "jdbc:sqlserver:")) {
			dbType = "sqlserver";
		} else if (StringUtils.contains(url, "jdbc:oracle:")) {
			dbType = "oracle";
		} else if (StringUtils.contains(url, "jdbc:db2:")) {
			dbType = "db2";
		} else if (StringUtils.contains(url, "jdbc:sqlite:")) {
			dbType = "sqlite";
		}
		return dbType;
	}

	public static Properties getDatabaseTypeMappings() {
		Properties databaseTypeMappings = new Properties();
		databaseTypeMappings.setProperty("H2", "h2");
		databaseTypeMappings.setProperty("SQLite", "sqlite");
		databaseTypeMappings.setProperty("MySQL", "mysql");
		databaseTypeMappings.setProperty("Oracle", "oracle");
		databaseTypeMappings.setProperty("PostgreSQL", "postgresql");
		databaseTypeMappings.setProperty("Microsoft SQL Server", "sqlserver");
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

	public static Map<String, Properties> getDataSourceProperties() {
		return dataSourceProperties;
	}

	public static Properties getDataSourcePropertiesByName(String name) {
		return dataSourceProperties.get(name);
	}

	public static Properties getDefaultDataSourceProperties() {
		return dataSourceProperties.get(DEFAULT_SYSTEM_NAME);
	}

	public static Properties getDialectMappings() {
		Properties dialectMappings = new Properties();
		dialectMappings.setProperty("h2", "com.glaf.core.dialect.H2Dialect");
		dialectMappings.setProperty("mysql",
				"com.glaf.core.dialect.MySQLDialect");
		dialectMappings.setProperty("oracle",
				"com.glaf.core.dialect.OracleDialect");
		dialectMappings.setProperty("postgresql",
				"com.glaf.core.dialect.PostgreSQLDialect");
		dialectMappings.setProperty("sqlserver",
				"com.glaf.core.dialect.SQLServerDialect");
		dialectMappings.setProperty("sqlite",
				"com.glaf.core.dialect.SQLiteDialect");
		dialectMappings.setProperty("db2", "com.glaf.core.dialect.DB2Dialect");
		return dialectMappings;
	}

	public static Map<String, Dialect> getDialects() {
		Map<String, Dialect> dialects = new HashMap<String, Dialect>();
		dialects.put("h2", new H2Dialect());
		dialects.put("mysql", new MySQLDialect());
		dialects.put("sqlite", new SQLiteDialect());
		dialects.put("oracle", new OracleDialect());
		dialects.put("postgresql", new PostgreSQLDialect());
		dialects.put("db2", new DB2Dialect());
		return dialects;
	}

	public static Properties getHibernateDialectMappings() {
		Properties dialectMappings = new Properties();
		dialectMappings.setProperty("h2", "org.hibernate.dialect.H2Dialect");
		dialectMappings.setProperty("mysql",
				"org.hibernate.dialect.MySQL5Dialect");
		dialectMappings.setProperty("oracle",
				"org.hibernate.dialect.Oracle10gDialect");
		dialectMappings.setProperty("postgresql",
				"org.hibernate.dialect.PostgreSQLDialect");
		dialectMappings.setProperty("sqlserver",
				"org.hibernate.dialect.SQLServerDialect");
		dialectMappings.setProperty("db2", "org.hibernate.dialect.DB2Dialect");
		return dialectMappings;
	}

	public static LoginContext getLoginContext() {
		if (threadLocalContexts.get() != null) {
			return threadLocalContexts.get();
		}
		return null;
	}

	public static Properties getProperties() {
		if (properties == null) {
			reload();
		}
		return properties;
	}

	public static Map<String, Properties> getSystemProperties() {
		return systemProperties;
	}

	public static Properties getSystemPropertiesByName(String name) {
		return systemProperties.get(name);
	}

	public static String getThreadLocalProperty(String key) {
		if (threadLocalVaribles.get() != null) {
			return threadLocalVaribles.get().get(key);
		}
		return null;
	}

	public static String getThreadLocalProperty(String key, String defaultValue) {
		if (threadLocalVaribles.get() != null) {
			return threadLocalVaribles.get().get(key);
		}
		return defaultValue;
	}

	public static String isolationLevelToString(int isolation) {
		return (String) ISOLATION_LEVELS.get(new Integer(isolation));
	}

	public static void reload() {
		try {
			String filename = SystemProperties.getConfigRootPath()
					+ Constants.DEFAULT_JDBC_CONFIG;
			Properties p = PropertiesUtils.loadFilePathResource(filename);
			if (p != null) {
				properties.clear();
				Enumeration<?> e = p.keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = p.getProperty(key);
					properties.setProperty(key, value);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected static void reloadDS() {
		Map<String, Properties> dataSourceMap = new HashMap<String, Properties>();
		synchronized (Environment.class) {
			try {
				String path = SystemProperties.getConfigRootPath()
						+ Constants.JDBC_CONFIG;
				logger.info("path:" + path);
				File dir = new File(path);
				if (dir.exists() && dir.isDirectory()) {
					File[] entries = dir.listFiles();
					for (int i = 0; i < entries.length; i++) {
						File file = entries[i];
						if (file.getName().endsWith(".properties")) {
							logger.info("load jdbc properties:"
									+ file.getAbsolutePath());
							try {
								Properties props = PropertiesUtils
										.loadProperties(new FileInputStream(
												file));
								if (props != null) {
									String name = props
											.getProperty(CONNECTION_NAME);
									if (StringUtils.isNotEmpty(name)) {
										String dbType = props
												.getProperty(CONNECTION_DATABASE_TYPE);
										if (StringUtils.isEmpty(dbType)) {
											dbType = getDatabaseType(props
													.getProperty(URL));
											props.setProperty(
													CONNECTION_DATABASE_TYPE,
													dbType);
										}
										dataSourceMap.put(name, props);
									}
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			String filename = SystemProperties.getConfigRootPath()
					+ Constants.DEFAULT_JDBC_CONFIG;
			Properties p = PropertiesUtils.loadFilePathResource(filename);
			String dbType = p.getProperty(CONNECTION_DATABASE_TYPE);
			if (StringUtils.isEmpty(dbType)) {
				dbType = getDatabaseType(p.getProperty(URL));
				if (dbType != null) {
					p.setProperty(CONNECTION_DATABASE_TYPE, dbType);
				}
			}
			dataSourceMap.put(DEFAULT_SYSTEM_NAME, p);
			logger.info("#datasources:" + dataSourceMap.keySet());
			dataSourceProperties.clear();
			dataSourceProperties.putAll(dataSourceMap);
			REQUIRE_RELOAD_JDBC_RESOURCE = true;
		}
	}

	public static boolean requireReloadDataSource() {
		return REQUIRE_RELOAD_JDBC_RESOURCE;
	}

	public static void setCurrentSystemName(String value) {
		Map<String, String> dataMap = threadLocalVaribles.get();
		if (dataMap == null) {
			dataMap = new HashMap<String, String>();
			threadLocalVaribles.set(dataMap);
		}
		dataMap.put(CURRENT_SYSTEM_NAME, value);
	}

	public static void setCurrentUser(String actorId) {
		Map<String, String> dataMap = threadLocalVaribles.get();
		if (dataMap == null) {
			dataMap = new HashMap<String, String>();
			threadLocalVaribles.set(dataMap);
		}
		dataMap.put(CURRENT_USER, actorId);
	}

	public static void setLoginContext(LoginContext loginContext) {
		if (loginContext != null) {
			threadLocalContexts.set(loginContext);
		}
	}

	public static void setThreadLocalProperty(String key, String value) {
		Map<String, String> dataMap = threadLocalVaribles.get();
		if (dataMap == null) {
			dataMap = new HashMap<String, String>();
			threadLocalVaribles.set(dataMap);
		}
		if (!(StringUtils.equals(key, CURRENT_SYSTEM_NAME) || StringUtils
				.equals(key, CURRENT_USER))) {
			dataMap.put(key, value);
		}
	}

	public static void successReloadDataSource() {
		REQUIRE_RELOAD_JDBC_RESOURCE = false;
	}

}