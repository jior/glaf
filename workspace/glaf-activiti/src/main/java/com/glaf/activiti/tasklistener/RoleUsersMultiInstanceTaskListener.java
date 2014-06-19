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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.activiti.mail.MailBean;
import com.glaf.activiti.util.ExecutionUtils;
import com.glaf.core.dao.MyBatisEntityDAO;
import com.glaf.core.util.StringTools;

public class RoleUsersMultiInstanceTaskListener implements TaskListener {
	private static final long serialVersionUID = 1L;

	protected final static Log logger = LogFactory
			.getLog(RoleUsersMultiInstanceTaskListener.class);

	protected Expression statementId;

	protected Expression roleId;

	protected Expression deptId;

	protected Expression outputVar;

	protected Expression userIds;

	protected Expression message;

	protected Expression sql;

	protected Expression sendMail;

	protected Expression subject;

	protected Expression content;

	protected Expression taskName;

	protected Expression taskContent;

	protected Expression templateId;

	public void notify(DelegateTask delegateTask) {
		logger.debug("----------------------------------------------------");
		logger.debug("--------------RoleUsersMultiInstanceTaskListener----");
		logger.debug("----------------------------------------------------");
		CommandContext commandContext = Context.getCommandContext();
		DelegateExecution execution = delegateTask.getExecution();
		logger.debug("task name:" + delegateTask.getName());

		if (execution != null) {
			Map<String, Object> paramMap = new java.util.HashMap<String, Object>();

			String statement = null;
			if (statementId != null) {
				statement = statementId.getExpressionText();
				logger.debug("statementId:" + statement);
			}
			if (roleId != null) {
				logger.debug("roleId:" + roleId.getExpressionText());
				paramMap.put("roleId", roleId.getExpressionText());
			}
			if (deptId != null) {
				logger.debug("deptId:"
						+ deptId.getValue(delegateTask.getExecution()));
				paramMap.put("deptId",
						deptId.getValue(delegateTask.getExecution()));
			}
			if (message != null) {
				logger.debug("message:"
						+ message.getValue(delegateTask.getExecution()));
			}
			if (StringUtils.isEmpty(statement)) {
				statement = "getMembershipUsers";
			}

			Collection<String> assigneeList = new java.util.ArrayList<String>();

			if (!paramMap.isEmpty()) {
				paramMap.putAll(execution.getVariables());
				MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(
						commandContext.getDbSqlSession().getSqlSession());
				List<?> list = entityDAO.getList(statement, paramMap);
				if (list != null && !list.isEmpty()) {

					for (Object object : list) {
						if (object instanceof org.activiti.engine.identity.User) {
							String actorId = ((org.activiti.engine.identity.User) object)
									.getId();
							if (!assigneeList.contains(actorId)) {
								assigneeList.add(actorId);
							}
						} else if (object instanceof com.glaf.core.identity.User) {
							String actorId = ((com.glaf.core.identity.User) object)
									.getActorId();
							if (!assigneeList.contains(actorId)) {
								assigneeList.add(actorId);
							}
						}
					}
					logger.debug("assigneeList:" + assigneeList);

					if (assigneeList.size() > 0) {
						if (outputVar != null) {
							String output = (String) outputVar
									.getValue(delegateTask.getExecution());
							execution.setVariable(output, assigneeList);
						} else {
							execution.setVariable("assigneeList", assigneeList);
						}
					}
					if (sendMail != null
							&& StringUtils.equals(sendMail.getExpressionText(),
									"true")) {
						MailBean bean = new MailBean();
						bean.setSubject(subject);
						bean.setContent(content);
						bean.setTaskContent(taskContent);
						bean.setTaskName(taskName);
						bean.setTemplateId(templateId);
						bean.sendMail(execution, assigneeList);
					}
				}
			}

			if (assigneeList.isEmpty()) {
				if (userIds != null) {
					String tmpUsers = userIds.getExpressionText();
					assigneeList = StringTools.split(tmpUsers);
					logger.debug("assigneeList:" + assigneeList);
					if (assigneeList.size() > 0) {
						if (outputVar != null) {
							String output = (String) outputVar
									.getValue(execution);
							execution.setVariable(output, assigneeList);
						} else {
							execution.setVariable("assigneeList", assigneeList);
						}
						if (sendMail != null
								&& StringUtils.equals(
										sendMail.getExpressionText(), "true")) {
							MailBean bean = new MailBean();
							bean.setSubject(subject);
							bean.setContent(content);
							bean.setTaskContent(taskContent);
							bean.setTaskName(taskName);
							bean.setTemplateId(templateId);
							bean.sendMail(execution, assigneeList);
						}
					}
				}
			}
		}
		if (sql != null) {
			ExecutionUtils.executeSqlUpdate(execution, sql);
		}
	}

	public void setContent(Expression content) {
		this.content = content;
	}

	public void setDeptId(Expression deptId) {
		this.deptId = deptId;
	}

	public void setMessage(Expression message) {
		this.message = message;
	}

	public void setOutputVar(Expression outputVar) {
		this.outputVar = outputVar;
	}

	public void setRoleId(Expression roleId) {
		this.roleId = roleId;
	}

	public void setSendMail(Expression sendMail) {
		this.sendMail = sendMail;
	}

	public void setSql(Expression sql) {
		this.sql = sql;
	}

	public void setStatementId(Expression statementId) {
		this.statementId = statementId;
	}

	public void setSubject(Expression subject) {
		this.subject = subject;
	}

	public void setTaskContent(Expression taskContent) {
		this.taskContent = taskContent;
	}

	public void setTaskName(Expression taskName) {
		this.taskName = taskName;
	}

	public void setTemplateId(Expression templateId) {
		this.templateId = templateId;
	}

	public void setUserIds(Expression userIds) {
		this.userIds = userIds;
	}

}