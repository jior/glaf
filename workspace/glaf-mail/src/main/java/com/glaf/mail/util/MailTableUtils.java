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

package com.glaf.mail.util;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

public class MailTableUtils {

	public static TableDefinition createTable(Connection conn, String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition column1 = new ColumnDefinition();
		column1.setColumnName("TASKID_");
		column1.setJavaType("String");
		column1.setLength(50);
		tableDefinition.addColumn(column1);

		ColumnDefinition column1x = new ColumnDefinition();
		column1x.setColumnName("MAILTO_");
		column1x.setJavaType("String");
		column1x.setLength(150);
		tableDefinition.addColumn(column1x);

		ColumnDefinition column11 = new ColumnDefinition();
		column11.setColumnName("ACCOUNTID_");
		column11.setJavaType("String");
		column11.setLength(50);
		tableDefinition.addColumn(column11);

		ColumnDefinition column3 = new ColumnDefinition();
		column3.setColumnName("SENDDATE_");
		column3.setJavaType("Date");
		tableDefinition.addColumn(column3);

		ColumnDefinition column8 = new ColumnDefinition();
		column8.setColumnName("SENDSTATUS_");
		column8.setJavaType("Integer");
		tableDefinition.addColumn(column8);

		ColumnDefinition column10 = new ColumnDefinition();
		column10.setColumnName("RETRYTIMES_");
		column10.setJavaType("Integer");
		tableDefinition.addColumn(column10);

		ColumnDefinition column2 = new ColumnDefinition();
		column2.setColumnName("RECEIVEIP_");
		column2.setJavaType("String");
		column2.setLength(100);
		tableDefinition.addColumn(column2);

		ColumnDefinition column3x = new ColumnDefinition();
		column3x.setColumnName("RECEIVEDATE_");
		column3x.setJavaType("Date");
		tableDefinition.addColumn(column3x);

		ColumnDefinition column3y = new ColumnDefinition();
		column3y.setColumnName("RECEIVESTATUS_");
		column3y.setJavaType("Integer");
		tableDefinition.addColumn(column3y);

		ColumnDefinition column12 = new ColumnDefinition();
		column12.setColumnName("BROWSER_");
		column12.setJavaType("String");
		column12.setLength(200);
		tableDefinition.addColumn(column12);

		ColumnDefinition column4 = new ColumnDefinition();
		column4.setColumnName("CONTENTTYPE_");
		column4.setJavaType("String");
		column4.setLength(50);
		tableDefinition.addColumn(column4);

		ColumnDefinition column6 = new ColumnDefinition();
		column6.setColumnName("CLIENTOS_");
		column6.setJavaType("String");
		column6.setLength(500);
		tableDefinition.addColumn(column6);

		ColumnDefinition column9 = new ColumnDefinition();
		column9.setColumnName("LASTMODIFIED_");
		column9.setJavaType("Long");
		tableDefinition.addColumn(column9);

		if (!DBUtils.tableExists(conn, tableName)) {
			DBUtils.createTable(conn, tableDefinition);
		} else {
			DBUtils.alterTable(conn, tableDefinition);
		}

		return tableDefinition;
	}

	public static int getYearMonthDay(Date date) {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		returnStr = f.format(date);
		return Integer.parseInt(returnStr);
	}

	private MailTableUtils() {

	}

}
