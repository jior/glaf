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


package org.jpage.jbpm.cmd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.jbpm.context.ProcessContext;
import org.jpage.jbpm.datafield.DataField;
import org.jpage.jbpm.model.TaskItem;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.util.ParamUtil;
import org.jpage.util.JSONTools;

public class CompleteTaskCmd {
	protected final static Log logger = LogFactory
			.getLog(CompleteTaskCmd.class);

	public CompleteTaskCmd() {

	}

	/**
	 * 完成任务
	 * 
	 * @param actorId
	 *            登录用户的唯一编号
	 * @param paramMap
	 *            参数表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean completeTask(String actorId, Map<String, Object> paramMap) {
		logger.debug(paramMap);
		String json = ParamUtil.getString(paramMap, "json");
		String processInstanceId = ParamUtil.getString(paramMap,
				"processInstanceId");
		String taskInstanceId = ParamUtil.getString(paramMap, "taskInstanceId");
		String transitionName = ParamUtil.getString(paramMap, "transitionName");
		String jumpToNode = ParamUtil.getString(paramMap, "jumpToNode");
		String isAgree = ParamUtil.getString(paramMap, "isAgree");
		String opinion = ParamUtil.getString(paramMap, "opinion");

		ProcessContainer container = ProcessContainer.getContainer();

		TaskItem taskItem = null;
		boolean isOK = false;

		if (StringUtils.isNotEmpty(taskInstanceId)
				&& StringUtils.isNotEmpty(actorId)) {
			/**
			 * 根据任务实例编号和用户编号获取待办任务
			 */
			taskItem = container.getTaskItem(taskInstanceId, actorId);
		} else if ((StringUtils.isNotEmpty(processInstanceId))
				&& StringUtils.isNotEmpty(actorId)) {
			/**
			 * 根据流程实例编号和用户编号获取待办任务
			 */
			taskItem = container.getMinTaskItem(actorId, processInstanceId);
		}

		logger.debug("taskItem:" + taskItem);

		/**
		 * 如果存在待办任务并且可以提交审核
		 */
		if (taskItem != null) {
			ProcessContext ctx = new ProcessContext();
			/**
			 * 流程控制参数为json格式的字符串.<br>
			 * 例如：{money:100000,day:5,pass:true,deptId:"123", roleId:"R001"}
			 */
			if (StringUtils.isNotEmpty(json)) {
				Map<String, Object> jsonMap = JSONTools.decode(json);
				if (jsonMap != null && jsonMap.size() > 0) {
					Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String name = entry.getKey();
						Object value = entry.getValue();
						if (name != null && value != null) {
							DataField dataField = new DataField();
							dataField.setName(name);
							dataField.setValue(value);
							ctx.addDataField(dataField);
						}
					}
				}
			} else {
				List<DataField> dataFields = (List<DataField>) paramMap
						.get("dataFields");
				if (dataFields != null && dataFields.size() > 0) {
					Iterator<DataField> iterator = dataFields.iterator();
					while (iterator.hasNext()) {
						DataField dataField = iterator.next();
						ctx.addDataField(dataField);
					}
				}
			}

			if (StringUtils.isNotEmpty(isAgree)) {
				DataField dataField = new DataField();
				dataField.setName("isAgree");
				dataField.setValue(isAgree);
				ctx.addDataField(dataField);
			}

			ctx.setActorId(actorId);
			ctx.setOpinion(opinion);
			ctx.setProcessInstanceId(taskItem.getProcessInstanceId());

			if (StringUtils.isNotEmpty(taskItem.getTaskInstanceId())) {
				if (Long.valueOf(taskItem.getTaskInstanceId()) > 0) {
					ctx.setTaskInstanceId(taskItem.getTaskInstanceId());
				}
			}

			if (StringUtils.isNotEmpty(jumpToNode)) {
				ctx.setJumpToNode(jumpToNode);
			}

			if (StringUtils.isNotEmpty(transitionName)) {
				ctx.setTransitionName(transitionName);
			}

			isOK = container.completeTask(ctx);

		}
		return isOK;
	}

	public static void main(String[] args) {
		CompleteTaskCmd cmd = new CompleteTaskCmd();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 流程实例编号，如果没有设置流程实例编号，必须始终任务实例编号
		paramMap.put("processInstanceId", "123");
		// 任务实例编号，如果没有设置任务实例编号，必须始终流程实例编号
		paramMap.put("taskInstanceId", "565");
		// 转移路径名称，可选值，如果设置了该值，流程即按该路径转移，否则根据表达式自动选择转出路径
		paramMap.put("transitionName", "提交下一步审核");
		// 是否通过，通过为true，否决为false
		paramMap.put("isAgree", "true");
		// 审核意见
		paramMap.put("opinion", "同意！");
		// 工作流控制参数，合法的JSON格式字符串
		String datafields = "{day:2}";
		paramMap.put("json", datafields);
		boolean isOK = cmd.completeTask("manager", paramMap);
		// 如果成功，返回true，否则抛出异常
		if (isOK) {
			System.out.println("OK!");
		}
	}

}
