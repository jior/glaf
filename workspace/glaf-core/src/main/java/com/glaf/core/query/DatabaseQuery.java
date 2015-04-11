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

public class DatabaseQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> databaseIds;
	protected Collection<String> appActorIds;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected String titleLike;
	protected String code;
	protected String host;
	protected String hostLike;
	protected String type;
	protected List<String> types;
	protected String active;
	protected String initFlag;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public DatabaseQuery() {

	}

	public DatabaseQuery active(String active) {
		if (active == null) {
			throw new RuntimeException("active is null");
		}
		this.active = active;
		return this;
	}

	public DatabaseQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public String getActive() {
		return active;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public String getCode() {
		return code;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public List<Long> getDatabaseIds() {
		return databaseIds;
	}

	public String getHost() {
		return host;
	}

	public String getHostLike() {
		if (hostLike != null && hostLike.trim().length() > 0) {
			if (!hostLike.startsWith("%")) {
				hostLike = "%" + hostLike;
			}
			if (!hostLike.endsWith("%")) {
				hostLike = hostLike + "%";
			}
		}
		return hostLike;
	}

	public String getInitFlag() {
		return initFlag;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public List<Long> getNodeIds() {
		return nodeIds;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("nodeId".equals(sortColumn)) {
				orderBy = "E.NODEID_" + a_x;
			}

			if ("host".equals(sortColumn)) {
				orderBy = "E.HOST_" + a_x;
			}

			if ("port".equals(sortColumn)) {
				orderBy = "E.PORT_" + a_x;
			}

			if ("user".equals(sortColumn)) {
				orderBy = "E.USER_" + a_x;
			}

			if ("password".equals(sortColumn)) {
				orderBy = "E.PASSWORD_" + a_x;
			}

			if ("path".equals(sortColumn)) {
				orderBy = "E.PATH_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("connectionString".equals(sortColumn)) {
				orderBy = "E.CONNECTIONSTRING_" + a_x;
			}

			if ("active".equals(sortColumn)) {
				orderBy = "E.ACTIVE_" + a_x;
			}

		}
		return orderBy;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + hostLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	public String getType() {
		return type;
	}

	public List<String> getTypes() {
		return types;
	}

	public DatabaseQuery host(String host) {
		if (host == null) {
			throw new RuntimeException("host is null");
		}
		this.host = host;
		return this;
	}

	public DatabaseQuery hostLike(String hostLike) {
		if (hostLike == null) {
			throw new RuntimeException("host is null");
		}
		this.hostLike = hostLike;
		return this;
	}

	public DatabaseQuery initFlag(String initFlag) {
		if (initFlag == null) {
			throw new RuntimeException("initFlag is null");
		}
		this.initFlag = initFlag;
		return this;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("nodeId", "NODEID_");
		addColumn("host", "HOST_");
		addColumn("port", "PORT_");
		addColumn("user", "USER_");
		addColumn("password", "PASSWORD_");
		addColumn("path", "PATH_");
		addColumn("type", "TYPE_");
		addColumn("connectionString", "CONNECTIONSTRING_");
		addColumn("active", "ACTIVE_");
	}

	public DatabaseQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public DatabaseQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDatabaseIds(List<Long> databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setHostLike(String hostLike) {
		this.hostLike = hostLike;
	}

	public void setInitFlag(String initFlag) {
		this.initFlag = initFlag;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<Long> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public DatabaseQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public DatabaseQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

}