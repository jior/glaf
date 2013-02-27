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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
		SysUserRole model = new SysUserRole();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getLong("userId"));
		}
		if (jsonObject.containsKey("deptRoleId")) {
			model.setDeptRoleId(jsonObject.getLong("deptRoleId"));
		}
		if (jsonObject.containsKey("authorized")) {
			model.setAuthorized(jsonObject.getInteger("authorized"));
		}
		if (jsonObject.containsKey("authorizeFromId")) {
			model.setAuthorizeFromId(jsonObject.getLong("authorizeFromId"));
		}
		if (jsonObject.containsKey("availDateStart")) {
			model.setAvailDateStart(jsonObject.getDate("availDateStart"));
		}
		if (jsonObject.containsKey("availDateEnd")) {
			model.setAvailDateEnd(jsonObject.getDate("availDateEnd"));
		}
		if (jsonObject.containsKey("processDescription")) {
			model.setProcessDescription(jsonObject
					.getString("processDescription"));
		}
		return model;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		jsonObject.put("userId", userId);
		jsonObject.put("deptRoleId", deptRoleId);
		jsonObject.put("authorized", authorized);
		jsonObject.put("authorizeFromId", authorizeFromId);
		if (availDateStart != null) {
			jsonObject.put("availDateStart", DateUtils.getDate(availDateStart));
			jsonObject.put("availDateStart_date",
					DateUtils.getDate(availDateStart));
			jsonObject.put("availDateStart_datetime",
					DateUtils.getDateTime(availDateStart));
		}
		if (availDateEnd != null) {
			jsonObject.put("availDateEnd", DateUtils.getDate(availDateEnd));
			jsonObject
					.put("availDateEnd_date", DateUtils.getDate(availDateEnd));
			jsonObject.put("availDateEnd_datetime",
					DateUtils.getDateTime(availDateEnd));
		}
		if (processDescription != null) {
			jsonObject.put("processDescription", processDescription);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		jsonObject.put("userId", userId);
		jsonObject.put("deptRoleId", deptRoleId);
		jsonObject.put("authorized", authorized);
		jsonObject.put("authorizeFromId", authorizeFromId);
		if (availDateStart != null) {
			jsonObject.put("availDateStart", DateUtils.getDate(availDateStart));
			jsonObject.put("availDateStart_date",
					DateUtils.getDate(availDateStart));
			jsonObject.put("availDateStart_datetime",
					DateUtils.getDateTime(availDateStart));
		}
		if (availDateEnd != null) {
			jsonObject.put("availDateEnd", DateUtils.getDate(availDateEnd));
			jsonObject
					.put("availDateEnd_date", DateUtils.getDate(availDateEnd));
			jsonObject.put("availDateEnd_datetime",
					DateUtils.getDateTime(availDateEnd));
		}
		if (processDescription != null) {
			jsonObject.put("processDescription", processDescription);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}