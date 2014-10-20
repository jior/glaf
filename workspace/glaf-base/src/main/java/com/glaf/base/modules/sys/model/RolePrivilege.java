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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.RolePrivilegeJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "SYS_ROLE_PRIVILEGE")
public class RolePrivilege implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 应用编号
	 */
	@Column(name = "APPID", nullable = false)
	protected long appId;

	/**
	 * 角色编号(对应SYS_ROLE的ID字段)
	 */
	@Column(name = "ROLEID", nullable = false)
	protected long roleId;

	public RolePrivilege() {

	}

	public long getAppId() {
		return appId;
	}

	public long getRoleId() {
		return roleId;
	}

	public RolePrivilege jsonToObject(JSONObject jsonObject) {
		return RolePrivilegeJsonFactory.jsonToObject(jsonObject);
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public JSONObject toJsonObject() {
		return RolePrivilegeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return RolePrivilegeJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}
