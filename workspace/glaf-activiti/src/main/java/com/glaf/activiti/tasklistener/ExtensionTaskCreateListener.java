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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.glaf.activiti.extension.factory.ExtensionFactory;
import com.glaf.activiti.extension.model.ExtensionEntity;
import com.glaf.activiti.extension.model.ExtensionFieldEntity;
import com.glaf.activiti.extension.service.ActivitiExtensionService;
import com.glaf.activiti.mail.MailBean;
import com.glaf.activiti.util.ThreadHolder;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.util.Constants;
import com.glaf.core.util.IdentityUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.StringTools;

public class ExtensionTaskCreateListener implements TaskListener {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(ExtensionTaskCreateListener.class);

	protected Expression sendMail;

	protected Expression subject;

	protected Expression content;

	protected Expression taskName;

	protected Expression taskContent;

	protected Expression templateId;

	protected List<String> getDeptRoleUsers(DelegateExecution execution,
			SqlSession sqlSession, ExtensionEntity extension) {
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		Map<String, Object> variables = execution.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator<String> iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = iterator.next();
				if (params.get(variableName) == null) {
					Object value = execution.getVariable(variableName);
					params.put(variableName, value);
				}
			}
		}

		Map<String, Object> paramMap = new java.util.HashMap<String, Object>();

		Map<String, ExtensionFieldEntity> fields = extension.getFields();

		if (fields != null && fields.size() > 0) {
			Iterator<String> iter = fields.keySet().iterator();
			while (iter.hasNext()) {
				String name = iter.next();
				String tmp = extension.getFieldValue(name);
				Object value = tmp;
				if (tmp.startsWith("#{") && tmp.endsWith("}")) {
					value = Mvel2ExpressionEvaluator.evaluate(tmp, params);
				} else if (tmp.startsWith("${") && tmp.endsWith("}")) {
					value = Mvel2ExpressionEvaluator.evaluate(tmp, params);
				} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
					tmp = StringTools.replaceIgnoreCase(tmp, "#P{", "");
					tmp = StringTools.replaceIgnoreCase(tmp, "}", "");
					value = Mvel2ExpressionEvaluator.evaluate(tmp, params);
				} else if (tmp.startsWith("$P{") && tmp.endsWith("}")) {
					tmp = StringTools.replaceIgnoreCase(tmp, "#P{", "");
					tmp = StringTools.replaceIgnoreCase(tmp, "}", "");
					value = Mvel2ExpressionEvaluator.evaluate(tmp, params);
				}
				paramMap.put(name, value);
			}
		}

		String statement = extension.getFieldValue("statement");

		if (LogUtils.isDebug()) {
			logger.debug("statement:" + statement);
			logger.debug("paramMap:" + paramMap);
		}

		List<String> actorIds = IdentityUtils.getActorIds(sqlSession,
				statement, paramMap);

		return actorIds;
	}

	protected List<String> getMemberships(DelegateExecution execution,
			SqlSession sqlSession, String processName, String taskDefinitionKey) {
		Map<String, Object> paramMap = new java.util.HashMap<String, Object>();
		Map<String, Object> variables = execution.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator<String> iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = iterator.next();
				if (paramMap.get(variableName) == null) {
					Object value = execution.getVariable(variableName);
					paramMap.put(variableName, value);
				}
			}
		}

		List<String> actorIds = new java.util.ArrayList<String>();
		String roleId = processName + "_" + taskDefinitionKey;

		paramMap.put("roleId", roleId);

		List<String> x_actorIds = IdentityUtils.getActorIds(sqlSession,
				paramMap);

		if (x_actorIds != null && x_actorIds.size() > 0) {
			actorIds.addAll(x_actorIds);
		}

		return actorIds;
	}

	protected List<String> getRuntimeAssign(DelegateExecution execution,
			String processName, String taskDefinitionKey) {
		List<String> actorIds = new java.util.ArrayList<String>();
		String dynamicActors = processName + "_" + taskDefinitionKey;
		String actors = (String) execution.getVariable(dynamicActors);
		if (StringUtils.isEmpty(actors)) {
			actors = (String) execution
					.getVariable(Constants.TM_RUNTIME_ACTORIDS);
		}
		if (StringUtils.isNotEmpty(actors)) {
			StringTokenizer st2 = new StringTokenizer(actors, ",");
			while (st2.hasMoreTokens()) {
				String elem = st2.nextToken();
				if (StringUtils.isNotEmpty(elem)) {
					actorIds.add(elem);
				}
			}
		}

		return actorIds;
	}

	public void notify(DelegateTask delegateTask) {
		logger.debug("-------------------------------------------------------");
		logger.debug("---------------ExtensionTaskCreateListener-------------");
		logger.debug("-------------------------------------------------------");

		ActivitiExtensionService service = ExtensionFactory
				.getActivitiExtensionService();
		CommandContext commandContext = Context.getCommandContext();
		SqlSession sqlSession = commandContext.getDbSqlSession()
				.getSqlSession();
		service.setSqlSession(sqlSession);
		DelegateExecution execution = delegateTask.getExecution();
		ExecutionEntity executionEntity = commandContext
				.getExecutionEntityManager().findExecutionById(
						execution.getId());
		String processDefinitionId = executionEntity.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinitionEntity = commandContext
				.getProcessDefinitionEntityManager().findProcessDefinitionById(
						processDefinitionId);
		String processName = processDefinitionEntity.getKey();
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		String processInstanceId = delegateTask.getProcessInstanceId();

		HistoricProcessInstanceEntity historicProcessInstanceEntity = commandContext
				.getHistoricProcessInstanceEntityManager()
				.findHistoricProcessInstance(processInstanceId);
		String startUserId = historicProcessInstanceEntity.getStartUserId();

		List<String> actorIds = null;
		ExtensionEntity extension = null;

		int taskMgmtType = Constants.TM_DEPT_ROLE_USER_TYPE;
		extension = service.getExtensionTask(processName,
				delegateTask.getTaskDefinitionKey());

		List<String> defActorIds = null;
		if (extension != null) {
			ExtensionFieldEntity taskMgmtTypeField = extension
					.getField("taskMgmtType");
			if (taskMgmtTypeField != null
					&& StringUtils.isNumeric(taskMgmtTypeField.getValue())) {
				taskMgmtType = Integer.parseInt(taskMgmtTypeField.getValue());
				logger.debug(taskMgmtType);
			}
			ExtensionFieldEntity actorIdsField = extension.getField("actorIds");
			if (actorIdsField != null) {
				String val = actorIdsField.getValue();
				defActorIds = StringTools.split(val, ",");
			}
		}

		if (extension == null) {
			extension = ThreadHolder.getExtension();
		}

		if (extension == null) {
			extension = (ExtensionEntity) execution
					.getVariableLocal("extensionTask");
		}

		logger.debug("extension:" + extension);
		logger.debug("taskMgmtType:" + taskMgmtType);

		switch (taskMgmtType) {
		// 运行时指定
		case Constants.TM_RUNTIME_TYPE:
			actorIds = this.getRuntimeAssign(execution, processName,
					taskDefinitionKey);
			break;
		// 流程启动者
		case Constants.TM_PROCESS_STARTER_TYPE:
			actorIds = new java.util.ArrayList<String>();
			actorIds.add(startUserId);
			break;
		// 流程启动者的直接上级
		case Constants.TM_PROCESS_STARTER_LEADER_TYPE:
			actorIds = IdentityUtils.getLeaderIds(sqlSession, startUserId);
			logger.debug("流程启动者的直接上级:" + actorIds);
			break;
		// 直接指定用户
		case Constants.TM_USER_TYPE:
			actorIds = defActorIds;
			break;
		// 部门角色
		case Constants.TM_DEPT_ROLE_USER_TYPE:
			if (extension != null) {
				actorIds = this.getDeptRoleUsers(execution, sqlSession,
						extension);
			}
			break;
		// 默认为控制台配置
		default:
			break;
		}

		logger.debug("actorIds:" + actorIds);

		if (actorIds == null || actorIds.size() == 0) {
			actorIds = this.getMemberships(execution, sqlSession, processName,
					taskDefinitionKey);
		}

		if (actorIds != null && actorIds.size() > 0) {
			if (actorIds.size() > 0) {
				if (actorIds.size() > 1) {
					delegateTask.setAssignee(null);
					delegateTask.addCandidateUsers(actorIds);
				} else {
					delegateTask.setAssignee(actorIds.get(0));
				}
			}
			if (sendMail != null
					&& StringUtils.equals(sendMail.getExpressionText(), "true")) {
				MailBean bean = new MailBean();
				bean.setSubject(subject);
				bean.setContent(content);
				bean.setTaskContent(taskContent);
				bean.setTaskName(taskName);
				bean.setTemplateId(templateId);
				bean.sendMail(execution, actorIds);
			}
		} else {

		}
	}

	public void setContent(Expression content) {
		this.content = content;
	}

	public void setSendMail(Expression sendMail) {
		this.sendMail = sendMail;
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

}