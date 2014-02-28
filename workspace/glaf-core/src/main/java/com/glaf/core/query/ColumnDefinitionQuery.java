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

package com.glaf.core.query;

import java.util.List;

public class ColumnDefinitionQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String columnName;
	protected String columnNameLike;
	protected List<String> columnNames;
	protected String discriminator;
	protected String name;
	protected String namedColumnSetId;
	protected String namedColumnSetIdLike;
	protected List<String> namedColumnSetIds;
	protected String nameLike;
	protected String queryId;
	protected String tableName;
	protected String tableNameLike;
	protected List<String> tableNames;
	protected String targetId;
	protected String titleLike;

	public ColumnDefinitionQuery() {

	}

	public ColumnDefinitionQuery columnName(String columnName) {
		if (columnName == null) {
			throw new RuntimeException("columnName is null");
		}
		this.columnName = columnName;
		return this;
	}

	public ColumnDefinitionQuery columnNameLike(String columnNameLike) {
		if (columnNameLike == null) {
			throw new RuntimeException("columnName is null");
		}
		this.columnNameLike = columnNameLike;
		return this;
	}

	public ColumnDefinitionQuery columnNames(List<String> columnNames) {
		if (columnNames == null) {
			throw new RuntimeException("columnNames is empty ");
		}
		this.columnNames = columnNames;
		return this;
	}

	public ColumnDefinitionQuery discriminator(String discriminator) {
		if (discriminator == null) {
			throw new RuntimeException("discriminator is null");
		}
		this.discriminator = discriminator;
		return this;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getColumnNameLike() {
		if (columnNameLike != null && columnNameLike.trim().length() > 0) {
			if (!columnNameLike.startsWith("%")) {
				columnNameLike = "%" + columnNameLike;
			}
			if (!columnNameLike.endsWith("%")) {
				columnNameLike = columnNameLike + "%";
			}
		}
		return columnNameLike;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public String getName() {
		return name;
	}

	public String getNamedColumnSetId() {
		return namedColumnSetId;
	}

	public String getNamedColumnSetIdLike() {
		if (namedColumnSetIdLike != null
				&& namedColumnSetIdLike.trim().length() > 0) {
			if (!namedColumnSetIdLike.startsWith("%")) {
				namedColumnSetIdLike = "%" + namedColumnSetIdLike;
			}
			if (!namedColumnSetIdLike.endsWith("%")) {
				namedColumnSetIdLike = namedColumnSetIdLike + "%";
			}
		}
		return namedColumnSetIdLike;
	}

	public List<String> getNamedColumnSetIds() {
		return namedColumnSetIds;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if ("namedColumnSetId".equals(sortField)) {
				orderBy = "E.NAMEDCOLUMNSETID_" + a_x;
			}

			if ("tableName".equals(sortField)) {
				orderBy = "E.TABLENAME_" + a_x;
			}

			if ("columnName".equals(sortField)) {
				orderBy = "E.COLUMNNAME_" + a_x;
			}

			if ("name".equals(sortField)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("title".equals(sortField)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("length".equals(sortField)) {
				orderBy = "E.LENGTH_" + a_x;
			}

			if ("scale".equals(sortField)) {
				orderBy = "E.SCALE_" + a_x;
			}

			if ("precision".equals(sortField)) {
				orderBy = "E.PRECISION_" + a_x;
			}

			if ("ordinal".equals(sortField)) {
				orderBy = "E.ORDINAL_" + a_x;
			}

			if ("javaType".equals(sortField)) {
				orderBy = "E.JAVATYPE_" + a_x;
			}

			if ("jdbcType".equals(sortField)) {
				orderBy = "E.JDBCTYPE_" + a_x;
			}

			if ("regex".equals(sortField)) {
				orderBy = "E.REGEX_" + a_x;
			}

			if ("expression".equals(sortField)) {
				orderBy = "E.EXPRESSION_" + a_x;
			}

			if ("formula".equals(sortField)) {
				orderBy = "E.FORMULA_" + a_x;
			}

			if ("translator".equals(sortField)) {
				orderBy = "E.TRANSLATOR_" + a_x;
			}

		}
		return orderBy;
	}

	public String getQueryId() {
		return queryId;
	}

	public String getSortField() {
		return sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTableNameLike() {
		if (tableNameLike != null && tableNameLike.trim().length() > 0) {
			if (!tableNameLike.startsWith("%")) {
				tableNameLike = "%" + tableNameLike;
			}
			if (!tableNameLike.endsWith("%")) {
				tableNameLike = tableNameLike + "%";
			}
		}
		return tableNameLike;
	}

	public List<String> getTableNames() {
		return tableNames;
	}

	public String getTargetId() {
		return targetId;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("namedColumnSetId", "NAMEDCOLUMNSETID_");
		addColumn("tableName", "TABLENAME_");
		addColumn("columnName", "COLUMNNAME_");
		addColumn("name", "NAME_");
		addColumn("title", "TITLE_");
		addColumn("length", "LENGTH_");
		addColumn("scale", "SCALE_");
		addColumn("precision", "PRECISION_");
		addColumn("nullable", "NULLABLE_");
		addColumn("ordinal", "ORDINAL_");
		addColumn("javaType", "JAVATYPE_");
		addColumn("jdbcType", "JDBCTYPE_");
		addColumn("regex", "REGEX_");
		addColumn("expression", "EXPRESSION_");
		addColumn("formula", "FORMULA_");
		addColumn("translator", "TRANSLATOR_");
	}

	public ColumnDefinitionQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public ColumnDefinitionQuery namedColumnSetId(String namedColumnSetId) {
		if (namedColumnSetId == null) {
			throw new RuntimeException("namedColumnSetId is null");
		}
		this.namedColumnSetId = namedColumnSetId;
		return this;
	}

	public ColumnDefinitionQuery namedColumnSetIdLike(
			String namedColumnSetIdLike) {
		if (namedColumnSetIdLike == null) {
			throw new RuntimeException("namedColumnSetId is null");
		}
		this.namedColumnSetIdLike = namedColumnSetIdLike;
		return this;
	}

	public ColumnDefinitionQuery namedColumnSetIds(
			List<String> namedColumnSetIds) {
		if (namedColumnSetIds == null) {
			throw new RuntimeException("namedColumnSetIds is empty ");
		}
		this.namedColumnSetIds = namedColumnSetIds;
		return this;
	}

	public ColumnDefinitionQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public ColumnDefinitionQuery queryId(String queryId) {
		if (queryId == null) {
			throw new RuntimeException("queryId is null");
		}
		this.queryId = queryId;
		return this;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnNameLike(String columnNameLike) {
		this.columnNameLike = columnNameLike;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNamedColumnSetId(String namedColumnSetId) {
		this.namedColumnSetId = namedColumnSetId;
	}

	public void setNamedColumnSetIdLike(String namedColumnSetIdLike) {
		this.namedColumnSetIdLike = namedColumnSetIdLike;
	}

	public void setNamedColumnSetIds(List<String> namedColumnSetIds) {
		this.namedColumnSetIds = namedColumnSetIds;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableNameLike(String tableNameLike) {
		this.tableNameLike = tableNameLike;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public ColumnDefinitionQuery tableName(String tableName) {
		if (tableName == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableName = tableName;
		return this;
	}

	public ColumnDefinitionQuery tableNameLike(String tableNameLike) {
		if (tableNameLike == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableNameLike = tableNameLike;
		return this;
	}

	public ColumnDefinitionQuery tableNames(List<String> tableNames) {
		if (tableNames == null) {
			throw new RuntimeException("tableNames is empty ");
		}
		this.tableNames = tableNames;
		return this;
	}

	public ColumnDefinitionQuery targetId(String targetId) {
		if (targetId == null) {
			throw new RuntimeException("targetId is null");
		}
		this.targetId = targetId;
		return this;
	}

	public ColumnDefinitionQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}