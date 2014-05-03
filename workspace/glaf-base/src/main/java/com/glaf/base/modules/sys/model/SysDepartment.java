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
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.SysDepartmentJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "SYS_DEPARTMENT")
public class SysDepartment implements Serializable, JSONable {
	private static final long serialVersionUID = -1700125499848402378L;
	@javax.persistence.Transient
	private List<SysDepartment> children = new java.util.ArrayList<SysDepartment>();

	/**
	 * 编码
	 */
	@Column(name = "CODE", length = 250)
	protected String code;

	/**
	 * 编码2
	 */
	@Column(name = "CODE2", length = 250)
	protected String code2;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME")
	protected Date createTime;

	/**
	 * 描述
	 */
	@Column(name = "DEPTDESC", length = 500)
	protected String desc;

	/**
	 * 财务代码
	 */
	@Column(name = "FINCODE", length = 250)
	protected String fincode;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 部门级别
	 */
	@Column(name = "DEPTLEVEL")
	protected Integer level;
	

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 200)
	protected String name;

	/**
	 * 部门编号
	 */
	@Column(name = "DEPTNO")
	protected String no;

	@javax.persistence.Transient
	private SysTree node;

	/**
	 * 节点编号
	 */
	@Column(name = "NODEID")
	protected long nodeId;

	@javax.persistence.Transient
	private SysDepartment parent;

	@javax.persistence.Transient
	private Set<SysDeptRole> roles = new HashSet<SysDeptRole>();

	/**
	 * 序号
	 */
	@Column(name = "SORT")
	protected int sort;

	/**
	 * 状态
	 */
	@Column(name = "STATUS")
	protected int status = 0;// 是否有效[默认有效]

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY", length = 50)
	protected String updateBy;

	/**
	 * 修改日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE")
	protected Date updateDate;

	public SysDepartment() {

	}

	public void addChild(SysDepartment dept) {
		if (children == null) {
			children = new java.util.ArrayList<SysDepartment>();
		}
		dept.setParent(this);
		children.add(dept);
	}

	public List<SysDepartment> getChildren() {
		return children;
	}

	public String getCode() {
		return code;
	}

	public String getCode2() {
		return code2;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getDesc() {
		return desc;
	}

	public String getFincode() {
		return fincode;
	}

	public long getId() {
		return id;
	}

	public Integer getLevel() {
		return level;
	}
	
	public Integer getDeptLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public String getNo() {
		return no;
	}

	public SysTree getNode() {
		return node;
	}

	public long getNodeId() {
		return nodeId;
	}

	public SysDepartment getParent() {
		return parent;
	}

	public Set<SysDeptRole> getRoles() {
		if (roles == null) {
			roles = new HashSet<SysDeptRole>();
		}
		return roles;
	}

	public int getSort() {
		return sort;
	}

	public int getStatus() {
		return status;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public SysDepartment jsonToObject(JSONObject jsonObject) {
		return SysDepartmentJsonFactory.jsonToObject(jsonObject);
	}

	public void setChildren(List<SysDepartment> children) {
		this.children = children;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setFincode(String fincode) {
		this.fincode = fincode;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public void setDeptLevel(Integer level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setNode(SysTree node) {
		this.node = node;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public void setParent(SysDepartment parent) {
		this.parent = parent;
	}

	public void setRoles(Set<SysDeptRole> roles) {
		this.roles = roles;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public JSONObject toJsonObject() {
		return SysDepartmentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysDepartmentJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}