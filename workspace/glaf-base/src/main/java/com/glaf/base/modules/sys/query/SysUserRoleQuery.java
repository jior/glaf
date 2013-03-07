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
	protected Integer authorizedGreaterThanOrEqual;
	protected Integer authorizedLessThanOrEqual;
	protected List<Integer> authorizeds;
	protected Long authorizeFromId;
	protected Long authorizeFromIdGreaterThanOrEqual;
	protected Long authorizeFromIdLessThanOrEqual;
	protected List<Long> authorizeFromIds;
	protected Date availDateStart;
	protected Date availDateStartGreaterThanOrEqual;
	protected Date availDateStartLessThanOrEqual;
	protected List<Date> availDateStarts;
	protected Date availDateEnd;
	protected Date availDateEndGreaterThanOrEqual;
	protected Date availDateEndLessThanOrEqual;
	protected List<Date> availDateEnds;
	protected String processDescription;
	protected String processDescriptionLike;
	protected List<String> processDescriptions;

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

	public Integer getAuthorizedGreaterThanOrEqual() {
		return authorizedGreaterThanOrEqual;
	}

	public Integer getAuthorizedLessThanOrEqual() {
		return authorizedLessThanOrEqual;
	}

	public List<Integer> getAuthorizeds() {
		return authorizeds;
	}

	public Long getAuthorizeFromId() {
		return authorizeFromId;
	}

	public Long getAuthorizeFromIdGreaterThanOrEqual() {
		return authorizeFromIdGreaterThanOrEqual;
	}

	public Long getAuthorizeFromIdLessThanOrEqual() {
		return authorizeFromIdLessThanOrEqual;
	}

	public List<Long> getAuthorizeFromIds() {
		return authorizeFromIds;
	}

	public Date getAvailDateStart() {
		return availDateStart;
	}

	public Date getAvailDateStartGreaterThanOrEqual() {
		return availDateStartGreaterThanOrEqual;
	}

	public Date getAvailDateStartLessThanOrEqual() {
		return availDateStartLessThanOrEqual;
	}

	public List<Date> getAvailDateStarts() {
		return availDateStarts;
	}

	public Date getAvailDateEnd() {
		return availDateEnd;
	}

	public Date getAvailDateEndGreaterThanOrEqual() {
		return availDateEndGreaterThanOrEqual;
	}

	public Date getAvailDateEndLessThanOrEqual() {
		return availDateEndLessThanOrEqual;
	}

	public List<Date> getAvailDateEnds() {
		return availDateEnds;
	}

	public String getProcessDescription() {
		return processDescription;
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

	public List<String> getProcessDescriptions() {
		return processDescriptions;
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

	public void setAuthorizedGreaterThanOrEqual(
			Integer authorizedGreaterThanOrEqual) {
		this.authorizedGreaterThanOrEqual = authorizedGreaterThanOrEqual;
	}

	public void setAuthorizedLessThanOrEqual(Integer authorizedLessThanOrEqual) {
		this.authorizedLessThanOrEqual = authorizedLessThanOrEqual;
	}

	public void setAuthorizeds(List<Integer> authorizeds) {
		this.authorizeds = authorizeds;
	}

	public void setAuthorizeFromId(Long authorizeFromId) {
		this.authorizeFromId = authorizeFromId;
	}

	public void setAuthorizeFromIdGreaterThanOrEqual(
			Long authorizeFromIdGreaterThanOrEqual) {
		this.authorizeFromIdGreaterThanOrEqual = authorizeFromIdGreaterThanOrEqual;
	}

	public void setAuthorizeFromIdLessThanOrEqual(
			Long authorizeFromIdLessThanOrEqual) {
		this.authorizeFromIdLessThanOrEqual = authorizeFromIdLessThanOrEqual;
	}

	public void setAuthorizeFromIds(List<Long> authorizeFromIds) {
		this.authorizeFromIds = authorizeFromIds;
	}

	public void setAvailDateStart(Date availDateStart) {
		this.availDateStart = availDateStart;
	}

	public void setAvailDateStartGreaterThanOrEqual(
			Date availDateStartGreaterThanOrEqual) {
		this.availDateStartGreaterThanOrEqual = availDateStartGreaterThanOrEqual;
	}

	public void setAvailDateStartLessThanOrEqual(
			Date availDateStartLessThanOrEqual) {
		this.availDateStartLessThanOrEqual = availDateStartLessThanOrEqual;
	}

	public void setAvailDateStarts(List<Date> availDateStarts) {
		this.availDateStarts = availDateStarts;
	}

	public void setAvailDateEnd(Date availDateEnd) {
		this.availDateEnd = availDateEnd;
	}

	public void setAvailDateEndGreaterThanOrEqual(
			Date availDateEndGreaterThanOrEqual) {
		this.availDateEndGreaterThanOrEqual = availDateEndGreaterThanOrEqual;
	}

	public void setAvailDateEndLessThanOrEqual(Date availDateEndLessThanOrEqual) {
		this.availDateEndLessThanOrEqual = availDateEndLessThanOrEqual;
	}

	public void setAvailDateEnds(List<Date> availDateEnds) {
		this.availDateEnds = availDateEnds;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public void setProcessDescriptionLike(String processDescriptionLike) {
		this.processDescriptionLike = processDescriptionLike;
	}

	public void setProcessDescriptions(List<String> processDescriptions) {
		this.processDescriptions = processDescriptions;
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

	public SysUserRoleQuery authorizedGreaterThanOrEqual(
			Integer authorizedGreaterThanOrEqual) {
		if (authorizedGreaterThanOrEqual == null) {
			throw new RuntimeException("authorized is null");
		}
		this.authorizedGreaterThanOrEqual = authorizedGreaterThanOrEqual;
		return this;
	}

	public SysUserRoleQuery authorizedLessThanOrEqual(
			Integer authorizedLessThanOrEqual) {
		if (authorizedLessThanOrEqual == null) {
			throw new RuntimeException("authorized is null");
		}
		this.authorizedLessThanOrEqual = authorizedLessThanOrEqual;
		return this;
	}

	public SysUserRoleQuery authorizeds(List<Integer> authorizeds) {
		if (authorizeds == null) {
			throw new RuntimeException("authorizeds is empty ");
		}
		this.authorizeds = authorizeds;
		return this;
	}

	public SysUserRoleQuery authorizeFromId(Long authorizeFromId) {
		if (authorizeFromId == null) {
			throw new RuntimeException("authorizeFromId is null");
		}
		this.authorizeFromId = authorizeFromId;
		return this;
	}

	public SysUserRoleQuery authorizeFromIdGreaterThanOrEqual(
			Long authorizeFromIdGreaterThanOrEqual) {
		if (authorizeFromIdGreaterThanOrEqual == null) {
			throw new RuntimeException("authorizeFromId is null");
		}
		this.authorizeFromIdGreaterThanOrEqual = authorizeFromIdGreaterThanOrEqual;
		return this;
	}

	public SysUserRoleQuery authorizeFromIdLessThanOrEqual(
			Long authorizeFromIdLessThanOrEqual) {
		if (authorizeFromIdLessThanOrEqual == null) {
			throw new RuntimeException("authorizeFromId is null");
		}
		this.authorizeFromIdLessThanOrEqual = authorizeFromIdLessThanOrEqual;
		return this;
	}

	public SysUserRoleQuery authorizeFromIds(List<Long> authorizeFromIds) {
		if (authorizeFromIds == null) {
			throw new RuntimeException("authorizeFromIds is empty ");
		}
		this.authorizeFromIds = authorizeFromIds;
		return this;
	}

	public SysUserRoleQuery availDateStart(Date availDateStart) {
		if (availDateStart == null) {
			throw new RuntimeException("availDateStart is null");
		}
		this.availDateStart = availDateStart;
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

	public SysUserRoleQuery availDateStarts(List<Date> availDateStarts) {
		if (availDateStarts == null) {
			throw new RuntimeException("availDateStarts is empty ");
		}
		this.availDateStarts = availDateStarts;
		return this;
	}

	public SysUserRoleQuery availDateEnd(Date availDateEnd) {
		if (availDateEnd == null) {
			throw new RuntimeException("availDateEnd is null");
		}
		this.availDateEnd = availDateEnd;
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

	public SysUserRoleQuery availDateEnds(List<Date> availDateEnds) {
		if (availDateEnds == null) {
			throw new RuntimeException("availDateEnds is empty ");
		}
		this.availDateEnds = availDateEnds;
		return this;
	}

	public SysUserRoleQuery processDescription(String processDescription) {
		if (processDescription == null) {
			throw new RuntimeException("processDescription is null");
		}
		this.processDescription = processDescription;
		return this;
	}

	public SysUserRoleQuery processDescriptionLike(String processDescriptionLike) {
		if (processDescriptionLike == null) {
			throw new RuntimeException("processDescription is null");
		}
		this.processDescriptionLike = processDescriptionLike;
		return this;
	}

	public SysUserRoleQuery processDescriptions(List<String> processDescriptions) {
		if (processDescriptions == null) {
			throw new RuntimeException("processDescriptions is empty ");
		}
		this.processDescriptions = processDescriptions;
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