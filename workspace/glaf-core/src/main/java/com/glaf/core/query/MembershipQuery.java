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

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MembershipQuery extends DataQuery {
	private static final long serialVersionUID = 1L;

	protected String actorId;

	protected List<String> actorIds;
	protected String attribute;

	protected String attributeLike;
	protected String modifyBy;

	protected Long nodeId;

	protected List<Long> nodeIds;
	protected String objectId;

	protected List<String> objectIds;
	protected String objectValue;
	protected List<String> objectValues;

	protected Long roleId;

	protected List<Long> roleIds;
	protected String superiorId;

	protected List<String> superiorIds;
	protected String type;

	protected List<String> types;

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

	public MembershipQuery attribute(String attribute) {
		if (attribute == null) {
			throw new RuntimeException("attribute is null");
		}
		this.attribute = attribute;
		return this;
	}

	public MembershipQuery attributeLike(String attributeLike) {
		if (attributeLike == null) {
			throw new RuntimeException("attribute is null");
		}
		this.attributeLike = attributeLike;
		return this;
	}

	public String getActorId() {
		return actorId;
	}

	public List<String> getActorIds() {
		return actorIds;
	}

	public String getAttribute() {
		return attribute;
	}

	public String getAttributeLike() {
		if (attributeLike != null && attributeLike.trim().length() > 0) {
			if (!attributeLike.startsWith("%")) {
				attributeLike = "%" + attributeLike;
			}
			if (!attributeLike.endsWith("%")) {
				attributeLike = attributeLike + "%";
			}
		}
		return attributeLike;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public List<Long> getNodeIds() {
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
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("superiorId".equals(sortColumn)) {
				orderBy = "E.SUPERIORID_" + a_x;
			}

			if ("nodeId".equals(sortColumn)) {
				orderBy = "E.NODEID_" + a_x;
			}

			if ("modifyBy".equals(sortColumn)) {
				orderBy = "E.MODIFYBY_" + a_x;
			}

			if ("objectId".equals(sortColumn)) {
				orderBy = "E.OBJECTID_" + a_x;
			}

			if ("actorId".equals(sortColumn)) {
				orderBy = "E.ACTORID_" + a_x;
			}

			if ("attribute".equals(sortColumn)) {
				orderBy = "E.ATTRIBUTE_" + a_x;
			}

			if ("modifyDate".equals(sortColumn)) {
				orderBy = "E.MODIFYDATE_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("objectValue".equals(sortColumn)) {
				orderBy = "E.OBJECTVALUE_" + a_x;
			}

			if ("roleId".equals(sortColumn)) {
				orderBy = "E.ROLEID_" + a_x;
			}

		}
		return orderBy;
	}

	public Long getRoleId() {
		return roleId;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public String getSuperiorId() {
		return superiorId;
	}

	public List<String> getSuperiorIds() {
		return superiorIds;
	}

	public String getType() {
		return type;
	}

	public List<String> getTypes() {
		return types;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("superiorId", "SUPERIORID_");
		addColumn("nodeId", "NODEID_");
		addColumn("modifyBy", "MODIFYBY_");
		addColumn("objectId", "OBJECTID_");
		addColumn("actorId", "ACTORID_");
		addColumn("attribute", "ATTRIBUTE_");
		addColumn("modifyDate", "MODIFYDATE_");
		addColumn("type", "TYPE_");
		addColumn("objectValue", "OBJECTVALUE_");
		addColumn("roleId", "ROLEID_");
	}

	public MembershipQuery modifyBy(String modifyBy) {
		if (modifyBy == null) {
			throw new RuntimeException("modifyBy is null");
		}
		this.modifyBy = modifyBy;
		return this;
	}

	public MembershipQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public MembershipQuery nodeIds(List<Long> nodeIds) {
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

	public MembershipQuery roleId(Long roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public MembershipQuery roleIds(List<Long> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorIds(List<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAttributeLike(String attributeLike) {
		this.attributeLike = attributeLike;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<Long> nodeIds) {
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

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	public void setSuperiorIds(List<String> superiorIds) {
		this.superiorIds = superiorIds;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public MembershipQuery superiorId(String superiorId) {
		if (superiorId == null) {
			throw new RuntimeException("superiorId is null");
		}
		this.superiorId = superiorId;
		return this;
	}

	public MembershipQuery superiorIds(List<String> superiorIds) {
		if (superiorIds == null) {
			throw new RuntimeException("superiorIds is empty ");
		}
		this.superiorIds = superiorIds;
		return this;
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