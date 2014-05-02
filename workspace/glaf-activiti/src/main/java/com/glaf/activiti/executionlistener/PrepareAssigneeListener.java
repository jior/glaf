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

public class PrepareAssigneeListener implements ExecutionListener {
	
	private static final long serialVersionUID = 1L;

	protected final static Log logger = LogFactory
			.getLog(PrepareAssigneeListener.class);

	protected Expression statementId;

	protected Expression roleId;

	protected Expression outputVar;

	protected Expression outputAssigneeVar;

	protected Expression outputUsersVar;

	protected Expression outputGroupsVar;

	@Override
	public void notify(DelegateExecution execution) {
		logger.debug("----------------------------------------------------");
		logger.debug("----------------PrepareAssigneeListener-------------");
		logger.debug("----------------------------------------------------");

		CommandContext commandContext = Context.getCommandContext();
		logger.debug("dbSqlsession:" + commandContext.getDbSqlSession());
		logger.debug("sqlsession:"
				+ commandContext.getDbSqlSession().getSqlSession());

		if (execution != null) {
			Map<String, Object> paramMap = new java.util.HashMap<String, Object>();

			String statement = null;
			if (statementId != null) {
				statement = (String) statementId.getValue(execution);
				logger.debug("statementId:" + statementId.getValue(execution));
			}
			if (roleId != null) {
				logger.debug("roleId:" + roleId.getValue(execution));
				paramMap.put("roleId", roleId.getValue(execution));
			}

			if (StringUtils.isNotEmpty(statement)) {
				paramMap.putAll(execution.getVariables());
				MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(
						commandContext.getDbSqlSession().getSqlSession());
				List<?> list = entityDAO.getList(statement, paramMap);
				if (list != null && !list.isEmpty()) {
					List<String> users = new java.util.ArrayList<String>();
					List<String> groups = new java.util.ArrayList<String>();
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

						if (outputVar != null) {
							Object name = outputVar.getValue(execution);
							if (name != null) {
								execution.setVariable(name.toString(), users);
							}
						}

						if (outputAssigneeVar != null) {
							Object name = outputAssigneeVar.getValue(execution);
							if (name != null) {
								execution.setVariable(name.toString(),
										users.get(0));
							}
						}

						if (outputUsersVar != null) {
							Object name = outputUsersVar.getValue(execution);
							if (name != null) {
								execution.setVariable(name.toString(), users);
							}
						}
					}

					if (groups.size() > 0) {
						if (outputGroupsVar != null) {
							Object name = outputGroupsVar.getValue(execution);
							if (name != null) {
								execution.setVariable(name.toString(), groups);
							}
						}
					}
				}
			}
		}
	}

	public void setOutputVar(Expression outputVar) {
		this.outputVar = outputVar;
	}

	public void setOutputAssigneeVar(Expression outputAssigneeVar) {
		this.outputAssigneeVar = outputAssigneeVar;
	}

	public void setOutputGroupsVar(Expression outputGroupsVar) {
		this.outputGroupsVar = outputGroupsVar;
	}

	public void setOutputUsersVar(Expression outputUsersVar) {
		this.outputUsersVar = outputUsersVar;
	}

	public void setRoleId(Expression roleId) {
		this.roleId = roleId;
	}

	public void setStatementId(Expression statementId) {
		this.statementId = statementId;
	}

}