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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.StringTools;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;
import com.glaf.jbpm.util.Constant;
import com.glaf.jbpm.util.HibernateUtils;

public class HibernateMultiNativeSQLAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(HibernateMultiNativeSQLAction.class);

	private static final long serialVersionUID = 1L;

	protected String bindName;

	protected String description;

	protected String expression;

	protected String sql;

	protected String tableName;

	public HibernateMultiNativeSQLAction() {

	}

	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("-------------------------------------------------------");
		logger.debug("---------------HibernateMultiNativeSQLAction-----------");
		logger.debug("-------------------------------------------------------");

		ContextInstance contextInstance = ctx.getContextInstance();

		Map<String, Object> params = new java.util.concurrent.ConcurrentHashMap<String, Object>();

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

		String bindValue = (String) contextInstance.getVariable(bindName);
		if (StringUtils.isNotEmpty(bindValue)) {
			JSONArray array = JSON.parseArray(bindValue);
			if (array != null && !array.isEmpty()) {
				java.util.Date now = new java.util.Date();
				Object rowId = contextInstance
						.getVariable(Constant.PROCESS_ROWID);
				for (int i = 0, len = array.size(); i < len; i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					Map<String, Object> dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
					dataMap.putAll(params);
					Iterator<Entry<String, Object>> iterator = jsonObject
							.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						String key = (String) entry.getKey();
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof String) {
								String tmp = (String) value;
								if ("#{processInstanceId}".equals(tmp)) {
									value = contextInstance
											.getProcessInstance().getId();
								} else if ("#{taskInstanceId}".equals(tmp)) {
									TaskInstance taskInstance = ctx
											.getTaskInstance();
									if (taskInstance != null) {
										value = taskInstance.getId();
									}
								} else if ("#{taskName}".equals(tmp)) {
									Task task = ctx.getTask();
									if (task != null) {
										value = task.getName();
									} else {
										if (ctx.getTaskInstance() != null) {
											value = ctx.getTaskInstance()
													.getName();
										}
									}
								} else if (tmp.equals("now()")) {
									value = new java.sql.Date(now.getTime());
								} else if (tmp.equals("date()")) {
									value = new java.sql.Date(now.getTime());
								} else if (tmp.equals("time()")) {
									value = new java.sql.Time(now.getTime());
								} else if (tmp.equals("timestamp()")) {
									value = new java.sql.Timestamp(
											now.getTime());
								} else if (tmp.equals("#{rowId}")) {
									value = rowId;
								} else if (tmp.equals("#{actorId}")) {
									value = ctx.getJbpmContext().getActorId();
								} else if (tmp.equals("#{status}")) {
									value = contextInstance
											.getVariable("status");
								} else if (tmp.startsWith("#P{")
										&& tmp.endsWith("}")) {
									tmp = StringTools.replaceIgnoreCase(tmp,
											"#P{", "");
									tmp = StringTools.replaceIgnoreCase(tmp,
											"}", "");
									value = contextInstance.getVariable(tmp);
								} else if (tmp.startsWith("#{")
										&& tmp.endsWith("}")) {
									Map<String, Object> vars = contextInstance
											.getVariables();
									if (vars != null && vars.size() > 0) {
										Iterator<String> it = vars.keySet()
												.iterator();
										while (it.hasNext()) {
											String variableName = it.next();
											if (dataMap.get(variableName) == null) {
												Object object = contextInstance
														.getVariable(variableName);
												dataMap.put(variableName,
														object);
											}
										}
									}
									value = DefaultExpressionEvaluator
											.evaluate(tmp, dataMap);
								}

								if (key.endsWith("_datetime")) {
									java.util.Date date = DateUtils.toDate(tmp);
									value = date;
									dataMap.put(
											key.substring(0, key.length() - 9),
											value);
								}

								dataMap.put(key, value);
							}
							if (value instanceof Long) {
								if (key.endsWith("_datetime")) {
									Long time = (Long) value;
									java.util.Date date = new java.util.Date(
											time);
									value = date;
									dataMap.put(
											key.substring(0, key.length() - 9),
											value);
								}
								dataMap.put(key, value);
							} else {
								dataMap.put(key, value);
							}
						}
					}

					SqlExecutor sqlExecutor = HibernateUtils.replaceSQL(sql,
							dataMap);
					String sqlx = sqlExecutor.getSql();
					if (sqlExecutor.getParameter() != null) {
						logger.debug("sqlExecutor.getParameter():"
								+ sqlExecutor.getParameter());
						if (sqlExecutor.getParameter() instanceof Map) {
							dataMap = (Map<String, Object>) sqlExecutor
									.getParameter();
						}
					}

					if (sqlx.indexOf("#{tableName}") != -1) {
						String tableName = (String) contextInstance
								.getVariable("tableName");
						if (StringUtils.isNotEmpty(tableName)) {
							sqlx = StringTools.replaceIgnoreCase(sqlx,
									"#{tableName}", tableName);
						}
					}

					logger.debug(sqlx);
					logger.debug(dataMap);

					Query query = ctx.getJbpmContext().getSession()
							.createSQLQuery(sqlx);
					HibernateUtils.fillParameters(query, dataMap);
					query.executeUpdate();
				}
			}
		}
	}

	public String getBindName() {
		return bindName;
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

	public void setBindName(String bindName) {
		this.bindName = bindName;
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