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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
 
import com.glaf.core.util.LogUtils;
import com.glaf.jbpm.config.JbpmObjectFactory;
import com.glaf.jbpm.container.ProcessContainer;

/**
 * 任务分配说明： 根据优先级顺序，设置任务的参与者<br>
 * 1、直接从外部获取的动态参与者ID <br>
 * 2、流程任务预定义的参与者<br>
 * 
 */
public class MxGeneralAssignment implements AssignmentHandler {
	private static final Log logger = LogFactory
			.getLog(MxGeneralAssignment.class);

	private static final long serialVersionUID = 1L;

	private transient MxAssignableHelper helper;

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("SendDocAuditor");
	 */
	private String dynamicActors;

	/**
	 * 角色编号
	 */
	private String roleId;

	public MxGeneralAssignment() {
		helper = new MxAssignableHelper();
	}

	public String getDynamicActors() {
		return dynamicActors;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void assign(Assignable assignable, ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("-------------------GeneralAssignment-------------------");
		logger.debug("-------------------------------------------------------");

		/**
		 * 1、直接从外部获取的动态参与者ID
		 */
		ContextInstance contextInstance = ctx.getContextInstance();
		if (StringUtils.isNotEmpty(dynamicActors)) {
			String actorId = (String) contextInstance
					.getVariable(dynamicActors);
			if (StringUtils.isNotEmpty(actorId)) {
				if (LogUtils.isDebug()) {
					logger.debug("外部输入的actors:" + actorId);
				}
				helper.setActors(assignable, actorId);
				return;
			}
		}

		String processName = contextInstance.getProcessInstance()
				.getProcessDefinition().getName();
		String taskName = ctx.getTask().getName();

		/**
		 * 2、流程任务预定义的参与者
		 */

		if (StringUtils.isEmpty(roleId)) {
			roleId = processName + "_" + taskName;
		}

		List<String> actorIds = new ArrayList<String>();

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("roleId", roleId);

		List<String> x_actorIds = ProcessContainer.getContainer().getMembershipActorIds(paramMap);

		if (x_actorIds != null && x_actorIds.size() > 0) {
			actorIds.addAll(x_actorIds);
		}

		if (JbpmObjectFactory.isDefaultActorEnable()) {
			String defaultActors = JbpmObjectFactory.getDefaultActors();
			if (StringUtils.isNotEmpty(defaultActors)) {
				StringTokenizer st2 = new StringTokenizer(defaultActors, ",");
				while (st2.hasMoreTokens()) {
					String elem = st2.nextToken();
					if (StringUtils.isNotEmpty(elem)) {
						actorIds.add(elem);
					}
				}
			}
		}

		if (actorIds.size() > 0) {
			if (LogUtils.isDebug()) {
				logger.debug("actorIds size:" + actorIds.size());
			}
			if (actorIds.size() == 1) {
				String actorId = actorIds.get(0);
				assignable.setActorId(actorId);
				if (LogUtils.isDebug()) {
					logger.debug("actorId:" + actorId);
				}
			} else if (actorIds.size() > 1) {
				int i = 0;
				String[] array = new String[actorIds.size()];
				Iterator<String> iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					String actorId = iterator.next();
					array[i++] = actorId;
					if (LogUtils.isDebug()) {
						logger.debug("pooed actorId:" + actorId);
					}
				}
				assignable.setPooledActors(array);
			}
		} else {
			throw new JbpmException(" actorId is null ");
		}
	}

}