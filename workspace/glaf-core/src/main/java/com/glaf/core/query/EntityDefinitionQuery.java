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

public class EntityDefinitionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> createBys;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String name;
	protected String nameLike;
	protected Long nodeId;
	protected String parseType;
	protected String tablename;
	protected String titleLike;
	protected String type;
	protected String updateBy;
	protected List<String> updateBys;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;

	public EntityDefinitionQuery() {

	}

	public EntityDefinitionQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public EntityDefinitionQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public EntityDefinitionQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
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

	public Long getNodeId() {
		return nodeId;
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

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("tablename".equals(sortColumn)) {
				orderBy = "E.TABLENAME_" + a_x;
			}

			if ("parseType".equals(sortColumn)) {
				orderBy = "E.PARSETYPE_" + a_x;
			}

			if ("primaryKey".equals(sortColumn)) {
				orderBy = "E.primaryKey" + a_x;
			}

			if ("filePrefix".equals(sortColumn)) {
				orderBy = "E.FILEPREFIX_" + a_x;
			}

			if ("stopWord".equals(sortColumn)) {
				orderBy = "E.STOPWORD_" + a_x;
			}

			if ("javaType".equals(sortColumn)) {
				orderBy = "E.JAVATYPE_" + a_x;
			}

			if ("aggregationKeys".equals(sortColumn)) {
				orderBy = "E.AGGREGATIONKEYS_" + a_x;
			}

			if ("startRow".equals(sortColumn)) {
				orderBy = "E.STARTROW_" + a_x;
			}

			if ("insertOnly".equals(sortColumn)) {
				orderBy = "E.INSERTONLY_" + a_x;
			}

			if ("fileContent".equals(sortColumn)) {
				orderBy = "E.FILECONTENT_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.UPDATEDATE_" + a_x;
			}

		}
		return orderBy;
	}

	public String getParseType() {
		return parseType;
	}

	public String getTablename() {
		return tablename;
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

	public String getType() {
		return type;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("name", "NAME_");
		addColumn("type", "TYPE_");
		addColumn("title", "TITLE_");
		addColumn("tablename", "TABLENAME_");
		addColumn("parseType", "PARSETYPE_");
		addColumn("primaryKey", "primaryKey");
		addColumn("filePrefix", "FILEPREFIX_");
		addColumn("stopWord", "STOPWORD_");
		addColumn("javaType", "JAVATYPE_");
		addColumn("aggregationKeys", "AGGREGATIONKEYS_");
		addColumn("startRow", "STARTROW_");
		addColumn("insertOnly", "INSERTONLY_");
		addColumn("fileContent", "FILECONTENT_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateDate", "UPDATEDATE_");
	}

	public EntityDefinitionQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public EntityDefinitionQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public EntityDefinitionQuery parseType(String parseType) {
		if (parseType == null) {
			throw new RuntimeException("parseType is null");
		}
		this.parseType = parseType;
		return this;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setParseType(String parseType) {
		this.parseType = parseType;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public void setUpdateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public EntityDefinitionQuery tablename(String tablename) {
		if (tablename == null) {
			throw new RuntimeException("tablename is null");
		}
		this.tablename = tablename;
		return this;
	}

	public EntityDefinitionQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public EntityDefinitionQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public EntityDefinitionQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public EntityDefinitionQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public EntityDefinitionQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public EntityDefinitionQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

}