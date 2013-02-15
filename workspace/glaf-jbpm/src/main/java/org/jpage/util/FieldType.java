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

import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class FieldType {
	private final static HashMap dataMap = new LinkedHashMap();

	private final static HashMap nameMap = new HashMap();

	private final static Map hibernateMap = new HashMap();

	public static final int STRING_TYPE = Types.VARCHAR;

	public static final int INTEGER_TYPE = Types.INTEGER;

	public static final int DOUBLE_TYPE = Types.DECIMAL;

	public static final int BOOLEAN_TYPE = Types.BOOLEAN;

	public static final int DATE_TYPE = Types.DATE;

	public static final int CHAR_TYPE = Types.CHAR;

	public static final int SHORT_TYPE = Types.SMALLINT;

	public static final int LONG_TYPE = Types.BIGINT;

	public static final int TIMESTAMP_TYPE = Types.TIMESTAMP;

	public static final int TEXT_TYPE = 1000;

	public static final int COLLECTION_TYPE = 5555;

	public static final int BLOB_TYPE = Types.BLOB;

	public static final int CLOB_TYPE = Types.CLOB;

	static {

		dataMap.put("INTEGER", new Integer(INTEGER_TYPE));
		dataMap.put("DOUBLE", new Integer(DOUBLE_TYPE));
		dataMap.put("BOOLEAN", new Integer(BOOLEAN_TYPE));
		dataMap.put("STRING", new Integer(STRING_TYPE));
		dataMap.put("DATE", new Integer(DATE_TYPE));
		dataMap.put("TIMESTAMP", new Integer(TIMESTAMP_TYPE));
		dataMap.put("CLOB", new Integer(CLOB_TYPE));
		dataMap.put("BLOB", new Integer(BLOB_TYPE));
		dataMap.put("CHAR", new Integer(CHAR_TYPE));
		dataMap.put("SHORT", new Integer(SHORT_TYPE));
		dataMap.put("LONG", new Integer(LONG_TYPE));
		dataMap.put("TEXT", new Integer(TEXT_TYPE));
		dataMap.put("COLLECTION", new Integer(COLLECTION_TYPE));

		hibernateMap.put("INT", "int");
		hibernateMap.put("INTEGER", "int");
		hibernateMap.put("LONG", "long");
		hibernateMap.put("SHORT", "short");
		hibernateMap.put("FLOAT", "float");
		hibernateMap.put("DOUBLE", "double");
		hibernateMap.put("CHARACTER", "character");
		hibernateMap.put("BOOLEAN", "boolean");
		hibernateMap.put("STRING", "string");
		hibernateMap.put("DATE", "date");
		hibernateMap.put("TIME", "time");
		hibernateMap.put("DATETIME", "timestamp");
		hibernateMap.put("TIMESTAMP", "timestamp");
		hibernateMap.put("CALENDAR", "calendar");
		hibernateMap.put("CALENDAR_DATE", "calendar_date");
		hibernateMap.put("BIG_DECIMAL", "big_decimal");
		hibernateMap.put("BIG_INTEGER", "big_integer");
		hibernateMap.put("LOCALE", "locale");
		hibernateMap.put("TIMEZONE", "timezone");
		hibernateMap.put("CURRENCY", "currency");
		hibernateMap.put("BINARY", "binary");
		hibernateMap.put("TEXT", "text");
		hibernateMap.put("CLOB", "clob");
		hibernateMap.put("BLOB", "blob");

		nameMap.put(new Integer(INTEGER_TYPE), "整数型");
		nameMap.put(new Integer(DOUBLE_TYPE), "数值型");
		nameMap.put(new Integer(BOOLEAN_TYPE), "布尔值");
		nameMap.put(new Integer(STRING_TYPE), "字符串型");
		nameMap.put(new Integer(DATE_TYPE), "日期型");
		nameMap.put(new Integer(TIMESTAMP_TYPE), "日期时间型");
		nameMap.put(new Integer(CLOB_TYPE), "文本大对象");
		nameMap.put(new Integer(BLOB_TYPE), "二进制大对象");
		nameMap.put(new Integer(CHAR_TYPE), "字符型");
		nameMap.put(new Integer(SHORT_TYPE), "短整数型");
		nameMap.put(new Integer(LONG_TYPE), "长整数型");
		nameMap.put(new Integer(TEXT_TYPE), "长文本型");
	}

	private FieldType() {
	}

	public String toString() {
		return (String) dataMap.get(this);
	}

	public static int getFieldType(String typeName) {
		if (typeName == null) {
			return 0;
		}
		typeName = typeName.trim().toUpperCase();
		if (dataMap.containsKey(typeName)) {
			Integer value = (Integer) dataMap.get(typeName);
			return value.intValue();
		}
		return 0;
	}

	public static String getTypeName(int typeValue) {
		return (String) nameMap.get(new Integer(typeValue));
	}

	public static String getTypeNameScript(String elementName, String typeName) {
		StringBuffer buffer = new StringBuffer();
		if (StringUtils.isBlank(elementName)) {
			elementName = "fieldType";
		}
		if (StringUtils.isBlank(typeName)) {
			typeName = "STRING";
		}
		buffer.append("<select name=\"").append(elementName).append(
				"\" size=\"1\">\n");
		Iterator iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String pType = (String) iterator.next();
			Integer value = (Integer) dataMap.get(pType);
			String display = (String) nameMap.get(value);
			if (display == null) {
				continue;
			}
			buffer.append("<option value=\"").append(pType).append("\"");
			if (StringUtils.equalsIgnoreCase(pType, typeName)) {
				buffer.append(" selected");
			}
			buffer.append(">").append(display).append("</option>\n");
		}
		buffer.append("</select>\n");
		return buffer.toString();
	}

	public static String getAcceptableTypeNameScript(String elementName,
			String typeName) {
		StringBuffer buffer = new StringBuffer();
		if (StringUtils.isBlank(elementName)) {
			elementName = "fieldType";
		}
		if (StringUtils.isBlank(typeName)) {
			typeName = "STRING";
		}
		buffer.append("<select name=\"").append(elementName).append(
				"\" size=\"1\">\n");
		Iterator iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String pType = (String) iterator.next();
			Integer value = (Integer) dataMap.get(pType);
			String display = (String) nameMap.get(value);
			if (display == null || value.intValue() >= 2000) {
				continue;
			}
			buffer.append("<option value=\"").append(pType).append("\"");
			if (StringUtils.equalsIgnoreCase(pType, typeName)) {
				buffer.append(" selected");
			}
			buffer.append(">").append(display).append("</option>\n");
		}
		buffer.append("</select>\n");
		return buffer.toString();
	}

	public static Map getDataType() {
		return hibernateMap;
	}

	public static boolean hasHibernateType(String type) {
		if (hibernateMap.containsKey(type)) {
			return true;
		}
		return false;
	}

	public static String getHibernateType(String type) {
		if (hibernateMap.containsKey(type)) {
			return (String) hibernateMap.get(type);
		}
		return (String) hibernateMap.get("STRING");
	}

	public static void main(String[] args) {
		System.out.println(FieldType.getTypeNameScript(null, "DATE"));
	}
}