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

package com.glaf.core.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.query.BaseQuery;
import com.glaf.core.query.QueryCondition;
import com.glaf.core.service.EntityService;

public class HttpQueryUtils {
	protected final static Log logger = LogFactory.getLog(HttpQueryUtils.class);

	protected static Boolean getBooleanValue(HttpServletRequest request,
			String name) {
		String value = request.getParameter(name);
		if (request.getAttribute(name) != null) {
			value = (String) request.getAttribute(name);
		}
		if (StringUtils.equalsIgnoreCase(value, "true")) {
			return true;
		}
		return false;
	}

	protected static Object getObjectValue(HttpServletRequest request,
			String name) {
		Object value = getParameter(request, name);
		if (request.getAttribute(name) != null) {
			value = request.getAttribute(name);
		}
		return value;
	}

	public static String getParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (StringUtils.isEmpty(value)) {
			String[] values = request.getParameterValues(name);
			if (values != null && values.length > 0) {
				StringBuffer buff = new StringBuffer(1000);
				for (int i = 0; i < values.length; i++) {
					if (i < values.length - 1) {
						if (StringUtils.isNotEmpty(values[i])) {
							buff.append(values[i]).append(',');
						}
					} else {
						if (StringUtils.isNotEmpty(values[i])) {
							buff.append(values[i]);
						}
					}
				}
				if (StringUtils.isNotEmpty(buff.toString())) {
					value = buff.toString();
				}
			}
		}
		return value;
	}

	protected static String getStringValue(HttpServletRequest request,
			String name) {
		String value = getParameter(request, name);
		if (request.getAttribute(name) != null) {
			value = (String) request.getAttribute(name);
		}
		return value;
	}

	public static BaseQuery prepareQuery(HttpServletRequest request,
			HttpServletResponse response, String serviceKey,
			Map<String, Object> paramMap) {
		BaseQuery query = new BaseQuery();
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		List<QueryCondition> conditions = new java.util.ArrayList<QueryCondition>();
		JSONObject rootJson = new JSONObject();
		JSONObject paramJson = new JSONObject();
		JSONObject queryJson = new JSONObject();

		String qt = getStringValue(request, "qt");
		String qid = getStringValue(request, "qid");
		String field = getStringValue(request, "field");
		boolean removeLast = getBooleanValue(request, "removeLast");

		queryJson.put("removeLast", removeLast);

		if (serviceKey != null) {
			queryJson.put("serviceKey", serviceKey);
		}
		if (qt != null) {
			queryJson.put("qt", qt);
		}
		if (qid != null) {
			queryJson.put("qid", qid);
		}
		if (field != null) {
			queryJson.put("field", field);
		}

		/**
		 * 将IP地址加入Cookie头中防止客户端篡改
		 */
		String ip = RequestUtils.getIPAddress(request);
		String cookieKey = ip + "_mx_query_" + serviceKey;
		cookieKey = Hex.bytesToHex(cookieKey.getBytes());

		String content = null;

		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				/**
				 * 从Cookie中获取以前的查询参数
				 */
				if (StringUtils.equals(cookie.getName(), cookieKey)) {
					content = cookie.getValue();
				}
			}
		}

		JSONObject oldJson = null;

		if (StringUtils.isNotEmpty(content)) {
			String str = new String(Hex.hexToBytes(content));
			if (str != null) {
				oldJson = JSON.parseObject(str);
			}
		}

		Object value = null;
		String fieldValue = null;
		QueryCondition currentCondition = null;

		if (oldJson != null) {
			logger.debug("@@previous query json:\n" + oldJson.toJSONString());
			JSONObject paramJx = oldJson.getJSONObject("params");
			if (paramJx != null && !paramJx.isEmpty()) {
				Set<String> keySet = paramJx.keySet();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					if (paramJx.getString(key) != null) {
						params.put(key, paramJx.getString(key));
						paramJson.put(key, paramJx.getString(key));
					}
				}
			}

			JSONObject conjx = oldJson.getJSONObject("currentCondition");
			if (conjx != null && conjx.get("value") != null) {
				currentCondition = new QueryCondition();
				currentCondition.setAlias(conjx.getString("alias"));
				currentCondition.setName(conjx.getString("name"));
				currentCondition.setColumn(conjx.getString("column"));
				currentCondition.setType(conjx.getString("type"));
				currentCondition.setFilter(conjx.getString("filter"));
				currentCondition.setStringValue(conjx.getString("stringValue"));
				currentCondition.setValue(conjx.get("value"));
			}
		}

		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = getStringValue(request, paramName);
			if (paramName != null) {
				if (StringUtils.isNotEmpty(paramValue)) {
					params.put(paramName, paramValue);
					paramJson.put(paramName, paramValue);
				}
			}
		}

		if (paramMap != null && !paramMap.isEmpty()) {
			if (paramMap != null && paramMap.size() > 0) {
				Set<Entry<String, Object>> entrySet = paramMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String name = entry.getKey();
					Object v = entry.getValue();
					if (name != null && v != null) {
						params.put(name, v);
						paramJson.put(name, v);
					}
				}
			}
		}

		if (qt == null) {
			qt = (String) params.get("qt");
		}
		if (qid == null) {
			qid = (String) params.get("qid");
		}
		if (field == null) {
			field = (String) params.get("field");
		}

		/**
		 * 获取本次查询的查询参数
		 */
		if (StringUtils.isNotEmpty(qid)) {
			EntityService entityService = ContextFactory
					.getBean("entityService");
			/**
			 * 根据查询参数定义编号获取查询参数列表
			 */
			List<Object> rows = entityService.getList(qid, params);
			if (rows != null && rows.size() > 0) {
				for (Object object : rows) {
					if (object instanceof ColumnDefinition) {
						ColumnDefinition column = (ColumnDefinition) object;
						query.addColumn(column.getName(),
								column.getColumnName());
						if (StringUtils.isNotEmpty(field)
								&& StringUtils.equals(field, column.getName())) {
							fieldValue = request.getParameter(field);
							if (StringUtils.isNotEmpty(fieldValue)) {
								String alias = getStringValue(request, "alias");
								String filter = getStringValue(request,
										"filter");
								if (StringUtils.isEmpty(alias)) {
									alias = getStringValue(request, field
											+ "_alias");
								}
								if (StringUtils.isEmpty(filter)) {
									filter = getStringValue(request, field
											+ "_filter");
								}
								String type = column.getJavaType();
								if (StringUtils.equalsIgnoreCase(type,
										"datetime")
										|| StringUtils.equalsIgnoreCase(type,
												"Date")) {
									type = "Date";
									value = fieldValue;
									if (StringUtils.isEmpty(filter)) {
										filter = SearchFilter.GREATER_THAN_OR_EQUAL;
									}
								} else if (StringUtils.equalsIgnoreCase(type,
										"i4")
										|| StringUtils.equalsIgnoreCase(type,
												"Integer")) {
									type = "Integer";
									value = Integer.parseInt(fieldValue);
									if (StringUtils.isEmpty(filter)) {
										filter = SearchFilter.GREATER_THAN_OR_EQUAL;
									}
								} else if (StringUtils.equalsIgnoreCase(type,
										"i8")
										|| StringUtils.equalsIgnoreCase(type,
												"Long")) {
									type = "Long";
									value = Long.parseLong(fieldValue);
									if (StringUtils.isEmpty(filter)) {
										filter = SearchFilter.GREATER_THAN_OR_EQUAL;
									}
								} else if (StringUtils.equalsIgnoreCase(type,
										"r8")
										|| StringUtils.equalsIgnoreCase(type,
												"Double")) {
									type = "Double";
									value = Double.parseDouble(fieldValue);
									if (StringUtils.isEmpty(filter)) {
										filter = SearchFilter.GREATER_THAN_OR_EQUAL;
									}
								} else if (StringUtils.equalsIgnoreCase(type,
										"String")) {
									type = "String";
									value = fieldValue;
									if (StringUtils.isEmpty(filter)) {
										filter = SearchFilter.LIKE;
									}
									if (StringUtils.equals(filter,
											SearchFilter.LIKE)) {
										value = "%" + fieldValue + "%";
									}
								}
								if (value != null && filter != null) {
									currentCondition = new QueryCondition();
									currentCondition.setType(type);
									currentCondition.setAlias(alias);
									currentCondition.setName(field);
									currentCondition.setColumn(column
											.getColumnName());
									currentCondition.setFilter(filter);
									currentCondition.setStringValue(fieldValue);
									currentCondition.setValue(value);
								}
							}
						}
					}
				}
			}
		}

		/**
		 * 如果在Cookie或Session中找到以前的查询参数，将Ta们解码后放到条件列表中
		 */
		if (oldJson != null) {
			JSONArray array = oldJson.getJSONArray("conditions");
			if (array != null) {
				// logger.debug("previous conditions:" + array.toJSONString());
				int size = array.size();
				for (int i = 0; i < size; i++) {
					JSONObject json = array.getJSONObject(i);
					QueryCondition c = new QueryCondition();
					c.setAlias(json.getString("alias"));
					c.setName(json.getString("name"));
					c.setColumn(json.getString("column"));
					c.setType(json.getString("type"));
					c.setFilter(json.getString("filter"));
					String val = json.getString("stringValue");

					if (StringUtils.equals(c.getType(), "Date")) {
						c.setValue(DateUtils.toDate(val));
						c.setStringValue(val);
					} else if (StringUtils.equals(c.getType(), "Integer")) {
						c.setValue(Integer.parseInt(val));
						c.setStringValue(val);
					} else if (StringUtils.equals(c.getType(), "Long")) {
						c.setValue(Long.parseLong(val));
						c.setStringValue(val);
					} else if (StringUtils.equals(c.getType(), "Double")) {
						c.setValue(Double.parseDouble(val));
						c.setStringValue(val);
					} else if (StringUtils.equals(c.getType(), "Boolean")) {
						c.setValue(Boolean.valueOf(val));
						c.setStringValue(val);
					} else {
						c.setValue(json.get("value"));
						c.setStringValue(val);
					}

					if (!conditions.contains(c)) {
						conditions.add(c);
					}
				}
			}
			/**
			 * 去除最近一次的查询条件
			 */
			if (removeLast && conditions.size() > 0) {
				conditions.remove(conditions.size() - 1);
			}
		}

		/**
		 * 如果不是从以前的结果中查找，则清空以前的查询条件
		 */
		if (StringUtils.equals("R", qt)) {
			logger.debug("#### clear conditions");
			conditions.clear();
		}

		if (currentCondition != null && currentCondition.getValue() != null) {
			query.setCurrentQueryCondition(currentCondition);
			if (!conditions.contains(currentCondition)) {
				conditions.add(currentCondition);
			}
			JSONObject json = new JSONObject();
			if (currentCondition.getAlias() != null) {
				json.put("alias", currentCondition.getAlias());
			}
			json.put("name", currentCondition.getName());
			json.put("column", currentCondition.getColumn());
			json.put("type", currentCondition.getType());
			json.put("filter", currentCondition.getFilter());
			json.put("value", currentCondition.getValue());
			json.put("stringValue", currentCondition.getStringValue());
			json.put("index", 0);
			rootJson.put("currentCondition", json);
		}

		if (conditions.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			int index = 0;
			for (QueryCondition c : conditions) {
				if (c.getValue() != null) {
					JSONObject json = new JSONObject();
					if (c.getAlias() != null) {
						json.put("alias", c.getAlias());
					}
					json.put("name", c.getName());
					json.put("column", c.getColumn());
					json.put("type", c.getType());
					json.put("filter", c.getFilter());
					json.put("value", c.getValue());
					json.put("stringValue", c.getStringValue());
					json.put("index", index++);
					jsonArray.add(json);
				}
			}
			rootJson.put("conditions", jsonArray);
		}

		rootJson.put("query", queryJson);
		rootJson.put("params", paramJson);

		String jsonText = rootJson.toJSONString();
		logger.debug("prepare query json:\n" + jsonText);

		jsonText = Hex.bytesToHex(jsonText.getBytes());

		if (response != null) {
			Cookie cookie = new Cookie(cookieKey, jsonText);
			response.addCookie(cookie);
		}

		query.setParameter(params);
		query.getParameters().putAll(params);

		logger.debug("#conditions:" + conditions);

		for (QueryCondition condition : conditions) {
			query.addCondition(condition);
		}

		return query;
	}

	public static BaseQuery prepareQuery(HttpServletRequest request,
			String serviceKey) {
		return prepareQuery(request, null, serviceKey, null);
	}

	public static BaseQuery prepareQuery(HttpServletRequest request,
			String serviceKey, Map<String, Object> paramMap) {
		return prepareQuery(request, null, serviceKey, paramMap);
	}

}