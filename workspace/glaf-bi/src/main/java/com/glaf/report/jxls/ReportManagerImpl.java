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

package com.glaf.report.jxls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import net.sf.jxls.report.ReportManager;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.el.Jexl2ExpressionEvaluator;
import com.glaf.core.util.StringTools;

public class ReportManagerImpl implements ReportManager {
	protected final Log log = LogFactory.getLog(getClass());
	protected Connection connection;
	protected Map<String, Object> beans;
	protected Map<String, Object> properties = new java.util.concurrent.ConcurrentHashMap<String, Object>();

	public ReportManagerImpl(Connection connection) {
		this.connection = connection;
	}

	public ReportManagerImpl(Connection connection, Map<String, Object> beans) {
		this.connection = connection;
		this.beans = beans;
	}

	public ReportManagerImpl(Connection connection, Map<String, Object> beans,
			Map<String, Object> properties) {
		this.connection = connection;
		this.beans = beans;
		this.properties = properties;
	}

	protected String evaluate(String expression, Map<String, Object> params) {
		if (expression == null || params == null) {
			return expression;
		}
		expression = StringTools.replaceIgnoreCase(expression, "${", "#{");
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < expression.length(); i++) {
			if (expression.charAt(i) == '#' && expression.charAt(i + 1) == '{') {
				sb.append(expression.substring(end, i));
				begin = i + 2;
				flag = true;
			}
			if (flag && expression.charAt(i) == '}') {
				String temp = expression.substring(begin, i);
				String value = null;
				try {
					value = (String) Jexl2ExpressionEvaluator.evaluate(temp,
							params);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (StringUtils.isNotEmpty(value)) {
					sb.append(value);
					end = i + 1;
					flag = false;
				} else {
					sb.append("");
					end = i + 1;
					flag = false;
				}
			}
			if (i == expression.length() - 1) {
				sb.append(expression.substring(end, i + 1));
			}
		}
		return sb.toString();
	}

	public List<?> exec(String sql) throws SQLException {
		try {
			sql = evaluate(sql, beans);
		} catch (Exception ex) {
			log.error(sql);
			ex.printStackTrace();
		}
		sql = sql.replaceAll("&apos;", "'");
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		RowSetDynaClass rsdc = new RowSetDynaClass(rs, false, true);
		stmt.close();
		rs.close();
		return rsdc.getRows();
	}

	public Connection getConnection() {
		return connection;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

}