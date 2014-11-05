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

package com.glaf.core.jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.util.PropertiesHelper;
import com.glaf.core.util.ReflectUtils;

/**
 * Druid连接池基本属性配置 druid.minPoolSize=5 druid.maxPoolSize=50
 * druid.initialPoolSize=5 druid.acquireIncrement=1 #超时，单位是秒 druid.maxWait=600
 * #间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是秒 druid.idleConnectionTestPeriod=120
 */
public class DruidConnectionProvider implements ConnectionProvider {

	private static final Log log = LogFactory
			.getLog(DruidConnectionProvider.class);

	protected static Configuration conf = BaseConfiguration.create();

	private final static String MIN_POOL_SIZE = "minPoolSize";
	private final static String MAX_POOL_SIZE = "maxPoolSize";
	private final static String INITIAL_POOL_SIZE = "initialPoolSize";
	private final static String MAX_WAIT = "maxWait";
	private final static String MAX_STATEMENTS = "maxStatements";
	private final static String ACQUIRE_INCREMENT = "acquireIncrement";
	private final static String TIMEBETWEENEVICTIONRUNS = "idleConnectionTestPeriod";

	private volatile DruidDataSource ds;
	private volatile Integer isolation;
	private volatile boolean autocommit;

	public void close() {
		try {
			ds.close();
		} catch (Exception sqle) {
			log.warn("could not destroy Druid connection pool", sqle);
		}
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	public void configure(Properties props) {
		Properties properties = new Properties();
		properties.putAll(props);

		for (Iterator<Object> ii = props.keySet().iterator(); ii.hasNext();) {
			String key = (String) ii.next();
			if (key.startsWith("druid.")) {
				String newKey = key.substring(6);
				properties.put(newKey, props.get(key));
			}
		}
		
		log.debug("jdbc properties:"+properties);

		String jdbcDriverClass = properties.getProperty("jdbc.driver");
		String jdbcUrl = properties.getProperty("jdbc.url");

		log.info("Druid using driver: " + jdbcDriverClass + " at URL: "
				+ jdbcUrl);

		autocommit = PropertiesHelper.getBoolean("jdbc.autocommit", properties);
		log.info("autocommit mode: " + autocommit);

		if (jdbcDriverClass == null) {
			log.warn("No JDBC Driver class was specified by property jdbc.driver");
		} else {
			try {
				Class.forName(jdbcDriverClass);
			} catch (ClassNotFoundException cnfe) {
				try {
					ReflectUtils.instantiate(jdbcDriverClass);
				} catch (Exception e) {
					String msg = "JDBC Driver class not found: "
							+ jdbcDriverClass;
					log.error(msg, e);
					throw new RuntimeException(msg, e);
				}
			}
		}

		try {
			Integer minPoolSize = PropertiesHelper.getInteger(MIN_POOL_SIZE,
					properties);
			Integer maxPoolSize = PropertiesHelper.getInteger(MAX_POOL_SIZE,
					properties);
			Integer maxStatements = PropertiesHelper.getInteger(MAX_STATEMENTS,
					properties);
			Integer acquireIncrement = PropertiesHelper.getInteger(
					ACQUIRE_INCREMENT, properties);
			Integer timeBetweenEvictionRuns = PropertiesHelper.getInteger(
					TIMEBETWEENEVICTIONRUNS, properties);

			Integer maxWait = PropertiesHelper.getInteger(MAX_WAIT, properties);

			String validationQuery = properties.getProperty("validationQuery");

			Integer initialPoolSize = PropertiesHelper.getInteger(
					INITIAL_POOL_SIZE, properties);
			if (initialPoolSize == null && minPoolSize != null) {
				properties.put(INITIAL_POOL_SIZE, String.valueOf(minPoolSize)
						.trim());
			}

			if (initialPoolSize == null) {
				initialPoolSize = 1;
			}

			if (minPoolSize == null) {
				minPoolSize = 1;
			}

			if (maxPoolSize == null) {
				maxPoolSize = 50;
			}

			if (acquireIncrement == null) {
				acquireIncrement = 1;
			}

			if (timeBetweenEvictionRuns == null) {
				timeBetweenEvictionRuns = 60;
			}

			if (maxWait == null) {
				maxWait = 60;
			}

			ds = new DruidDataSource();

			DruidDataSourceFactory.config(ds, properties);
			ds.setConnectProperties(properties);

			ds.setInitialSize(initialPoolSize);
			ds.setMinIdle(minPoolSize);
			ds.setMaxActive(maxPoolSize);
			ds.setMaxWait(maxWait * 1000L);

			ds.setTestOnReturn(false);
			ds.setTestOnBorrow(false);

			ds.setRemoveAbandoned(true);// 对于长时间不使用的连接强制关闭
			ds.setRemoveAbandonedTimeout(600);// 超过10分钟开始关闭空闲连接
			ds.setLogAbandoned(true);// 将当前关闭动作记录到日志

			if (StringUtils.isNotEmpty(validationQuery)) {
				log.debug("validationQuery:" + validationQuery);
				ds.setValidationQuery(validationQuery);
				ds.setTestWhileIdle(true);// 保证连接池内部定时检测连接的可用性，不可用的连接会被抛弃或者重建
			} else {
				ds.setTestWhileIdle(false);
			}

			ds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRuns * 1000L);// 间隔多久才进行一次检测，检测需要关闭的空闲连接

			ds.setMinEvictableIdleTimeMillis(300 * 1000L);// 配置一个连接在池中最小生存的时间，单位是毫秒

			if (maxStatements != null) {
				ds.setPoolPreparedStatements(true);
				ds.setMaxOpenPreparedStatements(maxStatements);
				ds.setMaxPoolPreparedStatementPerConnectionSize(200);
			}

			ds.setUrl(jdbcUrl);
			ds.setDriverClassName(jdbcDriverClass);

			String dbUser = properties.getProperty("jdbc.user");
			String dbPassword = properties.getProperty("jdbc.password");

			if (dbUser == null) {
				dbUser = "";
			}

			if (dbPassword == null) {
				dbPassword = "";
			}

			ds.setUsername(dbUser);
			ds.setPassword(dbPassword);
			ds.init();
		} catch (Exception e) {
			log.error("could not instantiate Druid connection pool", e);
			throw new RuntimeException(
					"Could not instantiate Druid connection pool", e);
		}

		String i = props.getProperty("jdbc.isolation");
		if (i == null) {
			isolation = null;
		} else {
			isolation = new Integer(i);
		}

	}

	public Connection getConnection() throws SQLException {
		Connection connection = null;
		int count = 0;
		while (count < conf.getInt("jdbc.connection.retryCount", 10)) {
			try {
				connection = ds.getConnection();
				if (connection != null) {
					if (isolation != null) {
						connection
								.setTransactionIsolation(isolation.intValue());
					}
					if (connection.getAutoCommit() != autocommit) {
						connection.setAutoCommit(autocommit);
					}
					log.debug("druid connection: " + connection.toString());
					return connection;
				} else {
					count++;
					try {
						Thread.sleep(conf.getInt("jdbc.connection.retryTimeMs",
								500));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException ex) {
				count++;
				try {
					Thread.sleep(conf
							.getInt("jdbc.connection.retryTimeMs", 500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (count >= conf.getInt("jdbc.connection.retryCount", 10)) {
					ex.printStackTrace();
					throw ex;
				}
			}
		}
		return connection;
	}

	public DataSource getDataSource() {
		return ds;
	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

}
