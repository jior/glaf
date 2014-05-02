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

package com.glaf.activiti.executionlistener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.dao.MyBatisEntityDAO;
import com.glaf.core.entity.SqlExecutor;

public class MyBatisExecutionListener implements ExecutionListener {
	private static final long serialVersionUID = 1L;
	
	protected final static Log logger = LogFactory
			.getLog(MyBatisExecutionListener.class);

	protected Expression statementId;

	protected Expression operation;

	protected Expression status;

	protected Expression wfStatus;

	protected Expression message;

	public void notify(DelegateExecution execution) throws Exception {
		logger.debug("----------------------------------------------------");
		logger.debug("------------------MyBatis3ExecutionListener---------");
		logger.debug("----------------------------------------------------");
		CommandContext commandContext = Context.getCommandContext();
		logger.debug("dbSqlsession:" + commandContext.getDbSqlSession());
		logger.debug("sqlsession:"
				+ commandContext.getDbSqlSession().getSqlSession());
		if (execution != null) {
			Map<String, Object> paramMap = new java.util.HashMap<String, Object>();
			paramMap.putAll(execution.getVariables());
			String statement = null;
			String op = null;
			if (statementId != null) {
				statement = statementId.getExpressionText();
				logger.debug("statementId:" + statement);
			}

			if (operation != null) {
				op = operation.getExpressionText();
				logger.debug("operation:" + op);
			}

			if (status != null) {
				logger.debug("status:" + status.getValue(execution));
				paramMap.put("status", status.getValue(execution));
			}

			if (wfStatus != null) {
				logger.debug("wfStatus:" + wfStatus.getValue(execution));
				paramMap.put("wfStatus", wfStatus.getValue(execution));
			}

			if (message != null) {
				logger.debug("message:" + message);
				logger.debug("message:" + message.getValue(execution));
			}

			if (StringUtils.isNotEmpty(statement) && StringUtils.isNotEmpty(op)) {
				MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(
						commandContext.getDbSqlSession().getSqlSession());
				SqlExecutor sqlExecutor = new SqlExecutor();
				sqlExecutor.setOperation(op);
				sqlExecutor.setStatementId(statement);
				sqlExecutor.setParameter(paramMap);
				entityDAO.execute(sqlExecutor);
			}
		}
	}

	public void setMessage(Expression message) {
		this.message = message;
	}

	public void setStatementId(Expression statementId) {
		this.statementId = statementId;
	}

	public void setStatus(Expression status) {
		this.status = status;
	}

	public void setWfStatus(Expression wfStatus) {
		this.wfStatus = wfStatus;
	}

}