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

package com.glaf.activiti.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.Constants;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.StringTools;

public class ExecutionUtils {
	private static final Log logger = LogFactory.getLog(ExecutionUtils.class);

	@SuppressWarnings("unchecked")
	public static void executeSqlUpdate(DelegateExecution execution,
			Expression sql) {
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
		params.put("now", new java.util.Date());

		if (sql != null) {
			String sqlx = sql.getExpressionText();

			if (sqlx.indexOf("#{tableName}") != -1) {
				String tableName = (String) execution.getVariable("tableName");
				if (StringUtils.isNotEmpty(tableName)) {
					sqlx = StringTools.replace(sqlx, "#{tableName}", tableName);
				}
			} else if (sqlx.indexOf("${tableName}") != -1) {
				String tableName = (String) execution.getVariable("tableName");
				if (StringUtils.isNotEmpty(tableName)) {
					sqlx = StringTools.replace(sqlx, "${tableName}", tableName);
				}
			}

			sqlx = StringTools.replaceIgnoreCase(sqlx, "${", "#{");

			List<Object> values = new java.util.ArrayList<Object>();
			SqlExecutor sqlExecutor = JdbcUtils.rebuildSQL(sqlx, params);
			sqlx = sqlExecutor.getSql();
			if (sqlExecutor.getParameter() != null) {
				if (sqlExecutor.getParameter() instanceof List) {
					List<Object> list = (List<Object>) sqlExecutor
							.getParameter();
					values.addAll(list);
				}
			}

			logger.debug(sqlx);
			logger.debug(values);

			Connection con = null;
			try {
				con = commandContext.getDbSqlSession().getSqlSession()
						.getConnection();
				PreparedStatement psmt = con.prepareStatement(sqlx);
				JdbcUtils.fillStatement(psmt, values);
				psmt.executeUpdate();
				psmt.close();
				psmt = null;
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}

		}
	}

	public static void executeUpdate(DelegateExecution execution, Expression sql) {
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

		if (sql != null) {
			String sqlx = sql.getExpressionText();

			if (sqlx.indexOf("#{tableName}") != -1) {
				String tableName = (String) execution.getVariable("tableName");
				if (StringUtils.isNotEmpty(tableName)) {
					sqlx = StringTools.replace(sqlx, "#{tableName}", tableName);
				}
			} else if (sqlx.indexOf("${tableName}") != -1) {
				String tableName = (String) execution.getVariable("tableName");
				if (StringUtils.isNotEmpty(tableName)) {
					sqlx = StringTools.replace(sqlx, "${tableName}", tableName);
				}
			}

			sqlx = StringTools.replaceIgnoreCase(sqlx, "${", "#{");

			logger.debug(sqlx);
			logger.debug(params);

			params.put("updateSql", sqlx);
			try {
				commandContext.getDbSqlSession().getSqlSession()
						.update("updateSql", params);
			} catch (Exception ex) {
				logger.error(sqlx);
				logger.error(params);
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	private ExecutionUtils() {

	}

}