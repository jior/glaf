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

package com.glaf.core.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import com.glaf.core.base.LowerLinkedMap;

/**
 * 使用小写key的Mapper
 */

public class JdbcRowMapper implements RowMapper<Map<String, Object>> {

	protected Map<String, Object> createColumnMap(int columnCount) {
		return new LowerLinkedMap(columnCount);
	}

	protected String getColumnKey(String columnName) {
		return columnName;
	}

	protected Object getColumnValue(ResultSet rs, int index)
			throws SQLException {
		return JdbcUtils.getResultSetValue(rs, index);
	}

	public Map<String, Object> mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map<String, Object> rowData = createColumnMap(columnCount);
		for (int index = 1; index <= columnCount; index++) {
			String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, index));
			Object value = getColumnValue(rs, index);
			rowData.put(key, value);
		}

		return rowData;
	}
}
