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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.el.ExpressionTools;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.todo.config.TodoConfig;
import com.glaf.core.util.Constants;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailThread;
import com.glaf.mail.util.MailTools;
import com.glaf.template.Template;
import com.glaf.template.TemplateContainer;
import com.glaf.template.util.TemplateUtils;

public class MailExecutionListener implements ExecutionListener {
	private static final Log logger = LogFactory
			.getLog(MailExecutionListener.class);

	private static final long serialVersionUID = 1L;

	private Expression mailTo;

	private Expression assigneeVar;

	private Expression subject;

	private Expression content;

	private Expression taskName;

	private Expression taskContent;

	private Expression templateId;

	public MailExecutionListener() {

	}

	@SuppressWarnings("rawtypes")
	public void notify(DelegateExecution execution) throws Exception {
		logger.debug("------------------------------------------------------");
		logger.debug("-------------------MailExecutionListener--------------");
		logger.debug("------------------------------------------------------");

		Map<String, Object> context = new HashMap<String, Object>();

		Map<String, Object> variables = execution.getVariables();
		if (variables != null && variables.size() > 0) {
			Set<Entry<String, Object>> entrySet = variables.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (name != null && value != null
						&& variables.get(name) == null) {
					context.put(name, value);
				}
			}
		}

		CommandContext commandContext = Context.getCommandContext();

		String processDefinitionId = execution.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinition = commandContext
				.getProcessDefinitionEntityManager().findProcessDefinitionById(
						processDefinitionId);

		context.put("x_process_key", processDefinition.getKey());
		context.put("x_process_name", processDefinition.getName());
		context.put("x_process_title", processDefinition.getDescription());

		String title = null;
		String body = null;

		if (subject != null) {
			title = (String) subject.getValue(execution);
		}

		if (content != null) {
			body = (String) content.getValue(execution);
		}

		if (templateId != null
				&& StringUtils.isNotEmpty(templateId.getExpressionText())) {
			Template template = TemplateContainer.getContainer().getTemplate(
					templateId.getExpressionText());
			if (template != null) {
				logger.debug(template);
				if (subject == null
						|| StringUtils.isEmpty(subject.getExpressionText())) {
					title = template.getTitle();
				}
				if (content == null
						|| StringUtils.isEmpty(content.getExpressionText())) {
					body = template.getContent();
				}
			}
		}

		if (title != null) {
			title = ExpressionTools.evaluate(title, context);
		}

		logger.debug("subject:" + title);
		logger.debug("content:" + body);

		if (StringUtils.isEmpty(title) || StringUtils.isEmpty(body)) {
			return;
		}

		context.put("task_subject", title);

		if (taskName != null) {
			context.put("taskName", taskName.getExpressionText());
			context.put("task_name", taskName.getValue(execution));
		}
		if (taskContent != null) {
			context.put("taskContent", taskContent.getExpressionText());
			context.put("task_content", taskContent.getValue(execution));
		}

		String processInstanceId = execution.getProcessInstanceId();
		HistoricProcessInstanceEntity historicProcessInstanceEntity = commandContext
				.getHistoricProcessInstanceEntityManager()
				.findHistoricProcessInstance(processInstanceId);
		String startUserId = historicProcessInstanceEntity.getStartUserId();

		String value = null;

		if (mailTo.getExpressionText().equals("process_starter")) {
			value = startUserId;
		} else if (mailTo.getExpressionText().equals("assigneeList")) {
			Object variable = execution.getVariable(assigneeVar
					.getExpressionText());
			if (variable != null) {
				// logger.debug(variable.getClass().getSimpleName());
				if (variable instanceof String) {
					value = variable.toString();
				} else if (variable instanceof Collection) {
					Collection assigneeList = (Collection) variable;
					value = StringTools.collectionToString(assigneeList);
				}
			}
		} else if (mailTo.getExpressionText().equals("x_running")) {
			List<Task> tasks = execution.getEngineServices().getTaskService()
					.createTaskQuery().processInstanceId(processInstanceId)
					.list();
			if (tasks != null && !tasks.isEmpty()) {
				logger.debug("tasks:" + tasks);
				StringBuffer buffer = new StringBuffer();
				for (Task task : tasks) {
					if (taskName != null) {
						if (StringUtils.equals(taskName.getExpressionText(),
								task.getTaskDefinitionKey())) {
							buffer.append(task.getAssignee()).append(",");
						}
					} else {
						buffer.append(task.getAssignee()).append(",");
					}
				}
				value = buffer.toString();
			}
		} else if (mailTo.getExpressionText().equals("x_finished")) {
			List<HistoricTaskInstance> tasks = execution.getEngineServices()
					.getHistoryService().createHistoricTaskInstanceQuery()
					.processInstanceId(processInstanceId).list();
			if (tasks != null && !tasks.isEmpty()) {
				StringBuffer buffer = new StringBuffer();
				for (HistoricTaskInstance task : tasks) {
					if (taskName != null) {
						if (StringUtils.equals(taskName.getExpressionText(),
								task.getTaskDefinitionKey())) {
							buffer.append(task.getAssignee()).append(",");
						}
					} else {
						buffer.append(task.getAssignee()).append(",");
					}
				}
				value = buffer.toString();
			}
		} else if (mailTo.getExpressionText().startsWith("#P{")
				&& mailTo.getExpressionText().endsWith("}")) {
			String tmp = StringTools.replaceIgnoreCase(
					mailTo.getExpressionText(), "#P{", "");
			tmp = StringTools.replaceIgnoreCase(tmp, "}", "");
			value = (String) execution.getVariable(tmp);
		} else if (mailTo.getExpressionText().startsWith("#{")
				&& mailTo.getExpressionText().endsWith("}")) {
			value = ExpressionTools.evaluate(mailTo.getExpressionText(),
					context);
		}

