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

package com.glaf.jbpm.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;

@SuppressWarnings("unchecked")
public class ExpressionUtils {

	private ExpressionUtils() {

	}

	public static List<Object> getValues(ExecutionContext ctx, Element elements) {
		List<Object> values = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		ContextInstance contextInstance = ctx.getContextInstance();
		Object rowId = contextInstance.getVariable(Constant.PROCESS_ROWID);
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
						value = contextInstance.getProcessInstance().getId();
					} else if ("#{taskInstanceId}".equals(tmp)) {
						TaskInstance taskInstance = ctx.getTaskInstance();
						if (taskInstance != null) {
							value = taskInstance.getId();
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
						value = System.currentTimeMillis();
					} else if (tmp.equals("#{rowId}")) {
						value = rowId;
					} else if (tmp.equals("#{uuid}")) {
						value = UUID32.getUUID();
					} else if (tmp.equals("#{actorId}")) {
						value = ctx.getJbpmContext().getActorId();
					} else if (tmp.equals("#{status}")) {
						value = contextInstance.getVariable("status");
					} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
						tmp = StringTools.replaceIgnoreCase(tmp, "#P{", "");
						tmp = StringTools.replaceIgnoreCase(tmp, "}", "");
						value = contextInstance.getVariable(tmp);
					} else if (tmp.startsWith("#{") && tmp.endsWith("}")) {
						Map<String, Object> params = new java.util.concurrent.ConcurrentHashMap<String, Object>();
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

	public static Map<String, Object> getParameters(ExecutionContext ctx,
			Element elements) {
		Map<String, Object> values = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		ContextInstance contextInstance = ctx.getContextInstance();
		Object rowId = contextInstance.getVariable(Constant.PROCESS_ROWID);
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
						value = contextInstance.getProcessInstance().getId();
					} else if (tmp.equals("#{taskInstanceId}")) {
						TaskInstance taskInstance = ctx.getTaskInstance();
						if (taskInstance != null) {
							value = taskInstance.getId();
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
						value = System.currentTimeMillis();
					} else if (tmp.equals("#{rowId}")) {
						value = rowId;
					} else if (tmp.equals("#{uuid}")) {
						value = UUID32.getUUID();
					} else if (tmp.equals("#{actorId}")) {
						value = ctx.getJbpmContext().getActorId();
					} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
						tmp = StringTools.replaceIgnoreCase(tmp, "#P{", "");
						tmp = StringTools.replaceIgnoreCase(tmp, "}", "");
						value = contextInstance.getVariable(tmp);
					} else if (tmp.startsWith("#{") && tmp.endsWith("}")) {
						Map<String, Object> params = new java.util.concurrent.ConcurrentHashMap<String, Object>();
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
}