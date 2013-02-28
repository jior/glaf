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
	private SysUser user;
	private long userId;
	private SysDeptRole deptRole;
	private long deptRoleId;
	private int authorized;
	private SysUser authorizeFrom;
	private long authorizeFromId;
	private Date availDateStart;
	private Date availDateEnd;
	private String processDescription;

	public SysUserRole() {

	}

	public long getAuthorizeFromId() {
		return authorizeFromId;
	}

	public void setAuthorizeFromId(long authorizeFromId) {
		this.authorizeFromId = authorizeFromId;
	}

	public int getAuthorized() {
		return authorized;
	}

	public SysUser getAuthorizeFrom() {
		return authorizeFrom;
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

	public void setAuthorized(int authorized) {
		this.authorized = authorized;
	}

	public void setAuthorizeFrom(SysUser authorizeFrom) {
		this.authorizeFrom = authorizeFrom;
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

	public SysUserRole jsonToObject(JSONObject jsonObject) {
		return SysUserRoleJsonFactory.jsonToObject(jsonObject);
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