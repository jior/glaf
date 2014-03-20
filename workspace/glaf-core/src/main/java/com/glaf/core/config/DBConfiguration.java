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
import java.io.InputStream;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.ConnectionDefinition;
import com.glaf.core.dialect.DB2Dialect;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.dialect.H2Dialect;
import com.glaf.core.dialect.MySQLDialect;
import com.glaf.core.dialect.OracleDialect;
import com.glaf.core.dialect.PostgreSQLDialect;
import com.glaf.core.dialect.SQLiteDialect;
import com.glaf.core.util.Constants;
import com.glaf.core.util.PropertiesUtils;

public class DBConfiguration {
	protected static final Log logger = LogFactory
			.getLog(DBConfiguration.class);

	public static final String DIALECT = "dialect";

	public static final String JDBC_PROVIDER = "jdbc.provider";

	public static final String JDBC_AUTOCOMMIT = "jdbc.autocommit";

	public static final String JDBC_TYPE = "jdbc.type";

	public static final String JDBC_NAME = "jdbc.name";

	public static final String JDBC_PREFIX = "jdbc";

	public static final String JDBC_DATASOURCE = "jdbc.datasource";

	public static final String JDBC_URL = "jdbc.url";

	public static final String JDBC_USER = "jdbc.user";

	public static final String JDBC_DRIVER = "jdbc.driver";

	public static final String JDBC_PASSWORD = "jdbc.password";

	public static final String JDBC_ISOLATION = "jdbc.isolation";

	public static final String POOL_ACQUIRE_INCREMENT = "jdbc.acquire_increment";

	public static final String POOL_IDLE_TEST_PERIOD = "jdbc.idle_test_period";

	public static final String POOL_INIT_SIZE = "jdbc.init_size";

	public static final String POOL_MAX_SIZE = "jdbc.max_size";

	public static final String POOL_MAX_STATEMENTS = "jdbc.max_statements";

	public static final String POOL_MIN_SIZE = "jdbc.min_size";

	public static final String POOL_SIZE = "jdbc.pool_size";

	public static final String POOL_TIMEOUT = "jdbc.timeout";

	/**
	 * 连接池类型，支持druid,C3P0,DBCP,TomcatJdbc,默认是druid
	 */
	public static final String JDBC_POOL_TYPE = "jdbc.pool_type";

	public static final String HOST = "host";

	public static final String USER = "user";

	public static final String PASSWORD = "password";

	public static final String DATABASE_NAME = "databaseName";

	private static boolean REQUIRE_RELOAD_JDBC_RESOURCE = false;

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	protected static ConcurrentMap<String, Properties> dataMap = new ConcurrentHashMap<String, Properties>();

	protected static ConcurrentMap<String, Properties> templateDataMap = new ConcurrentHashMap<String, Properties>();

