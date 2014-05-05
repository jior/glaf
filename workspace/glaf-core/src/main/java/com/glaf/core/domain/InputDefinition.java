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

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.InputDefinitionJsonFactory;

@Entity
@Table(name = "SYS_INPUT_DEF")
public class InputDefinition implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 50, nullable = false)
	protected String id;

	/**
	 * 模块标识号
	 */
	@Column(name = "service_key", length = 50, nullable = false)
	protected String serviceKey;

	/**
	 * 分类码
	 */
	@Column(name = "type_cd", length = 20, nullable = false)
	protected String typeCd;

	/**
	 * 分类标题
	 */
	@Column(name = "type_title", length = 200)
	protected String typeTitle;

	/**
	 * key名称
	 */
	@Column(name = "key_name", length = 50, nullable = false)
	protected String keyName;

	/**
	 * 数据类型
	 */
	@Column(name = "java_type", length = 20, nullable = false)
	protected String javaType;

	/**
	 * 标题
	 */
	@Column(name = "title", length = 200, nullable = false)
	protected String title;

	/**
	 * 输入类型，easyui对应的输入组件 <br>
	 * text（文本输入）<br>
	 * datebox（日期输入）<br>
	 * numberbox（数值输入）<br>
	 * combobox（下拉列表输入）<br>
	 * checkbox（复选框输入）<br>
	 */
	@Column(name = "input_type", length = 50)
	protected String inputType;

	/**
	 * 下拉列表的值字段
	 */
	@Column(name = "value_field", length = 50)
	protected String valueField;

	/**
	 * 下拉列表的文本字段
	 */
	@Column(name = "text_field", length = 50)
	protected String textField;

	/**
	 * 下拉列表的取数URL
	 */
	@Column(name = "url", length = 250)
	protected String url;

	/**
	 * 验证类型
	 */
	@Column(name = "valid_type", length = 50)
	protected String validType;

	/**
	 * 是否必填
	 */
	@Column(name = "required", length = 10)
	protected String required;

	/**
	 * 初始值
	 */
	@Column(name = "init_value", length = 500)
	protected String initValue;

	@javax.persistence.Transient
	protected List<ColumnModel> extendedColumns = new java.util.ArrayList<ColumnModel>();

	public InputDefinition() {

	}

	public void addExtendedColumn(ColumnModel cm) {
		if (cm != null) {
			getExtendedColumns().add(cm);
		}
	}

	public List<ColumnModel> getExtendedColumns() {
		if (extendedColumns == null) {
			extendedColumns = new java.util.ArrayList<ColumnModel>();
		}
		return extendedColumns;
	}

	public void setExtendedColumns(List<ColumnModel> extendedColumns) {
		this.extendedColumns = extendedColumns;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InputDefinition other = (InputDefinition) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public String getInitValue() {
		return initValue;
	}

	public String getInputType() {
		return inputType;
	}

	public String getJavaType() {
		return javaType;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public String getRequired() {
		return required;
	}

	public String getServiceKey() {
		return this.serviceKey;
	}

	public String getTextField() {
		return textField;
	}

	public String getTitle() {
		return title;
	}

	public String getTypeCd() {
		return this.typeCd;
	}

	public String getTypeTitle() {
		return typeTitle;
	}

	public String getUrl() {
		return url;
	}

	public String getValidType() {
		return validType;
	}

	public String getValueField() {
		return valueField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public InputDefinition jsonToObject(JSONObject jsonObject) {
		return InputDefinitionJsonFactory.jsonToObject(jsonObject);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public JSONObject toJsonObject() {
		return InputDefinitionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return InputDefinitionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}