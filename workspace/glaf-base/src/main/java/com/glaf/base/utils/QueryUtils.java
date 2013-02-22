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

package com.glaf.base.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class QueryUtils {

	public final static String newline = System.getProperty("line.separator");

	public static String getSelectFilter(String name) {
		return getSelectFilter(name, true);
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

	public static String getSelectFilter(String name, boolean isString) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(newline).append("		 <select id=\"x_filter_").append(name)
				.append("\" name=\"x_filter_").append(name)
				.append("\"	class=\"span2\">");
		buffer.append(newline).append(
				"			<option value=\"\">----��ѡ��----</option>");
		buffer.append(newline).append("			<option value=\"=\">����</option>");
		buffer.append(newline).append("			<option value=\"!=\">������</option>");
		if (!isString) {
			buffer.append(newline).append(
					"			<option value=\">=\">���ڻ����</option>");
			buffer.append(newline).append("			<option value=\">\">����</option>");
			buffer.append(newline).append(
					"			<option value=\"<=\">С�ڻ����</option>");
			buffer.append(newline).append("			<option value=\"<\">С��</option>");
		} else {
			buffer.append(newline).append(
					"			<option value=\"LIKE\">����</option>");
			buffer.append(newline).append(
					"			<option value=\"NOT LIKE\">������</option>");
		}
		buffer.append(newline).append("		 </select>");
		return buffer.toString();
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

	private static Map<String, Object> lowerKeyMap(Map<String, Object> paramMap) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Set<Entry<String, Object>> entrySet = paramMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			dataMap.put(key, value);
			dataMap.put(key.toLowerCase(), value);
		}
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
				if (dataMap.get(temp) != null) {
					String value = dataMap.get(temp).toString();
					sb.append(value);
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
		boolean flag = false; // ƥ���־
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
		boolean flag = false; // ƥ���־
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