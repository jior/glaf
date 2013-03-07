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

package com.glaf.core.base;

import java.util.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TableModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String tableName;

	protected ColumnModel idColumn;

	protected String aggregationKey;

	protected String sql;

	/**
	 * 实体名称
	 */
	protected String entityName;

	/**
	 * Java 包名
	 */
	protected String packageName;

	/**
	 * 文件前缀
	 */
	protected String filePrefix;

	/**
	 * 开始行数
	 */
	protected int startRow;

	/**
	 * 最后跳过行数(不需要处理的footer信息行数)
	 */
	protected int stopSkipRow;

	/**
	 * 合法数据的最小长度
	 */
	protected int minLength;

	/**
	 * 批处理的大小
	 */
	protected int batchSize;

	/**
	 * 标题
	 */
	protected String title;

	/**
	 * 英文标题
	 */
	protected String englishTitle;

	protected String stopWord;

	/**
	 * 物理表的主键字段名称
	 */
	protected String primaryKey;

	/**
	 * 解析类型,csv,text,xls
	 */
	protected String parseType;

	/**
	 * 自行提供的解析器类名
	 */
	protected String parseClass;

	protected List<ColumnModel> columns = new ArrayList<ColumnModel>();

	protected Collection<String> aggregationKeys = new ArrayList<String>();

	public TableModel() {

	}

	public void addColumn(ColumnModel column) {
		if (columns == null) {
			columns = new ArrayList<ColumnModel>();
		}
		column.setTable(this);
		columns.add(column);
	}

	public void addColumn(String columnName, String javaType, Object value) {
		if (columns == null) {
			columns = new ArrayList<ColumnModel>();
		}
		ColumnModel column = new ColumnModel();
		column.setColumnName(columnName);
		column.setJavaType(javaType);
		column.setValue(value);
		column.setTable(this);
		columns.add(column);
	}

	public String getAggregationKey() {
		return aggregationKey;
	}

	public Collection<String> getAggregationKeys() {
		return aggregationKeys;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public ColumnModel getIdColumn() {
		return idColumn;
	}

	public int getMinLength() {
		return minLength;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getParseClass() {
		return parseClass;
	}

	public String getParseType() {
		return parseType;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public String getSql() {
		return sql;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getStopSkipRow() {
		return stopSkipRow;
	}

	public String getStopWord() {
		return stopWord;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTitle() {
		return title;
	}

	public void removeColumn(ColumnModel column) {
		if (columns != null) {
			column.setTable(null);
			columns.remove(column);
		}
	}

	public void setAggregationKey(String aggregationKey) {
		this.aggregationKey = aggregationKey;
	}

	public void setAggregationKeys(Collection<String> aggregationKeys) {
		this.aggregationKeys = aggregationKeys;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public void setIdColumn(ColumnModel idColumn) {
		this.idColumn = idColumn;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setParseClass(String parseClass) {
		this.parseClass = parseClass;
	}

	public void setParseType(String parseType) {
		this.parseType = parseType;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setStopSkipRow(int stopSkipRow) {
		this.stopSkipRow = stopSkipRow;
	}

	public void setStopWord(String stopWord) {
		this.stopWord = stopWord;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}