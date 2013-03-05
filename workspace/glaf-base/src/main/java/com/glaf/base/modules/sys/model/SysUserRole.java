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
import java.util.Date;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.SysUserRoleJsonFactory;
import com.glaf.core.base.JSONable;

public class SysUserRole implements Serializable, JSONable {
	private static final long serialVersionUID = 4335486314285694158L;
	private long id;
	private long userId;
	private long deptRoleId;
	private long authorized;// 0-角色用户 1-代理用户
	private long authorizeFrom;
	private String authorizeFromName;
	private Date availDateStart;
	private Date availDateEnd;
	private String processDescription;
	private SysUser user;
	private SysDeptRole deptRole;

	public SysUserRole() {

	}

	public long getAuthorized() {
		return authorized;
	}

	public long getAuthorizeFrom() {
		return authorizeFrom;
	}

	public String getAuthorizeFromName() {
		return authorizeFromName;
	}

	public Date getAvailDateEnd() {
		return availDateEnd;
	}

	public Date getAvailDateStart() {
		return availDateStart;
	}

	public SysDeptRole getDeptRole() {
		return deptRole;
	}

	public long getDeptRoleId() {
		return deptRoleId;
	}

	public long getId() {
		return id;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public SysUser getUser() {
		return user;
	}

	public long getUserId() {
		return userId;
	}

	public SysUserRole jsonToObject(JSONObject jsonObject) {
		return SysUserRoleJsonFactory.jsonToObject(jsonObject);
	}

	public void setAuthorized(long authorized) {
		this.authorized = authorized;
	}

	public void setAuthorizeFrom(long authorizeFrom) {
		this.authorizeFrom = authorizeFrom;
	}

	public void setAuthorizeFromName(String authorizeFromName) {
		this.authorizeFromName = authorizeFromName;
	}

	public void setAvailDateEnd(Date availDateEnd) {
		this.availDateEnd = availDateEnd;
	}

	public void setAvailDateStart(Date availDateStart) {
		this.availDateStart = availDateStart;
	}

	public void setDeptRole(SysDeptRole deptRole) {
		this.deptRole = deptRole;
	}

	public void setDeptRoleId(long deptRoleId) {
		this.deptRoleId = deptRoleId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public JSONObject toJsonObject() {
		return SysUserRoleJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysUserRoleJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}