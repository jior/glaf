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

package com.glaf.core.domain.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;

public class SysDataLogTableUtils {

	public static void createTable(int year) {
		int now = DateUtils.getNowYearMonthDay();
		List<Integer> list = DateUtils.getYearDays(year);
		for (Integer day : list) {
			if (day < now) {
				continue;
			}
			String tableName = "SYS_DATA_LOG_" + day;
			boolean success = false;
			int retry = 0;
			while (retry < 2 && !success) {
				try {
					retry++;
					createTable(tableName);
					DBUtils.createIndex("default", tableName, "ACTORID_",
							tableName + "_ACT_IDX");
					success = true;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static TableDefinition createTable(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition column1 = new ColumnDefinition();
		column1.setColumnName("ACTORID_");
		column1.setJavaType("String");
		column1.setLength(50);
		tableDefinition.addColumn(column1);

		ColumnDefinition column11 = new ColumnDefinition();
		column11.setColumnName("ACCOUNTID_");
		column11.setJavaType("Long");
		tableDefinition.addColumn(column11);

		ColumnDefinition column12 = new ColumnDefinition();
		column12.setColumnName("OPENID_");
		column12.setJavaType("String");
		column12.setLength(200);
		tableDefinition.addColumn(column12);

		ColumnDefinition column2 = new ColumnDefinition();
		column2.setColumnName("IP_");
		column2.setJavaType("String");
		column2.setLength(100);
		tableDefinition.addColumn(column2);

		ColumnDefinition column3 = new ColumnDefinition();
		column3.setColumnName("CREATETIME_");
		column3.setJavaType("Date");
		tableDefinition.addColumn(column3);

		ColumnDefinition column4 = new ColumnDefinition();
		column4.setColumnName("OPERATE_");
		column4.setJavaType("String");
		column4.setLength(50);
		tableDefinition.addColumn(column4);

		ColumnDefinition column6 = new ColumnDefinition();
		column6.setColumnName("CONTENT_");
		column6.setJavaType("String");
		column6.setLength(500);
		tableDefinition.addColumn(column6);

		ColumnDefinition column8 = new ColumnDefinition();
		column8.setColumnName("FLAG_");
		column8.setJavaType("Integer");
		tableDefinition.addColumn(column8);

		ColumnDefinition column9 = new ColumnDefinition();
		column9.setColumnName("MODULEID_");
		column9.setJavaType("String");
		column9.setLength(50);
		tableDefinition.addColumn(column9);

		ColumnDefinition column10 = new ColumnDefinition();
		column10.setColumnName("TIMEMS_");
		column10.setJavaType("Integer");
		tableDefinition.addColumn(column10);

		if (!DBUtils.tableExists(tableName)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}

		return tableDefinition;
	}

	public static int getYearMonthDay(Date date) {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		returnStr = f.format(date);
		return Integer.parseInt(returnStr);
	}

	public static void main(String[] args) {
		Date date = DateUtils.getDateAfter(new Date(), 0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int daysOfMonth = DateUtils.getYearMonthDays(year, month + 1);

		calendar.set(year, month, daysOfMonth);

		int begin = getYearMonthDay(date);
		int end = getYearMonthDay(calendar.getTime());

		for (int i = begin; i <= end; i++) {
			System.out.println(i);
			try {
				SysDataLogTableUtils.createTable("SYS_DATA_LOG_" + i);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println(ex);
			}
		}

	}

	private SysDataLogTableUtils() {

	}

}
