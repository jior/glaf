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
import com.glaf.activiti.extension.model.ExtensionParamEntity;
import com.glaf.activiti.extension.service.ActivitiExtensionService;
import com.glaf.activiti.util.ThreadHolder;
import com.glaf.core.dao.MyBatisEntityDAO;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.Constants;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.StringTools;

public class ExtensionMyBatisExecutionListener implements ExecutionListener {
 
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(ExtensionMyBatisExecutionListener.class);
	protected Expression extensionId;

	public void notify(DelegateExecution execution) throws Exception {
		logger.debug("-------------------------------------------------------");
		logger.debug("-----------ExtensionMyBatis3ExecutionListener-----------");
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
			extension = ThreadHolder.getExtension();
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

		String businessKey = (String) execution
				.getVariable(Constants.BUSINESS_KEY);

		if (extension != null) {
			String operation = extension.getFieldValue("operation");
			String statementId = extension.getFieldValue("statementId");
			if (StringUtils.isEmpty(operation)) {
				operation = "update";
			}
			if (StringUtils.isNotEmpty(statementId)) {
				java.util.Date now = new java.util.Date();
				List<ExtensionParamEntity> x_params = extension.getParams();
				Iterator<ExtensionParamEntity> iterator = x_params.iterator();
				while (iterator.hasNext()) {
					ExtensionParamEntity param = iterator.next();
					String tmp = param.getValue();
					Object value = param.getValue();
					if (StringUtils.isNotEmpty(tmp)) {
						if (tmp.equals("now()")) {
							value = new java.sql.Date(now.getTime());
						} else if (tmp.equals("date()")) {
							value = new java.sql.Date(now.getTime());
						} else if (tmp.equals("time()")) {
							value = new java.sql.Time(now.getTime());
						} else if (tmp.equals("timestamp()")) {
							value = new java.sql.Timestamp(now.getTime());
						} else if (tmp.equals("dateTime()")) {
							value = new java.sql.Timestamp(now.getTime());
						} else if (tmp.equals("currentTimeMillis()")) {
							value = Long.valueOf(System.currentTimeMillis());
						} else if (tmp.equals("#{businessKey}")) {
							value = businessKey;
						} else if (tmp.equals("#{processInstanceId}")) {
							value = execution.getProcessInstanceId();
						} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
							tmp = StringTools.replaceIgnoreCase(tmp, "#P{", "");
							tmp = StringTools.replaceIgnoreCase(tmp, "}", "");
							value = execution.getVariable(tmp);
						} else if (tmp.startsWith("#{") && tmp.endsWith("}")) {
							value = Mvel2ExpressionEvaluator.evaluate(tmp,
									params);
						}
					}
					params.put(param.getName(), value);
				}

				String tableName = (String) execution.getVariable("tableName");
				params.put("tableName", tableName);
				params.put("processName", processName);
				params.put("processInstanceId",
						execution.getProcessInstanceId());
				logger.debug("execute statement:" + statementId);
				logger.debug("params:" + params);

				MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(
						commandContext.getDbSqlSession().getSqlSession());
				SqlExecutor sqlExecutor = new SqlExecutor();
				sqlExecutor.setOperation(operation);
				sqlExecutor.setStatementId(statementId);
				sqlExecutor.setParameter(params);
				entityDAO.execute(sqlExecutor);

			}
		}
	}

	public void setExtensionId(Expression extensionId) {
		this.extensionId = extensionId;
	}

}