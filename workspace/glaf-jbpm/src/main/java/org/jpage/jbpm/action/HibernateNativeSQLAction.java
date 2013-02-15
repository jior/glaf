/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.action;

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
import org.jbpm.graph.exe.ExecutionContext;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.persistence.SqlExecutor;
import org.jpage.util.HibernateUtil;
import org.jpage.util.Tools;

public class HibernateNativeSQLAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(HibernateNativeSQLAction.class);

	private static final long serialVersionUID = 1L;

	protected String tableName;

	protected Integer status;

	protected String sql;

	protected String description;

	protected String expression;

	protected String nodeType;

	protected boolean visible;

	protected boolean selected;

	public HibernateNativeSQLAction() {

	}

	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("-------------------------------------------------------");
		logger.debug("----------------HibernateNativeSQLAction---------------");
		logger.debug("-------------------------------------------------------");

		ContextInstance contextInstance = ctx.getContextInstance();

		Map<String, Object> params = new HashMap<String, Object>();

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
		params.put("processInstanceId",
				String.valueOf(contextInstance.getProcessInstance().getId()));

		Map<String, Object> paramMap = null;

		logger.debug("before replace sql:" + sql);
		logger.debug("params:" + params);
		SqlExecutor sqlExecutor = HibernateUtil.replaceSQL(sql, params);
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
				sqlx = Tools.replaceIgnoreCase(sqlx, "#{tableName}", tableName);
			}
		}

		if (sqlx.indexOf("#{") != -1 && sqlx.indexOf("}") != -1) {
			sqlx = (String) DefaultExpressionEvaluator.evaluate(sqlx, params);
		}

		// if (LogUtil.isDebug()) {
		logger.debug(sqlx);
		logger.debug(paramMap);
		// }
		try {
			Query query = ctx.getJbpmContext().getSession()
					.createSQLQuery(sqlx);
			HibernateUtil.fillParameters(query, paramMap);
			int count = query.executeUpdate();
			if (count < 1) {
				logger.error("sql:" + sqlx);
				logger.error("paramMap:" + paramMap);
				throw new JbpmException("update count is " + count);
			}
		} catch (Exception ex) {
			logger.error(sqlx);
			logger.error(paramMap);
			throw new JbpmException(ex);
		}
	}

	public String getDescription() {
		return description;
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
