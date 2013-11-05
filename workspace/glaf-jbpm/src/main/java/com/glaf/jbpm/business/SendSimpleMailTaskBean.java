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

package com.glaf.jbpm.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.freemarker.TemplateUtils;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.service.ISystemPropertyService;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.TodoTotal;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;

import com.glaf.mail.MailMessage;
import com.glaf.mail.MailSender;

import com.glaf.jbpm.config.JbpmBaseConfiguration;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.model.TaskItem;

public class SendSimpleMailTaskBean {
	protected final static Log logger = LogFactory
			.getLog(SendSimpleMailTaskBean.class);

	private static Configuration conf = JbpmBaseConfiguration.create();

	public void sendAllRunningTasks() {
		Map<String, User> userMap = IdentityFactory.getUserMap();
		Iterator<User> iterator = userMap.values().iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (StringUtils.isNotEmpty(user.getMail())
					&& StringUtils.containsOnly(user.getMail(), "@")) {
				this.sendRunningTasks(user);
			}
		}
	}

	public void sendRunningTasks(String actorId) {
		User user = IdentityFactory.getUser(actorId);
		if (StringUtils.isNotEmpty(user.getMail())
				&& StringUtils.containsOnly(user.getMail(), "@")) {
			this.sendRunningTasks(user);
		}
	}

	public void sendRunningTasks(User user) {
		String tpl_path = "/conf/templates/mail/simpletasklist.html";
		if (conf.get("RunningTasks_template") != null) {
			tpl_path = conf.get("RunningTasks_template");
		}
		String subject = "${res_system_name} ${now}待办任务提醒";
		if (conf.get("RunningTasks_subject") != null) {
			subject = conf.get("RunningTasks_subject");
		}
		String filename = SystemProperties.getConfigRootPath() + tpl_path;
		String content = null;
		try {
			content = new String(FileUtils.getBytes(filename), "UTF-8");
			if (StringUtils.isNotEmpty(subject)
					&& StringUtils.isNotEmpty(content)) {
				List<TaskItem> taskItems = ProcessContainer.getContainer()
						.getTaskItems(user.getActorId());
				if (taskItems != null && !taskItems.isEmpty()) {
					logger.debug("taskItems size=" + taskItems.size());

					Map<String, Object> context = new HashMap<String, Object>();
					context.put("actorId", user.getActorId());
					context.put("user", user);
					context.put("taskItems", taskItems);
					ISystemPropertyService systemPropertyService = ContextFactory
							.getBean("systemPropertyService");
					SystemProperty property = systemPropertyService
							.getSystemProperty("SYS", "serviceUrl");

					if (property != null) {
						context.put("property", property);
						context.put("serviceUrl", property.getValue());
					}

					List<SystemProperty> props = systemPropertyService
							.getSystemProperties("SYS");
					if (props != null && !props.isEmpty()) {
						for (SystemProperty p : props) {
							if (!context.containsKey(p.getName())) {
								context.put(p.getName(), p.getValue());
							}
						}
					}

					List<Todo> userTasks = new ArrayList<Todo>();

					Map<String, Todo> todoMap = new HashMap<String, Todo>();
					Map<String, TodoTotal> todoTotalMap = new HashMap<String, TodoTotal>();
					for (TaskItem task : taskItems) {
						String key = task.getProcessName() + "_"
								+ task.getTaskName();
						Todo todo = new Todo();
						String bt = task.getProcessDescription()
								+ task.getTaskDescription();
						todo.setProcessName(task.getProcessName());
						todo.setTaskName(task.getTaskName());
						todo.setTitle(bt);
						todoMap.put(key, todo);
						TodoTotal total = new TodoTotal();
						total.setTodo(todo);
						total.setTotalQty(0);
						todoTotalMap.put(key, total);
					}

					for (TaskItem task : taskItems) {
						String key = task.getProcessName() + "_"
								+ task.getTaskName();
						TodoTotal todoTotal = todoTotalMap.get(key);
						if (todoTotal != null) {
							todoTotal.setTotalQty(todoTotal.getTotalQty() + 1);
							todoTotal.getRowIds().add(task.getRowId());
							todoTotal.getProcessInstanceIds().add(
									task.getProcessInstanceId());
							todoTotal.getAllBuffer().append(task.getRowId())
									.append(",");
							todoTotal.getAllProcessBuffer()
									.append(task.getProcessInstanceId())
									.append(",");
						}
					}

					Collection<TodoTotal> values = todoTotalMap.values();
					if (!values.isEmpty()) {
						for (TodoTotal t : values) {
							if (t.getTotalQty() > 0) {
								Todo todo = t.getTodo();
								todo.setTotalQty(t.getTotalQty());
								userTasks.add(todo);
							}
						}
					}

					context.put("tasks", userTasks);
					context.put("userTasks", userTasks);
					context.put("now", DateUtils.getDate(new Date()));
					context.put("current_date", DateUtils.getDate(new Date()));

					String title = TemplateUtils.process(context, subject);
					String text = TemplateUtils.process(context, content);

					MailMessage mailMessage = new MailMessage();

					mailMessage.setTo(user.getMail());
					mailMessage.setSubject(title);
					mailMessage.setDataMap(context);
					mailMessage.setContent(text);
					mailMessage.setSupportExpression(false);

					mailMessage.setSaveMessage(false);
					MailSender mailSender = ContextFactory
							.getBean("mailSender");
					mailSender.send(mailMessage);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

}
