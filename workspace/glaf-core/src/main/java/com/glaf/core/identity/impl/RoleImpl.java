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

package com.glaf.core.identity.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.identity.Role;

public class RoleImpl implements Role {

	private static final long serialVersionUID = 1L;

	protected int id;

	protected String busiessId;

	protected int domainIndex;

	protected String name;

	protected int type;

	protected int sort;

	protected int locked;

	protected int accessLevel;

	protected int roleuse;

	protected int listno;

	protected String remark;

	public RoleImpl() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleImpl other = (RoleImpl) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public String getBusiessId() {
		return busiessId;
	}

	public int getDomainIndex() {
		return domainIndex;
	}

	public int getId() {
		return id;
	}

	public int getListno() {
		return listno;
	}

	public int getLocked() {
		return locked;
	}

	public String getName() {
		return name;
	}

	public String getRemark() {
		return remark;
	}

	public int getRoleId() {
		return id;
	}

	public int getRoleuse() {
		return roleuse;
	}

	public int getSort() {
		return sort;
	}

	public int getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public Role jsonToObject(JSONObject jsonObject) {
		Role model = new RoleImpl();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getInteger("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}
		 
		return model;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public void setBusiessId(String busiessId) {
		this.busiessId = busiessId;
	}

	public void setDomainIndex(int domainIndex) {
		this.domainIndex = domainIndex;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setListno(int listno) {
		this.listno = listno;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRoleId(int roleId) {
		this.id = roleId;
	}

	public void setRoleuse(int roleuse) {
		this.roleuse = roleuse;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setType(int type) {
		this.type = type;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		jsonObject.put("roleId", id);
		jsonObject.put("name", name);
		jsonObject.put("locked", locked);
		jsonObject.put("type", type);
		jsonObject.put("roleuse", roleuse);
		jsonObject.put("listno", listno);
		if (busiessId != null) {
			jsonObject.put("busiessId", busiessId);
		}
		jsonObject.put("domainIndex", domainIndex);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		jsonObject.put("listno", listno);
		jsonObject.put("locked", locked);
		jsonObject.put("name", name);
		jsonObject.put("roleuse", roleuse);
		jsonObject.put("type", type);
		if (busiessId != null) {
			jsonObject.put("busiessId", busiessId);
		}
		jsonObject.put("domainIndex", domainIndex);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}