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

package com.glaf.jbpm.action;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.StringTools;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;
import com.glaf.jbpm.util.HibernateUtils;

public class HibernateNativeSQLAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(HibernateNativeSQLAction.class);

	private static final long serialVersionUID = 1L;

	protected String tableName;

	protected String sql;

	protected String description;

	protected String expression;

	public HibernateNativeSQLAction() {

	}

	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("-------------------------------------------------------");
		logger.debug("----------------HibernateNativeSQLAction---------------");
		logger.debug("-------------------------------------------------------");

		ContextInstance contextInstance = ctx.getContextInstance();

		Map<String, Object> params = new java.util.HashMap<String, Object>();

		Map<String, Object> variables = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Set<Entry<String, Object>> entrySet = variables.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (name != null && value != null) {
					params.put(name, value);
				}
			}
		}

		boolean executable = true;

		if (StringUtils.isNotEmpty(expression)) {
			if (expression.startsWith("#{") && expression.endsWith("}")) {
				Object value = DefaultExpressionEvaluator.evaluate(expression,
						params);
				if (value != null) {
					if (value instanceof Boolean) {
						Boolean b = (Boolean) value;
						executable = b.booleanValue();
					}
				}
			}
		}

		if (!executable) {
			logger.debug("表达式计算后取值为false，不执行后续动作。");
			return;
		}

		params.put("now", new java.util.Date());
		params.put("date", new java.util.Date());
		params.put("timestamp", new java.util.Date());
		params.put("dateTime", new java.util.Date());
		params.put("actorId", ctx.getJbpmContext().getActorId());
		ProcessInstance processInstance = ctx.getProcessInstance();
		params.put("processInstanceId", processInstance.getId());

		Map<String, Object> paramMap = null;

		logger.debug("before replace sql:" + sql);
		logger.debug("params:" + params);
		SqlExecutor sqlExecutor = HibernateUtils.replaceSQL(sql, params);
		String sqlx = sqlExecutor.getSql();
		if (sqlExecutor.getParameter() != null) {
			logger.debug("sqlExecutor.getParameter():"
					+ sqlExecutor.getParameter());
			if (sqlExecutor.getParameter() instanceof Map) {
				paramMap = (Map<String, Object>) sqlExecutor.getParameter();
			}
		}

		if (sqlx.indexOf("#{tableName}") != -1) {
			String tableName = (String) contextInstance
					.getVariable("tableName");
			if (StringUtils.isNotEmpty(tableName)) {
				sqlx = StringTools.replaceIgnoreCase(sqlx, "#{tableName}",
						tableName);
			}
		}

		if (sqlx.indexOf("#{") != -1 && sqlx.indexOf("}") != -1) {
			sqlx = (String) DefaultExpressionEvaluator.evaluate(sqlx, params);
		}

		logger.debug(sqlx);
		logger.debug(paramMap);

		Query query = ctx.getJbpmContext().getSession().createSQLQuery(sqlx);
		HibernateUtils.fillParameters(query, paramMap);
		int count = query.executeUpdate();
		if (count < 1) {
			logger.error("sql:" + sqlx);
			logger.error("paramMap:" + paramMap);
			throw new JbpmException("update count is " + count);
		}
	}

	public String getDescription() {
		return description;
	}

	public String getExpression() {
		return expression;
	}

	public String getSql() {
		return sql;
	}

	public String getTableName() {
		return tableName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}