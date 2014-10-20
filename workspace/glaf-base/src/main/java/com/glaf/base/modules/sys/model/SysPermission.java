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
import com.glaf.base.modules.sys.util.SysPermissionJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "SYS_PERMISSION")
public class SysPermission implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 功能编号
	 */
	@Column(name = "FUNCID", nullable = false)
	protected long funcId;

	/**
	 * 部门角色编号(对应SYS_DEPT_ROLE的ID字段)
	 */
	@Column(name = "ROLEID", nullable = false)
	protected long roleId;

	public SysPermission() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysPermission other = (SysPermission) obj;
		if (funcId != other.funcId)
			return false;
		if (roleId != other.roleId)
			return false;
		return true;
	}

	public long getFuncId() {
		return this.funcId;
	}

	public long getRoleId() {
		return this.roleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (funcId ^ (funcId >>> 32));
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
		return result;
	}

	public SysPermission jsonToObject(JSONObject jsonObject) {
		return SysPermissionJsonFactory.jsonToObject(jsonObject);
	}

	public void setFuncId(long funcId) {
		this.funcId = funcId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public JSONObject toJsonObject() {
		return SysPermissionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysPermissionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}
