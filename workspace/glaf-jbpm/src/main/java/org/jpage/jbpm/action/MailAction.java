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


package org.jpage.jbpm.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.jpage.actor.User;
import org.jpage.core.mail.model.MailTemplate;
import org.jpage.core.mail.service.MailMessage;
import org.jpage.core.mail.util.MailTools;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.config.SystemProperties;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.mail.JavaMailContainer;
import org.jpage.jbpm.mail.MailThread;
import org.jpage.jbpm.model.MessageInstance;
import org.jpage.jbpm.model.StateInstance;
import org.jpage.jbpm.service.ActorManager;
import org.jpage.jbpm.service.PersistenceManager;
import org.jpage.jbpm.service.ServiceManager;
import org.jpage.jbpm.util.Constant;
import org.jpage.jbpm.util.MessageType;
import org.jpage.util.Tools;
import org.jpage.util.UUID32;

public class MailAction implements ActionHandler {
	private static final Log logger = LogFactory.getLog(MailAction.class);

	private static final long serialVersionUID = 1L;

	private String suffix;

	private String mailTo;

	private String subject;

	private String content;

	private String link;

	private String expression;

	private String taskName;

	private String templateId;

	public MailAction() {

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("--------------------------------------------------------");
		logger.debug("---------------------MailAction-------------------------");
		logger.debug("--------------------------------------------------------");

		ActorManager actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");
		ServiceManager serviceManager = (ServiceManager) JbpmContextFactory
				.getBean("serviceManager");
		PersistenceManager persistenceManager = (PersistenceManager) JbpmContextFactory
				.getBean("persistenceManager");

		boolean executable = true;

		Map context = new HashMap();

		ContextInstance contextInstance = ctx.getContextInstance();
		Map variables = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = (String) iterator.next();
				if (context.get(variableName) == null) {
					Object value = contextInstance.getVariable(variableName);
					context.put(variableName, value);
				}
			}
		}

		if (StringUtils.isNotBlank(expression)) {
			if (expression.startsWith("#{") && expression.endsWith("}")) {
				if (logger.isDebugEnabled()) {
					logger.debug("expression->" + expression);
					logger.debug("context->" + context);
				}
				Object value = DefaultExpressionEvaluator.evaluate(expression,
						context);
				if (value != null) {
					if (value instanceof Boolean) {
						Boolean b = (Boolean) value;
						executable = b.booleanValue();
						logger.debug("executable->" + executable);
					}
				}
			}
		}

		if (!executable) {
			logger.debug("在表达式计算后取值为false，不执行后续动作。");
			return;
		}

		if (StringUtils.isNotBlank(templateId)) {
			MailTemplate template = JavaMailContainer.getContainer()
					.getMailTemplate(templateId);
			if (template != null) {
				if (StringUtils.isBlank(subject)) {
					subject = template.getSubject();
				}
				if (StringUtils.isBlank(content)) {
					content = template.getContent();
				}
			}
		}

		if (StringUtils.isBlank(subject) || StringUtils.isBlank(content)) {
			return;
		}

		String process_starter = (String) contextInstance
				.getVariable(Constant.PROCESS_START_ACTORID);
		String latestActorId = (String) contextInstance
				.getVariable(Constant.PROCESS_LATEST_ACTORID);

		ProcessInstance processInstance = ctx.getProcessInstance();
		TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
		String processInstanceId = String.valueOf(processInstance.getId());
		JbpmContext jbpmContext = ctx.getJbpmContext();
		String value = null;

		String tmp = mailTo;
		if (tmp.equals(Constant.PROCESS_STARTER_EXPRESSION)) {
			value = process_starter;
		} else if (tmp.equals(Constant.PROCESS_LATESTER_EXPRESSION)) {
			value = latestActorId;
		} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
			tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
			tmp = Tools.replaceIgnoreCase(tmp, "}", "");
			value = (String) contextInstance.getVariable(tmp);
		} else if (tmp.startsWith("#{") && tmp.endsWith("}")) {
			value = (String) DefaultExpressionEvaluator.evaluate(tmp, context);
		} else if (tmp.equals(Constant.RUNNING_TASKINSTANCE_ACTOR_EXPRESSION)) {
			if (tmi != null) {
				Collection taskInstances = tmi.getTaskInstances();
				Collection unfinishedTasks = new ArrayList();
				if (taskInstances != null) {
					Iterator iter = taskInstances.iterator();
					while (iter.hasNext()) {
						TaskInstance task = (TaskInstance) iter.next();
						if ((!task.hasEnded())) {
							unfinishedTasks.add(task);
						}
					}
				}
				if (unfinishedTasks.size() > 0) {
					Iterator iterator = unfinishedTasks.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						if (StringUtils.isNotBlank(taskName)) {
							if (!StringUtils.equals(taskName,
									taskInstance.getName())) {
								continue;
							}
						}
						String actorId = taskInstance.getActorId();
						if (StringUtils.isNotBlank(actorId)) {
							value += actorId + ",";
						} else {
							Set pooledActors = taskInstance.getPooledActors();
							if (pooledActors != null && pooledActors.size() > 0) {
								Iterator iter = pooledActors.iterator();
								while (iter.hasNext()) {
									PooledActor actor = (PooledActor) iter
											.next();
									String pooledActorId = actor.getActorId();
									value += pooledActorId + ",";
								}
							}
						}
					}
				}
			}
		} else if (tmp.equals(Constant.TASKINSTANCE_ACTOR_EXPRESSION)) {
			Collection rows = serviceManager.getStateInstances(jbpmContext,
					processInstanceId);
			if (rows != null && rows.size() > 0) {
				Iterator it = rows.iterator();
				while (it.hasNext()) {
					StateInstance x = (StateInstance) it.next();
					if (StringUtils.isNotBlank(taskName)) {
						if (!StringUtils.equals(taskName, x.getTaskName())) {
							continue;
						}
					}
					value += x.getActorId() + ",";
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(value);
		}

		if (StringUtils.isBlank(value)) {
			return;
		}

		Map mailsTo = new HashMap();

		StringTokenizer st = new StringTokenizer(value, ",");
		while (st.hasMoreTokens()) {
			String actorId = st.nextToken();
			if (MailTools.isMailAddress(actorId)) {
				mailsTo.put(actorId, actorId);
			} else {
				if (StringUtils.isNotBlank(suffix)) {
					String mailAddress = actorId + suffix;
					if (MailTools.isMailAddress(mailAddress)) {
						mailsTo.put(actorId, mailAddress);
					}
				} else {
					User user = actorManager.getUser(jbpmContext, actorId);
					if (user != null && user.getMail() != null) {
						mailsTo.put(user.getActorId(), user.getMail());
					}
				}
			}
		}

		if (mailsTo.size() == 0) {
			return;
		}

		Iterator iterator = mailsTo.keySet().iterator();
		while (iterator.hasNext()) {
			String actorId = (String) iterator.next();
			String mailAddress = (String) mailsTo.get(actorId);

			System.out.println("正在发送邮件:" + mailAddress);
			System.out.println(subject);

			String serviceUrl = SystemProperties.getProperties().getProperty(
					"serviceUrl");

			StringBuffer urlBuffer = new StringBuffer();
			StringBuffer imgBuffer = new StringBuffer();

			if (StringUtils.isBlank(link)) {
				link = "/app/gotopage.jsp?actionType=view";
			}

			urlBuffer.append(serviceUrl).append(link);

			imgBuffer.append(serviceUrl).append("/")
					.append(ObjectFactory.getProcessStatusUrl());

			String rowId = (String) contextInstance
					.getVariable(Constant.ROW_ID);

			String messageId = UUID32.getUUID();
			MessageInstance messageInstance = new MessageInstance();
			messageInstance.setMessageId(messageId);
			messageInstance.setMessageType("mail");
			messageInstance.setCreateDate(new Date());
			messageInstance.setReceiverId(mailAddress);
			messageInstance.setRepeatCount(1);
			messageInstance.setProcessName(processInstance
					.getProcessDefinition().getName());
			messageInstance.setProcessInstanceId(processInstanceId);
			messageInstance.setVersionNo(System.currentTimeMillis());
			messageInstance.setStatus(MessageType.PROCESS_MAIL_MESSAGE);
			messageInstance.setObjectName("rowId");
			messageInstance.setObjectId("rowId");
			messageInstance.setObjectValue(rowId);
			messageInstance.setRowId(rowId);

			Task task = ctx.getTask();
			TaskInstance taskInstance = ctx.getTaskInstance();
			if (taskInstance != null) {
				messageInstance.setTaskName(taskInstance.getName());
				messageInstance.setTaskDescription(taskInstance
						.getDescription());
				messageInstance.setTaskInstanceId(String.valueOf(taskInstance
						.getId()));
			} else {
				if (task != null) {
					messageInstance.setTaskName(task.getName());
					messageInstance.setTaskDescription(task.getDescription());
				}
			}

			String messageId_xy = org.jpage.util.RequestUtil
					.encodeString(messageId);
			urlBuffer.append("&messageId_xy=").append(messageId_xy);

			context.put("messageInstance", messageInstance);
			context.put("serviceUrl", serviceUrl);

			context.put("businessValue", messageInstance.getObjectValue());
			context.put("rowId", messageInstance.getObjectValue());
			context.put("rowId_xy", org.jpage.util.RequestUtil
					.encodeString(messageInstance.getObjectValue()));
			urlBuffer.append("&rowId_xy=").append(
					org.jpage.util.RequestUtil.encodeString(messageInstance
							.getObjectValue()));

			context.put("objectId", messageInstance.getObjectId());
			context.put("objectName", messageInstance.getObjectName());
			context.put("objectValue", messageInstance.getObjectValue());
			context.put("processName", messageInstance.getProcessName());
			context.put("processInstanceId",
					messageInstance.getProcessInstanceId());
			context.put("receiverId", messageInstance.getReceiverId());

			String processName_xy = org.jpage.util.RequestUtil
					.encodeString(messageInstance.getProcessName());
			context.put("processName_xy", processName_xy);
			urlBuffer.append("&processName_xy=").append(processName_xy);

			String processInstanceId_xy = org.jpage.util.RequestUtil
					.encodeString(messageInstance.getProcessInstanceId());
			context.put("processInstanceId_xy", processInstanceId_xy);
			urlBuffer.append("&processInstanceId_xy=").append(
					processInstanceId_xy);

			imgBuffer.append("&processInstanceId=").append(
					messageInstance.getProcessInstanceId());

			String gotopage = urlBuffer.toString();

			context.put("gotopage", gotopage);
			context.put("callback", gotopage);
			context.put("processStatusUrl", imgBuffer.toString());

			MailMessage mailMessage = new MailMessage();
			if (subject != null) {
				subject = (String) DefaultExpressionEvaluator.evaluate(subject,
						context);
			}
			if (content != null) {
				content = (String) DefaultExpressionEvaluator.evaluate(content,
						context);
			}

			mailMessage.setTo(mailAddress);
			mailMessage.setSubject(subject);
			mailMessage.setText(content);
			mailMessage.setDataMap(context);

			try {
				MailThread thread = new MailThread(mailMessage);
				thread.start();
				persistenceManager.save(jbpmContext, messageInstance);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}
}
