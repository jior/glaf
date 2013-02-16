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

package org.jpage.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.util.DateTools;
import org.jpage.util.FieldType;
import org.jpage.util.SQLFormatter;
import org.jpage.util.Tools;

public class ExecutorHelper {

	private final static Log logger = LogFactory.getLog(ExecutorHelper.class);

	public Executor getExecutor(String sql, List params, Map valueMap) {
		Executor executor = new Executor();
		List values = new ArrayList();
		if (params != null && valueMap != null) {
			Iterator iterator = params.iterator();
			while (iterator.hasNext()) {
				SQLParameter param = (SQLParameter) iterator.next();
				String name = param.getName();
				int type = param.getType();
				Object obj = valueMap.get(name);
				if (obj == null) {
					obj = valueMap.get(name.toLowerCase());
				}
				if (obj == null) {
					String defaultValue = param.getDefaultValue();
					if (StringUtils.isNotBlank(defaultValue)) {
						if (defaultValue.startsWith("${")
								&& defaultValue.endsWith("}")) {
							String paramName = Tools.replaceIgnoreCase(defaultValue, "${", "");
							paramName = paramName.replaceAll("}", "");
							if (valueMap.get(paramName) != null) {
								obj = valueMap.get(paramName);
							}
						}
					}
				}

				if (obj == null) {
					/**
					 * 如果该参数是必须的，而参数值为空，则不能继续往下处理，抛出异常。
					 */
					if (param.getRequired() > 0) {
						throw new RuntimeException("parameter \"" + name
								+ "\" is not set");
					}
					values.add(null);
					continue;
				}

				String value = null;
				java.util.Date date = null;

				if (obj instanceof String) {
					value = (String) obj;
				}

				switch (type) {
				case FieldType.DATE_TYPE:
					if (!(obj instanceof java.util.Date)) {
						if (StringUtils.isNotBlank(value)) {
							date = DateTools.toDate(value);
							obj = date;
						}
					}
					break;
				case FieldType.TIMESTAMP_TYPE:
					if (!(obj instanceof java.sql.Timestamp)) {
						if (obj instanceof java.util.Date) {
							date = (java.util.Date) obj;
						} else {
							if (StringUtils.isNotBlank(value)) {
								date = DateTools.toDate(value);
							}
						}
						obj = DateTools.toTimestamp(date);
					}
					break;
				case FieldType.DOUBLE_TYPE:
					if (!(obj instanceof Double)) {
						if (StringUtils.isNotBlank(value)) {
							Double d = new Double(value);
							obj = d;
						}
					}
					break;
				case FieldType.BOOLEAN_TYPE:
					if (!(obj instanceof Boolean)) {
						if (StringUtils.isNotBlank(value)) {
							Boolean b = new Boolean(value);
							obj = b;
						}
					}
					break;
				case FieldType.SHORT_TYPE:
					if (!(obj instanceof Short)) {
						if (StringUtils.isNotBlank(value)) {
							Short s = new Short(value);
							obj = s;
						}
					}
					break;
				case FieldType.INTEGER_TYPE:
					if (!(obj instanceof Integer)) {
						if (StringUtils.isNotBlank(value)) {
							Integer integer = new Integer(value);
							obj = integer;
						}
					}
					break;
				case FieldType.LONG_TYPE:
					if (!(obj instanceof Long)) {
						if (StringUtils.isNotBlank(value)) {
							Long l = new Long(value);
							obj = l;
						}
					}
					break;
				default:
					break;
				}
				values.add(obj);
			}
			executor.setValues(values.toArray());
		}

		executor.setQuery(sql);

		SQLFormatter formatter = new SQLFormatter();
		sql = formatter.format(sql);
		
		logger.info("[resolve value] = " + values);
		logger.info("[resolve query] = " + sql);
		return executor;
	}

}
