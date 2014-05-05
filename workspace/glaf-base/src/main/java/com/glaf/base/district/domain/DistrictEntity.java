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

package com.glaf.base.district.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.district.util.DistrictJsonFactory;
import com.glaf.core.base.JSONable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "SYS_DISTRICT")
public class DistrictEntity implements java.io.Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	@Column(name = "PARENTID_")
	protected long parentId;

	@Column(name = "CODE_", length = 50)
	protected String code;

	@Column(name = "NAME_", length = 200, nullable = false)
	protected String name;

	@Column(name = "TREEID_", length = 500)
	protected String treeId;

	@Column(name = "LEVEL_")
	protected int level;

	@Column(name = "USETYPE_", length = 50)
	protected String useType;

	@Column(name = "SORTNO_")
	protected int sortNo;

	/**
	 * 是否启用
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	public DistrictEntity() {

	}

	public String getCode() {
		return code;
	}

	public String getCreateBy() {
		return createBy;
	}

	public long getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public int getLocked() {
		return locked;
	}

	public String getName() {
		return name;
	}

	public long getParentId() {
		return parentId;
	}

	public int getSortNo() {
		return sortNo;
	}

	public String getTreeId() {
		return treeId;
	}

	public String getUseType() {
		return useType;
	}

	public DistrictEntity jsonToObject(JSONObject jsonObject) {
		return DistrictJsonFactory.jsonToObject(jsonObject);
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public JSONObject toJsonObject() {
		return DistrictJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DistrictJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}