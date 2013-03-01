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
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.base.modules.sys.util.SysTreeJsonFactory;
import com.glaf.core.base.ITreeModel;
import com.glaf.core.base.JSONable;

public class SysTree implements Serializable, ITreeModel, JSONable {
	private static final long serialVersionUID = 2666681837822864771L;
	private long id;
	private long parent;
	private String name;
	private String desc;
	private int sort;
	private String code;
	private int deep;
	private SysApplication app;
	private SysDepartment department;

	public SysApplication getApp() {
		return app;
	}

	public String getCode() {
		return code;
	}

	public int getDeep() {
		return deep;
	}

	public SysDepartment getDepartment() {
		return department;
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

	public long getParent() {
		return parent;
	}

	public long getParentId() {
		return parent;
	}

	public int getSort() {
		return sort;
	}

	public void setApp(SysApplication app) {
		this.app = app;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
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

	public void setParent(long parent) {
		this.parent = parent;
	}

	public void setParentId(long parentId) {
		this.parent = parentId;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public SysTree jsonToObject(JSONObject jsonObject) {
		return SysTreeJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SysTreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysTreeJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}
}