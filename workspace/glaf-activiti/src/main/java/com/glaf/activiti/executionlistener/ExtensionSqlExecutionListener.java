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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.activiti.extension.factory.ExtensionFactory;
import com.glaf.activiti.extension.model.ExtensionEntity;
import com.glaf.activiti.extension.service.ActivitiExtensionService;
import com.glaf.activiti.util.ExtensionUtils;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.StringTools;

public class ExtensionSqlExecutionListener implements ExecutionListener {
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(ExtensionSqlExecutionListener.class);
	protected Expression extensionId;

	public void notify(DelegateExecution execution) throws Exception {
		logger.debug("-------------------------------------------------------");
		logger.debug("--------------ExtensionSQLExecutionListener------------");
		logger.debug("-------------------------------------------------------");

		ActivitiExtensionService service = ExtensionFactory
				.getActivitiExtensionService();
		CommandContext commandContext = Context.getCommandContext();
		service.setSqlSession(commandContext.getDbSqlSession().getSqlSession());
		ExecutionEntity executionEntity = commandContext.getExecutionEntityManager()
				.findExecutionById(execution.getId());
		String processDefinitionId = executionEntity.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinitionEntity = commandContext
				.getProcessDefinitionEntityManager().findProcessDefinitionById(
						processDefinitionId);
		String processName = processDefinitionEntity.getKey();
		ExtensionEntity extension = null;
		String extendId = null;
		String extensionName = null;

		if (extensionId != null) {
			extensionName = (String) extensionId.getValue(execution);
		}

		if (StringUtils.isNotEmpty(extensionName)) {
			if (extension == null) {
				extendId = processName + "_" + extensionName;
				logger.debug("1--search EX1......");
				extension = service.getExtension(extendId);
			}
			if (extension == null) {
				/**
				 * 取指定流程指定名称的动作定义信息
				 */
				logger.debug("2--search EX2......");
				extension = service.getExtensionListener(processName,
						extensionName);
			}
		}

		if (extension == null) {
			if (StringUtils.isNotEmpty(extensionName)) {
				extension = service.getExtension(extensionName);
			}
		}

		if (extension == null) {
			extension = (ExtensionEntity) execution
					.getVariableLocal("extension");
		}

		if (LogUtils.isDebug()) {
			logger.debug("extension:" + extension.toJsonObject());
		}

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

		params.put("processInstanceId", execution.getProcessInstanceId());
		params.put("processName", processName);

		StringBuffer buffer = new StringBuffer();
		List<Object> values = null;

		if (extension != null) {
			String sql = extension.getFieldValue("sql");
			if (StringUtils.isNotEmpty(sql)) {
				buffer.append(sql);
				values = ExtensionUtils.getValues(params, extension);
			} else {

				int executionType = 0;
				if (extension.getIntFieldValue("executionType") != 0) {
					executionType = extension.getIntFieldValue("executionType");
				}

				ExtensionEntity ex = null;

				switch (executionType) {
				case -1:
				case -5555:
					ex = service.getExtensionListener("extension_5555");
					break;
				case 30:
				case 1000:
					ex = service.getExtensionListener("extension_1000");
					break;
				case 40:
				case 1002:
					ex = service.getExtensionListener("extension_1002");
					break;
				case 50:
				case 9999:
					ex = service.getExtensionListener("extension_9999");
					break;
				default:
					break;
				}

				if (ex != null) {
					String sql2 = ex.getFieldValue("sql");
					if (StringUtils.isNotEmpty(sql2)) {
						buffer.append(sql2);
						values = ExtensionUtils.getValues(params, ex);
					}
				}
			}
		}

		if (buffer.length() == 0) {
			if (execution.getVariable("app_name") != null) {
				extension = service.getExtensionListener("extension_1002");
				if (extension != null) {
					String sql2 = extension.getFieldValue("sql");
					if (StringUtils.isNotEmpty(sql2)) {
						buffer.append(sql2);
						values = ExtensionUtils.getValues(params, extension);
					}
				}
			}
		}

		if (buffer.length() > 0) {
			String sql = buffer.toString();

			if (sql.indexOf("#{tableName}") != -1) {
				String tableName = (String) execution.getVariable("tableName");
				if (StringUtils.isNotEmpty(tableName)) {
					sql = StringTools.replace(sql, "#{tableName}", tableName);
				}
			} else if (sql.indexOf("${tableName}") != -1) {
				String tableName = (String) execution.getVariable("tableName");
				if (StringUtils.isNotEmpty(tableName)) {
					sql = StringTools.replace(sql, "${tableName}", tableName);
				}
			}

			if (sql.indexOf("#{") != -1 && sql.indexOf("}") != -1) {
				sql = (String) Mvel2ExpressionEvaluator.evaluate(sql, params);
			} else if (sql.indexOf("${") != -1 && sql.indexOf("}") != -1) {
				sql = (String) Mvel2ExpressionEvaluator.evaluate(sql, params);
			}

			if (LogUtils.isDebug()) {
				logger.debug(sql);
				logger.debug(values);
			}

			Connection con = null;
			try {
				con = commandContext.getDbSqlSession().getSqlSession()
						.getConnection();
				PreparedStatement psmt = con.prepareStatement(sql);
				JdbcUtils.fillStatement(psmt, values);
				psmt.executeUpdate();
				psmt.close();
				psmt = null;
			} catch (SQLException ex) {
				throw ex;
			}
		}
	}

	public void setExtensionId(Expression extensionId) {
		this.extensionId = extensionId;
	}
}