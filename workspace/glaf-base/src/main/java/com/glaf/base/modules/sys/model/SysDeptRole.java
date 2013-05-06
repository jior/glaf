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
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.SysDeptRoleJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "sys_dept_role")
public class SysDeptRole implements Serializable, JSONable {
	private static final long serialVersionUID = 273479478656626289L;

	@javax.persistence.Transient
	protected Set<SysApplication> apps = new HashSet<SysApplication>();

	/**
	 * 编码
	 */
	@Column(name = "CODE", length = 50)
	protected String code;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	protected Date createDate;

	@javax.persistence.Transient
	protected SysDepartment dept;

	/**
	 * 部门编号
	 */
	@Column(name = "DEPTID")
	protected long deptId;

	@javax.persistence.Transient
	protected Set<SysFunction> functions = new HashSet<SysFunction>();

	/**
	 * 级别
	 */
	@Column(name = "GRADE")
	protected int grade;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	@javax.persistence.Transient
	protected SysRole role;

	/**
	 * 序号
	 */
	@Column(name = "SORT")
	protected int sort;

	/**
	 * 角色编号
	 */
	@Column(name = "SYSROLEID")
	protected long sysRoleId;

	@javax.persistence.Transient
	protected Set<SysUser> users = new HashSet<SysUser>();

	public SysDeptRole() {

	}

	public Set<SysApplication> getApps() {
		if (apps == null) {
			apps = new HashSet<SysApplication>();
		}
		return apps;
	}

	public String getCode() {
		return code;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public SysDepartment getDept() {
		return dept;
	}

	public long getDeptId() {
		return deptId;
	}

	public Set<SysFunction> getFunctions() {
		if (functions == null) {
			functions = new HashSet<SysFunction>();
		}
		return functions;
	}

	public int getGrade() {
		return grade;
	}

	public long getId() {
		return id;
	}

	public SysRole getRole() {
		return role;
	}

	public int getSort() {
		return sort;
	}

	public long getSysRoleId() {
		return sysRoleId;
	}

	public Set<SysUser> getUsers() {
		if (users == null) {
			users = new HashSet<SysUser>();
		}
		return users;
	}

	public SysDeptRole jsonToObject(JSONObject jsonObject) {
		return SysDeptRoleJsonFactory.jsonToObject(jsonObject);
	}

	public void setApps(Set<SysApplication> apps) {
		this.apps = apps;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDept(SysDepartment dept) {
		this.dept = dept;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public void setFunctions(Set<SysFunction> functions) {
		this.functions = functions;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRole(SysRole role) {
		this.role = role;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setSysRoleId(long sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

	public JSONObject toJsonObject() {
		return SysDeptRoleJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysDeptRoleJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}