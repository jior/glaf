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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.node.TaskNode;
import org.jpage.jbpm.model.StateInstance;
import org.jpage.jbpm.model.TaskItem;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.util.ParamUtil;

public class TakeTaskCmd {
	protected final static Log logger = LogFactory.getLog(TakeTaskCmd.class);

	public TakeTaskCmd() {

	}

	/**
	 * ��ȡ����ʵ��
	 * 
	 * @param actorId
	 *            ��¼�û���Ψһ���
	 * @param paramMap
	 *            ������
	 * @return
	 */
	public Map<String, Object> getTask(String actorId,
			Map<String, Object> paramMap) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String processInstanceId = ParamUtil.getString(paramMap,
				"processInstanceId");
		String taskInstanceId = ParamUtil.getString(paramMap, "taskInstanceId");
		ProcessContainer container = ProcessContainer.getContainer();
		boolean canSubmit = false;
		TaskItem taskItem = null;
		if (StringUtils.isNotEmpty(taskInstanceId)
				&& StringUtils.isNotEmpty(actorId)) {
			/**
			 * ��������ʵ����ź��û���Ż�ȡ��������
			 */
			taskItem = container.getTaskItem(taskInstanceId, actorId);
			if (taskItem != null) {
				canSubmit = true;
				processInstanceId = taskItem.getProcessInstanceId();
			}
		} else if ((StringUtils.isNotEmpty(processInstanceId))
				&& StringUtils.isNotEmpty(actorId)) {
			/**
			 * ��������ʵ����ź��û���Ż�ȡ��������
			 */
			taskItem = container.getMinTaskItem(actorId, processInstanceId);
			if (taskItem != null) {
				canSubmit = true;
				taskInstanceId = taskItem.getTaskInstanceId();
			}
		}

		/**
		 * ������ڴ��������ҿ����ύ���
		 */
		if (canSubmit && StringUtils.isNotEmpty(taskInstanceId)) {

			if (StringUtils.isNotEmpty(taskInstanceId)) {
				Collection<String> transitionNames = container
						.getTransitionNames(taskInstanceId);
				// ������ڵ��ת��·������
				dataMap.put("transitionNames", transitionNames);
			}

			int signal = container.getSignal(taskInstanceId);
			if (signal != TaskNode.SIGNAL_LAST) {
				Collection<String> nodeNames = container
						.getNodeNames(processInstanceId);
				// ������ȫ���Ľڵ�����
				dataMap.put("nodeNames", nodeNames);
			}

			// ������������
			dataMap.put("taskItem", taskItem);
		}

		/**
		 * ��ʷ������
		 */
		if (StringUtils.isNotEmpty(processInstanceId)) {
			List<StateInstance> stepInstances = container
					.getStateInstances(processInstanceId);
			dataMap.put("stepInstances", stepInstances);
		}

		return dataMap;
	}

	public static void main(String[] args) throws Exception {
		TakeTaskCmd cmd = new TakeTaskCmd();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// ����ʵ����ţ����û����������ʵ����ţ�����ʼ������ʵ�����
		paramMap.put("processInstanceId", "123");
		// ����ʵ����ţ����û����������ʵ����ţ�����ʼ������ʵ�����
		paramMap.put("taskInstanceId", "565");
		Map<String, Object> dataMap = cmd.getTask("joy", paramMap);
		if (dataMap != null) {
			System.out.println(dataMap.get("taskItem"));
			// transitionNames ������ڵ��ת��·������
			// nodeNames ������ȫ���Ľڵ�����
			// taskItem ������������
			// stepInstances ��ʷ������
		}
	}

}
