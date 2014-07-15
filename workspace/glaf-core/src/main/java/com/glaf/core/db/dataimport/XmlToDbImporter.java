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
import java.io.FileInputStream;
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
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;

public class XmlToDbImporter {
	protected static final Log logger = LogFactory
			.getLog(XmlToDbImporter.class);

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		XmlToDbImporter imp = new XmlToDbImporter();
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection("default");
			imp.importToDB(new File("./data/xml"), conn);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
		long time = System.currentTimeMillis() - start;
		logger.debug("time(ms):" + time);
	}

	public void doImport(File file, Connection conn) throws IOException {
		String filename = file.getName();
		String tableName = filename.substring(0, filename.lastIndexOf("_"));
		SAXReader xmlReader = new SAXReader();
		Document doc = null;
		try {
			doc = xmlReader.read(new FileInputStream(file));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		Element root = doc.getRootElement();
		Element metadata = root.element("MetaData");
		List<?> metas = metadata.elements();

		if (root.attributeValue("TableName") != null) {
			tableName = root.attributeValue("TableName");
			logger.debug("tableName:" + tableName);
		}

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select ");
		String primaryKeyColumn = null;
		ColumnDefinition idColumn = null;
		List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();
		int length = metas.size();
		for (int i = 0; i < length; i++) {
			Element elem = (Element) metas.get(i);
			ColumnDefinition column = new ColumnDefinition();
			column.setColumnName(elem.attributeValue("Name"));
			column.setJavaType(elem.attributeValue("Type"));
			if (elem.attribute("Length") != null) {
				column.setLength(Integer.parseInt(elem.attributeValue("Length")));
			}
			if (StringUtils.equalsIgnoreCase(elem.attributeValue("PrimaryKey"),
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
		
		if(idColumn != null){
		    columns.add(idColumn);
		}
		
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

		Element rowSet = root.element("RowSet");
		List<?> rows = rowSet.elements();

		boolean autoCommit = false;
		PreparedStatement psmt = null;
		try {

			int size = rows.size();
			if (size > 0) {
				int k = 0;
				autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				psmt = conn.prepareStatement(sb.toString());

				for (int i = 0; i < size; i++) {
					index = 1;
					Element elem = (Element) rows.get(i);
					if (primaryKeyColumn != null && !rowIds.isEmpty()) {
						String rowId = elem.elementText(primaryKeyColumn);
						if (rowId != null && rowIds.contains(rowId)) {
							continue;
						}
						rowIds.add(rowId);
					}
					for (ColumnDefinition column : columns) {
						String javaType = column.getJavaType();
						if (elem.element(column.getColumnName()) != null) {
							if ("Integer".equals(javaType)) {
								psmt.setInt(index++, Integer.parseInt(elem
										.elementText(column.getColumnName())));
							} else if ("Long".equals(javaType)) {
								psmt.setLong(index++, Long.parseLong(elem
										.elementText(column.getColumnName())));
							} else if ("Double".equals(javaType)) {
								psmt.setDouble(index++, Double.parseDouble(elem
										.elementText(column.getColumnName())));
							} else if ("Date".equals(javaType)) {
								Timestamp time = DateUtils.toTimestamp(elem
										.elementText(column.getColumnName()));
								psmt.setTimestamp(index++, time);
							} else if ("Boolean".equals(javaType)) {
								psmt.setBoolean(index++, Boolean.valueOf(elem
										.elementText(column.getColumnName())));
							} else if ("String".equals(javaType)) {
								psmt.setString(index++, elem.elementText(column
										.getColumnName()));
							} else {
								psmt.setNull(index++, Types.NULL);
							}
						} else {
							psmt.setNull(index++, Types.NULL);
						}
					}
					k++;
					psmt.addBatch();
					if (i > 0 && i % 50 == 0) {
						logger.debug("process " + i + " row ");
						psmt.executeBatch();
					}
				}
				logger.debug("process " + k + " row ");
				psmt.executeBatch();
				conn.commit();
				psmt.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			rows.clear();
			rows = null;
			rowIds.clear();
			rowIds = null;
			JdbcUtils.close(psmt);
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
			}
		}
	}

	public void importToDB(File path, Connection conn) {
		if (path.isDirectory()) {
			File[] entries = path.listFiles();
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				if (file.isFile() && file.getName().endsWith(".xml")) {
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

}