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
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.glaf.core.util;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SqlUtils {

	public static String getBlobValue(ResultSet result, String strField)
			throws java.sql.SQLException {
		String strValueReturn = "";
		Blob blob = result.getBlob(strField);
		if (result.wasNull()) {
			strValueReturn = "";
		} else {
			int length = (int) blob.length();
			if (length > 0) {
				strValueReturn = new String(blob.getBytes(1, length));
			}
		}
		return strValueReturn;
	}

	public static String getDateTimeValue(ResultSet result, String strField,
			String strDateFormat) throws java.sql.SQLException {
		// Format the current time.
		String strValueReturn;
		Timestamp timestamp = result.getTimestamp(strField);
		if (result.wasNull()) {
			strValueReturn = "";
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(strDateFormat);
			strValueReturn = formatter.format(timestamp);
		}
		return strValueReturn;
	}

	public static String getDateValue(ResultSet result, String strField)
			throws java.sql.SQLException {
		return getDateValue(result, strField, "yyyy-MM-dd");
	}

	public static String getDateValue(ResultSet result, String strField,
			String strDateFormat) throws java.sql.SQLException {
		// Format the current time.
		String strValueReturn;
		Date date = result.getDate(strField);
		if (result.wasNull()) {
			strValueReturn = "";
		} else {
			// SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
			SimpleDateFormat formatter = new SimpleDateFormat(strDateFormat);
			strValueReturn = formatter.format(date);
		}
		return strValueReturn;
	}

	public static String getStringCallableStatement(CallableStatement cs,
			int position) throws java.sql.SQLException {
		String strValueReturn = cs.getString(position);
		if (strValueReturn == null) {
			strValueReturn = "";
		}
		return strValueReturn;
	}

	public static String getValue(ResultSet result, int position)
			throws java.sql.SQLException {
		String strValueReturn = result.getString(position);
		if (result.wasNull()) {
			strValueReturn = "";
		}
		return strValueReturn;
	}

	public static String getValue(ResultSet result, String strField)
			throws java.sql.SQLException {
		String strValueReturn = result.getString(strField);
		if (result.wasNull()) {
			strValueReturn = "";
		}
		return strValueReturn;
	}

	public static boolean setValue(PreparedStatement ps, int position,
			int tipo, String defaultValue, String strValue) {
		try {
			if (strValue == null) {
				strValue = defaultValue;
			}
			if (strValue != null) {
				if (strValue.compareTo("") == 0) {
					ps.setNull(position, tipo);
				} else {
					switch (tipo) {
					case 2:
						ps.setLong(position, Long.valueOf(strValue).longValue());
						break;
					case 12:
						ps.setString(position, strValue);
						break;
					case java.sql.Types.LONGVARCHAR:
						ps.setString(position, strValue);
						break;
					case 0:
						ps.setDouble(position, Double.valueOf(strValue)
								.doubleValue());
						break;
					}
				}
			} else {
				ps.setNull(position, tipo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
}
