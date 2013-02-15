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

package org.jpage.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class QueryTool {

	public QueryTool() {
	}

	public static boolean isNotEmpty(Map paramMap, String name) {
		if (paramMap != null && paramMap.get(name) != null) {
			Object obj = paramMap.get(name);
			if (obj instanceof Collection) {
				Collection rows = (Collection) obj;
				if (rows != null && rows.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public static String replaceSQLParas(String str, Map params) {
		if (str == null || params == null) {
			return str;
		}
		Map dataMap = lowerKeyMap(params);
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
			if (str.charAt(i) == '$' && str.charAt(i + 1) == '{') {
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

	public static String replaceInSQLParas(String str, Map params) {
		if (str == null || params == null) {
			return str;
		}
		Map dataMap = lowerKeyMap(params);
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
			if (str.charAt(i) == '$' && str.charAt(i + 1) == '{') {
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

	public static String replaceTextParas(String str, Map params) {
		if (str == null || params == null) {
			return str;
		}
		Map dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '$' && str.charAt(i + 1) == '{') {
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
					sb.append("${").append(temp).append("}");
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

	public static String replaceBlankParas(String str, Map params) {
		if (str == null || params == null) {
			return str;
		}
		Map dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '$' && str.charAt(i + 1) == '{') {
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

	public static String replaceParameters(String str, Map params) {
		if (str == null || params == null) {
			return str;
		}
		Iterator paramNames = params.keySet().iterator();
		while (paramNames.hasNext()) {
			String paramName = (String) paramNames.next();
			String paramValue = (String) params.get(paramName);
			StringBuffer replaceBuffer = new StringBuffer(64).append("${")
					.append(paramName).append("}");
			str = substituteSubString(str, replaceBuffer.toString(), paramValue);
		}
		return str;
	}

	private static String substituteSubString(String input, String find,
			String replace) {
		int find_length = find.length();
		int replace_length = replace.length();
		StringBuffer output = new StringBuffer(input);
		int index = input.indexOf(find);
		int outputOffset = 0;
		while (index > -1) {
			output.replace(index + outputOffset, index + outputOffset
					+ find_length, replace);
			outputOffset = outputOffset + (replace_length - find_length);
			index = input.indexOf(find, index + find_length);
		}
		String result = output.toString();
		return result;
	}

	private static Map lowerKeyMap(Map paramMap) {
		Map dataMap = new HashMap();
		Iterator iterator = paramMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (key == null) {
				continue;
			}
			dataMap.put(key, paramMap.get(key));
			dataMap.put(key.toLowerCase(), paramMap.get(key));
		}
		return dataMap;
	}

	public static Map generateQueryTreeMap(Collection fields, Map map) {
		if (fields == null || map == null) {
			return map;
		}
		Map dataMap = new TreeMap();
		Iterator item = fields.iterator();
		while (item.hasNext()) {
			String field = (String) item.next();
			if (map.get(field) != null) {
				dataMap.put(field, map.get(field));
			}
		}
		return dataMap;
	}

	public static String parseCountSQL(String selectSQL) {
		if (selectSQL == null) {
			return null;
		}
		String temp = selectSQL.toLowerCase();
		int fromIndex = temp.indexOf(" from ");
		int distinctIndex = temp.indexOf(" distinct ");
		int orderByIndex = temp.lastIndexOf(" order by ");
		int groupByIndex = temp.lastIndexOf(" group by ");

		if (fromIndex == -1) {
			fromIndex = 0;
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(" select ");
		if (distinctIndex != -1) {
			buffer.append(" distinct ");
		}
		buffer.append(" count(*) ");
		if (orderByIndex != -1) {
			buffer.append(selectSQL.substring(fromIndex, orderByIndex));
			return buffer.toString();
		} else if (groupByIndex != -1) {
			buffer.append(selectSQL.substring(fromIndex, groupByIndex));
			return buffer.toString();
		} else {
			buffer.append(selectSQL.substring(fromIndex));
		}

		return buffer.toString();
	}

	public static void main(String[] args) throws Exception {
		StringBuffer buffer = FileTools
				.fileToStringBuffer("config/sqlResources.xml");
		Map params = new HashMap();
		params.put("table", "users");
		System.out.println(QueryTool.replaceParameters(buffer.toString(),
				params));
	}

}