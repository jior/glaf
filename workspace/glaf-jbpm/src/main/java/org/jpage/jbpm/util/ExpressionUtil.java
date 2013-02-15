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

package org.jpage.jbpm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.instantiation.CustomFieldInstantiator;
import org.jpage.util.Tools;

@SuppressWarnings("unchecked")
public class ExpressionUtil {

	public static Map<String, Object> getParameters(ExecutionContext ctx,
			Element elements) {
		Map<String, Object> values = new HashMap<String, Object>();
		ContextInstance contextInstance = ctx.getContextInstance();
		String rowId = (String) contextInstance.getVariable(Constant.ROW_ID);
		if (elements != null) {
			Map<String, Object> dataMap = (Map<String, Object>) CustomFieldInstantiator
					.getValue(Map.class, elements);
			java.util.Date now = new java.util.Date();
			Set<Entry<String, Object>> entrySet = dataMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key == null || value == null) {
					continue;
				}
				if (value instanceof String
						&& StringUtils.isNotEmpty(value.toString())) {
					String tmp = (String) value;
					if (tmp.equals("#{processInstanceId}")) {
						value = String.valueOf(contextInstance
								.getProcessInstance().getId());
					} else if (tmp.equals("#{taskInstanceId}")) {
						TaskInstance taskInstance = ctx.getTaskInstance();
						if (taskInstance != null) {
							value = String.valueOf(taskInstance.getId());
						}
					} else if (tmp.equals("#{taskName}")) {
						Task task = ctx.getTask();
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
						value = Long.valueOf(System.currentTimeMillis());
					} else if (tmp.equals("#{rowId}")) {
						value = rowId;
					} else if (tmp.equals("#{actorId}")) {
						value = ctx.getJbpmContext().getActorId();
					} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
						tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
						tmp = Tools.replaceIgnoreCase(tmp, "}", "");
						value = contextInstance.getVariable(tmp);
					} else if (tmp.startsWith("#{") && tmp.endsWith("}")) {
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, Object> vars = contextInstance
								.getVariables();
						if (vars != null && vars.size() > 0) {
							Iterator<String> it = vars.keySet().iterator();
							while (it.hasNext()) {
								String variableName = it.next();
								if (params.get(variableName) == null) {
									Object object = contextInstance
											.getVariable(variableName);
									params.put(variableName, object);
								}
							}
						}
						value = DefaultExpressionEvaluator
								.evaluate(tmp, params);
					}
				}
				values.put(key.toString(), value);
			}
		}

		return values;
	}

	public static List<Object> getValues(ExecutionContext ctx, Element elements) {
		List<Object> values = new ArrayList<Object>();
		ContextInstance contextInstance = ctx.getContextInstance();
		String rowId = (String) contextInstance.getVariable(Constant.ROW_ID);
		if (elements != null) {
			Map<String, Object> dataMap = (Map<String, Object>) CustomFieldInstantiator
					.getValue(Map.class, elements);
			java.util.Date now = new java.util.Date();
			Set<Entry<String, Object>> entrySet = dataMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key == null || value == null) {
					continue;
				}
				if (value instanceof String
						&& StringUtils.isNotEmpty(value.toString())) {
					String tmp = (String) value;
					if ("#{processInstanceId}".equals(tmp)) {
						value = String.valueOf(contextInstance
								.getProcessInstance().getId());
					} else if ("#{taskInstanceId}".equals(tmp)) {
						TaskInstance taskInstance = ctx.getTaskInstance();
						if (taskInstance != null) {
							value = String.valueOf(taskInstance.getId());
						}
					} else if ("#{taskName}".equals(tmp)) {
						Task task = ctx.getTask();
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
						value = Long.valueOf(System.currentTimeMillis());
					} else if (tmp.equals("#{rowId}")) {
						value = rowId;
					} else if (tmp.equals("#{actorId}")) {
						value = ctx.getJbpmContext().getActorId();
					} else if (tmp.equals("#{status}")) {
						value = contextInstance.getVariable("status");
					} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
						tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
						tmp = Tools.replaceIgnoreCase(tmp, "}", "");
						value = contextInstance.getVariable(tmp);
					} else if (tmp.startsWith("#{") && tmp.endsWith("}")) {
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, Object> vars = contextInstance
								.getVariables();
						if (vars != null && vars.size() > 0) {
							Iterator<String> it = vars.keySet().iterator();
							while (it.hasNext()) {
								String variableName = it.next();
								if (params.get(variableName) == null) {
									Object object = contextInstance
											.getVariable(variableName);
									params.put(variableName, object);
								}
							}
						}
						value = DefaultExpressionEvaluator
								.evaluate(tmp, params);
					}
				}
				values.add(value);
			}
		}
		return values;
	}

	private ExpressionUtil() {

	}
}
