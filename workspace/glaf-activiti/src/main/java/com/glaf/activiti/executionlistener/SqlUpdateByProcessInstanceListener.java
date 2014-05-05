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

package com.glaf.activiti.executionlistener;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.util.Constants;

public class SqlUpdateByProcessInstanceListener implements ExecutionListener {
	private static final long serialVersionUID = 1L;

	private static final Log logger = LogFactory
			.getLog(SqlUpdateByProcessInstanceListener.class);

	/**
	 * 表名称，例如：sys_log
	 */
	protected Expression table;

	/**
	 * 流程实例保存字段（业务表流程实例编号的数据库字段名称）
	 */
	protected Expression processInstanceField;

	/**
	 * 字段JSON定义，例如：
	 * {"account":"#{actorId}","ip":"#{ip}","createtime":"#{now}","operate":
	 * "#{operate}","flag":"#{flag}"}
	 */
	protected Expression fields;

	public void notify(DelegateExecution execution) throws Exception {
		logger.debug("-------------------------------------------------------");
		logger.debug("-------------SqlUpdateByProcessInstanceListener--------");
		logger.debug("-------------------------------------------------------");
		String tableName = table.getExpressionText();
		if (StringUtils.startsWith(tableName, "#{")
				&& StringUtils.endsWith(tableName, "}")) {
			tableName = (String) table.getValue(execution);
		}

		CommandContext commandContext = Context.getCommandContext();
		ExecutionEntity executionEntity = commandContext
				.getExecutionEntityManager().findExecutionById(
						execution.getId());
		String processDefinitionId = executionEntity.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinitionEntity = commandContext
				.getProcessDefinitionEntityManager().findProcessDefinitionById(
						processDefinitionId);
		String processName = processDefinitionEntity.getKey();

		Map<String, Object> params = new java.util.HashMap<String, Object>();

		Map<String, Object> variables = execution.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator<String> iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = iterator.next();
				if (params.get(variableName) == null) {
					Object value = execution.getVariable(variableName);
					params.put(variableName, value);
				}
			}
		}

		params.put(Constants.BUSINESS_KEY, execution.getProcessBusinessKey());
		params.put("processInstanceId", execution.getProcessInstanceId());
		params.put("processDefinitionId", processDefinitionEntity.getId());
		params.put("processName", processName);
		params.put("now", new Date());
		params.put("today", new Date());
		params.put("currentDate", new Date());

		String variable = (String) execution.getVariable(fields
				.getExpressionText());
		JSONObject jsonObject = JSON.parseObject(variable);

		TableModel tableModel = new TableModel();
		tableModel.setTableName(tableName);

		ColumnModel idColumn = new ColumnModel();
		idColumn.setColumnName(processInstanceField.getExpressionText());
		idColumn.setJavaType("String");
		idColumn.setValue(execution.getProcessInstanceId());
		tableModel.setIdColumn(idColumn);

		Iterator<Entry<String, Object>> iterator = jsonObject.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String columnName = (String) entry.getKey();
			String value = (String) entry.getValue();

			if (value.indexOf("#{") != -1 && value.indexOf("}") != -1) {
				value = value.substring(2, value.length() - 1);
				Object x = params.get(value);
				if (x != null) {
					ColumnModel column = new ColumnModel();
					column.setColumnName(columnName);
					column.setJavaType(x.getClass().getSimpleName());
					column.setValue(x);
					tableModel.addColumn(column);
				}
			} else {
				Object x = params.get(value);
				if (x != null) {
					ColumnModel column = new ColumnModel();
					column.setColumnName(columnName);
					column.setJavaType(x.getClass().getSimpleName());
					column.setValue(x);
					tableModel.addColumn(column);
				}
			}
		}

		commandContext.getDbSqlSession().getSqlSession()
				.update("updateBusinessTableDataByPrimaryKey", tableModel);
	}

	public void setFields(Expression fields) {
		this.fields = fields;
	}

	public void setTable(Expression table) {
		this.table = table;
	}

	public void setProcessInstanceField(Expression processInstanceField) {
		this.processInstanceField = processInstanceField;
	}

	public static void main(String[] args) {
		Long lx = 5L;
		System.out.println(lx.getClass().getSimpleName());
	}

}