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

package com.glaf.jbpm.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.el.ExpressionTools;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.todo.config.TodoConfig;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailThread;
import com.glaf.mail.util.MailTools;
import com.glaf.template.Template;
import com.glaf.template.TemplateContainer;
import com.glaf.template.util.TemplateUtils;
import com.glaf.jbpm.util.Constant;

public class MailAction implements ActionHandler {
	private static final Log logger = LogFactory.getLog(MailAction.class);

	private static final long serialVersionUID = 1L;

	private String mailTo;

	private String subject;

	private String content;

	private String taskName;

	private String taskContent;

	private String templateId;

	public MailAction() {

	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("------------------------------------------------------");
		logger.debug("-------------------MailAction-------------------------");
		logger.debug("------------------------------------------------------");

		Map<String, Object> context = new HashMap<String, Object>();

		ContextInstance contextInstance = ctx.getContextInstance();
		Map<String, Object> variables = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator<String> iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = iterator.next();
				if (context.get(variableName) == null) {
					Object value = contextInstance.getVariable(variableName);
					context.put(variableName, value);
				}
			}
		}

		ProcessInstance processInstance = ctx.getProcessInstance();
		ProcessDefinition processDefinition = processInstance
				.getProcessDefinition();

		context.put("x_process_name", processDefinition.getName());
		context.put("x_process_title", processDefinition.getDescription());
		context.put("task_subject", subject);
		context.put("taskName", taskName);
		context.put("task_name", taskName);
		context.put("taskContent", taskContent);
		context.put("task_content", taskContent);

		if (StringUtils.isNotEmpty(templateId)) {
			Template template = TemplateContainer.getContainer().getTemplate(
					templateId);
			if (template != null) {
				logger.debug(template);
				if (StringUtils.isEmpty(subject)) {
					subject = template.getTitle();
				}
				if (StringUtils.isEmpty(content)) {
					content = template.getContent();
				}
			}
		}

		if (subject != null) {
			subject = ExpressionTools.evaluate(subject, context);
		}

		logger.debug("subject:" + subject);
		logger.debug("content:" + content);

		if (StringUtils.isEmpty(subject) || StringUtils.isEmpty(content)) {
			return;
		}

		String process_starter = (String) contextInstance
				.getVariable(Constant.PROCESS_STARTERID);

		String value = null;

		String tmp = null;
		if (mailTo.equals(Constant.PROCESS_STARTER_EXPRESSION)) {
			value = process_starter;
		} else if (mailTo.equals("x_running")) {
			TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
			Collection<TaskInstance> taskInstances = tmi.getTaskInstances();
			if (taskInstances != null && taskInstances.size() > 0) {
				StringBuffer actBuffer = new StringBuffer();
				Iterator<TaskInstance> iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance ti = iter.next();
					if ((!ti.isOpen()) || ti.isSuspended()) {
						continue;
					}
					if (taskName != null
							&& (!StringUtils.contains(taskName, ti.getName()))) {
						continue;
					}
					if (ti.getActorId() != null) {
						actBuffer.append(ti.getActorId()).append(',');
					} else {
						Set<PooledActor> pas = ti.getPooledActors();
						if (pas != null && pas.size() > 0) {
							Iterator<PooledActor> it = pas.iterator();
							while (it.hasNext()) {
								PooledActor pa = it.next();
								if (pa.getActorId() != null) {
									actBuffer.append(pa.getActorId()).append(
											',');
								}
							}
						}
					}
				}
				value = actBuffer.toString();
			}
		} else if (mailTo.equals("x_finished")) {
			TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
			Collection<TaskInstance> taskInstances = tmi.getTaskInstances();
			if (taskInstances != null && taskInstances.size() > 0) {
				StringBuffer actBuffer = new StringBuffer();
				Iterator<TaskInstance> iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance ti = iter.next();
					if (!ti.hasEnded()) {
						continue;
					}
					if (taskName != null
							&& (!StringUtils.contains(taskName, ti.getName()))) {
						continue;
					}
					if (ti.getActorId() != null) {
						actBuffer.append(ti.getActorId()).append(',');
					}
				}
				value = actBuffer.toString();
			}
		} else if (mailTo.startsWith("#P{") && mailTo.endsWith("}")) {
			tmp = StringTools.replaceIgnoreCase(mailTo, "#P{", "");
			tmp = StringTools.replaceIgnoreCase(tmp, "}", "");
			value = (String) contextInstance.getVariable(tmp);
		} else if (mailTo.startsWith("#{") && mailTo.endsWith("}")) {
			value = ExpressionTools.evaluate(mailTo, context);
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
			context.put("appId", contextInstance.getVariable("appId"));
			context.put("app_name", contextInstance.getVariable("app_name"));
			context.put("rowId",
					contextInstance.getVariable(Constant.PROCESS_ROWID));
			context.put("processName", processDefinition.getName());
			context.put("processInstanceId",
					String.valueOf(processInstance.getId()));
			if (contextInstance.getVariable("appId") != null) {
				urlBuffer.append("&appId=").append(
						contextInstance.getVariable("appId"));
			}
			if (contextInstance.getVariable("app_name") != null) {
				urlBuffer.append("&app_name=").append(
						contextInstance.getVariable("app_name"));
			}
			urlBuffer.append("&rowId=").append(
					contextInstance.getVariable(Constant.PROCESS_ROWID));
			urlBuffer.append("&processName=").append(
					processDefinition.getName());
			urlBuffer.append("&processInstanceId=").append(
					String.valueOf(processInstance.getId()));
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

			content = TemplateUtils.process(context, content);

			logger.debug("content:" + content);

			MailMessage mailMessage = new MailMessage();
			// mailMessage.setTemplateId(templateId);
			mailMessage.setMessageId(messageId);
			mailMessage.setTo(mailAddress);
			mailMessage.setSubject(subject);
			mailMessage.setContent(content);
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

	public void setContent(String content) {
		this.content = content;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
}
