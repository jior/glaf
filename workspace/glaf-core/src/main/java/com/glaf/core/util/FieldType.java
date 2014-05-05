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

import java.sql.Types;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FieldType {
	private final static ConcurrentMap<String, Integer> dataMap = new ConcurrentHashMap<String, Integer>();

	private final static ConcurrentMap<Integer, String> nameMap = new ConcurrentHashMap<Integer, String>();

	private final static ConcurrentMap<Integer, String> javaTypeMap = new ConcurrentHashMap<Integer, String>();

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

	public static final int BLOB_TYPE = Types.BLOB;

	public static final int CLOB_TYPE = Types.CLOB;

	static {

		dataMap.put("INT", INTEGER_TYPE);
		dataMap.put("INTEGER", INTEGER_TYPE);
		dataMap.put("DOUBLE", DOUBLE_TYPE);
		dataMap.put("BOOLEAN", BOOLEAN_TYPE);
		dataMap.put("STRING", STRING_TYPE);
		dataMap.put("DATE", DATE_TYPE);
		dataMap.put("TIMESTAMP", TIMESTAMP_TYPE);
		dataMap.put("CLOB", CLOB_TYPE);
		dataMap.put("BLOB", BLOB_TYPE);
		dataMap.put("CHAR", CHAR_TYPE);
		dataMap.put("SHORT", SHORT_TYPE);
		dataMap.put("LONG", LONG_TYPE);
		dataMap.put("TEXT", TEXT_TYPE);

		javaTypeMap.put(Types.BIGINT, "Long");
		javaTypeMap.put(Types.BINARY, "Blob");
		javaTypeMap.put(Types.BLOB, "Blob");
		javaTypeMap.put(Types.VARBINARY, "Blob");
		javaTypeMap.put(Types.LONGVARBINARY, "Blob");
		javaTypeMap.put(Types.BOOLEAN, "Boolean");
		javaTypeMap.put(Types.CHAR, "String");
		javaTypeMap.put(Types.DATE, "Date");
		javaTypeMap.put(Types.DECIMAL, "Double");
		javaTypeMap.put(Types.DOUBLE, "Double");
		javaTypeMap.put(Types.FLOAT, "Double");
		javaTypeMap.put(Types.INTEGER, "Integer");
		javaTypeMap.put(Types.LONGNVARCHAR, "String");
		javaTypeMap.put(Types.LONGVARCHAR, "String");
		javaTypeMap.put(Types.NCHAR, "String");
		javaTypeMap.put(Types.NVARCHAR, "String");
		javaTypeMap.put(Types.NUMERIC, "Double");
		javaTypeMap.put(Types.REAL, "Double");
		javaTypeMap.put(Types.SMALLINT, "Integer");
		javaTypeMap.put(Types.TINYINT, "Integer");
		javaTypeMap.put(Types.TIMESTAMP, "Date");
		javaTypeMap.put(Types.VARCHAR, "String");

		nameMap.put(INTEGER_TYPE, "整数型");
		nameMap.put(DOUBLE_TYPE, "数值型");
		nameMap.put(BOOLEAN_TYPE, "布尔值");
		nameMap.put(STRING_TYPE, "字符型");
		nameMap.put(DATE_TYPE, "日期型");
		nameMap.put(TIMESTAMP_TYPE, "日期时间型");
		nameMap.put(CLOB_TYPE, "长文本型");
		nameMap.put(BLOB_TYPE, "二进制数据");
		nameMap.put(CHAR_TYPE, "字符型");
		nameMap.put(SHORT_TYPE, "短整数型");
		nameMap.put(LONG_TYPE, "长整数型");
		nameMap.put(TEXT_TYPE, "长文本型");
	}

	/**
	 * 根据类型名称获取数据类型
	 * 
	 * @param typeName
	 *            类型名称
	 * @return java.sql.Types 对应的值
	 */
	public static int getFieldType(String typeName) {
		if (typeName == null) {
			return 0;
		}
		typeName = typeName.trim().toUpperCase();
		if (dataMap.containsKey(typeName)) {
			Integer value = dataMap.get(typeName);
			return value.intValue();
		}
		return 0;
	}

	/**
	 * 根据值返回类型名称
	 * 
	 * @param dataType
	 *            java.sql.Types 对应的值
	 * @return 类型名称
	 */
	public static String getJavaType(int dataType) {
		if (javaTypeMap.get(dataType) != null) {
			return javaTypeMap.get(dataType);
		}
		return "String";
	}

	/**
	 * 根据值返回类型名称
	 * 
	 * @param dataType
	 *            java.sql.Types 对应的值
	 * @return 类型名称
	 */
	public static String getType(int dataType) {
		Iterator<String> iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String typeName = iterator.next();
			Integer type = dataMap.get(typeName);
			if (type.intValue() == dataType) {
				return typeName;
			}
		}
		return "STRING";
	}

	/**
	 * 根据值返回类型中文名称
	 * 
	 * @param dataType
	 *            java.sql.Types 对应的值
	 * @return 类型中文名称
	 */
	public static String getTypeName(int dataType) {
		return nameMap.get(dataType);
	}

	private FieldType() {
	}
}