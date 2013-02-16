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
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.util.ParamUtil;
import org.jpage.util.JSONTools;

public class StartProcessCmd {
	protected final static Log logger = LogFactory
			.getLog(StartProcessCmd.class);

	public StartProcessCmd() {

	}

	/**
	 * 启动工作流
	 * 
	 * @param actorId
	 *            登录用户的唯一编号
	 * @param paramMap
	 *            参数表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String startProcess(String actorId, Map<String, Object> paramMap) {
		String json = ParamUtil.getString(paramMap, "json");
		String rowId = ParamUtil.getString(paramMap, "rowId");
		String processName = ParamUtil.getString(paramMap, "processName");

		ProcessContainer container = ProcessContainer.getContainer();
		String processInstanceId = null;

		if (StringUtils.isNotEmpty(rowId)
				&& StringUtils.isNotEmpty(processName)
				&& StringUtils.isNotEmpty(actorId)) {
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(rowId);
			ctx.setActorId(actorId);
			ctx.setTitle("单据编号：" + rowId);
			ctx.setProcessName(processName);

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
							if (name != null && value != null) {
								DataField dataField = new DataField();
								dataField.setName(name);
								dataField.setValue(value);
								ctx.addDataField(dataField);
							}
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

			processInstanceId = container.startProcess(ctx);

			logger.debug("processInstanceId:" + processInstanceId);

		}

		return processInstanceId;
	}

	public static void main(String[] args) {
		StartProcessCmd cmd = new StartProcessCmd();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 审核表的记录ID
		paramMap.put("rowId", "LeaveApply_1001");
		// 流程名称（英文的名称）
		paramMap.put("processName", "LeaveApplyProc");
		// 工作流控制参数，合法的JSON格式字符串
		String datafields = "{day:2, tableName:'X_APP_LeaveApply'}";
		paramMap.put("json", datafields);
		String processInstanceId = cmd.startProcess("joy", paramMap);
		// 如果流程实例编号不为空，那么已经成功创建流程实例，否则抛出异常
		if (processInstanceId != null) {
			System.out.println("OK!");
		}
	}

}
