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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.SystemPropertyJsonFactory;

@Entity
@Table(name = "SYS_PROPERTY")
public class SystemProperty implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "NAME_", length = 50)
	protected String name;

	@Column(name = "TITLE_", length = 200)
	protected String title;

	@Column(name = "CATEGORY_", length = 200)
	protected String category;

	/**
	 * 输入类型，easyui对应的输入组件 <br>
	 * text（文本输入）<br>
	 * datebox（日期输入）<br>
	 * numberbox（数值输入）<br>
	 * combobox（下拉列表输入）<br>
	 * checkbox（复选框输入）<br>
	 */
	@Column(name = "INPUTTYPE_", length = 50)
	protected String inputType;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	@Column(name = "VALUE_", length = 1000)
	protected String value;

	@Column(name = "INITVALUE_", length = 1000)
	protected String initValue;

	@Column(name = "MAXVALUE_")
	protected Double maxValue;

	@Column(name = "MINVALUE_")
	protected Double minValue;

	@Column(name = "LOCKED_")
	protected int locked;

	@javax.persistence.Transient
	protected JSONArray array = null;

	@javax.persistence.Transient
	protected String selectedScript = null;

	public SystemProperty() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemProperty other = (SystemProperty) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public JSONArray getArray() {
		return array;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public double getDoubleValue() {
		if (StringUtils.isNotEmpty(value) && StringUtils.isNumeric(value)) {
			return Double.parseDouble(value);
		}
		if (StringUtils.isNotEmpty(initValue)
				&& StringUtils.isNumeric(initValue)) {
			return Double.parseDouble(initValue);
		}
		return -1;
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

	public int getIntValue() {
		if (StringUtils.isNotEmpty(value) && StringUtils.isNumeric(value)) {
			return Integer.parseInt(value);
		}
		if (StringUtils.isNotEmpty(initValue)
				&& StringUtils.isNumeric(initValue)) {
			return Integer.parseInt(initValue);
		}
		return -1;
	}

	public int getLocked() {
		return locked;
	}

	public long getLongValue() {
		if (StringUtils.isNotEmpty(value) && StringUtils.isNumeric(value)) {
			return Long.parseLong(value);
		}
		if (StringUtils.isNotEmpty(initValue)
				&& StringUtils.isNumeric(initValue)) {
			return Long.parseLong(initValue);
		}
		return -1;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public Double getMinValue() {
		return minValue;
	}

	public String getName() {
		return name;
	}

	public String getSelectedScript() {
		return selectedScript;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SystemProperty jsonToObject(JSONObject jsonObject) {
		return SystemPropertyJsonFactory.jsonToObject(jsonObject);
	}

	public void setArray(JSONArray array) {
		this.array = array;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSelectedScript(String selectedScript) {
		this.selectedScript = selectedScript;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public JSONObject toJsonObject() {
		return SystemPropertyJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SystemPropertyJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}