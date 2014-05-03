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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TableModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String aggregationKey;

	protected Collection<String> aggregationKeys = new java.util.ArrayList<String>();

	/**
	 * ������Ĵ�С
	 */
	protected int batchSize;

	protected List<ColumnModel> columns = new java.util.ArrayList<ColumnModel>();

	/**
	 * Ӣ�ı���
	 */
	protected String englishTitle;

	/**
	 * ʵ������
	 */
	protected String entityName;

	/**
	 * ��Ҫ�ų������б�
	 */
	protected List<String> excludes = new java.util.ArrayList<String>();

	/**
	 * �ļ�ǰ׺
	 */
	protected String filePrefix;

	protected ColumnModel idColumn;

	/**
	 * �Ƿ����
	 */
	protected boolean insertOnly;

	/**
	 * �Ϸ����ݵ���С����
	 */
	protected int minLength;

	/**
	 * Java ����
	 */
	protected String packageName;

	/**
	 * �����ṩ�Ľ���������
	 */
	protected String parseClass;

	/**
	 * ��������,csv,text,xls
	 */
	protected String parseType;

	/**
	 * �����������ֶ�����
	 */
	protected String primaryKey;

	/**
	 * �ָ���
	 */
	protected String split;

	protected String sql;

	/**
	 * ��ʼ����,��1��ʼ
	 */
	protected int startRow;

	/**
	 * �����������(����Ҫ�����footer��Ϣ����)
	 */
	protected int stopSkipRow;

	/**
	 * ֹͣ�����ַ�
	 */
	protected String stopWord;

	/**
	 * ���ݿ������
	 */
	protected String tableName;

	/**
	 * ����
	 */
	protected String title;

	public TableModel() {

	}

	public void addCollectionColumn(String columnName,
			Collection<Object> collection) {
		if (columns == null) {
			columns = new java.util.ArrayList<ColumnModel>();
		}
		ColumnModel column = new ColumnModel();
		column.setColumnName(columnName);
		column.setJavaType("Collection");
		column.setCollectionValues(collection);
		column.setValue(collection);
		column.setTable(this);
		columns.add(column);
	}

	public void addColumn(ColumnModel column) {
		if (columns == null) {
			columns = new java.util.ArrayList<ColumnModel>();
		}
		column.setTable(this);
		columns.add(column);
	}

	@SuppressWarnings({ "rawtypes" })
	public void addColumn(String columnName, String javaType, Object value) {
		if (columns == null) {
			columns = new java.util.ArrayList<ColumnModel>();
		}
		ColumnModel column = new ColumnModel();
		column.setColumnName(columnName);
		column.setJavaType(javaType);
		if (value instanceof Collection) {
			column.setCollectionValues((Collection) value);
		}
		column.setValue(value);
		column.setTable(this);
		columns.add(column);
	}

	public void addDateColumn(String columnName, Date value) {
		this.addColumn(columnName, "Date", value);
	}

	public void addDoubleColumn(String columnName, Double value) {
		this.addColumn(columnName, "Double", value);
	}

	public void addExclude(String exclude) {
		if (excludes == null) {
			excludes = new java.util.ArrayList<String>();
		}
		excludes.add(exclude);
	}

	public void addIntegerColumn(String columnName, Integer value) {
		this.addColumn(columnName, "Integer", value);
	}

	public void addLongColumn(String columnName, Long value) {
		this.addColumn(columnName, "Long", value);
	}

	public void addStringColumn(String columnName, String value) {
		this.addColumn(columnName, "String", value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableModel other = (TableModel) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
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

	public List<String> getExcludes() {
		if (excludes == null) {
			excludes = new java.util.ArrayList<String>();
		}
		return excludes;
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

	public String getSplit() {
		return split;
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
		if (tableName != null) {
			tableName = tableName.trim().toUpperCase();
		}
		return tableName;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	public boolean isInsertOnly() {
		return insertOnly;
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

	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public void setIdColumn(ColumnModel idColumn) {
		this.idColumn = idColumn;
	}

	public void setInsertOnly(boolean insertOnly) {
		this.insertOnly = insertOnly;
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

	public void setSplit(String split) {
		this.split = split;
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