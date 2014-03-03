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

public class SysAccessQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long roleId;
	protected List<Long> roleIds;
	protected Long appId;
	protected List<Long> appIds;

	public SysAccessQuery() {

	}

	public SysAccessQuery appId(Long appId) {
		if (appId == null) {
			throw new RuntimeException("appId is null");
		}
		this.appId = appId;
		return this;
	}

	public SysAccessQuery appIds(List<Long> appIds) {
		if (appIds == null) {
			throw new RuntimeException("appIds is null");
		}
		this.appIds = appIds;
		return this;
	}

	public Long getAppId() {
		return appId;
	}

	public List<Long> getAppIds() {
		return appIds;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("roleId".equals(sortColumn)) {
				orderBy = "E.ROLEID" + a_x;
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("appId", "APPID");
		addColumn("roleId", "ROLEID");
	}

	public SysAccessQuery roleId(Long roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public SysAccessQuery roleIds(List<Long> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is null");
		}
		this.roleIds = roleIds;
		return this;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setAppIds(List<Long> appIds) {
		this.appIds = appIds;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

}