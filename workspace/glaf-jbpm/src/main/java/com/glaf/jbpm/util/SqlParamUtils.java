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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;

public class SqlParamUtils {

	private SqlParamUtils() {

	}

	@SuppressWarnings("unchecked")
	public static List<Object> getValues(Map<String, Object> paramMap,
			SqlExecutor executor) {
		java.util.Date now = new java.util.Date();
		List<Object> values = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		Object parameter = executor.getParameter();
		if (parameter instanceof Map) {
			Map<String, Object> params = (Map<String, Object>) parameter;
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key != null && value != null) {
					if (value instanceof String) {
						String tmp = (String) value;
						if (StringUtils.isNotEmpty(tmp)) {
							if (tmp.equals("now()")) {
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
								value = Long
										.valueOf(System.currentTimeMillis());
							} else if (tmp.equals("#{rowId}")) {
								value = paramMap.get("rowId");
							} else if (tmp.equals("#{processInstanceId}")) {
								value = ParamUtils.getString(paramMap,
										"processInstanceId");
							} else if (tmp.equals("#{processName}")) {
								value = ParamUtils.getString(paramMap,
										"processName");
							} else if (tmp.equals("#{status}")) {
								value = paramMap.get("status");
							} else if (tmp.startsWith("#P{")
									&& tmp.endsWith("}")) {
								tmp = StringTools.replaceIgnoreCase(tmp, "#P{",
										"");
								tmp = StringTools.replaceIgnoreCase(tmp, "}",
										"");
								value = paramMap.get(tmp);
							} else if (tmp.startsWith("#{")
									&& tmp.endsWith("}")) {
								value = DefaultExpressionEvaluator.evaluate(
										tmp, paramMap);
							}
						}
					}
				}
				values.add(value);
			}
		}

		return values;
	}
}