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


package org.jpage.jbpm.job;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.service.ProcessManager;
import org.jpage.jbpm.service.ServiceManager;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.DateTools;

public class DeleteProcessJobMethodBean {
	private final static Log logger = LogFactory
			.getLog(DeleteProcessJobMethodBean.class);

	 

	private ProcessManager processManager;

	private ServiceManager serviceManager;

	public DeleteProcessJobMethodBean() {
		processManager = (ProcessManager) org.jpage.jbpm.context.JbpmContextFactory
				.getBean("processManager");
		serviceManager = (ServiceManager) org.jpage.jbpm.context.JbpmContextFactory
				.getBean("serviceManager");
	}

	public void execute() {
		logger.debug("DeleteProcessJobMethodBean execute...");
	}

	/**
	 * 删除工作流引擎中已经完成的流程
	 * 
	 */
	public void deleteFinishedProcess() {
		JbpmContext jbpmContext = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year - 5);
		Date date = new Date(calendar.getTimeInMillis());
		logger.debug("删除" + DateTools.getDateTime(date) + "以前已经完成的流程.");
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			jbpmContext.setActorId("system");
			List processDefinitions = jbpmContext.getGraphSession()
					.findAllProcessDefinitions();
			if (processDefinitions != null && processDefinitions.size() > 0) {
				Iterator iterator = processDefinitions.iterator();
				while (iterator.hasNext()) {
					ProcessDefinition pd = (ProcessDefinition) iterator.next();
					processManager.deleteFinishedProcessInstances(jbpmContext,
							String.valueOf(pd.getId()),
							Constant.MAX_WORK_ITEM_SIZE, date);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 删除已经完成的流程相关的实例文件
	 * 
	 */
	public void deleteFinishedProcessFile() {
		JbpmContext jbpmContext = null;
		Set processInstanceIds = new HashSet();
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			jbpmContext.setActorId("system");

			List processDefinitions = jbpmContext.getGraphSession()
					.findAllProcessDefinitions();
			if (processDefinitions != null && processDefinitions.size() > 0) {
				Iterator iterator = processDefinitions.iterator();
				while (iterator.hasNext()) {
					ProcessDefinition pd = (ProcessDefinition) iterator.next();
					List rows = processManager.getPageFinishedProcessInstances(
							jbpmContext, 1, 5000, pd.getId()).getRows();
					if (rows != null && rows.size() > 0) {
						Iterator iter = rows.iterator();
						while (iter.hasNext()) {
							ProcessInstance pi = (ProcessInstance) iter.next();
							processInstanceIds.add(String.valueOf(pi.getId()));
						}
					}
					rows = null;
				}
				processDefinitions = null;
			}

		 
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

 

}
