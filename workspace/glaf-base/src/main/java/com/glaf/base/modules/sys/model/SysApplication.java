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
import java.util.HashSet;
import java.util.Set;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SysApplication implements Serializable, JSONable {
	private static final long serialVersionUID = 5148300850285163044L;
	private long id;
	private String name;
	private String desc;
	private String url;
	private int sort;
	private int showMenu;
	private SysTree node;
	private long nodeId;
	private Set<SysFunction> functions = new HashSet<SysFunction>();

	public SysApplication() {

	}

	public String getDesc() {
		return desc;
	}

	public Set<SysFunction> getFunctions() {
		return functions;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public SysTree getNode() {
		return node;
	}

	public long getNodeId() {
		return nodeId;
	}

	public int getShowMenu() {
		return showMenu;
	}

	public int getSort() {
		return sort;
	}

	public String getUrl() {
		return url;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setFunctions(Set<SysFunction> functions) {
		this.functions = functions;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNode(SysTree node) {
		this.node = node;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public void setShowMenu(int showMenu) {
		this.showMenu = showMenu;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SysApplication jsonToObject(JSONObject jsonObject) {
		SysApplication model = new SysApplication();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("showMenu")) {
			model.setShowMenu(jsonObject.getInteger("showMenu"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
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
		if (url != null) {
			jsonObject.put("url", url);
		}
		jsonObject.put("sort", sort);
		jsonObject.put("showMenu", showMenu);
		jsonObject.put("nodeId", nodeId);
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
		if (url != null) {
			jsonObject.put("url", url);
		}
		jsonObject.put("sort", sort);
		jsonObject.put("showMenu", showMenu);
		jsonObject.put("nodeId", nodeId);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}