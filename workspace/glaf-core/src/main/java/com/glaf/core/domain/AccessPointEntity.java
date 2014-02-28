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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.AccessEntry;
import com.glaf.core.base.AccessPoint;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.AccessPointEntityJsonFactory;

@Entity
@Table(name = "SYS_ACCESSPOINT")
public class AccessPointEntity implements java.io.Serializable, AccessPoint,
		JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Basic
	@Column(name = "NAME_")
	protected String name;

	@Basic
	@Column(name = "ACCESSLEVEL_")
	protected int accessLevel = 0;

	@Column(name = "ACCESSENTRY_", nullable = false)
	protected String accessEntryId;

	@javax.persistence.Transient
	protected AccessEntry accessEntry;

	public AccessPointEntity() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessPointEntity other = (AccessPointEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public AccessEntry getAccessEntry() {
		return accessEntry;
	}

	public String getAccessEntryId() {
		return accessEntryId;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public AccessPointEntity jsonToObject(JSONObject jsonObject) {
		return AccessPointEntityJsonFactory.jsonToObject(jsonObject);
	}

	public void setAccessEntry(AccessEntry accessEntry) {
		this.accessEntry = accessEntry;
	}

	public void setAccessEntryId(String accessEntryId) {
		this.accessEntryId = accessEntryId;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject toJsonObject() {
		return AccessPointEntityJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return AccessPointEntityJsonFactory.toObjectNode(this);
	}

}