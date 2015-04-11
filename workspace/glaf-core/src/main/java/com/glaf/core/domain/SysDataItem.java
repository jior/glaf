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
import com.glaf.core.util.DateUtils;
import com.glaf.core.domain.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_DATA_ITEM")
public class SysDataItem implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	@Column(name = "NAME_", length = 50)
	protected String name;

	@Column(name = "TITLE_", length = 100)
	protected String title;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "QUERYID_", length = 100)
	protected String queryId;

	@Lob
	@Column(name = "QUERYSQL_", length = 2000)
	protected String querySQL;

	@Lob
	@Column(name = "PARAMETER_", length = 2000)
	protected String parameter;

	@Column(name = "TEXTFIELD_", length = 50)
	protected String textField;

	@Column(name = "VALUEFIELD_", length = 50)
	protected String valueField;

	@Column(name = "TREEIDFIELD_", length = 50)
	protected String treeIdField;

	@Column(name = "TREEPARENTIDFIELD_", length = 50)
	protected String treeParentIdField;

	@Column(name = "TREETREEIDFIELD_", length = 50)
	protected String treeTreeIdField;

	@Column(name = "TREENAMEFIELD_", length = 50)
	protected String treeNameField;

	@Column(name = "TREELISTNOFIELD_", length = 50)
	protected String treeListNoField;

	@Column(name = "URL_", length = 500)
	protected String url;

	@Column(name = "CACHEFLAG_", length = 5)
	protected String cacheFlag;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	@Column(name = "LOCKED_")
	protected int locked = 0;

	@javax.persistence.Transient
	protected List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();

	@javax.persistence.Transient
	protected JSONArray jsonArray;

	@javax.persistence.Transient
	protected String json;

	public SysDataItem() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysDataItem other = (SysDataItem) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getCacheFlag() {
		return cacheFlag;
	}

	public List<ColumnDefinition> getColumns() {
		return columns;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public long getId() {
		return this.id;
	}

	public String getJson() {
		return json;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public int getLocked() {
		return locked;
	}

	public String getName() {
		return this.name;
	}

	public String getParameter() {
		return this.parameter;
	}

	public String getQueryId() {
		return this.queryId;
	}

	public String getQuerySQL() {
		return this.querySQL;
	}

	public String getTextField() {
		return this.textField;
	}

	public String getTitle() {
		return this.title;
	}

	public String getTreeIdField() {
		return this.treeIdField;
	}

	public String getTreeListNoField() {
		return this.treeListNoField;
	}

	public String getTreeNameField() {
		return this.treeNameField;
	}

	public String getTreeParentIdField() {
		return this.treeParentIdField;
	}

	public String getTreeTreeIdField() {
		return this.treeTreeIdField;
	}

	public String getType() {
		return type;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getUpdateTimeString() {
		if (this.updateTime != null) {
			return DateUtils.getDateTime(this.updateTime);
		}
		return "";
	}

	public String getUrl() {
		return this.url;
	}

	public String getValueField() {
		return this.valueField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public SysDataItem jsonToObject(JSONObject jsonObject) {
		return SysDataItemJsonFactory.jsonToObject(jsonObject);
	}

	public void setCacheFlag(String cacheFlag) {
		this.cacheFlag = cacheFlag;
	}

	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setQuerySQL(String querySQL) {
		this.querySQL = querySQL;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTreeIdField(String treeIdField) {
		this.treeIdField = treeIdField;
	}

	public void setTreeListNoField(String treeListNoField) {
		this.treeListNoField = treeListNoField;
	}

	public void setTreeNameField(String treeNameField) {
		this.treeNameField = treeNameField;
	}

	public void setTreeParentIdField(String treeParentIdField) {
		this.treeParentIdField = treeParentIdField;
	}

	public void setTreeTreeIdField(String treeTreeIdField) {
		this.treeTreeIdField = treeTreeIdField;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public JSONObject toJsonObject() {
		return SysDataItemJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysDataItemJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
