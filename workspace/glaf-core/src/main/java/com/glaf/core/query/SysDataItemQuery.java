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

public class SysDataItemQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String name;
	protected String nameLike;
	protected String titleLike;
	protected String queryId;
	protected List<String> queryIds;
	protected List<String> createBys;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected List<String> updateBys;
	protected Date updateTimeGreaterThanOrEqual;
	protected Date updateTimeLessThanOrEqual;

	public SysDataItemQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
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

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	public String getQueryId() {
		return queryId;
	}

	public List<String> getQueryIds() {
		return queryIds;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public Date getUpdateTimeGreaterThanOrEqual() {
		return updateTimeGreaterThanOrEqual;
	}

	public Date getUpdateTimeLessThanOrEqual() {
		return updateTimeLessThanOrEqual;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setQueryIds(List<String> queryIds) {
		this.queryIds = queryIds;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public void setUpdateTimeGreaterThanOrEqual(
			Date updateTimeGreaterThanOrEqual) {
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
	}

	public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
	}

	public SysDataItemQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public SysDataItemQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public SysDataItemQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public SysDataItemQuery queryId(String queryId) {
		if (queryId == null) {
			throw new RuntimeException("queryId is null");
		}
		this.queryId = queryId;
		return this;
	}

	public SysDataItemQuery queryIds(List<String> queryIds) {
		if (queryIds == null) {
			throw new RuntimeException("queryIds is empty ");
		}
		this.queryIds = queryIds;
		return this;
	}

	public SysDataItemQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public SysDataItemQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public SysDataItemQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("queryId".equals(sortColumn)) {
				orderBy = "E.QUERYID_" + a_x;
			}

			if ("querySQL".equals(sortColumn)) {
				orderBy = "E.QUERYSQL_" + a_x;
			}

			if ("parameter".equals(sortColumn)) {
				orderBy = "E.PARAMETER_" + a_x;
			}

			if ("textField".equals(sortColumn)) {
				orderBy = "E.TEXTFIELD_" + a_x;
			}

			if ("valueField".equals(sortColumn)) {
				orderBy = "E.VALUEFIELD_" + a_x;
			}

			if ("treeIdField".equals(sortColumn)) {
				orderBy = "E.TREEIDFIELD_" + a_x;
			}

			if ("treeParentIdField".equals(sortColumn)) {
				orderBy = "E.TREEPARENTIDFIELD_" + a_x;
			}

			if ("treeTreeIdField".equals(sortColumn)) {
				orderBy = "E.TREETREEIDFIELD_" + a_x;
			}

			if ("treeNameField".equals(sortColumn)) {
				orderBy = "E.TREENAMEFIELD_" + a_x;
			}

			if ("treeListNoField".equals(sortColumn)) {
				orderBy = "E.TREELISTNOFIELD_" + a_x;
			}

			if ("url".equals(sortColumn)) {
				orderBy = "E.URL_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

			if ("updateTime".equals(sortColumn)) {
				orderBy = "E.UPDATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("name", "NAME_");
		addColumn("title", "TITLE_");
		addColumn("queryId", "QUERYID_");
		addColumn("querySQL", "QUERYSQL_");
		addColumn("parameter", "PARAMETER_");
		addColumn("textField", "TEXTFIELD_");
		addColumn("valueField", "VALUEFIELD_");
		addColumn("treeIdField", "TREEIDFIELD_");
		addColumn("treeParentIdField", "TREEPARENTIDFIELD_");
		addColumn("treeTreeIdField", "TREETREEIDFIELD_");
		addColumn("treeNameField", "TREENAMEFIELD_");
		addColumn("treeListNoField", "TREELISTNOFIELD_");
		addColumn("url", "URL_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

}