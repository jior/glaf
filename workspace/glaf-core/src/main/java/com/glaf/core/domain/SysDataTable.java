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

package com.glaf.core.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.core.domain.util.SysDataTableJsonFactory;

@Entity
@Table(name = "SYS_DATA_TABLE")
public class SysDataTable implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	@Column(name = "TABLENAME_", length = 50)
	protected String tablename;

	@Column(name = "TITLE_", length = 255)
	protected String title;

	/**
	 * 0-系统表<br/>
	 * 1-扩展表
	 */
	@Column(name = "TYPE_")
	protected Integer type;

	@Column(name = "MAXUSER_")
	protected Integer maxUser;

	@Column(name = "MAXSYS_")
	protected Integer maxSys;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	@Column(name = "CONTENT_", length = 250)
	protected String content;

	@Column(name = "ISSUBTABLE_", length = 1)
	protected String isSubTable;

	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	@javax.persistence.Transient
	protected List<SysDataField> fields = new ArrayList<SysDataField>();

	public SysDataTable() {

	}

	public void addField(SysDataField field) {
		if (fields == null) {
			fields = new ArrayList<SysDataField>();
		}
		fields.add(field);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysDataTable other = (SysDataTable) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getContent() {
		return this.content;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public List<SysDataField> getFields() {
		return fields;
	}

	public String getId() {
		return this.id;
	}

	public String getIsSubTable() {
		return this.isSubTable;
	}

	public Integer getMaxSys() {
		return this.maxSys;
	}

	public Integer getMaxUser() {
		return this.maxUser;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getTablename() {
		return this.tablename;
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getType() {
		return this.type;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SysDataTable jsonToObject(JSONObject jsonObject) {
		return SysDataTableJsonFactory.jsonToObject(jsonObject);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setFields(List<SysDataField> fields) {
		this.fields = fields;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public void setMaxSys(Integer maxSys) {
		this.maxSys = maxSys;
	}

	public void setMaxUser(Integer maxUser) {
		this.maxUser = maxUser;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public JSONObject toJsonObject() {
		return SysDataTableJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysDataTableJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
