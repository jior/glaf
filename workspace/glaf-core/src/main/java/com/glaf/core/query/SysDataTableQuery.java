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

public class SysDataTableQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String tablename;
	protected List<String> tablenames;
	protected String titleLike;
	protected Integer type;
	protected List<Integer> types;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected String contentLike;
	protected String isSubTable;

	public SysDataTableQuery() {

	}

	public SysDataTableQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public SysDataTableQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public SysDataTableQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public String getContentLike() {
		if (contentLike != null && contentLike.trim().length() > 0) {
			if (!contentLike.startsWith("%")) {
				contentLike = "%" + contentLike;
			}
			if (!contentLike.endsWith("%")) {
				contentLike = contentLike + "%";
			}
		}
		return contentLike;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getIsSubTable() {
		return isSubTable;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("tablename".equals(sortColumn)) {
				orderBy = "E.TABLENAME_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("maxUser".equals(sortColumn)) {
				orderBy = "E.MAXUSER_" + a_x;
			}

			if ("maxSys".equals(sortColumn)) {
				orderBy = "E.MAXSYS_" + a_x;
			}

			if ("actorId".equals(sortColumn)) {
				orderBy = "E.ACTORID_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT_" + a_x;
			}

			if ("isSubTable".equals(sortColumn)) {
				orderBy = "E.ISSUBTABLE_" + a_x;
			}

		}
		return orderBy;
	}

	public String getTablename() {
		return tablename;
	}

	public List<String> getTablenames() {
		return tablenames;
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

	public Integer getType() {
		return type;
	}

	public List<Integer> getTypes() {
		return types;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("businessKey", "BUSINESS_KEY");
		addColumn("serviceKey", "SERVICE_KEY");
		addColumn("tablename", "TABLENAME_");
		addColumn("title", "TITLE_");
		addColumn("type", "TYPE_");
		addColumn("maxUser", "MAXUSER_");
		addColumn("maxSys", "MAXSYS_");
		addColumn("actorId", "ACTORID_");
		addColumn("createTime", "CREATETIME_");
		addColumn("content", "CONTENT_");
		addColumn("isSubTable", "ISSUBTABLE_");
	}

	public SysDataTableQuery isSubTable(String isSubTable) {
		if (isSubTable == null) {
			throw new RuntimeException("isSubTable is null");
		}
		this.isSubTable = isSubTable;
		return this;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTablenames(List<String> tablenames) {
		this.tablenames = tablenames;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}

	public SysDataTableQuery tablename(String tablename) {
		if (tablename == null) {
			throw new RuntimeException("tablename is null");
		}
		this.tablename = tablename;
		return this;
	}

	public SysDataTableQuery tablenames(List<String> tablenames) {
		if (tablenames == null) {
			throw new RuntimeException("tablenames is empty ");
		}
		this.tablenames = tablenames;
		return this;
	}

	public SysDataTableQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public SysDataTableQuery type(Integer type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public SysDataTableQuery types(List<Integer> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

}