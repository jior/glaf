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
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.SysUserRoleJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "sys_user_role")
public class SysUserRole implements Serializable, JSONable {
	private static final long serialVersionUID = 4335486314285694158L;

	/**
	 * 授权人
	 */
	@Column(name = "AUTHORIZED")
	protected int authorized;// 0-角色用户 1-代理用户

	/**
	 * 委托人
	 */
	@Column(name = "AUTHORIZEFROM")
	protected long authorizeFrom;

	@javax.persistence.Transient
	protected String authorizeFromName;

	/**
	 * 结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AVAILDATEEND")
	protected Date availDateEnd;

	/**
	 * 开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AVAILDATESTART")
	protected Date availDateStart;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY")
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	protected Date createDate;

	@javax.persistence.Transient
	private SysDeptRole deptRole;

	/**
	 * 部门角色编号
	 */
	@Column(name = "ROLEID")
	protected long deptRoleId;

	@Id
	@Column(name = "ID", length = 50, nullable = false)
	protected long id;

	/**
	 * 流程描述
	 */
	@Column(name = "PROCESSDESCRIPTION")
	protected String processDescription;

	@javax.persistence.Transient
	private SysUser user;

	/**
	 * 用户名
	 */
	@Column(name = "USERID")
	protected long userId;

	public SysUserRole() {

	}

	public int getAuthorized() {
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

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
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

	public void setAuthorized(int authorized) {
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

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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