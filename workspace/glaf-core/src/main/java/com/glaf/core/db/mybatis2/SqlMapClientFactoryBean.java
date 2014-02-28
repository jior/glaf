/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.db.mybatis2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.ObjectUtils;

import com.glaf.core.util.ReflectUtils;
import com.ibatis.common.xml.NodeletException;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapConfigParser;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapParser;
import com.ibatis.sqlmap.engine.builder.xml.XmlParserState;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

/**
 * {@link org.springframework.beans.factory.FactoryBean} that creates an iBATIS
 * {@link com.ibatis.sqlmap.client.SqlMapClient}. This is the usual way to set
 * up a shared iBATIS SqlMapClient in a Spring application context; the
 * SqlMapClient can then be passed to iBATIS-based DAOs via dependency
 * injection.
 * 
 * <p>
 * Either
 * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager} or
 * {@link org.springframework.transaction.jta.JtaTransactionManager} can be used
 * for transaction demarcation in combination with a SqlMapClient, with JTA only
 * necessary for transactions which span multiple databases.
 * 
 * <p>
 * Allows for specifying a DataSource at the SqlMapClient level. This is
 * preferable to per-DAO DataSource references, as it allows for lazy loading
 * and avoids repeated DataSource references in every DAO.
 * 
 * <p>
 * <b>Note:</b> As of Spring 2.5.5, this class (finally) requires iBATIS 2.3 or
 * higher. The new "mappingLocations" feature requires iBATIS 2.3.2.
 * 
 * @author Juergen Hoeller
 * @since 24.02.2004
 * @see #setConfigLocation
 * @see #setDataSource
 * @see SqlMapClientTemplate#setSqlMapClient
 * @see SqlMapClientTemplate#setDataSource
 */

