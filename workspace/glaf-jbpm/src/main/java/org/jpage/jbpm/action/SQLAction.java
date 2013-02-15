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


package org.jpage.jbpm.action;

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
import org.hibernate.Query;
import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.util.ExpressionUtil;
import org.jpage.util.HibernateUtil;
import org.jpage.util.Tools;

public class SQLAction implements ActionHandler {
	private static final Log logger = LogFactory.getLog(SQLAction.class);

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

	public SQLAction() {

	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("---------------------------------------------------");
		logger.debug("----------------------SQLAction--------------------");
		logger.debug("---------------------------------------------------");
		logger.debug("description:" + description);

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
			logger.debug("���ʽ�����ȡֵΪfalse����ִ�к���������");
			return;
		}

		List<Object> values = new ArrayList<Object>();

		if (elements != null) {
			values = ExpressionUtil.getValues(ctx, elements);
		}

		String sqlx = sql;

		if (sqlx.indexOf("#{tableName}") != -1) {
			String tableName = (String) contextInstance
					.getVariable("tableName");
			if (StringUtils.isNotEmpty(tableName)) {
				sqlx = Tools.replaceIgnoreCase(sqlx, "#{tableName}", tableName);
			}
		}

		if (sqlx.indexOf("#{") != -1 && sqlx.indexOf("}") != -1) {
			sqlx = (String) DefaultExpressionEvaluator.evaluate(sqlx, params);
		}

		logger.debug(sqlx);
		logger.debug(values);

		Query query = ctx.getJbpmContext().getSession().createSQLQuery(sqlx);
		HibernateUtil.fillParameters(query, values);
		int count = query.executeUpdate();
		if (count < 1) {
			logger.error("sql:" + sqlx);
			logger.error("values:" + values);
			throw new JbpmException("update count is " + count);
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
