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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.SysApplicationJsonFactory;
import com.glaf.core.base.JSONable;

public class SysApplication implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;
	private String code;
	private String desc;
	private Set<SysFunction> functions = new HashSet<SysFunction>();
	private long id;
	private String name;
	private SysTree node;
	private long nodeId;
	private int showMenu;
	private int sort;
	private String url;

	public SysApplication() {

	}

	public String getCode() {
		return code;
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

	public SysApplication jsonToObject(JSONObject jsonObject) {
		return SysApplicationJsonFactory.jsonToObject(jsonObject);
	}

	public void setCode(String code) {
		this.code = code;
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

	public JSONObject toJsonObject() {
		return SysApplicationJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysApplicationJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}
}