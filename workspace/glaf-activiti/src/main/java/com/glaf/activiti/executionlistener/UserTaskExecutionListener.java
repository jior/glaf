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

import java.util.List;
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
import com.glaf.core.util.StringTools;

public class UserTaskExecutionListener implements ExecutionListener {

	private static final long serialVersionUID = 1L;
	
	protected final static Log logger = LogFactory
			.getLog(UserTaskExecutionListener.class);

	protected Expression statementId;

	protected Expression roleId;

	protected Expression expression;

	protected Expression outputVar;

	@Override
	public void notify(DelegateExecution execution) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------------UserTaskExecutionListener----------");
		logger.debug("----------------------------------------------------");

		CommandContext commandContext = Context.getCommandContext();

		logger.debug("dbSqlsession:" + commandContext.getDbSqlSession());
		logger.debug("sqlsession:"
				+ commandContext.getDbSqlSession().getSqlSession());

		if (execution != null && outputVar != null) {
			Map<String, Object> paramMap = new java.util.HashMap<String, Object>();
			paramMap.putAll(execution.getVariables());

			String statement = "getMembershipUsers";

			String output = (String) outputVar.getValue(execution);

			if (statementId != null && statementId.getExpressionText() != null) {
				statement = statementId.getExpressionText();
				logger.debug("statementId:" + statement);
			}

			if (roleId != null && roleId.getExpressionText() != null) {
				logger.debug("roleId:" + roleId.getExpressionText());
				paramMap.put("roleId", roleId.getExpressionText());
			}

			if (StringUtils.isNotEmpty(statement)) {
				MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(
						commandContext.getDbSqlSession().getSqlSession());
				List<?> list = entityDAO.getList(statement, paramMap);
				if (list != null && !list.isEmpty()) {
					List<String> users = new java.util.ArrayList<String>();

					for (Object object : list) {
						if (object instanceof org.activiti.engine.identity.User) {
							String actorId = ((org.activiti.engine.identity.User) object)
									.getId();
							if (!users.contains(actorId)) {
								users.add(actorId);
							}
						} else if (object instanceof com.glaf.core.identity.User) {
							String actorId = ((com.glaf.core.identity.User) object)
									.getActorId();
							if (!users.contains(actorId)) {
								users.add(actorId);
							}
						}
					}

					logger.debug("users:" + users);

					if (users.size() > 0) {
						execution.setVariable(output, users);
					}

				} else {
					String expr = (String) expression.getValue(execution);
					if (expr != null) {
						if (expr.startsWith("user(") && expr.endsWith("")) {
							expr = StringTools.replaceIgnoreCase(expr, "user(",
									"");
							expr = StringTools.replaceIgnoreCase(expr, ")", "");
							execution.setVariable(output, expr);
						} else if (expr.startsWith("users(")
								&& expr.endsWith("")) {
							expr = StringTools.replaceIgnoreCase(expr,
									"users(", "");
							expr = StringTools.replaceIgnoreCase(expr, ")", "");
							List<String> candidateUsers = StringTools.split(
									expr, ",");
							execution.setVariable(output, candidateUsers);
						}
					}
				}
			}
		}
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public void setOutputVar(Expression outputVar) {
		this.outputVar = outputVar;
	}

	public void setRoleId(Expression roleId) {
		this.roleId = roleId;
	}

	public void setStatementId(Expression statementId) {
		this.statementId = statementId;
	}

}