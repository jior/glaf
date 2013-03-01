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

package com.glaf.core.query;

import java.util.ArrayList;
import java.util.List;

public class MembershipQuery implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	protected String actorId;
	protected List<String> actorIds = new ArrayList<String>();
	protected List<String> rowIds = new ArrayList<String>();
	protected Integer nodeId;
	protected List<Integer> nodeIds;
	protected Integer roleId;
	protected List<Integer> roleIds;
	protected String objectId;
	protected String objectValue;
	protected String type;
	protected List<String> objectIds;
	protected List<String> objectValues;
	protected List<String> types;
	protected String sortColumn;
	protected String sortOrder;
	protected String orderBy;

	public MembershipQuery() {

	}

	public MembershipQuery actorId(String actorId) {
		if (actorId == null) {
			throw new RuntimeException("actorId is null");
		}
		this.actorId = actorId;
		return this;
	}

	public MembershipQuery actorIds(List<String> actorIds) {
		if (actorIds == null) {
			throw new RuntimeException("actorIds is empty ");
		}
		this.actorIds = actorIds;
		return this;
	}

	public String getActorId() {
		return actorId;
	}

	public List<String> getActorIds() {
		return actorIds;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public List<Integer> getNodeIds() {
		return nodeIds;
	}

	public String getObjectId() {
		return objectId;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public List<String> getRowIds() {
		return rowIds;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getType() {
		return type;
	}

	public List<String> getTypes() {
		return types;
	}

	public MembershipQuery nodeId(Integer nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public MembershipQuery nodeIds(List<Integer> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public MembershipQuery objectId(String objectId) {
		if (objectId == null) {
			throw new RuntimeException("objectId is null");
		}
		this.objectId = objectId;
		return this;
	}

	public MembershipQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public MembershipQuery objectValue(String objectValue) {
		if (objectValue == null) {
			throw new RuntimeException("objectValue is null");
		}
		this.objectValue = objectValue;
		return this;
	}

	public MembershipQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public MembershipQuery roleId(Integer roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public MembershipQuery roleIds(List<Integer> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}

	public MembershipQuery rowIds(List<String> rowIds) {
		if (rowIds == null) {
			throw new RuntimeException("rowIds is null");
		}
		this.rowIds = rowIds;
		return this;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorIds(List<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<Integer> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public void setRowIds(List<String> rowIds) {
		this.rowIds = rowIds;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public MembershipQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public MembershipQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

}