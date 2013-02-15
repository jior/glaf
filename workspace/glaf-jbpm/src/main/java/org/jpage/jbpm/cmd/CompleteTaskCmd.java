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
	 * �������
	 * 
	 * @param actorId
	 *            ��¼�û���Ψһ���
	 * @param paramMap
	 *            ������
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
			 * ��������ʵ����ź��û���Ż�ȡ��������
			 */
			taskItem = container.getTaskItem(taskInstanceId, actorId);
		} else if ((StringUtils.isNotEmpty(processInstanceId))
				&& StringUtils.isNotEmpty(actorId)) {
			/**
			 * ��������ʵ����ź��û���Ż�ȡ��������
			 */
			taskItem = container.getMinTaskItem(actorId, processInstanceId);
		}

		logger.debug("taskItem:" + taskItem);

		/**
		 * ������ڴ��������ҿ����ύ���
		 */
		if (taskItem != null) {
			ProcessContext ctx = new ProcessContext();
			/**
			 * ���̿��Ʋ���Ϊjson��ʽ���ַ���.<br>
			 * ���磺{money:100000,day:5,pass:true,deptId:"123", roleId:"R001"}
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
		// ����ʵ����ţ����û����������ʵ����ţ�����ʼ������ʵ�����
		paramMap.put("processInstanceId", "123");
		// ����ʵ����ţ����û����������ʵ����ţ�����ʼ������ʵ�����
		paramMap.put("taskInstanceId", "565");
		// ת��·�����ƣ���ѡֵ����������˸�ֵ�����̼�����·��ת�ƣ�������ݱ��ʽ�Զ�ѡ��ת��·��
		paramMap.put("transitionName", "�ύ��һ�����");
		// �Ƿ�ͨ����ͨ��Ϊtrue�����Ϊfalse
		paramMap.put("isAgree", "true");
		// ������
		paramMap.put("opinion", "ͬ�⣡");
		// ���������Ʋ������Ϸ���JSON��ʽ�ַ���
		String datafields = "{day:2}";
		paramMap.put("json", datafields);
		boolean isOK = cmd.completeTask("manager", paramMap);
		// ����ɹ�������true�������׳��쳣
		if (isOK) {
			System.out.println("OK!");
		}
	}

}
