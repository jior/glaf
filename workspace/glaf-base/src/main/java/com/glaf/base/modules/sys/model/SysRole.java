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

package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SysRole implements Serializable, JSONable {
	private static final long serialVersionUID = 7738558740111388611L;
	private long id;
	private String name;
	private String desc;
	private String code;
	private int sort;

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getSort() {
		return sort;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public SysRole jsonToObject(JSONObject jsonObject) {
		SysRole model = new SysRole();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		return model;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (desc != null) {
			jsonObject.put("desc", desc);
		}
		if (code != null) {
			jsonObject.put("code", code);
		}
		jsonObject.put("sort", sort);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (desc != null) {
			jsonObject.put("desc", desc);
		}
		if (code != null) {
			jsonObject.put("code", code);
		}
		jsonObject.put("sort", sort);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}