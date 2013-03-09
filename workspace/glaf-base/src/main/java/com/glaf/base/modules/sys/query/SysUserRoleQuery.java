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

package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysUserRoleQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> rowIds;
	protected Long userId;
	protected Long userIdGreaterThanOrEqual;
	protected Long userIdLessThanOrEqual;
	protected List<Long> userIds;
	protected Long deptRoleId;
	protected Long deptRoleIdGreaterThanOrEqual;
	protected Long deptRoleIdLessThanOrEqual;
	protected List<Long> deptRoleIds;
	protected Integer authorized;
	protected Long authorizeFrom;
	protected List<Long> authorizeFroms;

	protected Date availDateStartGreaterThanOrEqual;
	protected Date availDateStartLessThanOrEqual;

	protected Date availDateEndGreaterThanOrEqual;
	protected Date availDateEndLessThanOrEqual;

	protected String processDescriptionLike;

	public SysUserRoleQuery() {

	}

	public Long getUserId() {
		return userId;
	}

	public Long getUserIdGreaterThanOrEqual() {
		return userIdGreaterThanOrEqual;
	}

	public Long getUserIdLessThanOrEqual() {
		return userIdLessThanOrEqual;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public Long getDeptRoleId() {
		return deptRoleId;
	}

	public Long getDeptRoleIdGreaterThanOrEqual() {
		return deptRoleIdGreaterThanOrEqual;
	}

	public Long getDeptRoleIdLessThanOrEqual() {
		return deptRoleIdLessThanOrEqual;
	}

	public List<Long> getDeptRoleIds() {
		return deptRoleIds;
	}

	public Integer getAuthorized() {
		return authorized;
	}

	public Long getAuthorizeFrom() {
		return authorizeFrom;
	}

	public List<Long> getAuthorizeFroms() {
		return authorizeFroms;
	}

	public Date getAvailDateStartGreaterThanOrEqual() {
		return availDateStartGreaterThanOrEqual;
	}

	public Date getAvailDateStartLessThanOrEqual() {
		return availDateStartLessThanOrEqual;
	}

	public Date getAvailDateEndGreaterThanOrEqual() {
		return availDateEndGreaterThanOrEqual;
	}

	public Date getAvailDateEndLessThanOrEqual() {
		return availDateEndLessThanOrEqual;
	}

	public String getProcessDescriptionLike() {
		if (processDescriptionLike != null
				&& processDescriptionLike.trim().length() > 0) {
			if (!processDescriptionLike.startsWith("%")) {
				processDescriptionLike = "%" + processDescriptionLike;
			}
			if (!processDescriptionLike.endsWith("%")) {
				processDescriptionLike = processDescriptionLike + "%";
			}
		}
		return processDescriptionLike;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserIdGreaterThanOrEqual(Long userIdGreaterThanOrEqual) {
		this.userIdGreaterThanOrEqual = userIdGreaterThanOrEqual;
	}

	public void setUserIdLessThanOrEqual(Long userIdLessThanOrEqual) {
		this.userIdLessThanOrEqual = userIdLessThanOrEqual;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public void setDeptRoleId(Long deptRoleId) {
		this.deptRoleId = deptRoleId;
	}

	public void setDeptRoleIdGreaterThanOrEqual(
			Long deptRoleIdGreaterThanOrEqual) {
		this.deptRoleIdGreaterThanOrEqual = deptRoleIdGreaterThanOrEqual;
	}

	public void setDeptRoleIdLessThanOrEqual(Long deptRoleIdLessThanOrEqual) {
		this.deptRoleIdLessThanOrEqual = deptRoleIdLessThanOrEqual;
	}

	public void setDeptRoleIds(List<Long> deptRoleIds) {
		this.deptRoleIds = deptRoleIds;
	}

	public void setAuthorized(Integer authorized) {
		this.authorized = authorized;
	}

	public void setAuthorizeFrom(Long authorizeFrom) {
		this.authorizeFrom = authorizeFrom;
	}

	public void setAuthorizeFroms(List<Long> authorizeFroms) {
		this.authorizeFroms = authorizeFroms;
	}

	public void setAvailDateStartGreaterThanOrEqual(
			Date availDateStartGreaterThanOrEqual) {
		this.availDateStartGreaterThanOrEqual = availDateStartGreaterThanOrEqual;
	}

	public void setAvailDateStartLessThanOrEqual(
			Date availDateStartLessThanOrEqual) {
		this.availDateStartLessThanOrEqual = availDateStartLessThanOrEqual;
	}

	public void setAvailDateEndGreaterThanOrEqual(
			Date availDateEndGreaterThanOrEqual) {
		this.availDateEndGreaterThanOrEqual = availDateEndGreaterThanOrEqual;
	}

	public void setAvailDateEndLessThanOrEqual(Date availDateEndLessThanOrEqual) {
		this.availDateEndLessThanOrEqual = availDateEndLessThanOrEqual;
	}

	public void setProcessDescriptionLike(String processDescriptionLike) {
		this.processDescriptionLike = processDescriptionLike;
	}

	public SysUserRoleQuery userId(Long userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public SysUserRoleQuery userIdGreaterThanOrEqual(
			Long userIdGreaterThanOrEqual) {
		if (userIdGreaterThanOrEqual == null) {
			throw new RuntimeException("userId is null");
		}
		this.userIdGreaterThanOrEqual = userIdGreaterThanOrEqual;
		return this;
	}

	public SysUserRoleQuery userIdLessThanOrEqual(Long userIdLessThanOrEqual) {
		if (userIdLessThanOrEqual == null) {
			throw new RuntimeException("userId is null");
		}
		this.userIdLessThanOrEqual = userIdLessThanOrEqual;
		return this;
	}

	public SysUserRoleQuery userIds(List<Long> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

	public SysUserRoleQuery deptRoleId(Long deptRoleId) {
		if (deptRoleId == null) {
			throw new RuntimeException("deptRoleId is null");
		}
		this.deptRoleId = deptRoleId;
		return this;
	}

	public SysUserRoleQuery deptRoleIdGreaterThanOrEqual(
			Long deptRoleIdGreaterThanOrEqual) {
		if (deptRoleIdGreaterThanOrEqual == null) {
			throw new RuntimeException("deptRoleId is null");
		}
		this.deptRoleIdGreaterThanOrEqual = deptRoleIdGreaterThanOrEqual;
		return this;
	}

	public SysUserRoleQuery deptRoleIdLessThanOrEqual(
			Long deptRoleIdLessThanOrEqual) {
		if (deptRoleIdLessThanOrEqual == null) {
			throw new RuntimeException("deptRoleId is null");
		}
		this.deptRoleIdLessThanOrEqual = deptRoleIdLessThanOrEqual;
		return this;
	}

	public SysUserRoleQuery deptRoleIds(List<Long> deptRoleIds) {
		if (deptRoleIds == null) {
			throw new RuntimeException("deptRoleIds is empty ");
		}
		this.deptRoleIds = deptRoleIds;
		return this;
	}

	public SysUserRoleQuery authorized(Integer authorized) {
		if (authorized == null) {
			throw new RuntimeException("authorized is null");
		}
		this.authorized = authorized;
		return this;
	}

	public SysUserRoleQuery authorizeFrom(Long authorizeFrom) {
		if (authorizeFrom == null) {
			throw new RuntimeException("authorizeFrom is null");
		}
		this.authorizeFrom = authorizeFrom;
		return this;
	}

	public SysUserRoleQuery availDateStartGreaterThanOrEqual(
			Date availDateStartGreaterThanOrEqual) {
		if (availDateStartGreaterThanOrEqual == null) {
			throw new RuntimeException("availDateStart is null");
		}
		this.availDateStartGreaterThanOrEqual = availDateStartGreaterThanOrEqual;
		return this;
	}

	public SysUserRoleQuery availDateStartLessThanOrEqual(
			Date availDateStartLessThanOrEqual) {
		if (availDateStartLessThanOrEqual == null) {
			throw new RuntimeException("availDateStart is null");
		}
		this.availDateStartLessThanOrEqual = availDateStartLessThanOrEqual;
		return this;
	}

	public SysUserRoleQuery availDateEndGreaterThanOrEqual(
			Date availDateEndGreaterThanOrEqual) {
		if (availDateEndGreaterThanOrEqual == null) {
			throw new RuntimeException("availDateEnd is null");
		}
		this.availDateEndGreaterThanOrEqual = availDateEndGreaterThanOrEqual;
		return this;
	}

	public SysUserRoleQuery availDateEndLessThanOrEqual(
			Date availDateEndLessThanOrEqual) {
		if (availDateEndLessThanOrEqual == null) {
			throw new RuntimeException("availDateEnd is null");
		}
		this.availDateEndLessThanOrEqual = availDateEndLessThanOrEqual;
		return this;
	}

	public SysUserRoleQuery processDescriptionLike(String processDescriptionLike) {
		if (processDescriptionLike == null) {
			throw new RuntimeException("processDescription is null");
		}
		this.processDescriptionLike = processDescriptionLike;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID" + a_x;
			}

			if ("deptRoleId".equals(sortColumn)) {
				orderBy = "E.ROLEID" + a_x;
			}

			if ("authorized".equals(sortColumn)) {
				orderBy = "E.AUTHORIZED" + a_x;
			}

			if ("authorizeFromId".equals(sortColumn)) {
				orderBy = "E.AUTHORIZEFROM" + a_x;
			}

			if ("availDateStart".equals(sortColumn)) {
				orderBy = "E.AVAILDATESTART" + a_x;
			}

			if ("availDateEnd".equals(sortColumn)) {
				orderBy = "E.AVAILDATEEND" + a_x;
			}

			if ("processDescription".equals(sortColumn)) {
				orderBy = "E.PROCESSDESCRIPTION" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("userId", "USERID");
		addColumn("deptRoleId", "ROLEID");
		addColumn("authorized", "AUTHORIZED");
		addColumn("authorizeFromId", "AUTHORIZEFROM");
		addColumn("availDateStart", "AVAILDATESTART");
		addColumn("availDateEnd", "AVAILDATEEND");
		addColumn("processDescription", "PROCESSDESCRIPTION");
	}

}