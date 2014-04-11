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

package com.glaf.activiti.tasklistener;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.activiti.util.ExecutionUtils;
import com.glaf.core.dao.MyBatisEntityDAO;
import com.glaf.core.util.StringTools;

public class UserTaskListener implements TaskListener {
 
	private static final long serialVersionUID = 1L;

	protected final static Log logger = LogFactory
			.getLog(UserTaskListener.class);

	protected Expression statementId;

	protected Expression roleId;

	protected Expression expression;

	protected Expression sql;

	@Override
	public void notify(DelegateTask delegateTask) {
		logger.debug("----------------------------------------------------");
		logger.debug("---------------UserTaskListener---------------------");
		logger.debug("----------------------------------------------------");

		CommandContext commandContext = Context.getCommandContext();
		DelegateExecution execution = delegateTask.getExecution();
		logger.debug("dbSqlsession:" + commandContext.getDbSqlSession());
		logger.debug("sqlsession:"
				+ commandContext.getDbSqlSession().getSqlSession());

		if (execution != null) {
			Map<String, Object> paramMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
			paramMap.putAll(execution.getVariables());

			ExecutionEntity executionEntity = commandContext
					.getExecutionEntityManager().findExecutionById(execution.getId());
			String processDefinitionId = executionEntity
					.getProcessDefinitionId();
			ProcessDefinitionEntity processDefinitionEntity = commandContext
					.getProcessDefinitionEntityManager()
					.findProcessDefinitionById(processDefinitionId);
			String processName = processDefinitionEntity.getKey();

			String rid = processName + "_role_"
					+ delegateTask.getTaskDefinitionKey();
			paramMap.put("roleId", rid);

			String statement = "getMembershipUsers";

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
					List<String> users = new java.util.concurrent.CopyOnWriteArrayList<String>();
					List<String> groups = new java.util.concurrent.CopyOnWriteArrayList<String>();
					for (Object object : list) {
						if (object instanceof org.activiti.engine.identity.User) {
							String actorId = ((org.activiti.engine.identity.User) object)
									.getId();
							if (!users.contains(actorId)) {
								users.add(actorId);
							}
						} else if (object instanceof org.activiti.engine.identity.Group) {
							String groupId = ((org.activiti.engine.identity.Group) object)
									.getId();
							if (!groups.contains(groupId)) {
								groups.add(groupId);
							}
						} else if (object instanceof com.glaf.core.identity.User) {
							String actorId = ((com.glaf.core.identity.User) object)
									.getActorId();
							if (!users.contains(actorId)) {
								users.add(actorId);
							}
						} else if (object instanceof com.glaf.core.identity.Role) {
							String groupId = String
									.valueOf(((com.glaf.core.identity.Role) object)
											.getRoleId());
							if (!groups.contains(groupId)) {
								groups.add(groupId);
							}
						}
					}

					logger.debug("users:" + users);
					logger.debug("groups:" + groups);

					if (users.size() > 0) {
						if (users.size() == 1) {
							delegateTask.setAssignee(users.get(0));
						} else {
							for (String userId : users) {
								delegateTask.addCandidateUser(userId);
							}
						}
					}

					if (groups.size() > 0) {
						for (String groupId : groups) {
							delegateTask.addCandidateGroup(groupId);
						}
					}
				} else {
					String expr = (String) expression.getValue(delegateTask
							.getExecution());
					if (expr != null) {
						if (expr.startsWith("user(") && expr.endsWith("")) {
							expr = StringTools.replaceIgnoreCase(expr, "user(",
									"");
							expr = StringTools.replaceIgnoreCase(expr, ")", "");
							delegateTask.setAssignee(expr);
						} else if (expr.startsWith("users(")
								&& expr.endsWith("")) {
							expr = StringTools.replaceIgnoreCase(expr,
									"users(", "");
							expr = StringTools.replaceIgnoreCase(expr, ")", "");
							List<String> candidateUsers = StringTools.split(
									expr, ",");
							delegateTask.addCandidateUsers(candidateUsers);
						} else if (expr.startsWith("group(")
								&& expr.endsWith("")) {
							expr = StringTools.replaceIgnoreCase(expr,
									"group(", "");
							expr = StringTools.replaceIgnoreCase(expr, ")", "");
							delegateTask.addCandidateGroup(expr);
						} else if (expr.startsWith("groups(")
								&& expr.endsWith("")) {
							expr = StringTools.replaceIgnoreCase(expr,
									"groups(", "");
							expr = StringTools.replaceIgnoreCase(expr, ")", "");
							List<String> candidateGroups = StringTools.split(
									expr, ",");
							delegateTask.addCandidateGroups(candidateGroups);
						}
					}
				}
			}
		}

		if (sql != null) {
			ExecutionUtils.executeSqlUpdate(execution, sql);
		}
	}

	public void setSql(Expression sql) {
		this.sql = sql;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public void setRoleId(Expression roleId) {
		this.roleId = roleId;
	}

	public void setStatementId(Expression statementId) {
		this.statementId = statementId;
	}

}