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
 
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.ColumnDefinitionJsonFactory;
import com.alibaba.fastjson.JSONObject;

/**
 * 数据字段定义
 * 
 */
@Entity
@Table(name = "SYS_COLUMN")
public class ColumnDefinition implements java.io.Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 100, nullable = false)
	protected String id;

	/**
	 * 查询编号
	 */
	@Column(name = "QUERYID_", length = 50, nullable = true)
	protected String queryId;

	/**
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 50, nullable = true)
	protected String tableName;

	/**
	 * 目标ID
	 */
	@Column(name = "TARGETID_", length = 50, nullable = true)
	protected String targetId;

	/**
	 * 数据库中表的别名
	 */
	@Column(name = "ALIAS_", length = 50)
	protected String alias;

	/**
	 * 数据库字段名称
	 */
	@Column(name = "COLUMNNAME_", length = 50)
	protected String columnName;

	/**
	 * 数据库字段标签
	 */
	@Column(name = "COLUMNLABEL_", length = 50)
	protected String columnLabel;

	/**
	 * 属性名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 100)
	protected String title;

	/**
	 * ENGLISH标题
	 */
	@Column(name = "ENGLISHTITLE_", length = 100)
	protected String englishTitle;

	/**
	 * 字段长度
	 */
	@Column(name = "LENGTH_")
	protected int length;

	/**
	 * 小数位数
	 */
	@Column(name = "SCALE_")
	protected int scale;

	/**
	 * 字段精度
	 */
	@Column(name = "PRECISION_")
	protected int precision;

	/**
	 * 是否主键
	 */
	@Column(name = "PRIMARYKEY_", length = 10)
	protected String primaryKey;

	/**
	 * 是否为空
	 */
	@Column(name = "NULL_", length = 10)
	protected String nullable = "1";

	/**
	 * 是否冻结列
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
	 * 是否可调整列宽
	 */
	@Column(name = "RESIZABLE_", length = 10)
	protected String resizable;

	/**
	 * 是否隐藏列
	 */
	@Column(name = "HIDDEN_", length = 10)
	protected String hidden;

	/**
	 * 在列头部显示的提示文字
	 */
	@Column(name = "TOOLTIP_", length = 100)
	protected String tooltip;

	/**
	 * 字段顺序号
	 */
	@Column(name = "ORDINAL_")
	protected int ordinal;

	/**
	 * 数据库类型
	 */
	@javax.persistence.Transient
	protected int dataType;

	/**
	 * Java类型
	 */
	@Column(name = "JAVATYPE_", length = 20)
	protected String javaType;

	/**
	 * 输入类型（文本框、数值输入、日期输入、下拉列表、复选框）
	 */
	@Column(name = "INPUTTYPE_", length = 50)
	protected String inputType;

	/**
	 * 下拉列表的值字段
	 */
	@Column(name = "VALUEFIELD_", length = 50)
	protected String valueField;

	/**
	 * 下拉列表的文本字段
	 */
	@Column(name = "TEXTFIELD_", length = 50)
	protected String textField;

	/**
	 * 下拉列表的取数URL
	 */
	@Column(name = "URL_", length = 250)
	protected String url;

	/**
	 * 验证类型
	 */
	@Column(name = "VALIDTYPE_", length = 50)
	protected String validType;

	/**
	 * 是否必填
	 */
	@Column(name = "required", length = 10)
	protected String required = "0";

	/**
	 * 正则表达式
	 */
	@Column(name = "REGEX_", length = 100)
	protected String regex;

	/**
	 * 默认值
	 */
	@Column(name = "DEFAULTVALUE_", length = 200)
	protected String defaultValue;

	/**
	 * 鉴别类型,C-数据库字段，P-查询参数
	 */
	@Column(name = "DISCRIMINATOR_", length = 10)
	protected String discriminator;

	/**
	 * 公式
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
	 * 转换器
	 */
	@Column(name = "TRANSLATOR_", length = 100)
	protected String translator;

	/**
	 * 汇总类型，sum-求和、count-算个数
	 */
	@Column(name = "SUMMARYTYPE_", length = 50)
	protected String summaryType;

	/**
	 * 汇总表达式
	 */
	@Column(name = "SUMMARYEXPR_", length = 200)
	protected String summaryExpr;

	/**
	 * 显示类型 0-不显示，1-表单，2-表单及列表
	 */
	@Column(name = "DISPLAYTYPE_")
	protected int displayType;

	/**
	 * 是否可排序
	 */
	@Column(name = "SORTABLE_", length = 10)
	protected String sortable;

	/**
	 * 排序类型 int-整形、number-数值、date-日期时间
	 */
	@Column(name = "SORTTYPE_", length = 50)
	protected String sortType;

	@Column(name = "SYSTEMFLAG_", length = 2)
	protected String systemFlag;

	/**
	 * 显示格式
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
	 * 参数是否为集合类型
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
		return ColumnDefinitionJsonFactory.jsonToObject(jsonObject);
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
		return ColumnDefinitionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ColumnDefinitionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}