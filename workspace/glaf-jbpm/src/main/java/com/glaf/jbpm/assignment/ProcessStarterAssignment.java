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

package com.glaf.jbpm.assignment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.jbpm.action.MailBean;
import com.glaf.jbpm.util.Constant;

public class ProcessStarterAssignment implements AssignmentHandler {
	private static final Log logger = LogFactory
			.getLog(ProcessStarterAssignment.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 是否发送邮件
	 */
	protected String sendMail;

	/**
	 * 邮件标题
	 */
	protected String subject;

	/**
	 * 邮件内容
	 */
	protected String content;

	/**
	 * 任务内容
	 */
	protected String taskContent;

	/**
	 * 邮件模板编号
	 */
	protected String templateId;

	public ProcessStarterAssignment() {

	}

	public void assign(Assignable assignable, ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("----------------ProcessStarterAssignment---------------");
		logger.debug("-------------------------------------------------------");

		ContextInstance contextInstance = ctx.getContextInstance();

		String actorId = (String) contextInstance
				.getVariable(Constant.PROCESS_STARTERID);
		if (StringUtils.isEmpty(actorId)) {
			long taskInstanceId = Long.MAX_VALUE;
			TaskMgmtInstance tmi = ctx.getProcessInstance()
					.getTaskMgmtInstance();
			Collection<TaskInstance> taskInstances = tmi.getTaskInstances();
			Iterator<TaskInstance> iterator = taskInstances.iterator();
			while (iterator.hasNext()) {
				TaskInstance taskInstance = iterator.next();
				if (taskInstance.hasEnded()
						&& StringUtils.isNotEmpty(taskInstance.getActorId())) {
					if (taskInstance.getId() < taskInstanceId) {
						taskInstanceId = taskInstance.getId();
					}
				}
			}
			if (taskInstanceId < Long.MAX_VALUE) {
				TaskInstance taskInstance = ctx.getJbpmContext()
						.getTaskInstance(taskInstanceId);
				actorId = taskInstance.getActorId();
			}
		}

		if (StringUtils.isNotEmpty(actorId)) {
			assignable.setActorId(actorId);
			if (StringUtils.isNotEmpty(sendMail)
					&& StringUtils.equals(sendMail, "true")) {
				Set<String> actorIds = new HashSet<String>();
				actorIds.add(actorId);
				MailBean mailBean = new MailBean();
				mailBean.setContent(content);
				mailBean.setSubject(subject);
				mailBean.setTaskContent(taskContent);
				mailBean.setTemplateId(templateId);
				mailBean.execute(ctx, actorIds);
			}
			return;
		}

	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}