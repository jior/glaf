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

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.activiti.mail.MailBean;
import com.glaf.activiti.util.ExecutionUtils;

public class ProcessStarterListener implements TaskListener {
	private static final long serialVersionUID = 1L;
	
	protected final static Log logger = LogFactory
			.getLog(ProcessStarterListener.class);

	protected Expression sql;
	
	protected Expression sendMail;

	protected Expression subject;

	protected Expression content;

	protected Expression taskName;

	protected Expression taskContent;

	protected Expression templateId;

	public void notify(DelegateTask delegateTask) {
		logger.debug("----------------------------------------------------");
		logger.debug("------------------ProcessStarterListener------------");
		logger.debug("----------------------------------------------------");
		CommandContext commandContext = Context.getCommandContext();
		DelegateExecution execution = delegateTask.getExecution();
		String processInstanceId = delegateTask.getProcessInstanceId();
		HistoricProcessInstanceEntity historicProcessInstanceEntity = commandContext
				.getHistoricProcessInstanceEntityManager()
				.findHistoricProcessInstance(processInstanceId);
		String startUserId = historicProcessInstanceEntity.getStartUserId();
		if (startUserId != null) {
			delegateTask.setAssignee(startUserId);
			if (sendMail != null
					&& StringUtils.equals(
							sendMail.getExpressionText(), "true")) {
				Collection<String> assigneeList = new java.util.ArrayList<String>();
				assigneeList.add(startUserId);
				MailBean bean = new MailBean();
				bean.setSubject(subject);
				bean.setContent(content);
				bean.setTaskContent(taskContent);
				bean.setTaskName(taskName);
				bean.setTemplateId(templateId);
				bean.sendMail(execution, assigneeList);
			}
		}

		if (sql != null) {
			ExecutionUtils.executeSqlUpdate(execution, sql);
		}
	}
	
	

	public void setContent(Expression content) {
		this.content = content;
	}



	public void setSendMail(Expression sendMail) {
		this.sendMail = sendMail;
	}



	public void setSql(Expression sql) {
		this.sql = sql;
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