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

public class ColumnModel implements java.io.Serializable,
		java.lang.Comparable<ColumnModel> {

	private static final long serialVersionUID = 1L;

	protected Boolean booleanValue;

	protected String category;

	protected Collection<?> collectionValues;

	protected String columnName;

	/**
	 * 货币格式
	 */
	protected String currency;

	protected Date dateValue;

	/**
	 * 小数位数
	 */
	protected int decimal;

	protected Double doubleValue;

	/**
	 * 数据格式，如果是日期，需要指定格式，如YYYYMMDD
	 */
	protected String format;

	protected Integer intValue;

	protected String javaType;

	/**
	 * 长度
	 */
	protected int length;

	protected Long longValue;

	/**
	 * 映射名称
	 */
	protected String mapping;

	/**
	 * Java属性名称
	 */
	protected String name;

	/**
	 * 取数位置，csv或Excel单元格的位置（从1开始）
	 */
	protected int position;

	/**
	 * 精度
	 */
	protected int precision;

	protected boolean required;

	/**
	 * 次标题
	 */
	protected String secondTitle;

	protected String series;

	protected String stringValue;

	protected TableModel table;

	protected boolean temporary;

	/**
	 * 标题
	 */
	protected String title;

	/**
	 * 去掉首尾空格
	 */
	protected String trimType;

	/**
	 * 数据类型:String,Boolean,Integer,Double,Long,Date
	 */
	protected String type;

	protected Object value;

	/**
	 * 取值表达式
	 */
	protected String valueExpression;

	public ColumnModel() {

	}

	public ColumnModel(String columnName, String name, Object value) {
		this.columnName = columnName;
		this.name = name;
		this.value = value;
	}

	public int compareTo(ColumnModel o) {
		if (doubleValue == null || o == null || o.getDoubleValue() == null) {
			return -1;
		}

		int l = (int) Math.round(this.doubleValue - o.getDoubleValue());

		int ret = 0;

		if (l > 0) {
			ret = -1;
		} else if (l < 0) {
			ret = 1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnModel other = (ColumnModel) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equalsIgnoreCase(other.columnName))
			return false;
		return true;
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public String getCategory() {
		return category;
	}

	public Collection<?> getCollectionValues() {
		return collectionValues;
	}

	public String getColumnName() {
		if (columnName != null) {
			columnName = columnName.trim().toUpperCase();
		}
		return columnName;
	}

	public String getCurrency() {
		return currency;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public int getDecimal() {
		return decimal;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public String getFormat() {
		return format;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public String getJavaType() {
		return javaType;
	}

	public int getLength() {
		return length;
	}

	public Long getLongValue() {
		return longValue;
	}

	public String getMapping() {
		return mapping;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	public int getPrecision() {
		return precision;
	}

	public String getSecondTitle() {
		return secondTitle;
	}

	public String getSeries() {
		return series;
	}

	public String getStringValue() {
		return stringValue;
	}

	public TableModel getTable() {
		return table;
	}

	public String getTitle() {
		return title;
	}

	public String getTrimType() {
		return trimType;
	}

	public String getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public Object getValue(String name) {
		return value;
	}

	public String getValueExpression() {
		return valueExpression;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		return result;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isTemporary() {
		return temporary;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCollectionValues(Collection<?> collectionValues) {
		this.collectionValues = collectionValues;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public void setDecimal(int decimal) {
		this.decimal = decimal;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public void setTable(TableModel table) {
		this.table = table;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTrimType(String trimType) {
		this.trimType = trimType;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}

}