		logger.debug("send actors:" + value);

		if (StringUtils.isEmpty(value)) {
			return;
		}

		Map<String, String> mailsTo = new HashMap<String, String>();
		Map<String, User> userMap = new HashMap<String, User>();

		StringTokenizer st = new StringTokenizer(value, ",");
		while (st.hasMoreTokens()) {
			String actorId = st.nextToken();
			User user = IdentityFactory.getUser(actorId);
			if (user != null && MailTools.isMailAddress(user.getMail())) {
				mailsTo.put(user.getActorId(), user.getMail());
				userMap.put(user.getActorId(), user);
				userMap.put(user.getMail(), user);
			}
		}

		logger.debug("send mailsTo:" + mailsTo);

		if (mailsTo.size() == 0) {
			return;
		}

		Iterator<String> iterator = mailsTo.keySet().iterator();
		while (iterator.hasNext()) {
			String actorId = iterator.next();
			String mailAddress = mailsTo.get(actorId);

			logger.debug("准备发送邮件:" + mailAddress);
			logger.debug(subject);

			StringBuffer urlBuffer = new StringBuffer();

			String messageId = UUID32.getUUID();
			String link = "";

			if (context.get("todo_url") != null) {
				link = (String) context.get("todo_url");
			} else {
				String key = processDefinition.getName() + "_" + taskName;
				link = TodoConfig.getLink(key);
				if (StringUtils.isEmpty(link)) {
					key = processDefinition.getName();
					link = TodoConfig.getLink(key);
				}
			}

			urlBuffer.append(link);
			urlBuffer.append("&messageId=").append(messageId);

			context.put("serviceUrl", SystemConfig.getServiceUrl());
			context.put("appId", execution.getVariable("appId"));
			context.put("app_name", execution.getVariable("app_name"));
			context.put("businessKey",
					execution.getVariable(Constants.BUSINESS_KEY));
			context.put("processName", processDefinition.getName());
			context.put("processInstanceId", String.valueOf(execution.getId()));
			if (execution.getVariable("appId") != null) {
				urlBuffer.append("&appId=").append(
						execution.getVariable("appId"));
			}
			if (execution.getVariable("app_name") != null) {
				urlBuffer.append("&app_name=").append(
						execution.getVariable("app_name"));
			}
			urlBuffer.append("&businessKey=").append(
					execution.getVariable(Constants.BUSINESS_KEY));
			urlBuffer.append("&processName=").append(
					processDefinition.getName());
			urlBuffer.append("&processInstanceId=").append(
					String.valueOf(execution.getId()));
			User user = userMap.get(actorId);
			if (user != null) {
				urlBuffer.append("&xyz_activationCode=").append(
						RequestUtils.encodeString(user.getActorId()));
			}

			String gotopage = urlBuffer.toString();

			String redirectUrl = RequestUtils.encodeURL(gotopage);

			context.put("gotopage", gotopage);
			context.put("callback", gotopage);
			context.put("redirectUrl", redirectUrl);
			if (user != null) {
				context.put("sid", RequestUtils.encodeString(user.getActorId()));
				context.put("xyz_activationCode",
						RequestUtils.encodeString(user.getActorId()));
			}
			context.put("user", userMap.get(actorId));
			context.put("mailToUser", userMap.get(actorId));

			body = TemplateUtils.process(context, body);

			logger.debug("content:" + content);

			MailMessage mailMessage = new MailMessage();
			// mailMessage.setTemplateId(templateId);
			mailMessage.setMessageId(messageId);
			mailMessage.setTo(mailAddress);
			mailMessage.setSubject(title);
			mailMessage.setContent(body);
			mailMessage.setDataMap(context);
			mailMessage.setSaveMessage(true);

			try {
				MailThread thread = new MailThread(mailMessage);
				com.glaf.core.util.threads.ThreadFactory.run(thread);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	public void setAssigneeVar(Expression assigneeVar) {
		this.assigneeVar = assigneeVar;
	}

	public void setMailTo(Expression mailTo) {
		this.mailTo = mailTo;
	}

	public void setSubject(Expression subject) {
		this.subject = subject;
	}

	public void setContent(Expression content) {
		this.content = content;
	}

	public void setTaskName(Expression taskName) {
		this.taskName = taskName;
	}

	public void setTaskContent(Expression taskContent) {
		this.taskContent = taskContent;
	}

	public void setTemplateId(Expression templateId) {
		this.templateId = templateId;
	}
}