public class SqlMapClientFactoryBean implements FactoryBean<SqlMapClient>,
		InitializingBean {

	private static final ThreadLocal<LobHandler> configTimeLobHandlerHolder = new ThreadLocal<LobHandler>();

	protected static final Log logger = LogFactory
			.getLog(SqlMapClientFactoryBean.class);

	/**
	 * Return the LobHandler for the currently configured iBATIS SqlMapClient,
	 * to be used by TypeHandler implementations like ClobStringTypeHandler.
	 * <p>
	 * This instance will be set before initialization of the corresponding
	 * SqlMapClient, and reset immediately afterwards. It is thus only available
	 * during configuration.
	 * 
	 * @see #setLobHandler
	 * @see com.glaf.core.db.mybatis2.support.ClobStringTypeHandler
	 * @see com.glaf.core.db.mybatis2.support.BlobByteArrayTypeHandler
	 * @see com.glaf.core.db.mybatis2.support.BlobSerializableTypeHandler
	 */
	public static LobHandler getConfigTimeLobHandler() {
		return configTimeLobHandlerHolder.get();
	}

	private Resource[] configLocations;

	private Resource[] mappingLocations;

	private Properties sqlMapClientProperties;

	private SqlExecutor sqlExecutor;

	private LobHandler lobHandler;

	private SqlMapClient sqlMapClient;

	public SqlMapClientFactoryBean() {

	}

	/**
	 * Set the location of the iBATIS SqlMapClient config file. A typical value
	 * is "WEB-INF/sql-map-config.xml".
	 * 
	 * @see #setConfigLocations
	 */
	public void setConfigLocation(Resource configLocation) {
		this.configLocations = (configLocation != null ? new Resource[] { configLocation }
				: null);
	}

	/**
	 * Set multiple locations of iBATIS SqlMapClient config files that are going
	 * to be merged into one unified configuration at runtime.
	 */
	public void setConfigLocations(Resource[] configLocations) {
		this.configLocations = configLocations;
	}

	/**
	 * Set locations of iBATIS sql-map mapping files that are going to be merged
	 * into the SqlMapClient configuration at runtime.
	 * <p>
	 * This is an alternative to specifying "&lt;sqlMap&gt;" entries in a
	 * sql-map-client config file. This property being based on Spring's
	 * resource abstraction also allows for specifying resource patterns here:
	 * e.g. "/myApp/*-map.xml".
	 * <p>
	 * Note that this feature requires iBATIS 2.3.2; it will not work with any
	 * previous iBATIS version.
	 */
	public void setMappingLocations(Resource[] mappingLocations) {
		this.mappingLocations = mappingLocations;
	}

	/**
	 * Set optional properties to be passed into the SqlMapClientBuilder, as
	 * alternative to a {@code &lt;properties&gt;} tag in the sql-map-config.xml
	 * file. Will be used to resolve placeholders in the config file.
	 * 
	 * @see #setConfigLocation
	 * @see com.ibatis.sqlmap.client.SqlMapClientBuilder#buildSqlMapClient(java.io.InputStream,
	 *      java.util.Properties)
	 */
	public void setSqlMapClientProperties(Properties sqlMapClientProperties) {
		this.sqlMapClientProperties = sqlMapClientProperties;
	}

	/**
	 * Set the LobHandler to be used by the SqlMapClient. Will be exposed at
	 * config time for TypeHandler implementations.
	 * 
	 * @see #getConfigTimeLobHandler
	 * @see com.ibatis.sqlmap.engine.type.TypeHandler
	 * @see com.glaf.core.db.mybatis2.support.ClobStringTypeHandler
	 * @see com.glaf.core.db.mybatis2.support.BlobByteArrayTypeHandler
	 * @see com.glaf.core.db.mybatis2.support.BlobSerializableTypeHandler
	 */
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public void afterPropertiesSet() throws Exception {
		if (this.lobHandler != null) {
			// Make given LobHandler available for SqlMapClient configuration.
			// Do early because because mapping resource might refer to custom
			// types.
			configTimeLobHandlerHolder.set(this.lobHandler);
		}

		try {
			this.sqlMapClient = buildSqlMapClient(this.configLocations,
					this.mappingLocations, this.sqlMapClientProperties);
		}

		finally {
			if (this.lobHandler != null) {
				// Reset LobHandler holder.
				configTimeLobHandlerHolder.remove();
			}
		}

		SqlMapClient c = getObject();
		logger.debug("SqlMapClient impl class:" + c.getClass().getName());
		if (sqlExecutor != null && c instanceof SqlMapClientImpl) {
			SqlMapClientImpl client = (SqlMapClientImpl) c;
			SqlMapExecutorDelegate delegate = client.getDelegate();
			try {
				logger.debug("start inject sqlExecutor ...");
				ReflectUtils
						.setFieldValue(delegate, "sqlExecutor", sqlExecutor);
				logger.debug("sqlExecutor injected.");
				logger.info("[iBATIS] success set ibatis SqlMapClient.sqlExecutor = "
						+ sqlExecutor.getClass().getName());
			} catch (Exception ex) {
				logger.error("[iBATIS] error,cannot set ibatis SqlMapClient.sqlExecutor = "
						+ sqlExecutor.getClass().getName() + " cause:" + ex);
			}
		}
	}

	/**
	 * Build a SqlMapClient instance based on the given standard configuration.
	 * <p>
	 * The default implementation uses the standard iBATIS
	 * {@link SqlMapClientBuilder} API to build a SqlMapClient instance based on
	 * an InputStream (if possible, on iBATIS 2.3 and higher) or on a Reader (on
	 * iBATIS up to version 2.2).
	 * 
	 * @param configLocations
	 *            the config files to load from
	 * @param properties
	 *            the SqlMapClient properties (if any)
	 * @return the SqlMapClient instance (never {@code null})
	 * @throws IOException
	 *             if loading the config file failed
	 * @see com.ibatis.sqlmap.client.SqlMapClientBuilder#buildSqlMapClient
	 */
	protected SqlMapClient buildSqlMapClient(Resource[] configLocations,
			Resource[] mappingLocations, Properties properties)
			throws IOException {

		if (ObjectUtils.isEmpty(configLocations)) {
			throw new IllegalArgumentException(
					"At least 1 'configLocation' entry is required");
		}

		SqlMapClient client = null;
		SqlMapConfigParser configParser = new SqlMapConfigParser();
		for (Resource configLocation : configLocations) {
			InputStream is = configLocation.getInputStream();
			try {
				client = configParser.parse(is, properties);
			} catch (RuntimeException ex) {
				throw new NestedIOException("Failed to parse config resource: "
						+ configLocation, ex.getCause());
			}
		}

		if (mappingLocations != null) {
			SqlMapParser mapParser = SqlMapParserFactory
					.createSqlMapParser(configParser);
			for (Resource mappingLocation : mappingLocations) {
				try {
					mapParser.parse(mappingLocation.getInputStream());
				} catch (NodeletException ex) {
					throw new NestedIOException(
							"Failed to parse mapping resource: "
									+ mappingLocation, ex);
				}
			}
		}

		return client;
	}

	public SqlMapClient getObject() {
		return this.sqlMapClient;
	}

	public Class<? extends SqlMapClient> getObjectType() {
		return (this.sqlMapClient != null ? this.sqlMapClient.getClass()
				: SqlMapClient.class);
	}

	public boolean isSingleton() {
		return true;
	}

	/**
	 * Inner class to avoid hard-coded iBATIS 2.3.2 dependency (XmlParserState
	 * class).
	 */
	private static class SqlMapParserFactory {

		public static SqlMapParser createSqlMapParser(
				SqlMapConfigParser configParser) {
			// Ideally: XmlParserState state = configParser.getState();
			// Should raise an enhancement request with iBATIS...
			XmlParserState state = null;
			try {
				Field stateField = SqlMapConfigParser.class
						.getDeclaredField("state");
				stateField.setAccessible(true);
				state = (XmlParserState) stateField.get(configParser);
			} catch (Exception ex) {
				throw new IllegalStateException(
						"iBATIS 2.3.2 'state' field not found in SqlMapConfigParser class - "
								+ "please upgrade to IBATIS 2.3.2 or higher in order to use the new 'mappingLocations' feature. "
								+ ex);
			}
			return new SqlMapParser(state);
		}
	}

}
