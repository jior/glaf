/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
