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

package com.glaf.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.alibaba.fastjson.JSONObject;

/**
 * �����ֶζ���
 * 
 */
@Entity
@Table(name = "MX_SYS_COLUMN")
public class ColumnDefinition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * ��ѯ���
	 */
	@Column(name = "QUERYID_", length = 50, nullable = true)
	protected String queryId;

	/**
	 * ����
	 */
	@Column(name = "TABLENAME_", length = 50, nullable = true)
	protected String tableName;

	/**
	 * Ŀ��ID
	 */
	@Column(name = "TARGETID_", length = 50, nullable = true)
	protected String targetId;

	/**
	 * ���ݿ��б��ı���
	 */
	@Column(name = "ALIAS_", length = 50)
	protected String alias;

	/**
	 * ���ݿ��ֶ�����
	 */
	@Column(name = "COLUMNNAME_", length = 50)
	protected String columnName;

	/**
	 * ���ݿ��ֶα�ǩ
	 */
	@Column(name = "COLUMNLABEL_", length = 50)
	protected String columnLabel;

	/**
	 * ��������
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * ����
	 */
	@Column(name = "TITLE_", length = 100)
	protected String title;

	/**
	 * ENGLISH����
	 */
	@Column(name = "ENGLISHTITLE_", length = 100)
	protected String englishTitle;

	/**
	 * �ֶγ���
	 */
	@Column(name = "LENGTH_")
	protected int length;

	/**
	 * С��λ��
	 */
	@Column(name = "SCALE_")
	protected int scale;

	/**
	 * �ֶξ���
	 */
	@Column(name = "PRECISION_")
	protected int precision;

	/**
	 * �Ƿ�����
	 */
	@Column(name = "PRIMARYKEY_", length = 10)
	protected String primaryKey;

	/**
	 * �Ƿ�Ϊ��
	 */
	@Column(name = "NULL_", length = 10)
	protected String nullable = "1";

	/**
	 * �Ƿ񶳽���
	 */
	@Column(name = "FROZEN_", length = 10)
	protected String frozen;

	@Column(name = "UNIQUE_", length = 10)
	protected String unique;

	@Column(name = "SEARCHABLE_", length = 10)
	protected String searchable;

	@Column(name = "EDITABLE_", length = 10)
	protected String editable;

	@Column(name = "UPDATEABLE_", length = 10)
	protected String updatable;

	/**
	 * �Ƿ�ɵ����п�
	 */
	@Column(name = "RESIZABLE_", length = 10)
	protected String resizable;

	/**
	 * �Ƿ�������
	 */
	@Column(name = "HIDDEN_", length = 10)
	protected String hidden;

	/**
	 * ����ͷ����ʾ����ʾ����
	 */
	@Column(name = "TOOLTIP_", length = 100)
	protected String tooltip;

	/**
	 * �ֶ�˳���
	 */
	@Column(name = "ORDINAL_")
	protected int ordinal;

	/**
	 * ���ݿ�����
	 */
	@javax.persistence.Transient
	protected int dataType;

	/**
	 * Java����
	 */
	@Column(name = "JAVATYPE_", length = 20)
	protected String javaType;

	/**
	 * �������ͣ��ı�����ֵ���롢�������롢�����б�����ѡ��
	 */
	@Column(name = "INPUTTYPE_", length = 50)
	protected String inputType;

	/**
	 * �����б���ֵ�ֶ�
	 */
	@Column(name = "VALUEFIELD_", length = 50)
	protected String valueField;

	/**
	 * �����б����ı��ֶ�
	 */
	@Column(name = "TEXTFIELD_", length = 50)
	protected String textField;

	/**
	 * �����б���ȡ��URL
	 */
	@Column(name = "URL_", length = 250)
	protected String url;

	/**
	 * ��֤����
	 */
	@Column(name = "VALIDTYPE_", length = 50)
	protected String validType;

	/**
	 * �Ƿ����
	 */
	@Column(name = "required", length = 10)
	protected String required = "0";

	/**
	 * �������ʽ
	 */
	@Column(name = "REGEX_", length = 100)
	protected String regex;

	/**
	 * Ĭ��ֵ
	 */
	@Column(name = "DEFAULTVALUE_", length = 200)
	protected String defaultValue;

	/**
	 * ��������,C-���ݿ��ֶΣ�P-��ѯ����
	 */
	@Column(name = "DISCRIMINATOR_", length = 10)
	protected String discriminator;

	/**
	 * ��ʽ
	 */
	@Column(name = "FORMULA_", length = 200)
	protected String formula;

	@Column(name = "MASK_", length = 100)
	protected String mask;

	@Column(name = "DATACODE_", length = 50)
	protected String dataCode;

	@Column(name = "RENDERTYPE_", length = 50)
	protected String renderType;

	/**
	 * ת����
	 */
	@Column(name = "TRANSLATOR_", length = 100)
	protected String translator;

	/**
	 * �������ͣ�sum-��͡�count-�����
	 */
	@Column(name = "SUMMARYTYPE_", length = 50)
	protected String summaryType;

	/**
	 * ���ܱ���ʽ
	 */
	@Column(name = "SUMMARYEXPR_", length = 200)
	protected String summaryExpr;

	/**
	 * ��ʾ���� 0-����ʾ��1-������2-�������б�
	 */
	@Column(name = "DISPLAYTYPE_")
	protected int displayType;

	/**
	 * �Ƿ������
	 */
	@Column(name = "SORTABLE_", length = 10)
	protected String sortable;

	/**
	 * �������� int-���Ρ�number-��ֵ��date-����ʱ��
	 */
	@Column(name = "SORTTYPE_", length = 50)
	protected String sortType;

	@Column(name = "SYSTEMFLAG_", length = 2)
	protected String systemFlag;

	/**
	 * ��ʾ��ʽ
	 */
	@Column(name = "FORMATTER_", length = 200)
	protected String formatter;

	@Column(name = "ALIGN_", length = 50)
	protected String align;

	@Column(name = "HEIGHT_", length = 50)
	protected String height;

	@Column(name = "WIDTH_", length = 50)
	protected String width;

	@Column(name = "LINK_", length = 200)
	protected String link;

	/**
	 * �����Ƿ�Ϊ��������
	 */
	@Column(name = "ISCOLLECTION_", length = 10)
	protected String isCollection;

	@Column(name = "VALUEEXPRESSION_", length = 200)
	protected String valueExpression;

	@Lob
	@Column(name = "RENDERER_", length = 100)
	protected String renderer;

	@javax.persistence.Transient
	protected boolean listShow;

	@javax.persistence.Transient
	protected String stringValue;

	@javax.persistence.Transient
	protected int intValue;

	@javax.persistence.Transient
	protected long longValue;

	@javax.persistence.Transient
	protected double doubleValue;

	@javax.persistence.Transient
	protected Date dateValue;

	@javax.persistence.Transient
	protected Object value;

	public ColumnDefinition() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnDefinition other = (ColumnDefinition) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		return true;
	}

	public String getAlias() {
		return alias;
	}

	public String getAlign() {
		return align;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getDataCode() {
		return dataCode;
	}

	public int getDataType() {
		return dataType;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public int getDisplayType() {
		return displayType;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public String getEditable() {
		return editable;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public String getFirstLowerName() {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	public String getFirstUpperName() {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public String getFormatter() {
		return formatter;
	}

	public String getFormula() {
		return formula;
	}

	public String getFrozen() {
		return frozen;
	}

	public String getHeight() {
		return height;
	}

	public String getHidden() {
		return hidden;
	}

	public String getId() {
		return id;
	}

	public String getInputType() {
		return inputType;
	}

	public int getIntValue() {
		return intValue;
	}

	public String getIsCollection() {
		return isCollection;
	}

	public String getJavaType() {
		return javaType;
	}

	public int getLength() {
		return length;
	}

	public String getLink() {
		return link;
	}

	public long getLongValue() {
		return longValue;
	}

	public String getMask() {
		return mask;
	}

	public String getName() {
		return name;
	}

	public String getNullable() {
		return nullable;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public int getPrecision() {
		return precision;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public String getQueryId() {
		return queryId;
	}

	public String getRegex() {
		return regex;
	}

	public String getRenderer() {
		return renderer;
	}

	public String getRenderType() {
		return renderType;
	}

	public String getRequired() {
		return required;
	}

	public String getResizable() {
		return resizable;
	}

	public int getScale() {
		return scale;
	}

	public String getSearchable() {
		return searchable;
	}

	public String getSortable() {
		return sortable;
	}

	public String getSortType() {
		return sortType;
	}

	public String getStringValue() {
		return stringValue;
	}

	public String getSummaryExpr() {
		return summaryExpr;
	}

	public String getSummaryType() {
		return summaryType;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTargetId() {
		return targetId;
	}

	public String getTextField() {
		return textField;
	}

	public String getTitle() {
		return title;
	}

	public String getTooltip() {
		return tooltip;
	}

	public String getTranslator() {
		return translator;
	}

	public String getUnique() {
		return unique;
	}

	public String getUpdatable() {
		return updatable;
	}

	public String getUrl() {
		return url;
	}

	public String getValidType() {
		return validType;
	}

	public Object getValue() {
		return value;
	}

	public String getValueExpression() {
		return valueExpression;
	}

	public String getValueField() {
		return valueField;
	}

	public String getWidth() {
		return width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		return result;
	}

	public boolean isCollection() {
		if (StringUtils.equalsIgnoreCase(isCollection, "1")
				|| StringUtils.equalsIgnoreCase(isCollection, "Y")
				|| StringUtils.equalsIgnoreCase(isCollection, "true")) {
			return true;
		}
		return false;
	}

	public boolean isEditable() {
		if (StringUtils.equalsIgnoreCase(editable, "1")
				|| StringUtils.equalsIgnoreCase(editable, "Y")
				|| StringUtils.equalsIgnoreCase(editable, "true")) {
			return true;
		}
		return false;
	}

	public boolean isFrozen() {
		if (StringUtils.equalsIgnoreCase(frozen, "1")
				|| StringUtils.equalsIgnoreCase(frozen, "Y")
				|| StringUtils.equalsIgnoreCase(frozen, "true")) {
			return true;
		}
		return false;
	}

	public boolean isHidden() {
		if (StringUtils.equalsIgnoreCase(hidden, "1")
				|| StringUtils.equalsIgnoreCase(hidden, "Y")
				|| StringUtils.equalsIgnoreCase(hidden, "true")) {
			return true;
		}
		return false;
	}

	public boolean isListShow() {
		if (displayType == 2) {
			listShow = true;
		}
		return listShow;
	}

	public boolean isNullable() {
		if (StringUtils.equalsIgnoreCase(nullable, "1")
				|| StringUtils.equalsIgnoreCase(nullable, "Y")
				|| StringUtils.equalsIgnoreCase(nullable, "true")) {
			return true;
		}
		return false;
	}

	public boolean isPrimaryKey() {
		if (StringUtils.equalsIgnoreCase(primaryKey, "1")
				|| StringUtils.equalsIgnoreCase(primaryKey, "Y")
				|| StringUtils.equalsIgnoreCase(primaryKey, "true")) {
			return true;
		}
		return false;
	}

	public boolean isRequired() {
		if (StringUtils.equalsIgnoreCase(required, "1")
				|| StringUtils.equalsIgnoreCase(required, "Y")
				|| StringUtils.equalsIgnoreCase(required, "true")) {
			return true;
		}
		return false;
	}

	public boolean isResizable() {
		if (StringUtils.equalsIgnoreCase(resizable, "1")
				|| StringUtils.equalsIgnoreCase(resizable, "Y")
				|| StringUtils.equalsIgnoreCase(resizable, "true")) {
			return true;
		}
		return false;
	}

	public boolean isSearchable() {
		if (StringUtils.equalsIgnoreCase(searchable, "1")
				|| StringUtils.equalsIgnoreCase(searchable, "Y")
				|| StringUtils.equalsIgnoreCase(searchable, "true")) {
			return true;
		}
		return false;
	}

	public boolean isSortable() {
		if (StringUtils.equalsIgnoreCase(sortable, "1")
				|| StringUtils.equalsIgnoreCase(sortable, "Y")
				|| StringUtils.equalsIgnoreCase(sortable, "true")) {
			return true;
		}
		return false;
	}

	public boolean isUnique() {
		if (StringUtils.equalsIgnoreCase(unique, "1")
				|| StringUtils.equalsIgnoreCase(unique, "Y")
				|| StringUtils.equalsIgnoreCase(unique, "true")) {
			return true;
		}
		return false;
	}

	public boolean isUpdatable() {
		if (StringUtils.equalsIgnoreCase(updatable, "1")
				|| StringUtils.equalsIgnoreCase(updatable, "Y")
				|| StringUtils.equalsIgnoreCase(updatable, "true")) {
			return true;
		}
		return false;
	}

	public ColumnDefinition jsonToObject(JSONObject jsonObject) {
		ColumnDefinition model = new ColumnDefinition();
		if (jsonObject.containsKey("queryId")) {
			model.setQueryId(jsonObject.getString("queryId"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("targetId")) {
			model.setTargetId(jsonObject.getString("targetId"));
		}
		if (jsonObject.containsKey("alias")) {
			model.setAlias(jsonObject.getString("alias"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("columnLabel")) {
			model.setColumnLabel(jsonObject.getString("columnLabel"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("englishTitle")) {
			model.setEnglishTitle(jsonObject.getString("englishTitle"));
		}
		if (jsonObject.containsKey("length")) {
			model.setLength(jsonObject.getInteger("length"));
		}
		if (jsonObject.containsKey("scale")) {
			model.setScale(jsonObject.getInteger("scale"));
		}
		if (jsonObject.containsKey("precision")) {
			model.setPrecision(jsonObject.getInteger("precision"));
		}
		if (jsonObject.containsKey("primaryKey")) {
			model.setPrimaryKey(jsonObject.getString("primaryKey"));
		}
		if (jsonObject.containsKey("nullable")) {
			model.setNullable(jsonObject.getString("nullable"));
		}
		if (jsonObject.containsKey("frozen")) {
			model.setFrozen(jsonObject.getString("frozen"));
		}
		if (jsonObject.containsKey("unique")) {
			model.setUnique(jsonObject.getString("unique"));
		}
		if (jsonObject.containsKey("searchable")) {
			model.setSearchable(jsonObject.getString("searchable"));
		}
		if (jsonObject.containsKey("editable")) {
			model.setEditable(jsonObject.getString("editable"));
		}
		if (jsonObject.containsKey("updatable")) {
			model.setUpdatable(jsonObject.getString("updatable"));
		}
		if (jsonObject.containsKey("resizable")) {
			model.setResizable(jsonObject.getString("resizable"));
		}
		if (jsonObject.containsKey("hidden")) {
			model.setHidden(jsonObject.getString("hidden"));
		}
		if (jsonObject.containsKey("tooltip")) {
			model.setTooltip(jsonObject.getString("tooltip"));
		}
		if (jsonObject.containsKey("ordinal")) {
			model.setOrdinal(jsonObject.getInteger("ordinal"));
		}
		if (jsonObject.containsKey("javaType")) {
			model.setJavaType(jsonObject.getString("javaType"));
		}
		if (jsonObject.containsKey("inputType")) {
			model.setInputType(jsonObject.getString("inputType"));
		}
		if (jsonObject.containsKey("valueField")) {
			model.setValueField(jsonObject.getString("valueField"));
		}
		if (jsonObject.containsKey("textField")) {
			model.setTextField(jsonObject.getString("textField"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("validType")) {
			model.setValidType(jsonObject.getString("validType"));
		}
		if (jsonObject.containsKey("required")) {
			model.setRequired(jsonObject.getString("required"));
		}
		if (jsonObject.containsKey("regex")) {
			model.setRegex(jsonObject.getString("regex"));
		}
		if (jsonObject.containsKey("defaultValue")) {
			model.setDefaultValue(jsonObject.getString("defaultValue"));
		}
		if (jsonObject.containsKey("discriminator")) {
			model.setDiscriminator(jsonObject.getString("discriminator"));
		}
		if (jsonObject.containsKey("formula")) {
			model.setFormula(jsonObject.getString("formula"));
		}
		if (jsonObject.containsKey("mask")) {
			model.setMask(jsonObject.getString("mask"));
		}
		if (jsonObject.containsKey("dataCode")) {
			model.setDataCode(jsonObject.getString("dataCode"));
		}
		if (jsonObject.containsKey("renderType")) {
			model.setRenderType(jsonObject.getString("renderType"));
		}
		if (jsonObject.containsKey("translator")) {
			model.setTranslator(jsonObject.getString("translator"));
		}
		if (jsonObject.containsKey("summaryType")) {
			model.setSummaryType(jsonObject.getString("summaryType"));
		}
		if (jsonObject.containsKey("summaryExpr")) {
			model.setSummaryExpr(jsonObject.getString("summaryExpr"));
		}
		if (jsonObject.containsKey("displayType")) {
			model.setDisplayType(jsonObject.getInteger("displayType"));
		}
		if (jsonObject.containsKey("sortable")) {
			model.setSortable(jsonObject.getString("sortable"));
		}
		if (jsonObject.containsKey("sortType")) {
			model.setSortType(jsonObject.getString("sortType"));
		}
		if (jsonObject.containsKey("systemFlag")) {
			model.setSystemFlag(jsonObject.getString("systemFlag"));
		}
		if (jsonObject.containsKey("formatter")) {
			model.setFormatter(jsonObject.getString("formatter"));
		}
		if (jsonObject.containsKey("align")) {
			model.setAlign(jsonObject.getString("align"));
		}
		if (jsonObject.containsKey("height")) {
			model.setHeight(jsonObject.getString("height"));
		}
		if (jsonObject.containsKey("width")) {
			model.setWidth(jsonObject.getString("width"));
		}
		if (jsonObject.containsKey("link")) {
			model.setLink(jsonObject.getString("link"));
		}
		if (jsonObject.containsKey("isCollection")) {
			model.setIsCollection(jsonObject.getString("isCollection"));
		}
		if (jsonObject.containsKey("valueExpression")) {
			model.setValueExpression(jsonObject.getString("valueExpression"));
		}
		if (jsonObject.containsKey("renderer")) {
			model.setRenderer(jsonObject.getString("renderer"));
		}
		return model;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setCollection(boolean isCollection) {
		if (isCollection) {
			this.isCollection = "1";
		} else {
			this.isCollection = "0";
		}
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public void setDisplayType(int displayType) {
		this.displayType = displayType;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public void setEditable(boolean editable) {
		if (editable) {
			this.editable = "1";
		} else {
			this.editable = "0";
		}
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setFrozen(boolean frozen) {
		if (frozen) {
			this.frozen = "1";
		} else {
			this.frozen = "0";
		}
	}

	public void setFrozen(String frozen) {
		this.frozen = frozen;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setHidden(boolean hidden) {
		if (hidden) {
			this.hidden = "1";
		} else {
			this.hidden = "0";
		}
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setListShow(boolean listShow) {
		this.listShow = listShow;
	}

	public void setLongValue(long longValue) {
		this.longValue = longValue;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNullable(boolean nullable) {
		if (nullable) {
			this.nullable = "1";
		} else {
			this.nullable = "0";
		}
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public void setPrimaryKey(boolean primaryKey) {
		if (primaryKey) {
			this.primaryKey = "1";
		} else {
			this.primaryKey = "0";
		}
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public void setRenderType(String renderType) {
		this.renderType = renderType;
	}

	public void setRequired(boolean required) {
		if (required) {
			this.required = "1";
		} else {
			this.required = "0";
		}
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setResizable(boolean resizable) {
		if (resizable) {
			this.resizable = "1";
		} else {
			this.resizable = "0";
		}
	}

	public void setResizable(String resizable) {
		this.resizable = resizable;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public void setSearchable(boolean searchable) {
		if (searchable) {
			this.searchable = "1";
		} else {
			this.searchable = "0";
		}
	}

	public void setSearchable(String searchable) {
		this.searchable = searchable;
	}

	public void setSortable(boolean sortable) {

		if (sortable) {
			this.sortable = "1";
		} else {
			this.sortable = "0";
		}
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public void setSummaryExpr(String summaryExpr) {
		this.summaryExpr = summaryExpr;
	}

	public void setSummaryType(String summaryType) {
		this.summaryType = summaryType;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public void setTranslator(String translator) {
		this.translator = translator;
	}

	public void setUnique(boolean unique) {
		if (unique) {
			this.unique = "1";
		} else {
			this.unique = "0";
		}
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public void setUpdatable(boolean updatable) {
		if (updatable) {
			this.updatable = "1";
		} else {
			this.updatable = "0";
		}
	}

	public void setUpdatable(String updatable) {
		this.updatable = updatable;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (queryId != null) {
			jsonObject.put("queryId", queryId);
		}
		if (tableName != null) {
			jsonObject.put("tableName", tableName);
		}
		if (targetId != null) {
			jsonObject.put("targetId", targetId);
		}
		if (alias != null) {
			jsonObject.put("alias", alias);
		}
		if (columnName != null) {
			jsonObject.put("columnName", columnName);
		}
		if (columnLabel != null) {
			jsonObject.put("columnLabel", columnLabel);
		}
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (englishTitle != null) {
			jsonObject.put("englishTitle", englishTitle);
		}
		jsonObject.put("length", length);
		jsonObject.put("scale", scale);
		jsonObject.put("precision", precision);
		if (primaryKey != null) {
			jsonObject.put("primaryKey", primaryKey);
		}
		if (nullable != null) {
			jsonObject.put("nullable", nullable);
		}
		if (frozen != null) {
			jsonObject.put("frozen", frozen);
		}
		if (unique != null) {
			jsonObject.put("unique", unique);
		}
		if (searchable != null) {
			jsonObject.put("searchable", searchable);
		}
		if (editable != null) {
			jsonObject.put("editable", editable);
		}
		if (updatable != null) {
			jsonObject.put("updatable", updatable);
		}
		if (resizable != null) {
			jsonObject.put("resizable", resizable);
		}
		if (hidden != null) {
			jsonObject.put("hidden", hidden);
		}
		if (tooltip != null) {
			jsonObject.put("tooltip", tooltip);
		}
		jsonObject.put("ordinal", ordinal);
		if (javaType != null) {
			jsonObject.put("javaType", javaType);
		}
		if (inputType != null) {
			jsonObject.put("inputType", inputType);
		}
		if (valueField != null) {
			jsonObject.put("valueField", valueField);
		}
		if (textField != null) {
			jsonObject.put("textField", textField);
		}
		if (url != null) {
			jsonObject.put("url", url);
		}
		if (validType != null) {
			jsonObject.put("validType", validType);
		}
		if (required != null) {
			jsonObject.put("required", required);
		}
		if (regex != null) {
			jsonObject.put("regex", regex);
		}
		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}
		if (discriminator != null) {
			jsonObject.put("discriminator", discriminator);
		}
		if (formula != null) {
			jsonObject.put("formula", formula);
		}
		if (mask != null) {
			jsonObject.put("mask", mask);
		}
		if (dataCode != null) {
			jsonObject.put("dataCode", dataCode);
		}
		if (renderType != null) {
			jsonObject.put("renderType", renderType);
		}
		if (translator != null) {
			jsonObject.put("translator", translator);
		}
		if (summaryType != null) {
			jsonObject.put("summaryType", summaryType);
		}
		if (summaryExpr != null) {
			jsonObject.put("summaryExpr", summaryExpr);
		}
		jsonObject.put("displayType", displayType);
		if (sortable != null) {
			jsonObject.put("sortable", sortable);
		}
		if (sortType != null) {
			jsonObject.put("sortType", sortType);
		}
		if (systemFlag != null) {
			jsonObject.put("systemFlag", systemFlag);
		}
		if (formatter != null) {
			jsonObject.put("formatter", formatter);
		}
		if (align != null) {
			jsonObject.put("align", align);
		}
		if (height != null) {
			jsonObject.put("height", height);
		}
		if (width != null) {
			jsonObject.put("width", width);
		}
		if (link != null) {
			jsonObject.put("link", link);
		}
		if (isCollection != null) {
			jsonObject.put("isCollection", isCollection);
		}
		if (valueExpression != null) {
			jsonObject.put("valueExpression", valueExpression);
		}
		if (renderer != null) {
			jsonObject.put("renderer", renderer);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (queryId != null) {
			jsonObject.put("queryId", queryId);
		}
		if (tableName != null) {
			jsonObject.put("tableName", tableName);
		}
		if (targetId != null) {
			jsonObject.put("targetId", targetId);
		}
		if (alias != null) {
			jsonObject.put("alias", alias);
		}
		if (columnName != null) {
			jsonObject.put("columnName", columnName);
		}
		if (columnLabel != null) {
			jsonObject.put("columnLabel", columnLabel);
		}
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (englishTitle != null) {
			jsonObject.put("englishTitle", englishTitle);
		}
		jsonObject.put("length", length);
		jsonObject.put("scale", scale);
		jsonObject.put("precision", precision);
		if (primaryKey != null) {
			jsonObject.put("primaryKey", primaryKey);
		}
		if (nullable != null) {
			jsonObject.put("nullable", nullable);
		}
		if (frozen != null) {
			jsonObject.put("frozen", frozen);
		}
		if (unique != null) {
			jsonObject.put("unique", unique);
		}
		if (searchable != null) {
			jsonObject.put("searchable", searchable);
		}
		if (editable != null) {
			jsonObject.put("editable", editable);
		}
		if (updatable != null) {
			jsonObject.put("updatable", updatable);
		}
		if (resizable != null) {
			jsonObject.put("resizable", resizable);
		}
		if (hidden != null) {
			jsonObject.put("hidden", hidden);
		}
		if (tooltip != null) {
			jsonObject.put("tooltip", tooltip);
		}
		jsonObject.put("ordinal", ordinal);
		if (javaType != null) {
			jsonObject.put("javaType", javaType);
		}
		if (inputType != null) {
			jsonObject.put("inputType", inputType);
		}
		if (valueField != null) {
			jsonObject.put("valueField", valueField);
		}
		if (textField != null) {
			jsonObject.put("textField", textField);
		}
		if (url != null) {
			jsonObject.put("url", url);
		}
		if (validType != null) {
			jsonObject.put("validType", validType);
		}
		if (required != null) {
			jsonObject.put("required", required);
		}
		if (regex != null) {
			jsonObject.put("regex", regex);
		}
		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}
		if (discriminator != null) {
			jsonObject.put("discriminator", discriminator);
		}
		if (formula != null) {
			jsonObject.put("formula", formula);
		}
		if (mask != null) {
			jsonObject.put("mask", mask);
		}
		if (dataCode != null) {
			jsonObject.put("dataCode", dataCode);
		}
		if (renderType != null) {
			jsonObject.put("renderType", renderType);
		}
		if (translator != null) {
			jsonObject.put("translator", translator);
		}
		if (summaryType != null) {
			jsonObject.put("summaryType", summaryType);
		}
		if (summaryExpr != null) {
			jsonObject.put("summaryExpr", summaryExpr);
		}
		jsonObject.put("displayType", displayType);
		if (sortable != null) {
			jsonObject.put("sortable", sortable);
		}
		if (sortType != null) {
			jsonObject.put("sortType", sortType);
		}
		if (systemFlag != null) {
			jsonObject.put("systemFlag", systemFlag);
		}
		if (formatter != null) {
			jsonObject.put("formatter", formatter);
		}
		if (align != null) {
			jsonObject.put("align", align);
		}
		if (height != null) {
			jsonObject.put("height", height);
		}
		if (width != null) {
			jsonObject.put("width", width);
		}
		if (link != null) {
			jsonObject.put("link", link);
		}
		if (isCollection != null) {
			jsonObject.put("isCollection", isCollection);
		}
		if (valueExpression != null) {
			jsonObject.put("valueExpression", valueExpression);
		}
		if (renderer != null) {
			jsonObject.put("renderer", renderer);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}