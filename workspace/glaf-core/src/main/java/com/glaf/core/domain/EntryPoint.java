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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.EntryPointJsonFactory;

@Entity
@Table(name = "SYS_ENTRYPOINT")
public class EntryPoint implements java.io.Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 节点编号
	 */
	@Basic
	@Column(name = "NODEID_")
	protected Long nodeId;

	/**
	 * 实体编号
	 */
	@Basic
	@Column(name = "ENTITYID_", length = 50)
	protected String entityId;

	/**
	 * 实体Key
	 */
	@Basic
	@Column(name = "ENTRYKEY_", length = 50)
	protected String entryKey;

	/**
	 * 名称
	 */
	@Basic
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 值
	 */
	@Basic
	@Column(name = "VALUE_", length = 500)
	protected String value;

	@javax.persistence.Transient
	protected String entityEntryId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ENTITYENTRY_", nullable = false)
	protected EntityEntry entityEntry;

	public EntryPoint() {

	}

	public EntityEntry getEntityEntry() {
		return entityEntry;
	}

	public String getEntityEntryId() {
		return entityEntryId;
	}

	public String getEntityId() {
		return entityId;
	}

	public String getEntryKey() {
		return entryKey;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public String getValue() {
		return value;
	}

	public void setEntityEntry(EntityEntry entityEntry) {
		this.entityEntry = entityEntry;
	}

	public void setEntityEntryId(String entityEntryId) {
		this.entityEntryId = entityEntryId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setEntryKey(String entryKey) {
		this.entryKey = entryKey;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntryPoint other = (EntryPoint) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public EntryPoint jsonToObject(JSONObject jsonObject) {
		return EntryPointJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return EntryPointJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EntryPointJsonFactory.toObjectNode(this);
	}

}