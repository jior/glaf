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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.base.JSONable;

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

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	@Column(name = "VALUE_", length = 1000)
	protected String value;

	@Column(name = "INITVALUE_", length = 1000)
	protected String initValue;

	@Column(name = "LOCKED_")
	protected int locked;

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

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public String getInitValue() {
		return initValue;
	}

	public int getLocked() {
		return locked;
	}

	public String getName() {
		return name;
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
		SystemProperty model = new SystemProperty();
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("category")) {
			model.setCategory(jsonObject.getString("category"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
		}
		if (jsonObject.containsKey("initValue")) {
			model.setInitValue(jsonObject.getString("initValue"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		return model;
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

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
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
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (category != null) {
			jsonObject.put("category", category);
		}
		if (type != null) {
			jsonObject.put("type", type);
		}
		if (description != null) {
			jsonObject.put("description", description);
		}
		if (value != null) {
			jsonObject.put("value", value);
		}
		if (initValue != null) {
			jsonObject.put("initValue", initValue);
		}
		jsonObject.put("locked", locked);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (category != null) {
			jsonObject.put("category", category);
		}
		if (type != null) {
			jsonObject.put("type", type);
		}
		if (description != null) {
			jsonObject.put("description", description);
		}
		if (value != null) {
			jsonObject.put("value", value);
		}
		if (initValue != null) {
			jsonObject.put("initValue", initValue);
		}
		jsonObject.put("locked", locked);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}