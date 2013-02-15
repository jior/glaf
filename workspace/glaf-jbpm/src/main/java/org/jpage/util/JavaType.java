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


package org.jpage.util;

public class JavaType {
	public static final String JAVA_UTIL_DATE_TYPE = "java.util.Date";

	public static final String JAVA_SQL_DATE_TYPE = "java.sql.Date";

	public static final String JAVA_SQL_TIME_TYPE = "java.sql.Time";

	public static final String JAVA_SQL_TIMESTAMP_TYPE = "java.sql.Timestamp";

	public static final String JAVA_SQL_CLOB_TYPE = "java.sql.Clob";

	public static final String JAVA_SQL_BLOB_TYPE = "java.sql.Blob";

	public static final String JAVA_IO_SERIALIZABLE_TYPE = "java.io.Serializable";

	public static final String BYTE_ARRAY_TYPE = "byte[]";

	public static final String STRING_TYPE = "java.lang.String";

	public static final String BOOLEAN_TYPE = "boolean";

	public static final String BYTE_TYPE = "byte";

	public static final String CHAR_TYPE = "char";

	public static final String SHORT_TYPE = "short";

	public static final String INTEGER_TYPE = "int";

	public static final String LONG_TYPE = "long";

	public static final String FLOAT_TYPE = "float";

	public static final String DOUBLE_TYPE = "double";

	public static int getFieldType(String javaType) {
		if (javaType == null) {
			return -1;
		}
		int type = 0;
		if (JavaType.JAVA_UTIL_DATE_TYPE.equals(javaType)) {
			type = FieldType.TIMESTAMP_TYPE;
		} else if (JavaType.JAVA_SQL_DATE_TYPE.equals(javaType)) {
			type = FieldType.DATE_TYPE;
		} else if (JavaType.JAVA_SQL_TIME_TYPE.equals(javaType)) {
			type = FieldType.TIMESTAMP_TYPE;
		} else if (JavaType.JAVA_SQL_TIMESTAMP_TYPE.equals(javaType)) {
			type = FieldType.TIMESTAMP_TYPE;
		} else if (JavaType.JAVA_SQL_CLOB_TYPE.equals(javaType)) {
			type = FieldType.CLOB_TYPE;
		} else if (JavaType.JAVA_SQL_BLOB_TYPE.equals(javaType)) {
			type = FieldType.BLOB_TYPE;
		} else if (JavaType.JAVA_IO_SERIALIZABLE_TYPE.equals(javaType)) {
			type = FieldType.BLOB_TYPE;
		} else if (JavaType.BYTE_ARRAY_TYPE.equals(javaType)) {
			type = FieldType.BLOB_TYPE;
		} else if (JavaType.STRING_TYPE.equals(javaType)) {
			type = FieldType.STRING_TYPE;
		} else if (JavaType.BOOLEAN_TYPE.equals(javaType)) {
			type = FieldType.BOOLEAN_TYPE;
		} else if (JavaType.CHAR_TYPE.equals(javaType)) {
			type = FieldType.STRING_TYPE;
		} else if (JavaType.SHORT_TYPE.equals(javaType)) {
			type = FieldType.SHORT_TYPE;
		} else if (JavaType.INTEGER_TYPE.equals(javaType)) {
			type = FieldType.INTEGER_TYPE;
		} else if (JavaType.LONG_TYPE.equals(javaType)) {
			type = FieldType.LONG_TYPE;
		} else if (JavaType.FLOAT_TYPE.equals(javaType)) {
			type = FieldType.DOUBLE_TYPE;
		} else if (JavaType.DOUBLE_TYPE.equals(javaType)) {
			type = FieldType.DOUBLE_TYPE;
		} else {
			type = FieldType.STRING_TYPE;
		}
		return type;
	}

}
