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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;
import com.glaf.jbpm.util.HibernateUtils;

public class JSONSQLAction implements ActionHandler {
	private static final Log logger = LogFactory.getLog(JSONSQLAction.class);

	private static final long serialVersionUID = 1L;

	protected String json;

	protected String description;

	protected String expression;

	protected String nodeType;

	protected boolean visible;

	protected boolean selected;

	public JSONSQLAction() {

	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("---------------------------------------------------");
		logger.debug("----------------------JSONSQLAction--------------------");
		logger.debug("---------------------------------------------------");

		ContextInstance contextInstance = ctx.getContextInstance();

		Map<String, Object> params = new HashMap<String, Object>();

		Map<String, Object> variables = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Set<Entry<String, Object>> entrySet = variables.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (name != null && value != null && params.get(name) == null) {
					params.put(name, value);
				}
			}
		}

		ProcessInstance processInstance = ctx.getProcessInstance();
		ProcessDefinition processDefinition = processInstance
				.getProcessDefinition();

		params.put("processInstanceId", processInstance.getId());
		params.put("processName", processDefinition.getName());
		params.put("processDefinitionId", processDefinition.getId());
		params.put("processDefinition", processDefinition);
		params.put("processInstance", processInstance);

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

		// {"operation":"delete","table":"TEST_CONTRACT","where":[{"column":"ICONTRACTID"
		// ,"field":"rowId"}]}

		if (StringUtils.isNotEmpty(json)) {
			JSONObject jsonObject = JSON.parseObject(json);
			if (jsonObject != null) {
				StringBuffer buffer = new StringBuffer();
				String tableName = jsonObject.getString("table");
				JSONArray columnsArray = jsonObject.getJSONArray("columns");
				JSONArray whereArray = jsonObject.getJSONArray("where");
				if (StringUtils.equals(jsonObject.getString("operation"),
						"insert")) {
					buffer.append(" insert into ").append(tableName);
					buffer.append(" ( ");
					for (int i = 0; i < columnsArray.size(); i++) {
						JSONObject item = columnsArray.getJSONObject(i);
						String column = item.getString("column");
						buffer.append(column);
						if (i != columnsArray.size() - 1) {
							buffer.append(", ");
						}
					}
					buffer.append(" ) values( ");
					for (int i = 0; i < columnsArray.size(); i++) {
						JSONObject item = columnsArray.getJSONObject(i);
						String field = item.getString("field");
						buffer.append(":").append(field);
						if (i != columnsArray.size() - 1) {
							buffer.append(", ");
						}
					}
					buffer.append(" ) ");
				} else if (StringUtils.equals(
						jsonObject.getString("operation"), "update")) {
					buffer.append(" update ").append(tableName);
					buffer.append(" set ");
					for (int i = 0; i < columnsArray.size(); i++) {
						JSONObject item = columnsArray.getJSONObject(i);
						String column = item.getString("column");
						String field = item.getString("field");
						buffer.append(column).append(" = :").append(field);
						if (i != columnsArray.size() - 1) {
							buffer.append(", ");
						}
					}
					buffer.append(" where 1=1 ");
					for (int i = 0; i < whereArray.size(); i++) {
						JSONObject item = whereArray.getJSONObject(i);
						String column = item.getString("column");
						String field = item.getString("field");
						buffer.append(" and ").append(column).append(" = :")
								.append(field);
					}
				} else if (StringUtils.equals(
						jsonObject.getString("operation"), "delete")) {
					buffer.append(" delete from ").append(tableName);
					buffer.append(" where 1=1 ");
					for (int i = 0; i < whereArray.size(); i++) {
						JSONObject item = whereArray.getJSONObject(i);
						String column = item.getString("column");
						String field = item.getString("field");
						buffer.append(" and ").append(column).append(" = :")
								.append(field);
					}
				}
				String sql = buffer.toString();
				Query query = ctx.getJbpmContext().getSession()
						.createSQLQuery(sql);
				HibernateUtils.fillParameters(query, params);
				int count = query.executeUpdate();
				if (count < 1) {
					logger.error("sql:" + sql);
					logger.error("params:" + params);
					throw new JbpmException("update count is " + count);
				}
			}
		}

	}

	public String getDescription() {
		return description;
	}

	public String getExpression() {
		return expression;
	}

	public String getJson() {
		return json;
	}

	public String getNodeType() {
		return nodeType;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}