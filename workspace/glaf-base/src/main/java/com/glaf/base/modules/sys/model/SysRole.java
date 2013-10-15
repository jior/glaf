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
import com.glaf.base.modules.sys.util.SysRoleJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "sys_role")
public class SysRole implements Serializable, JSONable {
	private static final long serialVersionUID = 7738558740111388611L;
	/**
	 * ����
	 */
	@Column(name = "CODE", length = 50)
	protected String code;

	/**
	 * ������
	 */
	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	/**
	 * ��������
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	protected Date createDate;

	/**
	 * ����
	 */
	@Column(name = "ROLEDESC", length = 500)
	protected String desc;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * ����
	 */
	@Column(name = "NAME", length = 100)
	protected String name;

	@javax.persistence.Transient
	protected long nodeId;

	/**
	 * ���
	 */
	@Column(name = "SORT")
	protected int sort;

	/**
	 * �޸���
	 */
	@Column(name = "UPDATEBY", length = 50)
	protected String updateBy;

	/**
	 * �޸�����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE")
	protected Date updateDate;

	public SysRole() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysRole other = (SysRole) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getCode() {
		return this.code;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getDesc() {
		return this.desc;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public long getNodeId() {
		return nodeId;
	}

	public int getSort() {
		return this.sort;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public SysRole jsonToObject(JSONObject jsonObject) {
		return SysRoleJsonFactory.jsonToObject(jsonObject);
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

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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