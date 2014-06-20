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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.el.ExpressionTools;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.todo.config.TodoConfig;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.UUID32;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailThread;
import com.glaf.mail.util.MailTools;
import com.glaf.template.Template;
import com.glaf.template.TemplateContainer;
import com.glaf.template.util.TemplateUtils;
import com.glaf.jbpm.util.Constant;

public class MailBean {
	private static final Log logger = LogFactory.getLog(MailBean.class);

	private String subject;

	private String content;

	private String taskName;

	private String taskContent;

	private String templateId;

	public MailBean() {

	}

	public MailBean(String templateId) {
		this.templateId = templateId;
	}

	public MailBean(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}

	public MailBean(String subject, String content, String taskName) {
		this.subject = subject;
		this.content = content;
		this.taskName = taskName;
	}

	public MailBean(String subject, String content, String taskName,
			String taskContent) {
		this.subject = subject;
		this.content = content;
		this.taskName = taskName;
		this.taskContent = taskContent;
	}

	public MailBean(String subject, String content, String taskName,
			String taskContent, String templateId) {
		this.subject = subject;
		this.content = content;
		this.taskName = taskName;
		this.taskContent = taskContent;
		this.templateId = templateId;
	}

	public void execute(ExecutionContext ctx, Collection<String> actorIds) {
		logger.debug("------------------------------------------------------");
		logger.debug("-------------------MailBean---------------------------");
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
				//logger.debug(template);
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
		//logger.debug("content:" + content);

		if (StringUtils.isEmpty(subject) || StringUtils.isEmpty(content)) {
			throw new RuntimeException("subject or content is empty");
		}

		Map<String, String> mailsTo = new HashMap<String, String>();
		Map<String, User> userMap = new HashMap<String, User>();

		Iterator<String> iterator = actorIds.iterator();
		while (iterator.hasNext()) {
			String actorId = iterator.next();
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

		iterator = mailsTo.keySet().iterator();
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
