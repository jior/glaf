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

package com.glaf.core.db.dataimport;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;

public class JsonToDbImporter {
	protected static final Log logger = LogFactory
			.getLog(JsonToDbImporter.class);

	public static void main(String[] args) {
		JsonToDbImporter imp = new JsonToDbImporter();
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection("src");
			imp.importToDB(new File("/tmp"), conn);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public void importToDB(File path, Connection conn) {
		if (path.isDirectory()) {
			File[] entries = path.listFiles();
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				if (file.isFile() && file.getName().endsWith(".json")) {
					logger.debug("start import " + file.getAbsolutePath());
					try {
						this.doImport(file, conn);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					this.importToDB(file, conn);
				}
			}
		}
	}

	public void doImport(File file, Connection conn) throws IOException {
		String filename = file.getName();
		String tableName = filename.substring(0, filename.lastIndexOf("_"));
		byte[] bytes = FileUtils.getBytes(file);
		String content = new String(bytes, "UTF-8");
		JSONObject jsonObject = JSONObject.parseObject(content);
		JSONArray meta = jsonObject.getJSONArray("MetaData");

		if (jsonObject.getString("TableName") != null) {
			tableName = jsonObject.getString("TableName");
			logger.debug("tableName:" + tableName);
		}

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select ");
		String primaryKeyColumn = null;
		ColumnDefinition idColumn = null;
		List<ColumnDefinition> columns = new java.util.concurrent.CopyOnWriteArrayList<ColumnDefinition>();
		int length = meta.size();
		for (int i = 0; i < length; i++) {
			JSONObject obj = meta.getJSONObject(i);
			ColumnDefinition column = new ColumnDefinition();
			column.setColumnName(obj.getString("Name"));
			column.setJavaType(obj.getString("Type"));
			if (obj.containsKey("Length")) {
				column.setLength(obj.getInteger("Length"));
			}
			if (StringUtils.equalsIgnoreCase(obj.getString("PrimaryKey"),
					"true")) {
				column.setPrimaryKey(true);
				if (primaryKeyColumn == null) {
					sqlBuffer.append(column.getColumnName());
					primaryKeyColumn = column.getColumnName();
					idColumn = column;
				}
			}
			if (!column.isPrimaryKey()) {
				columns.add(column);
			}
		}

		sqlBuffer.append(" from ").append(tableName);

		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setIdColumn(idColumn);
		tableDefinition.setColumns(columns);
		tableDefinition.setTableName(tableName);
		DBUtils.createTable(conn, tableDefinition);

		Set<String> rowIds = new HashSet<String>();
		if (primaryKeyColumn != null) {
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sqlBuffer.toString());
				while (rs.next()) {
					rowIds.add(rs.getString(1));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(stmt);
				JdbcUtils.close(rs);
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" insert into ").append(tableName).append(" (");

		int index = 0;
		columns.add(idColumn);
		for (ColumnDefinition column : columns) {
			index++;
			sb.append(column.getColumnName());
			if (index < columns.size()) {
				sb.append(", ");
			}
		}

		sb.append(" ) values ( ");

		for (int i = 0; i < length; i++) {
			sb.append(" ? ");
			if (i < length - 1) {
				sb.append(", ");
			}
		}

		sb.append(" ) ");

		logger.debug(sb.toString());

		boolean autoCommit = false;
		PreparedStatement psmt = null;
		try {

			JSONArray rows = jsonObject.getJSONArray("RowSet");
			int size = rows.size();
			if (size > 0) {
				autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				psmt = conn.prepareStatement(sb.toString());

				for (int i = 0; i < size; i++) {
					index = 1;
					JSONObject obj = rows.getJSONObject(i);
					if (primaryKeyColumn != null && !rowIds.isEmpty()) {
						String rowId = obj.getString(primaryKeyColumn);
						if (rowId != null && rowIds.contains(rowId)) {
							continue;
						}
						rowIds.add(rowId);
					}

					for (ColumnDefinition column : columns) {
						String javaType = column.getJavaType();
						if (obj.containsKey(column.getColumnName())) {
							if ("Integer".equals(javaType)) {
								psmt.setInt(index++,
										obj.getInteger(column.getColumnName()));
							} else if ("Long".equals(javaType)) {
								psmt.setLong(index++,
										obj.getLong(column.getColumnName()));
							} else if ("Double".equals(javaType)) {
								psmt.setDouble(index++,
										obj.getDouble(column.getColumnName()));
							} else if ("Date".equals(javaType)) {
								Timestamp time = DateUtils.toTimestamp(obj
										.getString(column.getColumnName()));
								psmt.setTimestamp(index++, time);
							} else if ("Boolean".equals(javaType)) {
								psmt.setBoolean(index++,
										obj.getBoolean(column.getColumnName()));
							} else if ("String".equals(javaType)) {
								psmt.setString(index++,
										obj.getString(column.getColumnName()));
							} else {
								psmt.setNull(index++, Types.NULL);
							}
						} else {
							psmt.setNull(index++, Types.NULL);
						}
					}
					psmt.addBatch();
					if (i > 0 && i % 50 == 0) {
						logger.debug("process " + i + " row ");
						psmt.executeBatch();
					}
				}
				logger.debug("process " + size + " row ");
				psmt.executeBatch();
				conn.commit();
				psmt.close();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			rowIds.clear();
			rowIds = null;
			JdbcUtils.close(psmt);
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
			}
		}
	}

}