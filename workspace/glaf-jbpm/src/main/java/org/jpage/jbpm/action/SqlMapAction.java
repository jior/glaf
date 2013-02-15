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
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.ibatis.MutableSQLMapContainer;
import org.jpage.jbpm.instantiation.CustomFieldInstantiator;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.Tools;

public class SqlMapAction implements ActionHandler {
	private static final Log logger = LogFactory.getLog(SqlMapAction.class);

	public final static String sp = System.getProperty("file.separator");

	private static final long serialVersionUID = 1L;

	private String expression;

	private String sqlmapId;

	private String operation;

	private int businessStatus;

	private int wfStatus;

	private Element elements;

	public SqlMapAction() {
	}

	public int getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(int businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSqlmapId() {
		return sqlmapId;
	}

	public void setSqlmapId(String sqlmapId) {
		this.sqlmapId = sqlmapId;
	}

	public int getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(int wfStatus) {
		this.wfStatus = wfStatus;
	}

	public Element getElements() {
		return elements;
	}

	public void setElements(Element elements) {
		this.elements = elements;
	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("------------------------------------------------------");
		logger.debug("-----------------SqlMapAction-------------------------");
		logger.debug("------------------------------------------------------");

		boolean executable = true;

		Map params = new HashMap();

		ContextInstance contextInstance = ctx.getContextInstance();
		Map variables = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = (String) iterator.next();
				if (params.get(variableName) == null) {
					Object value = contextInstance.getVariable(variableName);
					params.put(variableName, value);
				}
			}
		}

		if (StringUtils.isNotBlank(expression)) {
			if (expression.startsWith("#{") && expression.endsWith("}")) {
				if (logger.isDebugEnabled()) {
					logger.debug("expression->" + expression);
					logger.debug("params->" + params);
				}
				Object value = DefaultExpressionEvaluator.evaluate(expression,
						params);
				if (value != null) {
					if (value instanceof Boolean) {
						Boolean b = (Boolean) value;
						executable = b.booleanValue();
						logger.debug("executable->" + executable);
					}
				}
			}
		}

		if (!executable) {
			logger.debug("在表达式计算后取值为false，不执行后续动作。");
			return;
		}

		String rowId = (String) contextInstance.getVariable(Constant.ROW_ID);
		String actorId = (String) contextInstance
				.getVariable(Constant.PROCESS_START_ACTORID);
		String latestActorId = (String) contextInstance
				.getVariable(Constant.PROCESS_LATEST_ACTORID);

		if (elements != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("elements:" + elements.asXML());
			}
			Object obj = CustomFieldInstantiator.getValue(Map.class, elements);
			if (obj instanceof Map) {
				Map paramMap = (Map) obj;
				Iterator iterator = paramMap.keySet().iterator();
				while (iterator.hasNext()) {
					Object key = iterator.next();
					Object value = paramMap.get(key);
					if (key != null && value != null) {
						params.put(key, value);
					}
				}
			}
		}

		Map varMap = contextInstance.getVariables();
		if (varMap != null && varMap.size() > 0) {
			Iterator iterator = varMap.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = (String) iterator.next();
				if (params.get(variableName) == null) {
					Object value = contextInstance.getVariable(variableName);
					params.put(variableName, value);
				}
			}
		}

		ProcessInstance processInstance = ctx.getProcessInstance();
		String processInstanceId = String.valueOf(processInstance.getId());
		params.put("processInstanceId", processInstanceId);

		Task task = ctx.getTask();
		if (task != null) {
			params.put("taskName", task.getName());
			params.put("taskDescription", task.getDescription());
		}

		TaskInstance taskInstance = ctx.getTaskInstance();
		if (taskInstance != null) {
			params.put("taskInstanceId", String.valueOf(taskInstance.getId()));
			params.put("taskInstanceName", taskInstance.getName());
			params.put("taskInstanceActorId", taskInstance.getActorId());
		}

		if (wfStatus != 0) {
			contextInstance.setVariable("wfStatus", new Integer(wfStatus));
		} else {
			if (contextInstance.getVariable("wfStatus") != null) {
				Integer i = (Integer) contextInstance.getVariable("wfStatus");
				wfStatus = i.intValue();
			}
		}

		if (businessStatus != 0) {
			contextInstance.setVariable("businessStatus", new Integer(
					businessStatus));
		} else {
			if (contextInstance.getVariable("businessStatus") != null) {
				Integer i = (Integer) contextInstance
						.getVariable("businessStatus");
				businessStatus = i.intValue();
			}
		}

		params.put("wfStatus", new Integer(wfStatus));
		params.put("businessStatus", new Integer(businessStatus));
		java.util.Date now = new java.util.Date();

		Iterator iterator008 = params.keySet().iterator();
		while (iterator008.hasNext()) {
			Object key = iterator008.next();
			Object value = params.get(key);
			if (key != null && value != null) {
				if (value instanceof String) {
					String tmp = (String) value;
					if (StringUtils.isNotBlank(tmp)) {
						if (tmp.equals("#{processInstanceId}")) {
							value = String.valueOf(contextInstance
									.getProcessInstance().getId());
						} else if (tmp.equals("#{taskInstanceId}")) {
							if (taskInstance != null) {
								value = String.valueOf(taskInstance.getId());
							}
						} else if (tmp.equals("#{taskName}")) {
							if (task != null) {
								value = task.getName();
							} else {
								if (ctx.getTaskInstance() != null) {
									value = ctx.getTaskInstance().getName();
								}
							}
						} else if (tmp.equals("now()")) {
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
							value = new Long(System.currentTimeMillis());
						} else if (tmp
								.equals(Constant.PROCESS_STARTER_EXPRESSION)) {
							value = actorId;
						} else if (tmp
								.equals(Constant.PROCESS_LATESTER_EXPRESSION)) {
							value = latestActorId;
						} else if (tmp.equals("#{rowId}")) {
							value = rowId;
						} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
							tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
							tmp = Tools.replaceIgnoreCase(tmp, "}", "");
							value = contextInstance.getVariable(tmp);
						} else if (tmp.startsWith("#{") && tmp.endsWith("}")) {
							value = DefaultExpressionEvaluator.evaluate(tmp,
									params);
						}
					}
					params.put(key, value);
				}
			}
		}

		if (StringUtils.isNotBlank(operation)
				&& StringUtils.isNotBlank(sqlmapId)) {
			MutableSQLMapContainer.getContainer().execute(ctx.getJbpmContext(),
					sqlmapId, operation, params);
		}

	}

}
