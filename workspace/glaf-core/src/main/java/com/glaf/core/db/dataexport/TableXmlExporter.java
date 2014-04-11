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
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;

public class TableXmlExporter {
	protected final static Log logger = LogFactory
			.getLog(TableXmlExporter.class);

	public void exportXml(String rootDir, String systemName, int pageSize) {
		Connection conn = null;
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			dbmd = conn.getMetaData();
			rs = dbmd.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				if ("fileatt".equalsIgnoreCase(tableName)) {
					continue;
				}
				if ("filedot".equalsIgnoreCase(tableName)) {
					continue;
				}
				this.exportXml(systemName, rootDir, tableName,
						tableName.toLowerCase(), pageSize);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(conn);
		}
	}

	public void exportXml(String systemName, String rootDir, String tableName,
			String prefix, int perFileSize) {
		long start = System.currentTimeMillis();

		String sql = " select * from " + tableName;

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Repository");
		root.addAttribute("TableName", tableName);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;

		try {
			conn = DBConnectionFactory.getConnection(systemName);
			List<String> primaryKeys = DBUtils.getPrimaryKeys(conn, tableName);
			stmt = conn.prepareStatement(sql);

			rs = stmt.executeQuery();

			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();

			List<ColumnDefinition> columns = new java.util.concurrent.CopyOnWriteArrayList<ColumnDefinition>();

			Element meta = root.addElement("MetaData");
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
				if (primaryKeys != null) {
					if (primaryKeys.contains(column.getColumnName())
							|| primaryKeys.contains(column.getColumnName()
									.toLowerCase())
							|| primaryKeys.contains(column.getColumnName()
									.toLowerCase())) {
						column.setPrimaryKey(true);
					}
				}
				columns.add(column);
				Element e = meta.addElement("Column");
				e.addAttribute("Name", column.getColumnLabel());
				e.addAttribute("Type", column.getJavaType());
				if (!StringUtils.equalsIgnoreCase(column.getJavaType(), "Date")) {
					if (column.getPrecision() > 0) {
						e.addAttribute("Precision",
								String.valueOf(column.getPrecision()));
					}
					if (column.getScale() > 0) {
						e.addAttribute("Scale",
								String.valueOf(column.getScale()));
					}
				}
				if (column.isPrimaryKey()) {
					e.addAttribute("PrimaryKey", "true");
				}
			}

			int index = 0;
			int pageNo = 0;

			Element rowset = root.addElement("RowSet");

			while (rs.next()) {
				index++;
				Element row = rowset.addElement("Row");
				Iterator<ColumnDefinition> iterator = columns.iterator();
				while (iterator.hasNext()) {
					ColumnDefinition column = iterator.next();
					String columnName = column.getColumnName();
					String columnLabel = column.getColumnLabel();
					if (columnLabel == null) {
						columnLabel = columnName;
					}
					String javaType = column.getJavaType();
					if ("String".equals(javaType)) {
						String value = rs.getString(column.getOrdinal());
						if (value != null) {
							row.addElement(columnLabel).setText(value);
						}
					} else if ("Integer".equals(javaType)) {
						Integer value = rs.getInt(column.getOrdinal());
						if (value != null) {
							row.addElement(columnLabel).setText(
									String.valueOf(value));
						}
					} else if ("Long".equals(javaType)) {
						Long value = rs.getLong(column.getOrdinal());
						if (value != null) {
							row.addElement(columnLabel).setText(
									String.valueOf(value));
						}
					} else if ("Double".equals(javaType)) {
						Double value = rs.getDouble(column.getOrdinal());
						if (value != null) {
							row.addElement(columnLabel).setText(
									String.valueOf(value));
						}
					} else if ("Boolean".equals(javaType)) {
						Boolean value = rs.getBoolean(column.getOrdinal());
						if (value != null) {
							row.addElement(columnLabel).setText(
									String.valueOf(value));
						}
					} else if ("Date".equals(javaType)) {
						Timestamp value = rs.getTimestamp(column.getOrdinal());
						if (value != null) {
							row.addElement(columnLabel).setText(
									DateUtils.getDateTime(value));
						}
					} else {
						Object value = rs.getObject(column.getOrdinal());
						if (value != null) {
							row.addElement(columnLabel).setText(
									value.toString());
						}
					}
				}

				if (index > 0 && index % perFileSize == 0) {
					index = 0;
					pageNo = pageNo + 1;
					boolean success = false;
					int retry = 0;
					while (retry <= 2 && !success) {
						try {
							retry++;
							FileUtils.mkdirs(rootDir);
							String filename = rootDir + FileUtils.sp + prefix
									+ "_" + pageNo + ".xml";
							Dom4jUtils.savePrettyDoument(doc, filename, "GBK");
							logger.debug("save file:" + filename);
							success = true;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					rowset.clearContent();
				}

			}

			if (index > 0) {
				pageNo = pageNo + 1;
				String filename = rootDir + FileUtils.sp + prefix + "_"
						+ pageNo + ".xml";
				boolean success = false;
				int retry = 0;
				while (retry <= 2 && !success) {
					try {
						retry++;
						FileUtils.mkdirs(rootDir);
						Dom4jUtils.savePrettyDoument(doc, filename, "GBK");
						logger.debug("save file:" + filename);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				rowset.clearContent();
				doc.clearContent();
				doc = null;
			}

			stmt.close();
			rs.close();
			stmt = null;
			rs = null;

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(stmt);
			JdbcUtils.close(conn);
			long time = System.currentTimeMillis() - start;
			logger.debug("time(ms):" + time);
		}
	}

	public static void main(String[] args) throws Exception {
		TableXmlExporter exp = new TableXmlExporter();
		exp.exportXml("default", "data", "sys_tree", "tree", 50000);
	}

}