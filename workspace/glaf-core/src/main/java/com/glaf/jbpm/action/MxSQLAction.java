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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;
import com.glaf.jbpm.util.ExpressionUtils;

public class MxSQLAction implements ActionHandler {
	private static final Log logger = LogFactory.getLog(MxSQLAction.class);

	private static final long serialVersionUID = 1L;

	protected String tableName;

	protected Integer status;

	protected String sql;

	protected String description;

	protected String expression;

	protected String nodeType;

	protected boolean visible;

	protected boolean selected;

	private transient Element elements;

	public MxSQLAction() {

	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("---------------------------------------------------");
		logger.debug("----------------------SQLAction--------------------");
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

		List<Object> values = new ArrayList<Object>();

		if (elements != null) {
			values = ExpressionUtils.getValues(ctx, elements);
		}

		sql = QueryUtils.replaceSQLParas(sql, params);

		if (sql.indexOf("#{tableName}") != -1) {
			String tableName = (String) contextInstance
					.getVariable("tableName");
			if (StringUtils.isNotEmpty(tableName)) {
				sql = StringTools.replace(sql, "#{tableName}", tableName);
			}
		}

		if (sql.indexOf("#{") != -1 && sql.indexOf("}") != -1) {
			sql = (String) DefaultExpressionEvaluator.evaluate(sql, params);
		}

		if (LogUtils.isDebug()) {
			logger.debug(sql);
			logger.debug(values);
		}

		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ctx.getJbpmContext().getConnection();
			psmt = con.prepareStatement(sql);
			JdbcUtils.fillStatement(psmt, values);
			psmt.executeUpdate();
			psmt.close();
			psmt = null;
		} finally {
			if (psmt != null) {
				psmt.close();
				psmt = null;
			}
		}
	}

	public String getDescription() {
		return description;
	}

	public Element getElements() {
		return elements;
	}

	public String getExpression() {
		return expression;
	}

	public String getNodeType() {
		return nodeType;
	}

	public String getSql() {
		return sql;
	}

	public Integer getStatus() {
		return status;
	}

	public String getTableName() {
		return tableName;
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

	public void setElements(Element elements) {
		this.elements = elements;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}