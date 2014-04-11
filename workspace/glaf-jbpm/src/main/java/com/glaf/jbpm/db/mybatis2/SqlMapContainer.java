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

package com.glaf.jbpm.db.mybatis2;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.util.LogUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.context.JbpmBeanFactory;

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
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null
					&& jbpmContext.getConnection() != null) {
				getEntityDAO().setConnection(jbpmContext.getConnection());

				if (StringUtils.equalsIgnoreCase("insert", operation)) {
					getEntityDAO().insert(statementId, params);
				} else if (StringUtils.equalsIgnoreCase("update", operation)) {
					getEntityDAO().update(statementId, params);
				} else if (StringUtils.equalsIgnoreCase("delete", operation)) {
					getEntityDAO().delete(statementId, params);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
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
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null
					&& jbpmContext.getConnection() != null) {
				getEntityDAO().setConnection(jbpmContext.getConnection());
				return getEntityDAO().getList(statementId, parameterObject);
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	public EntityDAO getEntityDAO() {
		if (entityDAO == null) {
			entityDAO = (EntityDAO) JbpmBeanFactory.getBean("sqlMapClientDAO");
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
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext.getSession() != null
					&& jbpmContext.getConnection() != null) {
				getEntityDAO().setConnection(jbpmContext.getConnection());
				return getEntityDAO().getSingleObject(statementId,
						parameterObject);
			}
			return null;
		} catch (Exception ex) {
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

}