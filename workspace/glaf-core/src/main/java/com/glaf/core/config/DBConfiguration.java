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
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ConnectionDefinition;
import com.glaf.core.dialect.DB2Dialect;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.dialect.H2Dialect;
import com.glaf.core.dialect.MySQLDialect;
import com.glaf.core.dialect.OracleDialect;
import com.glaf.core.dialect.PostgreSQLDialect;
import com.glaf.core.dialect.SQLServer2008Dialect;
import com.glaf.core.dialect.SQLiteDialect;
import com.glaf.core.domain.util.ConnectionDefinitionJsonFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.connection.ConnectionConstants;
import com.glaf.core.jdbc.datasource.MultiRoutingDataSource;
import com.glaf.core.security.SecurityUtils;
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

	/**
	 * 连接池类型，支持druid,C3P0,DBCP,TomcatJdbc,默认是druid
	 */
	public static final String JDBC_POOL_TYPE = "jdbc.pool_type";

	public static final String HOST = "host";

	public static final String PORT = "port";

	public static final String DATABASE = "databaseName";

	public static final String DATABASE_NAME = "databaseName";

	public static final String SUBJECT = "subject";

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	protected static ConcurrentMap<String, ConnectionDefinition> dataSourceProperties = new ConcurrentHashMap<String, ConnectionDefinition>();

	protected static ConcurrentMap<String, Properties> jdbcTemplateProperties = new ConcurrentHashMap<String, Properties>();

	protected static ConcurrentMap<String, String> jsonProperties = new ConcurrentHashMap<String, String>();

	protected static ConcurrentMap<Integer, String> ISOLATION_LEVELS = new ConcurrentHashMap<Integer, String>();

	protected static ConcurrentMap<String, String> dbTypes = new ConcurrentHashMap<String, String>();

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

	/**
	 * 添加数据源
	 * 
	 * @param name
	 *            名称
	 * @param props
	 *            属性
	 */
	public static void addDataSourceProperties(String name, Properties props) {
		if (!dataSourceProperties.containsKey(name)) {
			try {
				if (DBConnectionFactory.checkConnection(props)) {
					String url = props.getProperty(DBConfiguration.JDBC_URL);
					ConnectionDefinition conn = toConnectionDefinition(props);
					String dbType = getDatabaseType(url);
					conn.setUrl(url);
					conn.setType(dbType);
					dbTypes.put(name, dbType);
					dataSourceProperties.put(name, conn);
					ConfigFactory.put(DBConfiguration.class.getSimpleName(),
							name, conn.toJsonObject().toJSONString());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 添加数据源
	 * 
	 * @param name
	 *            名称
	 * @param dbType
	 *            数据库类型
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param databaseName
	 *            数据库名称
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 */
	public static void addDataSourceProperties(String name, String dbType,
			String host, int port, String databaseName, String user,
			String password) {
		Properties props = getTemplateProperties(dbType);
		if (props != null && !props.isEmpty()) {
			Map<String, Object> context = new HashMap<String, Object>();
			context.put("host", host);
			if (port > 0) {
				context.put("port", port);
			} else {
				context.put("port", props.getProperty(PORT));
			}
			context.put("databaseName", databaseName);
			String driver = props.getProperty(JDBC_DRIVER);
			String url = props.getProperty(JDBC_URL);
			url = com.glaf.core.el.ExpressionTools.evaluate(url, context);
			logger.debug("name:" + name);
			logger.debug("driver:" + driver);
			logger.debug("url:" + url);
			addDataSourceProperties(name, driver, url, user, password);
		}
	}

	/**
	 * 添加数据源
	 * 
	 * @param name
	 *            名称
	 * @param driver
	 *            驱动
	 * @param url
	 *            JDBC完整URL
	 * @param user
	 *            数据库用户名
	 * @param password
	 *            密码
	 */
	public static void addDataSourceProperties(String name, String driver,
			String url, String user, String password) {
		if (!dataSourceProperties.containsKey(name)) {
			Properties props = new Properties();
			props.put(JDBC_NAME, name);
			props.put(JDBC_DRIVER, driver);
			props.put(JDBC_URL, url);
			props.put(JDBC_USER, user);
			props.put(JDBC_PASSWORD, password);

			String dbType = getDatabaseType(url);
			if (StringUtils.equals(dbType, "postgresql")) {
				props.put(ConnectionConstants.PROP_VALIDATIONQUERY,
						" SELECT 'X' ");
			} else if (StringUtils.equals(dbType, "sqlserver")) {
				props.put(ConnectionConstants.PROP_VALIDATIONQUERY,
						" SELECT 'X' ");
			} else if (StringUtils.equals(dbType, "mysql")) {
				props.put(ConnectionConstants.PROP_VALIDATIONQUERY,
						" SELECT 'X' ");
			} else if (StringUtils.equals(dbType, "oracle")) {
				props.put(ConnectionConstants.PROP_VALIDATIONQUERY,
						" SELECT 'x' FROM dual  ");
			}

			try {
				if (DBConnectionFactory.checkConnection(props)) {
					MultiRoutingDataSource.addDataSource(name, props);
					ConnectionDefinition conn = toConnectionDefinition(props);
					conn.setUrl(url);
					conn.setType(dbType);
					dbTypes.put(name, dbType);
					props.put(JDBC_TYPE, dbType);
					dataSourceProperties.put(name, conn);
					ConfigFactory.put(DBConfiguration.class.getSimpleName(),
							name, conn.toJsonObject().toJSONString());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String encodeJson(Properties props) {
		JSONObject jsonObject = new JSONObject();
		Enumeration<?> e = props.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = props.getProperty(key);
			jsonObject.put(key, value);
		}
		String content = jsonObject.toJSONString();
		String key = SystemProperties.getDefaultSecurityKey();
		content = SecurityUtils.encode(key, content);
		return content;
	}

	public static String encodeJsonCurrentSystem() {
		String systemName = Environment.getCurrentSystemName();
		if (jsonProperties.containsKey(systemName)) {
			return jsonProperties.get(systemName);
		}
		Properties props = getDataSourcePropertiesByName(systemName);
		if (props != null) {
			String content = encodeJson(props);
			jsonProperties.put(systemName, content);
		}
		return jsonProperties.get(systemName);
	}

	public static ConnectionDefinition getConnectionDefinition(String systemName) {
		if (systemName == null) {
			return null;
		}
		ConnectionDefinition model = dataSourceProperties.get(systemName);
		if (model != null) {
			try {
				JSONObject jsonObject = model.toJsonObject();
				return ConnectionDefinitionJsonFactory.jsonToObject(jsonObject);
			} catch (Exception ex) {
				// Ignore Exception
			}
		}
		String text = ConfigFactory.getString(
				DBConfiguration.class.getSimpleName(), systemName);
		if (StringUtils.isNotEmpty(text)) {
			try {
				JSONObject jsonObject = JSON.parseObject(text);
				return ConnectionDefinitionJsonFactory.jsonToObject(jsonObject);
			} catch (Exception ex) {
				// Ignore Exception
			}
		}
		return null;
	}

	public static List<ConnectionDefinition> getConnectionDefinitions() {
		List<ConnectionDefinition> rows = new java.util.ArrayList<ConnectionDefinition>();
		Iterator<Entry<String, ConnectionDefinition>> iterator = dataSourceProperties
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, ConnectionDefinition> entry = iterator.next();
			String name = (String) entry.getKey();
			ConnectionDefinition model = getConnectionDefinition(name);
			if (model != null && model.getName() != null) {
				rows.add(model);
			}
		}
		return rows;
	}

	public static String getCurrentDatabaseType() {
		String currentSystemName = Environment.getCurrentSystemName();
		ConnectionDefinition conn = getConnectionDefinition(currentSystemName);
		if (conn != null && conn.getType() != null) {
			return conn.getType();
		}
		return null;
	}

	public static Properties getCurrentDataSourceProperties() {
		String currentSystemName = Environment.getCurrentSystemName();
		ConnectionDefinition conn = getConnectionDefinition(currentSystemName);
		Properties props = toProperties(conn);
		return props;
	}

	public static Dialect getCurrentDialect() {
		Map<String, Dialect> dialects = getDialects();
		String currentSystemName = Environment.getCurrentSystemName();
		ConnectionDefinition conn = getConnectionDefinition(currentSystemName);
		if (conn != null && conn.getType() != null) {
			String dbType = conn.getType();
			if (dbType == null) {
				dbType = getCurrentDatabaseType();
			}
			logger.debug("databaseType:" + dbType);
			return dialects.get(dbType);
		}
		return null;
	}

	public static String getCurrentDialectClass() {
		Properties dialects = getDialectMappings();
		String currentSystemName = Environment.getCurrentSystemName();
		ConnectionDefinition conn = getConnectionDefinition(currentSystemName);
		if (conn != null && conn.getType() != null) {
			String dbType = conn.getType();
			if (dbType == null) {
				dbType = getCurrentDatabaseType();
			}
			logger.debug("databaseType:" + dbType);
			return dialects.getProperty(dbType);
		}
		return null;
	}

	public static String getCurrentHibernateDialect() {
		Properties dialects = getHibernateDialectMappings();
		String currentSystemName = Environment.getCurrentSystemName();
		ConnectionDefinition conn = getConnectionDefinition(currentSystemName);
		if (conn != null && conn.getType() != null) {
			String dbType = conn.getType();
			if (dbType == null) {
				dbType = getCurrentDatabaseType();
			}
			logger.debug("databaseType:" + dbType);
			return dialects.getProperty(dbType);
		}
		return null;
	}

	public static Dialect getDatabaseDialect(Connection connection) {
		Dialect dialect = null;
		String dbType = DBConnectionFactory.getDatabaseType(connection);
		if (dbType != null) {
			dialect = getDialects().get(dbType);
		}
		return dialect;
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

	public static String getDatabaseTypeByName(String systemName) {
		if (dbTypes.get(systemName) != null) {
			return dbTypes.get(systemName);
		}
		ConnectionDefinition conn = getConnectionDefinition(systemName);
		if (conn != null && conn.getType() != null) {
			return conn.getType();
		}
		return null;
	}

	public static Map<String, Properties> getDataSourceProperties() {
		Map<String, Properties> dsMap = new HashMap<String, Properties>();
		if (dataSourceProperties != null && !dataSourceProperties.isEmpty()) {
			Iterator<Entry<String, ConnectionDefinition>> iterator = dataSourceProperties
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, ConnectionDefinition> entry = iterator.next();
				String name = (String) entry.getKey();
				ConnectionDefinition conn = entry.getValue();
				dsMap.put(name, toProperties(conn));
			}
		}
		return dsMap;
	}

	public static Properties getDataSourcePropertiesByName(String name) {
		if (name == null) {
			return null;
		}
		logger.debug("->name:" + name);
		ConnectionDefinition conn = getConnectionDefinition(name);
		Properties props = toProperties(conn);
		if (props == null || props.isEmpty()) {
			// props = getDefaultDataSourceProperties();
		}
		return props;
	}

	public static Properties getDefaultDataSourceProperties() {
		ConnectionDefinition conn = getConnectionDefinition(Environment.DEFAULT_SYSTEM_NAME);
		Properties props = toProperties(conn);
		if (props == null) {
			// 如果没有默认的jdbc配置，重装配置文件。
			reloadDS();
		}
		return props;
	}

	public static String getDefaultHibernateDialect() {
		Properties dialects = getHibernateDialectMappings();
		Properties props = getDefaultDataSourceProperties();
		if (props != null) {
			String dbType = props.getProperty(JDBC_TYPE);
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
				"com.glaf.core.dialect.SQLServer2008Dialect");
		dialectMappings.setProperty("sqlite",
				"com.glaf.core.dialect.SQLiteDialect");
		dialectMappings.setProperty("db2", "com.glaf.core.dialect.DB2Dialect");
		return dialectMappings;
	}

	public static Map<String, Dialect> getDialects() {
		Map<String, Dialect> dialects = new java.util.HashMap<String, Dialect>();
		dialects.put("h2", new H2Dialect());
		dialects.put("mysql", new MySQLDialect());
		dialects.put("sqlserver", new SQLServer2008Dialect());
		dialects.put("sqlite", new SQLiteDialect());
		dialects.put("oracle", new OracleDialect());
		dialects.put("postgresql", new PostgreSQLDialect());
		dialects.put("db2", new DB2Dialect());
		return dialects;
	}

	public static Map<String, Dialect> getDatabaseDialects() {
		Map<String, Dialect> dialects = new java.util.HashMap<String, Dialect>();
		logger.debug("dataSourceProperties:" + dataSourceProperties);
		if (dataSourceProperties != null && !dataSourceProperties.isEmpty()) {
			Iterator<Entry<String, ConnectionDefinition>> iterator = dataSourceProperties
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, ConnectionDefinition> entry = iterator.next();
				String key = (String) entry.getKey();
				ConnectionDefinition conn = entry.getValue();
				String url = conn.getUrl();
				String dbType = getDatabaseType(url);
				logger.debug(dbType + "->" + url);
				if (StringUtils.equals(dbType, "h2")) {
					dialects.put(key, new H2Dialect());
				} else if (StringUtils.equals(dbType, "mysql")) {
					dialects.put(key, new MySQLDialect());
				} else if (StringUtils.equals(dbType, "sqlserver")) {
					dialects.put(key, new SQLServer2008Dialect());
				} else if (StringUtils.equals(dbType, "sqlite")) {
					dialects.put(key, new SQLiteDialect());
				} else if (StringUtils.equals(dbType, "oracle")) {
					dialects.put(key, new OracleDialect());
				} else if (StringUtils.equals(dbType, "postgresql")) {
					dialects.put(key, new PostgreSQLDialect());
				} else if (StringUtils.equals(dbType, "db2")) {
					dialects.put(key, new DB2Dialect());
				}
			}
		}
		logger.debug("dialects->" + dialects);
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

	/**
	 * 获取主库数据源属性
	 * 
	 * @return
	 */
	public static Properties getMasterDataSourceProperties() {
		String configFile = SystemProperties.getMasterDataSourceConfigFile();
		String filename = SystemProperties.getConfigRootPath() + configFile;
		Properties props = PropertiesUtils.loadFilePathResource(filename);
		return props;
	}

	public static Properties getProperties(String name) {
		if (name == null) {
			return null;
		}
		if (dataSourceProperties.isEmpty()) {
			reloadDS();
		}
		ConnectionDefinition conn = getConnectionDefinition(name);
		Properties props = toProperties(conn);
		return props;
	}

	public static Properties getTemplateProperties(String name) {
		if (name == null) {
			return null;
		}
		if (jdbcTemplateProperties.isEmpty()) {
			init();
		}
		logger.debug("name:" + name);
		Properties props = jdbcTemplateProperties.get(name);
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
					if (filelist != null) {
						for (int i = 0, len = filelist.length; i < len; i++) {
							String filename = config + "/" + filelist[i];
							File file = new File(filename);
							if (file.isFile()
									&& file.getName().endsWith(".properties")) {
								InputStream inputStream = new FileInputStream(
										file);
								Properties props = PropertiesUtils
										.loadProperties(inputStream);
								if (props != null) {
									jdbcTemplateProperties
											.put(props.getProperty(JDBC_NAME),
													props);
								}
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
		if (!loading.get()) {
			try {
				loading.set(true);
				String path = null;
				String deploymentSystemName = SystemProperties
						.getDeploymentSystemName();
				if (deploymentSystemName != null
						&& deploymentSystemName.length() > 0) {
					path = SystemProperties.getConfigRootPath()
							+ Constants.DEPLOYMENT_JDBC_PATH
							+ deploymentSystemName + "/jdbc/";
				} else {
					path = SystemProperties.getConfigRootPath()
							+ Constants.JDBC_CONFIG;
				}
				logger.info("datasource path:" + path);
				File dir = new File(path);
				if (dir.exists() && dir.isDirectory()) {
					File[] filelist = dir.listFiles();
					if (filelist != null) {
						for (int i = 0, len = filelist.length; i < len; i++) {
							File file = filelist[i];
							if (file.getName().endsWith(".properties")) {
								logger.info("load jdbc properties:"
										+ file.getAbsolutePath());
								try {
									Properties props = PropertiesUtils
											.loadProperties(new FileInputStream(
													file));
									if (DBConnectionFactory
											.checkConnection(props)) {
										String name = props
												.getProperty(JDBC_NAME);
										if (StringUtils.isNotEmpty(name)) {
											String dbType = props
													.getProperty(JDBC_TYPE);
											if (StringUtils.isEmpty(dbType)) {
												dbType = getDatabaseType(props
														.getProperty(JDBC_URL));
												props.setProperty(JDBC_TYPE,
														dbType);
											}
											ConnectionDefinition conn = toConnectionDefinition(props);
											dbTypes.put(name, dbType);
											dataSourceProperties
													.put(name, conn);
											ConfigFactory.put(
													DBConfiguration.class
															.getSimpleName(),
													name, conn.toJsonObject()
															.toJSONString());
										}
									}
								} catch (Exception ex) {
									ex.printStackTrace();
									logger.error(ex);
								}
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
					+ SystemProperties.getMasterDataSourceConfigFile();
			File file = new File(filename);
			if (file.exists() && file.isFile()) {
				logger.info("load default jdbc config:" + filename);
				Properties props = PropertiesUtils
						.loadFilePathResource(filename);
				String dbType = props.getProperty(JDBC_TYPE);
				if (StringUtils.isEmpty(dbType)) {
					try {
						dbType = getDatabaseType(props.getProperty(JDBC_URL));
						if (dbType != null) {
							props.setProperty(JDBC_TYPE, dbType);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex);
					}
				}
				ConnectionDefinition conn = toConnectionDefinition(props);
				dataSourceProperties.put(Environment.DEFAULT_SYSTEM_NAME, conn);
				if (dbType != null) {
					dbTypes.put(Environment.DEFAULT_SYSTEM_NAME, dbType);
				}
				jdbcTemplateProperties.put(Environment.DEFAULT_SYSTEM_NAME,
						props);
				ConfigFactory.put(DBConfiguration.class.getSimpleName(),
						Environment.DEFAULT_SYSTEM_NAME, conn.toJsonObject()
								.toJSONString());
			}
			logger.info("#datasources:" + dataSourceProperties.keySet());
			if (!dataSourceProperties
					.containsKey(Environment.DEFAULT_SYSTEM_NAME)) {
				logger.warn("default jdbc properties not found!!!");
			}
		}
	}

	public static ConnectionDefinition toConnectionDefinition(Properties props) {
		if (props != null && !props.isEmpty()) {
			ConnectionDefinition model = new ConnectionDefinition();
			model.setDatasource(props.getProperty(JDBC_DATASOURCE));
			model.setDriver(props.getProperty(JDBC_DRIVER));
			model.setUrl(props.getProperty(JDBC_URL));
			model.setName(props.getProperty(JDBC_NAME));
			model.setUser(props.getProperty(JDBC_USER));
			model.setPassword(props.getProperty(JDBC_PASSWORD));
			model.setSubject(props.getProperty(SUBJECT));
			model.setProvider(props.getProperty(JDBC_PROVIDER));
			model.setType(props.getProperty(JDBC_TYPE));
			model.setHost(props.getProperty(HOST));
			model.setDatabase(props.getProperty(DATABASE));
			if (StringUtils.isNotEmpty(props.getProperty(PORT))) {
				model.setPort(Integer.parseInt(props.getProperty(PORT)));
			}

			if (StringUtils.equals("true", props.getProperty(JDBC_AUTOCOMMIT))) {
				model.setAutoCommit(true);
			}

			Properties p = new Properties();
			Enumeration<?> e = props.keys();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = props.getProperty(key);
				p.put(key, value);
			}
			model.setProperties(p);
			return model;
		}
		return null;
	}

	public static Properties toProperties(ConnectionDefinition conn) {
		if (conn != null) {
			Properties props = new Properties();
			if (conn.getProperties() != null) {
				props.putAll(conn.getProperties());
			}
			if (conn.getSubject() != null) {
				props.setProperty(SUBJECT, conn.getSubject());
			}
			if (conn.getDatasource() != null) {
				props.setProperty(JDBC_DATASOURCE, conn.getDatasource());
			}
			if (conn.getName() != null) {
				props.setProperty(JDBC_NAME, conn.getName());
			}
			props.setProperty(JDBC_DRIVER, conn.getDriver());
			props.setProperty(JDBC_URL, conn.getUrl());
			props.setProperty(JDBC_USER, conn.getUser());
			if (conn.getPassword() != null) {
				props.setProperty(JDBC_PASSWORD, conn.getPassword());
			}
			if (conn.getProvider() != null) {
				props.setProperty(JDBC_PROVIDER, conn.getProvider());
			}
			String type = getDatabaseType(conn.getUrl());
			props.setProperty(JDBC_TYPE, type);
			if (conn.getHost() != null) {
				props.setProperty(HOST, conn.getHost());
			}
			props.setProperty(PORT, String.valueOf(conn.getPort()));
			if (conn.getDatabase() != null) {
				props.setProperty(DATABASE, conn.getDatabase());
			}
			return props;
		}
		return null;
	}

	private DBConfiguration() {

	}

}