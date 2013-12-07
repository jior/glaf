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

package com.glaf.base.district.query;

import java.util.List;

import com.glaf.core.query.DataQuery;

public class DistrictQuery extends DataQuery {
	private static final long serialVersionUID = 1L;

	protected List<String> treeIds;

	protected String nameLike;

	protected String useType;

	protected Integer sortGreaterThanOrEqual;

	protected Integer sortLessThanOrEqual;

	public DistrictQuery() {

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

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if (columns.get(sortField) != null) {
				orderBy = " E." + columns.get(sortField) + a_x;
			}
		}
		return orderBy;
	}

	public Integer getSortGreaterThanOrEqual() {
		return sortGreaterThanOrEqual;
	}

	public Integer getSortLessThanOrEqual() {
		return sortLessThanOrEqual;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public List<String> getTreeIds() {
		return treeIds;
	}

	public String getUseType() {
		return useType;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("parentId", "PARENTID_");
		addColumn("name", "NAME_");
		addColumn("code", "CODE_");
		addColumn("level", "LEVEL_");
		addColumn("useType", "USETYPE_");
		addColumn("sortNo", "SORTNO_");
	}

	public DistrictQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
	}

	public void setSortLessThanOrEqual(Integer sortLessThanOrEqual) {
		this.sortLessThanOrEqual = sortLessThanOrEqual;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setTreeIds(List<String> treeIds) {
		this.treeIds = treeIds;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public DistrictQuery useType(String useType) {
		if (useType == null) {
			throw new RuntimeException("useType is null");
		}
		this.useType = useType;
		return this;
	}

}