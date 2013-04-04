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

import java.util.List;

public class RoleQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected Integer accessLevel;
	protected Integer accessLevelGreaterThanOrEqual;
	protected Integer accessLevelLessThanOrEqual;
	protected String code;
	protected Integer domainIndex;
	protected String name;
	protected String nameLike;
	protected List<String> objectIds;
	protected List<String> objectValues;
	protected String remarkLike;
	protected Integer roleId;
	protected List<Integer> roleIds;
	protected Integer roleuse;
	protected Integer type;
	protected List<String> types;

	public RoleQuery() {

	}

	public RoleQuery accessLevel(Integer accessLevel) {
		if (accessLevel == null) {
			throw new RuntimeException("accessLevel is null");
		}
		this.accessLevel = accessLevel;
		return this;
	}

	public RoleQuery accessLevelGreaterThanOrEqual(
			Integer accessLevelGreaterThanOrEqual) {
		if (accessLevelGreaterThanOrEqual == null) {
			throw new RuntimeException("accessLevel is null");
		}
		this.accessLevelGreaterThanOrEqual = accessLevelGreaterThanOrEqual;
		return this;
	}

	public RoleQuery accessLevelLessThanOrEqual(
			Integer accessLevelLessThanOrEqual) {
		if (accessLevelLessThanOrEqual == null) {
			throw new RuntimeException("accessLevel is null");
		}
		this.accessLevelLessThanOrEqual = accessLevelLessThanOrEqual;
		return this;
	}

	public RoleQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public RoleQuery domainIndex(Integer domainIndex) {
		if (domainIndex == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndex = domainIndex;
		return this;
	}

	public Integer getAccessLevel() {
		return accessLevel;
	}

	public Integer getAccessLevelGreaterThanOrEqual() {
		return accessLevelGreaterThanOrEqual;
	}

	public Integer getAccessLevelLessThanOrEqual() {
		return accessLevelLessThanOrEqual;
	}

	public String getCode() {
		return code;
	}

	public Integer getDomainIndex() {
		return domainIndex;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public String getRemarkLike() {
		if (remarkLike != null && remarkLike.trim().length() > 0) {
			if (!remarkLike.startsWith("%")) {
				remarkLike = "%" + remarkLike;
			}
			if (!remarkLike.endsWith("%")) {
				remarkLike = remarkLike + "%";
			}
		}
		return remarkLike;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public Integer getRoleuse() {
		return roleuse;
	}

	public Integer getType() {
		return type;
	}

	public List<String> getTypes() {
		return types;
	}

	public RoleQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public RoleQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public RoleQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public RoleQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public RoleQuery remarkLike(String remarkLike) {
		if (remarkLike == null) {
			throw new RuntimeException("remark is null");
		}
		this.remarkLike = remarkLike;
		return this;
	}

	public RoleQuery roleId(Integer roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public RoleQuery roleIds(List<Integer> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}

	public RoleQuery roleuse(Integer roleuse) {
		if (roleuse == null) {
			throw new RuntimeException("roleuse is null");
		}
		this.roleuse = roleuse;
		return this;
	}

	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}

	public void setAccessLevelGreaterThanOrEqual(
			Integer accessLevelGreaterThanOrEqual) {
		this.accessLevelGreaterThanOrEqual = accessLevelGreaterThanOrEqual;
	}

	public void setAccessLevelLessThanOrEqual(Integer accessLevelLessThanOrEqual) {
		this.accessLevelLessThanOrEqual = accessLevelLessThanOrEqual;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDomainIndex(Integer domainIndex) {
		this.domainIndex = domainIndex;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setRemarkLike(String remarkLike) {
		this.remarkLike = remarkLike;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public void setRoleuse(Integer roleuse) {
		this.roleuse = roleuse;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public RoleQuery type(Integer type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}