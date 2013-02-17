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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.JdbcUtils;

import com.glaf.base.api.FieldDefinition;
import com.glaf.base.config.DataSourceConfig;

public class DBToolkit {

	public static List<FieldDefinition> getFieldDefinitions(String tableName) {
		List<FieldDefinition> columns = new ArrayList<FieldDefinition>();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DataSourceConfig.getConnection();
			List<String> primaryKeys = getPrimaryKeys(conn, tableName);
			DatabaseMetaData metaData = conn.getMetaData();
			if ("postgresql".equals(DataSourceConfig.getDatabaseType(conn))) {
				tableName = tableName.toLowerCase();
			}
			rs = metaData.getColumns(null, null, tableName, null);
			while (rs.next()) {
				String name = rs.getString("COLUMN_NAME");
				int dataType = rs.getInt("DATA_TYPE");
				int nullable = rs.getInt("NULLABLE");
				int length = rs.getInt("COLUMN_SIZE");
				int ordinal = rs.getInt("ORDINAL_POSITION");
				FieldDefinition column = new FieldDefinition();
				column.setColumnName(name);
				column.setType(FieldType.getJavaType(dataType));
				if (nullable == 1) {
					column.setNullable(true);
				} else {
					column.setNullable(false);
				}
				column.setLength(length);
				column.setSortNo(ordinal);

				if ("String".equals(column.getType())) {
					if (column.getLength() > 8000) {
						column.setType("Clob");
					}
				}

				if (primaryKeys.contains(name)) {
					column.setNullable(false);
				}

				column.setName(StringTools.camelStyle(column.getColumnName()));
				column.setEnglishTitle(StringTools.camelStyle(column
						.getColumnName()));

				columns.add(column);
			}
			rs.close();
			rs = null;

			return columns;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeConnection(conn);
			JdbcUtils.closeResultSet(rs);
		}
	}

	public static List<String> getPrimaryKeys(Connection connection,
			String tableName) {
		List<String> primaryKeys = new ArrayList<String>();
		try {
			DatabaseMetaData metaData = connection.getMetaData();

			if ("postgresql".equals(DataSourceConfig
					.getDatabaseType(connection))) {
				tableName = tableName.toLowerCase();
			}

			ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);
			while (rs.next()) {
				primaryKeys.add(rs.getString("column_name").toLowerCase());
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return primaryKeys;
	}

	public static List<String> getPrimaryKeys(String tableName) {
		List<String> primaryKeys = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = DataSourceConfig.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();

			if ("postgresql".equals(DataSourceConfig
					.getDatabaseType(connection))) {
				tableName = tableName.toLowerCase();
			}

			ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);
			while (rs.next()) {
				primaryKeys.add(rs.getString("column_name"));
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeConnection(connection);
		}
		return primaryKeys;
	}

	public static List<String> getTables() {
		List<String> tables = new ArrayList<String>();
		String[] types = { "TABLE" };
		Connection connection = null;
		try {
			connection = DataSourceConfig.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getTables(null, null, null, types);
			while (rs.next()) {
				tables.add(rs.getObject("TABLE_NAME").toString());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeConnection(connection);
		}
		return tables;
	}

}
