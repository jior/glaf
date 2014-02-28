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

import java.util.Date;
import java.util.List;

public class QueryDefinitionQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected String descriptionLike;
	protected String dsName;
	protected Integer lockedGreaterThanOrEqual;
	protected Integer lockedLessThanOrEqual;
	protected String name;
	protected String parentId;
	protected List<String> queryIds;
	protected Integer revision;
	protected Integer revisionGreaterThanOrEqual;
	protected Integer revisionLessThanOrEqual;
	protected String titleLike;
	protected String type;
	protected Long nodeId;

	public QueryDefinitionQuery() {

	}

	public QueryDefinitionQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public QueryDefinitionQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public QueryDefinitionQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public QueryDefinitionQuery dsName(String dsName) {
		if (dsName == null) {
			throw new RuntimeException("dsName is null");
		}
		this.dsName = dsName;
		return this;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getDescriptionLike() {
		if (descriptionLike != null && descriptionLike.trim().length() > 0) {
			if (!descriptionLike.startsWith("%")) {
				descriptionLike = "%" + descriptionLike;
			}
			if (!descriptionLike.endsWith("%")) {
				descriptionLike = descriptionLike + "%";
			}
		}
		return descriptionLike;
	}

	public String getDsName() {
		return dsName;
	}

	public Integer getLocked() {
		return locked;
	}

	public Integer getLockedGreaterThanOrEqual() {
		return lockedGreaterThanOrEqual;
	}

	public Integer getLockedLessThanOrEqual() {
		return lockedLessThanOrEqual;
	}

	public String getName() {
		return name;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if ("name".equals(sortField)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("description".equals(sortField)) {
				orderBy = "E.DESCRIPTION_" + a_x;
			}

			if ("sql".equals(sortField)) {
				orderBy = "E.SQL_" + a_x;
			}

			if ("dsName".equals(sortField)) {
				orderBy = "E.DSNAME_" + a_x;
			}

			if ("createTime".equals(sortField)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("createBy".equals(sortField)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("locked".equals(sortField)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("revision".equals(sortField)) {
				orderBy = "E.REVISION_" + a_x;
			}

		}
		return orderBy;
	}

	public String getParentId() {
		return parentId;
	}

	public List<String> getQueryIds() {
		return queryIds;
	}

	public Integer getRevision() {
		return revision;
	}

	public Integer getRevisionGreaterThanOrEqual() {
		return revisionGreaterThanOrEqual;
	}

	public Integer getRevisionLessThanOrEqual() {
		return revisionLessThanOrEqual;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public String getType() {
		return type;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("parentId", "PARENTID_");
		addColumn("nodeId", "NODEID_");
		addColumn("targetTableName", "TARGETTABLENAME_");
		addColumn("serviceKey", "SERVICEKEY_");
		addColumn("title", "TITLE_");
		addColumn("type", "TYPE_");
		addColumn("description", "DESCRIPTION_");
		addColumn("dsName", "DSNAME_");
		addColumn("sql", "SQL_");
		addColumn("countSql", "COUNTSQL_");
		addColumn("idField", "IDFIELD_");
		addColumn("statementId", "STATEMENTID_");
		addColumn("countStatementId", "COUNTSTATEMENTID_");
		addColumn("parameterType", "PARAMETERTYPE_");
		addColumn("resultType", "RESULTTYPE_");
		addColumn("createTime", "CREATETIME_");
		addColumn("createBy", "CREATEBY_");
		addColumn("locked", "LOCKED_");
		addColumn("deleteFlag", "DELETEFLAG_");
		addColumn("revision", "REVISION_");
		addColumn("listUrl", "LISTURL_");
		addColumn("detailUrl", "DETAILURL_");
	}

	public QueryDefinitionQuery lockedGreaterThanOrEqual(
			Integer lockedGreaterThanOrEqual) {
		if (lockedGreaterThanOrEqual == null) {
			throw new RuntimeException("locked is null");
		}
		this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
		return this;
	}

	public QueryDefinitionQuery lockedLessThanOrEqual(
			Integer lockedLessThanOrEqual) {
		if (lockedLessThanOrEqual == null) {
			throw new RuntimeException("locked is null");
		}
		this.lockedLessThanOrEqual = lockedLessThanOrEqual;
		return this;
	}

	public QueryDefinitionQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}
	
	public QueryDefinitionQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public QueryDefinitionQuery parentId(String parentId) {
		if (parentId == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentId = parentId;
		return this;
	}

	public QueryDefinitionQuery queryIds(List<String> queryIds) {
		if (queryIds == null) {
			throw new RuntimeException("queryIds is null");
		}
		this.queryIds = queryIds;
		return this;
	}

	public QueryDefinitionQuery revision(Integer revision) {
		if (revision == null) {
			throw new RuntimeException("revision is null");
		}
		this.revision = revision;
		return this;
	}

	public QueryDefinitionQuery revisionGreaterThanOrEqual(
			Integer revisionGreaterThanOrEqual) {
		if (revisionGreaterThanOrEqual == null) {
			throw new RuntimeException("revision is null");
		}
		this.revisionGreaterThanOrEqual = revisionGreaterThanOrEqual;
		return this;
	}

	public QueryDefinitionQuery revisionLessThanOrEqual(
			Integer revisionLessThanOrEqual) {
		if (revisionLessThanOrEqual == null) {
			throw new RuntimeException("revision is null");
		}
		this.revisionLessThanOrEqual = revisionLessThanOrEqual;
		return this;
	}

	public QueryDefinitionQuery serviceKey(String serviceKey) {
		if (serviceKey == null) {
			throw new RuntimeException("serviceKey is null");
		}
		this.serviceKey = serviceKey;
		return this;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public void setLockedGreaterThanOrEqual(Integer lockedGreaterThanOrEqual) {
		this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
	}

	public void setLockedLessThanOrEqual(Integer lockedLessThanOrEqual) {
		this.lockedLessThanOrEqual = lockedLessThanOrEqual;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setQueryIds(List<String> queryIds) {
		this.queryIds = queryIds;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public void setRevisionGreaterThanOrEqual(Integer revisionGreaterThanOrEqual) {
		this.revisionGreaterThanOrEqual = revisionGreaterThanOrEqual;
	}

	public void setRevisionLessThanOrEqual(Integer revisionLessThanOrEqual) {
		this.revisionLessThanOrEqual = revisionLessThanOrEqual;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public QueryDefinitionQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public QueryDefinitionQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}