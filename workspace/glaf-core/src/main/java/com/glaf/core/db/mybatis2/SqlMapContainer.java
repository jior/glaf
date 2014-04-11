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

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.LogUtils;

public class SqlMapContainer {
	protected final static Log logger = LogFactory
			.getLog(SqlMapContainer.class);

	private static SqlMapContainer container = new SqlMapContainer();

	public final static SqlMapContainer getContainer() {
		return container;
	}

	private EntityDAO entityDAO;

	private SqlMapContainer() {

	}

	public void execute(Connection connection, String statementId,
			String operation, Map<String, Object> params) throws Exception {
		if (LogUtils.isDebug()) {
			logger.debug("execute sqlmap:" + statementId);
			logger.debug("params:" + params);
		}
		getEntityDAO().setConnection(connection);
		if (StringUtils.equalsIgnoreCase("insert", operation)) {
			getEntityDAO().insert(statementId, params);
		} else if (StringUtils.equalsIgnoreCase("update", operation)) {
			getEntityDAO().update(statementId, params);
		} else if (StringUtils.equalsIgnoreCase("delete", operation)) {
			getEntityDAO().delete(statementId, params);
		}
	}

	public void execute(String statementId, String operation,
			Map<String, Object> params) throws Exception {
		if (LogUtils.isDebug()) {
			logger.debug("execute sqlmap:" + statementId);
			logger.debug("params:" + params);
		}
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection();
			getEntityDAO().setConnection(connection);
			if (StringUtils.equalsIgnoreCase("insert", operation)) {
				getEntityDAO().insert(statementId, params);
			} else if (StringUtils.equalsIgnoreCase("update", operation)) {
				getEntityDAO().update(statementId, params);
			} else if (StringUtils.equalsIgnoreCase("delete", operation)) {
				getEntityDAO().delete(statementId, params);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	public void execute(String dataSourceName, String statementId,
			String operation, Map<String, Object> params) throws Exception {
		if (LogUtils.isDebug()) {
			logger.debug("execute sqlmap:" + statementId);
			logger.debug("params:" + params);
		}
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection(dataSourceName);
			getEntityDAO().setConnection(connection);
			if (StringUtils.equalsIgnoreCase("insert", operation)) {
				getEntityDAO().insert(statementId, params);
			} else if (StringUtils.equalsIgnoreCase("update", operation)) {
				getEntityDAO().update(statementId, params);
			} else if (StringUtils.equalsIgnoreCase("delete", operation)) {
				getEntityDAO().delete(statementId, params);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	public List<Object> getList(Connection connection, String statementId,
			Object parameterObject) {
		if (LogUtils.isDebug()) {
			logger.debug("execute sqlmap:" + statementId);
			logger.debug("parameterObject:" + parameterObject);
		}
		getEntityDAO().setConnection(connection);
		return getEntityDAO().getList(statementId, parameterObject);
	}

	public List<Object> getList(String statementId, Object parameterObject) {
		if (LogUtils.isDebug()) {
			logger.debug("execute sqlmap:" + statementId);
			logger.debug("parameterObject:" + parameterObject);
		}
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection();
			getEntityDAO().setConnection(connection);
			return getEntityDAO().getList(statementId, parameterObject);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	public List<Object> getList(String dataSourceName, String statementId,
			Object parameterObject) {
		if (LogUtils.isDebug()) {
			logger.debug("execute sqlmap:" + statementId);
			logger.debug("parameterObject:" + parameterObject);
		}
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection(dataSourceName);
			getEntityDAO().setConnection(connection);
			return getEntityDAO().getList(statementId, parameterObject);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	public EntityDAO getEntityDAO() {
		if (entityDAO == null) {
			entityDAO = (EntityDAO) ContextFactory.getBean("sqlMapClientDAO");
		}
		return entityDAO;
	}

	public Object getSingleObject(Connection connection, String statementId,
			Object parameterObject) {
		if (LogUtils.isDebug()) {
			logger.debug("execute sqlmap:" + statementId);
			logger.debug("parameterObject:" + parameterObject);
		}
		getEntityDAO().setConnection(connection);
		return getEntityDAO().getSingleObject(statementId, parameterObject);
	}

	public Object getSingleObject(String statementId, Object parameterObject) {
		if (LogUtils.isDebug()) {
			logger.debug("execute sqlmap:" + statementId);
			logger.debug("parameterObject:" + parameterObject);
		}
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection();
			getEntityDAO().setConnection(connection);
			return getEntityDAO().getSingleObject(statementId, parameterObject);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	public Object getSingleObject(String dataSourceName, String statementId,
			Object parameterObject) {
		if (LogUtils.isDebug()) {
			logger.debug("execute sqlmap:" + statementId);
			logger.debug("parameterObject:" + parameterObject);
		}
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection(dataSourceName);
			getEntityDAO().setConnection(connection);
			return getEntityDAO().getSingleObject(statementId, parameterObject);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

}
