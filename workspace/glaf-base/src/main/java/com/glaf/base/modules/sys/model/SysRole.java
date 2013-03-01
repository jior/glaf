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
import com.glaf.base.modules.sys.util.SysRoleJsonFactory;
import com.glaf.core.base.JSONable;

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
		return SysRoleJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SysRoleJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysRoleJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}