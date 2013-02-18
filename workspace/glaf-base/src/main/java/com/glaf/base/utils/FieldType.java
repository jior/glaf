/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.base.utils;

import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FieldType {
	private final static Map<String, Integer> dataMap = new LinkedHashMap<String, Integer>();

	private final static Map<Integer, String> nameMap = new HashMap<Integer, String>();

	private final static Map<Integer, String> javaTypeMap = new LinkedHashMap<Integer, String>();

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

		nameMap.put(INTEGER_TYPE, "������");
		nameMap.put(DOUBLE_TYPE, "��ֵ��");
		nameMap.put(BOOLEAN_TYPE, "����ֵ");
		nameMap.put(STRING_TYPE, "�ַ�����");
		nameMap.put(DATE_TYPE, "������");
		nameMap.put(TIMESTAMP_TYPE, "����ʱ����");
		nameMap.put(CLOB_TYPE, "�ı������");
		nameMap.put(BLOB_TYPE, "�����ƴ����");
		nameMap.put(CHAR_TYPE, "�ַ���");
		nameMap.put(SHORT_TYPE, "��������");
		nameMap.put(LONG_TYPE, "��������");
		nameMap.put(TEXT_TYPE, "���ı���");
	}

	/**
	 * �����������ƻ�ȡ��������
	 * 
	 * @param typeName
	 *            ��������
	 * @return java.sql.Types ��Ӧ��ֵ
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
	 * ����ֵ������������
	 * 
	 * @param dataType
	 *            java.sql.Types ��Ӧ��ֵ
	 * @return ��������
	 */
	public static String getJavaType(int dataType) {
		if (javaTypeMap.get(dataType) != null) {
			return javaTypeMap.get(dataType);
		}
		return "String";
	}

	/**
	 * ����ֵ������������
	 * 
	 * @param dataType
	 *            java.sql.Types ��Ӧ��ֵ
	 * @return ��������
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
	 * ����ֵ����������������
	 * 
	 * @param dataType
	 *            java.sql.Types ��Ӧ��ֵ
	 * @return ������������
	 */
	public static String getTypeName(int dataType) {
		return nameMap.get(dataType);
	}

	private FieldType() {
	}
}