	protected static ConcurrentMap<Integer, String> ISOLATION_LEVELS = new ConcurrentHashMap<Integer, String>();

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
		init();
		reloadDS();
	}

	public static List<ConnectionDefinition> getConnectionDefinitions() {
		List<ConnectionDefinition> rows = new java.util.concurrent.CopyOnWriteArrayList<ConnectionDefinition>();
		Collection<Properties> list = dataMap.values();
		if (list != null && !list.isEmpty()) {
			for (Properties props : list) {
				ConnectionDefinition model = new ConnectionDefinition();
				model.setDatasource(props.getProperty(JDBC_DATASOURCE));
				model.setDriver(props.getProperty(JDBC_DRIVER));
				model.setUrl(props.getProperty(JDBC_URL));
				model.setName(props.getProperty(JDBC_NAME));
				model.setSubject(props.getProperty("subject"));
				model.setProvider(props.getProperty(JDBC_PROVIDER));
				model.setType(props.getProperty(JDBC_TYPE));
				if (StringUtils.equals("true",
						props.getProperty(JDBC_AUTOCOMMIT))) {
					model.setAutoCommit(true);
				}
				rows.add(model);
			}
		}
		return rows;
	}

	public static String getCurrentDatabaseType() {
		Properties props = getCurrentDataSourceProperties();
		if (props != null) {
			return props.getProperty(JDBC_TYPE);
		}
		return null;
	}

	public static Properties getCurrentDataSourceProperties() {
		String dsName = Environment.getCurrentSystemName();
		Properties props = dataMap.get(dsName);
		Properties p = new Properties();
		Enumeration<?> e = props.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = props.getProperty(key);
			p.put(key, value);
		}
		return p;
	}

	public static Dialect getCurrentDialect() {
		Map<String, Dialect> dialects = getDialects();
		Properties props = getCurrentDataSourceProperties();
		if (props != null) {
			String dbType = props.getProperty(JDBC_TYPE);
			if (dbType == null) {
				dbType = DataSourceConfig.getDatabaseType();
			}
			logger.debug("databaseType:" + dbType);
			return dialects.get(dbType);
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

	public static Map<String, Properties> getDataSourceProperties() {
		return dataMap;
	}

	public static Properties getDataSourcePropertiesByName(String name) {
		Properties props = dataMap.get(name);
		Properties p = new Properties();
		Enumeration<?> e = props.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = props.getProperty(key);
			p.put(key, value);
		}
		return p;
	}

	public static Properties getDefaultDataSourceProperties() {
		Properties props = dataMap.get(Environment.DEFAULT_SYSTEM_NAME);
		Properties p = new Properties();
		Enumeration<?> e = props.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = props.getProperty(key);
			p.put(key, value);
		}
		return p;
	}

	public static String getDefaultHibernateDialect() {
		Properties dialects = getHibernateDialectMappings();
		Properties props = getDefaultDataSourceProperties();
		if (props != null) {
			String dbType = props.getProperty(JDBC_TYPE);
			if (dbType == null) {
				dbType = DataSourceConfig.getDatabaseType();
			}
			logger.debug("databaseType:" + dbType);
			return dialects.getProperty(dbType);
		}
		return null;
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
		Map<String, Dialect> dialects = new java.util.concurrent.ConcurrentHashMap<String, Dialect>();
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

	public static Properties getProperties(String name) {
		if (dataMap.isEmpty()) {
			reloadDS();
		}
		Properties props = dataMap.get(name);
		Properties p = new Properties();
		Enumeration<?> e = props.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = props.getProperty(key);
			p.put(key, value);
		}
		return p;
	}

	public static Properties getTemplateProperties(String name) {
		if (templateDataMap.isEmpty()) {
			init();
		}
		Properties props = templateDataMap.get(name);
		Properties p = new Properties();
		Enumeration<?> e = props.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = props.getProperty(key);
			p.put(key, value);
		}
		return p;
	}

	public static void init() {
		if (!loading.get()) {
			try {
				loading.set(true);
				String config = SystemProperties.getConfigRootPath()
						+ "/conf/templates/jdbc";
				File directory = new File(config);
				if (directory.exists() && directory.isDirectory()) {
					String[] filelist = directory.list();
					for (int i = 0; i < filelist.length; i++) {
						String filename = config + "/" + filelist[i];
						File file = new File(filename);
						if (file.isFile()
								&& file.getName().endsWith(".properties")) {
							InputStream inputStream = new FileInputStream(file);
							Properties props = PropertiesUtils
									.loadProperties(inputStream);
							if (props != null) {
								templateDataMap.put(
										props.getProperty(JDBC_NAME), props);
							}
						}
					}
				}

				String glabal_config = SystemProperties.getConfigRootPath()
						+ Constants.DEFAULT_JDBC_CONFIG;
				File file = new File(glabal_config);
				if (file.isFile() && file.getName().endsWith(".properties")) {
					InputStream inputStream = new FileInputStream(file);
					Properties props = PropertiesUtils
							.loadProperties(inputStream);
					if (props != null) {
						dataMap.put(Environment.DEFAULT_SYSTEM_NAME, props);
						templateDataMap.put(Environment.DEFAULT_SYSTEM_NAME,
								props);
						if (props.getProperty(JDBC_NAME) != null) {
							dataMap.put(props.getProperty(JDBC_NAME), props);
							templateDataMap.put(props.getProperty(JDBC_NAME),
									props);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			} finally {
				loading.set(false);
			}
		}
	}

	public static boolean isJndiDataSource(String systemName) {
		Properties props = getDataSourcePropertiesByName(systemName);
		if (props != null
				&& StringUtils.isNotEmpty(props.getProperty(JDBC_DATASOURCE))) {
			return true;
		}
		return false;
	}

	public static String isolationLevelToString(int isolation) {
		return (String) ISOLATION_LEVELS.get(new Integer(isolation));
	}

	protected static void reloadDS() {
		Map<String, Properties> dataSourceMap = new java.util.concurrent.ConcurrentHashMap<String, Properties>();
		if (!loading.get()) {
			try {
				loading.set(true);
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
									if (DataSourceConfig.getConnection(props) != null) {
										String name = props
												.getProperty(JDBC_NAME);
										if (StringUtils.isNotEmpty(name)) {
											String dbType = props
													.getProperty(JDBC_TYPE);
											if (StringUtils.isEmpty(dbType)) {
												dbType = DBConfiguration
														.getDatabaseType(props
																.getProperty(JDBC_URL));
												props.setProperty(JDBC_TYPE,
														dbType);
											}
											dataSourceMap.put(name, props);
										}
									}
								}
							} catch (Exception ex) {
								ex.printStackTrace();
								logger.error(ex);
							}
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			} finally {
				loading.set(false);
			}

			String filename = SystemProperties.getConfigRootPath()
					+ Constants.DEFAULT_JDBC_CONFIG;
			File file = new File(filename);
			if (file.exists() && file.isFile()) {
				Properties p = PropertiesUtils.loadFilePathResource(filename);
				String dbType = p.getProperty(JDBC_TYPE);
				if (StringUtils.isEmpty(dbType)) {
					try {
						dbType = DBConfiguration.getDatabaseType(p
								.getProperty(JDBC_URL));
						if (dbType != null) {
							p.setProperty(JDBC_TYPE, dbType);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex);
					}
				}
				dataSourceMap.put(Environment.DEFAULT_SYSTEM_NAME, p);
			}
			logger.info("#datasources:" + dataSourceMap.keySet());
			dataMap.clear();
			dataMap.putAll(dataSourceMap);
			REQUIRE_RELOAD_JDBC_RESOURCE = true;
		}
	}

	public static boolean requireReloadDataSource() {
		return REQUIRE_RELOAD_JDBC_RESOURCE;
	}

	public static void successReloadDataSource() {
		REQUIRE_RELOAD_JDBC_RESOURCE = false;
	}

	private DBConfiguration() {

	}

}