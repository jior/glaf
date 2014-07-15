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

package com.glaf.core.db.dataexport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;

public class FastJsonExporter {
	protected final static Log logger = LogFactory
			.getLog(FastJsonExporter.class);

	@SuppressWarnings("unchecked")
	public void exportJson(String systemName, String rootDir, String sql,
			Map<String, Object> paramMap, String prefix, int perFileSize) {
		logger.debug("----------------------FastJsonExporter------------");
		long start = System.currentTimeMillis();

		List<Object> values = null;
		if (paramMap != null) {
			SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, paramMap);
			sql = sqlExecutor.getSql();
			values = (List<Object>) sqlExecutor.getParameter();
		}

		JSONObject rootJSON = new JSONObject();

		JSONObject repositoryJSON = new JSONObject();
		rootJSON.put("Repository", repositoryJSON);

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;

		try {
			conn = DBConnectionFactory.getConnection(systemName);
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			if (values != null && !values.isEmpty()) {
				JdbcUtils.fillStatement(stmt, values);
			}

			stmt.setFetchSize(25);

			rs = stmt.executeQuery();

			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();

			List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();

			JSONArray columnsJSON = new JSONArray();

			for (int i = 1; i <= count; i++) {
				int sqlType = rsmd.getColumnType(i);
				ColumnDefinition column = new ColumnDefinition();
				column.setColumnName(rsmd.getColumnName(i));
				column.setColumnLabel(rsmd.getColumnLabel(i));
				column.setJavaType(FieldType.getJavaType(sqlType));
				column.setPrecision(rsmd.getPrecision(i));
				column.setScale(rsmd.getScale(i));
				column.setOrdinal(i);
				if (column.getScale() == 0 && sqlType == Types.NUMERIC) {
					column.setJavaType("Long");
				}
				columns.add(column);
				JSONObject columnJSON = new JSONObject();
				columnJSON.put("Name", column.getColumnLabel().toUpperCase());
				columnJSON.put("Type", column.getJavaType());
				if (!StringUtils.equalsIgnoreCase(column.getJavaType(), "Date")) {
					if (column.getPrecision() > 0) {
						columnJSON.put("Precision",
								String.valueOf(column.getPrecision()));
					}
					if (column.getScale() > 0) {
						columnJSON.put("Scale",
								String.valueOf(column.getScale()));
					}
				}
				columnsJSON.add(columnJSON);
			}

			repositoryJSON.put("MetaData", columnsJSON);

			int index = 0;
			int pageSize = 0;

			JSONArray rowsJSON = new JSONArray();

			while (rs.next()) {
				index++;
				JSONObject rowJSON = new JSONObject();
				Iterator<ColumnDefinition> iterator = columns.iterator();
				while (iterator.hasNext()) {
					ColumnDefinition column = iterator.next();
					String columnName = column.getColumnName();
					String columnLabel = column.getColumnLabel();
					if (columnLabel == null) {
						columnLabel = columnName;
					}
					columnLabel = columnLabel.toUpperCase();
					String javaType = column.getJavaType();
					if ("String".equals(javaType)) {
						String value = rs.getString(column.getOrdinal());
						if (value != null) {
							rowJSON.put(columnLabel, value);
						}
					} else if ("Integer".equals(javaType)) {
						Integer value = rs.getInt(column.getOrdinal());
						if (value != null) {
							rowJSON.put(columnLabel, value);
						}
					} else if ("Long".equals(javaType)) {
						Long value = rs.getLong(column.getOrdinal());
						if (value != null) {
							rowJSON.put(columnLabel, value);
						}
					} else if ("Double".equals(javaType)) {
						Double value = rs.getDouble(column.getOrdinal());
						if (value != null) {
							rowJSON.put(columnLabel, value);
						}
					} else if ("Boolean".equals(javaType)) {
						Boolean value = rs.getBoolean(column.getOrdinal());
						if (value != null) {
							rowJSON.put(columnLabel, value);
						}
					} else if ("Date".equals(javaType)) {
						Timestamp value = rs.getTimestamp(column.getOrdinal());
						if (value != null) {
							rowJSON.put(columnLabel,
									DateUtils.getDateTime(value));
						}
					} else {
						Object value = rs.getObject(column.getOrdinal());
						if (value != null) {
							rowJSON.put(columnLabel, value.toString());
						}
					}
				}

				rowsJSON.add(rowJSON);

				if (index > 0 && index % perFileSize == 0) {
					index = 0;
					pageSize = pageSize + 1;
					FileUtils.mkdirs(rootDir);
					String filename = rootDir + FileUtils.sp + prefix + "_"
							+ pageSize + ".json";

					repositoryJSON.put("RowSet", rowsJSON);

					boolean success = false;
					int retry = 0;
					while (retry <= 2 && !success) {
						try {
							retry++;
							byte[] bytes = rootJSON.toJSONString().getBytes(
									"UTF-8");
							FileUtils.save(filename, bytes);
							logger.debug("save file:" + filename);
							success = true;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					rowsJSON.clear();
				}
			}

			if (index > 0) {
				pageSize = pageSize + 1;
				String filename = rootDir + FileUtils.sp + prefix + "_"
						+ pageSize + ".json";
				repositoryJSON.put("RowSet", rowsJSON);
				boolean success = false;
				int retry = 0;
				while (retry <= 2 && !success) {
					try {
						retry++;
						byte[] bytes = rootJSON.toJSONString()
								.getBytes("UTF-8");
						FileUtils.save(filename, bytes);
						logger.debug("save file:" + filename);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				repositoryJSON.clear();
				repositoryJSON = null;
			}

			stmt.close();
			rs.close();
			stmt = null;
			rs = null;

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			JdbcUtils.close(conn);
			JdbcUtils.close(stmt);
			JdbcUtils.close(rs);
			long time = System.currentTimeMillis() - start;
			logger.debug("time(ms):" + time);
		}
	}

	public void exportJson(String systemName, String rootDir, String tableName,
			String prefix, int perFileSize) {
		String sql = " select * from " + tableName;
		this.exportJson(systemName, rootDir, sql,
				new java.util.HashMap<String, Object>(), prefix, perFileSize);
	}

	public static void main(String[] args) throws Exception {
		FastJsonExporter exp = new FastJsonExporter();
		exp.exportJson("default", "data", "sys_tree", "tree", 50000);
	}

}