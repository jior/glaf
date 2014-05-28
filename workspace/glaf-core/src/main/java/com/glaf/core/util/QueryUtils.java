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

import java.util.Collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.LowerLinkedMap;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.query.QueryCondition;

public class QueryUtils {
	protected static final Log logger = LogFactory.getLog(QueryUtils.class);

	public final static String newline = System.getProperty("line.separator");

	public static SqlExecutor getMyBatisAndConditionSql(
			List<QueryCondition> conditions) {
		SqlExecutor se = new SqlExecutor();
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		StringBuffer buffer = new StringBuffer(1000);
		if (conditions != null && !conditions.isEmpty()) {
			int index = 0;
			Iterator<QueryCondition> iter = conditions.iterator();
			while (iter.hasNext()) {
				index++;
				QueryCondition c = iter.next();
				buffer.append(FileUtils.newline);
				String column = c.getColumn();
				String filter = c.getFilter();
				String alias = c.getAlias();
				params.put("param_" + index, c.getValue());
				String operator = " = ";
				if (StringUtils.isNotEmpty(filter)) {
					if (SearchFilter.GREATER_THAN.equals(filter)) {
						operator = SearchFilter.GREATER_THAN;
					} else if (SearchFilter.GREATER_THAN_OR_EQUAL
							.equals(filter)) {
						operator = SearchFilter.GREATER_THAN_OR_EQUAL;
					} else if (SearchFilter.LESS_THAN.equals(filter)) {
						operator = SearchFilter.LESS_THAN;
					} else if (SearchFilter.LESS_THAN_OR_EQUAL.equals(filter)) {
						operator = SearchFilter.LESS_THAN_OR_EQUAL;
					} else if (SearchFilter.LIKE.equals(filter)) {
						operator = SearchFilter.LIKE;
					} else if (SearchFilter.NOT_LIKE.equals(filter)) {
						operator = SearchFilter.NOT_LIKE;
					} else if (SearchFilter.EQUALS.equals(filter)) {
						operator = SearchFilter.EQUALS;
					} else if (SearchFilter.NOT_EQUALS.equals(filter)) {
						operator = SearchFilter.NOT_EQUALS;
					} else {
						operator = SearchFilter.EQUALS;
					}
				}
				if (StringUtils.isNotEmpty(alias)) {
					buffer.append(" and ").append(alias).append(".")
							.append(column).append(" ").append(operator)
							.append(" #{").append("param_" + index)
							.append("} ");
				} else {
					buffer.append(" and ").append(column).append(" ")
							.append(operator).append(" #{")
							.append("param_" + index).append("} ");
				}
			}
		}

		se.setSql(buffer.toString());
		se.setParameter(params);

		return se;
	}

	public static String getSelectFilter(String name) {
		return getSelectFilter(name, true);
	}

