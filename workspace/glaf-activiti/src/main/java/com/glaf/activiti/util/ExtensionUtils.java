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

package com.glaf.activiti.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.glaf.activiti.extension.model.ExtensionEntity;
import com.glaf.activiti.extension.model.ExtensionParamEntity;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;

public class ExtensionUtils {

	public static List<Object> getValues(Map<String, Object> paramMap,
			ExtensionEntity extension) {
		java.util.Date now = new java.util.Date();
		List<Object> values = new java.util.ArrayList<Object>();
		List<ExtensionParamEntity> x_params = extension.getParams();
		Iterator<ExtensionParamEntity> iterator = x_params.iterator();
		while (iterator.hasNext()) {
			ExtensionParamEntity param = iterator.next();
			String key = param.getValue();
			Object value = param.getValue();
			if (key != null && value != null) {
				String tmp = param.getValue();
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
						value = Long.valueOf(System.currentTimeMillis());
					} else if (tmp.equals("#{businessKey}")) {
						value = ParamUtils.getString(paramMap, "businessKey");
					} else if (tmp.equals("#{processInstanceId}")) {
						value = ParamUtils.getString(paramMap,
								"processInstanceId");
					} else if (tmp.equals("#{processName}")) {
						value = ParamUtils.getString(paramMap, "processName");
					} else if (tmp.equals("#{status}")) {
						value = paramMap.get("status");
					} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
						tmp = StringTools.replaceIgnoreCase(tmp, "#P{", "");
						tmp = StringTools.replaceIgnoreCase(tmp, "}", "");
						value = paramMap.get(tmp);
					} else if (tmp.startsWith("#{") && tmp.endsWith("}")) {
						value = Mvel2ExpressionEvaluator
								.evaluate(tmp, paramMap);
					}
				}
			}
			values.add(value);
		}
		return values;
	}
}