	public static String getSelectFilter(String name, boolean isString) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(newline).append("		 <select id=\"x_filter_").append(name)
				.append("\" name=\"x_filter_").append(name)
				.append("\"	class=\"span2\">");
		buffer.append(newline).append(
				"			<option value=\"\">----请选择----</option>");
		buffer.append(newline).append("			<option value=\"=\">等于</option>");
		buffer.append(newline).append("			<option value=\"!=\">不等于</option>");
		if (!isString) {
			buffer.append(newline).append(
					"			<option value=\">=\">大于或等于</option>");
			buffer.append(newline).append("			<option value=\">\">大于</option>");
			buffer.append(newline).append(
					"			<option value=\"<=\">小于或等于</option>");
			buffer.append(newline).append("			<option value=\"<\">小于</option>");
		} else {
			buffer.append(newline).append(
					"			<option value=\"LIKE\">包含</option>");
			buffer.append(newline).append(
					"			<option value=\"NOT LIKE\">不包含</option>");
		}
		buffer.append(newline).append("		 </select>");
		return buffer.toString();
	}

	public static String getSQLCondition(List<String> rows, String alias,
			String columnName) {
		if (rows == null || rows.size() <= 0) {
			return "";
		}
		int index = 1;
		StringBuffer whereBuffer = new StringBuffer();
		whereBuffer.append(" and ( ").append(alias).append(".")
				.append(columnName).append(" in ('0') ");
		StringBuffer idsBuffer = new StringBuffer();
		Iterator<String> iter = rows.iterator();
		while (iter.hasNext()) {
			String x = iter.next();
			idsBuffer.append('\'').append(x).append('\'');
			if (index == 500) {
				whereBuffer.append(" or ").append(alias).append(".")
						.append(columnName).append(" in (")
						.append(idsBuffer.toString()).append(')');
				index = 0;
				idsBuffer.delete(0, idsBuffer.length());
			}
			if (iter.hasNext() && index > 0) {
				idsBuffer.append(',');
			}
			index++;
		}
		if (idsBuffer.length() > 0) {
			whereBuffer.append(" or ").append(alias).append(".")
					.append(columnName).append(" in (")
					.append(idsBuffer.toString()).append(')');
			idsBuffer.delete(0, idsBuffer.length());
		}
		whereBuffer.append(" ) ");
		return whereBuffer.toString();
	}

	public static boolean isNotEmpty(Map<String, Object> paramMap, String name) {
		if (paramMap != null && paramMap.get(name) != null) {
			Object obj = paramMap.get(name);
			if (obj instanceof Collection<?>) {
				Collection<?> rows = (Collection<?>) obj;
				if (rows != null && rows.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public static Map<String, Object> lowerKeyMap(Map<String, Object> paramMap) {
		Map<String, Object> dataMap = new LowerLinkedMap();
		dataMap.putAll(paramMap);
		return dataMap;
	}

	public static String replaceBlankParas(String str,
			Map<String, Object> params) {
		if (str == null || params == null) {
			return str;
		}
		Map<String, Object> dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '#' && str.charAt(i + 1) == '{') {
				sb.append(str.substring(end, i));
				begin = i + 2;
				flag = true;
			}
			if (flag && str.charAt(i) == '}') {
				String temp = str.substring(begin, i);
				temp = temp.toLowerCase();
				Object value = dataMap.get(temp);
				if (value != null) {
					if (value instanceof java.util.Date) {
						java.util.Date date = (java.util.Date) value;
						sb.append(DateUtils.getDateTime(date));
					} else {
						sb.append(value);
					}
					end = i + 1;
					flag = false;
				} else {
					sb.append("");
					end = i + 1;
					flag = false;
				}
			}
			if (i == str.length() - 1) {
				sb.append(str.substring(end, i + 1));
			}
		}
		return sb.toString();
	}

	public static String replaceInSQLParas(String str,
			Map<String, Object> params) {
		if (str == null || params == null) {
			return str;
		}
		Map<String, Object> dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int left = 0;
		int end = 0;
		boolean flag = false; // 匹配标志
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				sb.append(str.substring(end, i));
				left = i;
				end = i;
			}
			if (str.charAt(i) == '#' && str.charAt(i + 1) == '{') {
				begin = i + 2;
				flag = true;
			}
			if (flag && str.charAt(i) == '}') {
				String temp = str.substring(begin, i);
				temp = temp.toLowerCase();
				if (dataMap.get(temp) != null) {
					String value = dataMap.get(temp).toString();
					sb.append(str.substring(left, begin - 2));
					sb.append(value);
					end = i + 1;
					flag = false;
				} else {
					sb.append(str.charAt(left));
					sb.append(" 1=1 ");
					end = str.indexOf(")", i);
				}
			}
			if (i == str.length() - 1) {
				sb.append(str.substring(end, i + 1));
			}
		}
		String newString = sb.toString();
		return newString;
	}

	public static String replaceSQLParas(String str, Map<String, Object> params) {
		if (str == null || params == null) {
			return str;
		}
		Map<String, Object> dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int left = 0;
		int end = 0;
		boolean flag = false; // 匹配标志
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				sb.append(str.substring(end, i));
				left = i;
				end = i;
			}
			if (str.charAt(i) == '#' && str.charAt(i + 1) == '{') {
				begin = i + 2;
				flag = true;
			}
			if (flag && str.charAt(i) == '}') {
				String temp = str.substring(begin, i);
				temp = temp.toLowerCase();
				if (dataMap.get(temp) != null) {
					String value = dataMap.get(temp).toString();
					sb.append(str.substring(left, begin - 2));
					sb.append(value);
					end = i + 1;
					flag = false;
				} else {
					sb.append(str.charAt(left));
					sb.append(" 1=1 ");
					end = str.indexOf(")", i);
				}
			}
			if (i == str.length() - 1) {
				sb.append(str.substring(end, i + 1));
			}
		}
		String newString = sb.toString();
		return newString;
	}

	public static String replaceSQLVars(String sql) {
		sql = StringTools.replace(sql, "${now}",
				SystemConfig.getCurrentYYYYMMDD());
		sql = StringTools.replace(sql, "#{now}",
				SystemConfig.getCurrentYYYYMMDD());
		sql = StringTools.replace(sql,
				ExpressionConstants.INPUT_YYYYMM_EXPRESSION,
				SystemConfig.getInputYYYYMM());
		sql = StringTools.replace(sql,
				ExpressionConstants.INPUT_YYYYMMDD_EXPRESSION,
				SystemConfig.getInputYYYYMMDD());
		sql = StringTools.replace(sql,
				ExpressionConstants.CURRENT_YYYYMM_EXPRESSION,
				SystemConfig.getCurrentYYYYMM());
		sql = StringTools.replace(sql,
				ExpressionConstants.CURRENT_YYYYMMDD_EXPRESSION,
				SystemConfig.getCurrentYYYYMMDD());
		return sql;
	}

	public static String replaceTextParas(String str, Map<String, Object> params) {
		if (str == null || params == null) {
			return str;
		}
		Map<String, Object> dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '#' && str.charAt(i + 1) == '{') {
				sb.append(str.substring(end, i));
				begin = i + 2;
				flag = true;
			}
			if (flag && str.charAt(i) == '}') {
				String temp = str.substring(begin, i);
				temp = temp.toLowerCase();
				if (dataMap.get(temp) != null) {
					String value = dataMap.get(temp).toString();
					sb.append(value);
					end = i + 1;
					flag = false;
				} else {
					sb.append("#{").append(temp).append('}');
					end = i + 1;
					flag = false;
				}
			}
			if (i == str.length() - 1) {
				sb.append(str.substring(end, i + 1));
			}
		}
		return sb.toString();
	}

	private QueryUtils() {
	}

}