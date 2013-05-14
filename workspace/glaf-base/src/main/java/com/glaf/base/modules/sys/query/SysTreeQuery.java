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

public class SysTreeQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected Integer departmentStatus;
	protected String descLike;
	protected String discriminator;
	protected Integer lockedGreaterThanOrEqual;
	protected Integer lockedLessThanOrEqual;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String relationColumn;
	protected String relationTable;
	protected Integer sortGreaterThan;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortLessThan;
	protected Integer sortLessThanOrEqual;
	protected String treeIdLeftLike;
	protected String treeIdLike;
	protected String treeIdRightLike;
	protected String urlLike;

	public SysTreeQuery() {

	}

	public SysTreeQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public SysTreeQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public SysTreeQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public SysTreeQuery descLike(String descLike) {
		if (descLike == null) {
			throw new RuntimeException("desc is null");
		}
		this.descLike = descLike;
		return this;
	}

	public String getCode() {
		return code;
	}

	public String getCodeLike() {
		if (codeLike != null && codeLike.trim().length() > 0) {
			if (!codeLike.startsWith("%")) {
				codeLike = "%" + codeLike;
			}
			if (!codeLike.endsWith("%")) {
				codeLike = codeLike + "%";
			}
		}
		return codeLike;
	}

	public List<String> getCodes() {
		return codes;
	}

	public Integer getDepartmentStatus() {
		return departmentStatus;
	}

	public String getDescLike() {
		if (descLike != null && descLike.trim().length() > 0) {
			if (!descLike.startsWith("%")) {
				descLike = "%" + descLike;
			}
			if (!descLike.endsWith("%")) {
				descLike = descLike + "%";
			}
		}
		return descLike;
	}

	public String getDiscriminator() {
		return discriminator;
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

	public List<String> getNames() {
		return names;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("parent".equals(sortColumn)) {
				orderBy = "E.PARENT" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("desc".equals(sortColumn)) {
				orderBy = "E.NODEDESC" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT" + a_x;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE" + a_x;
			}

			if ("icon".equals(sortColumn)) {
				orderBy = "E.icon" + a_x;
			}

			if ("iconCls".equals(sortColumn)) {
				orderBy = "E.iconCls" + a_x;
			}

			if ("url".equals(sortColumn)) {
				orderBy = "E.url" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.locked" + a_x;
			}

		}
		return orderBy;
	}

	public String getRelationColumn() {
		return relationColumn;
	}

	public String getRelationTable() {
		return relationTable;
	}

	public Integer getSortGreaterThan() {
		return sortGreaterThan;
	}

	public Integer getSortGreaterThanOrEqual() {
		return sortGreaterThanOrEqual;
	}

	public Integer getSortLessThan() {
		return sortLessThan;
	}

	public Integer getSortLessThanOrEqual() {
		return sortLessThanOrEqual;
	}

	public String getTreeIdLeftLike() {
		if (treeIdLeftLike != null && treeIdLeftLike.trim().length() > 0) {
			if (!treeIdLeftLike.endsWith("%")) {
				treeIdLeftLike = treeIdLeftLike + "%";
			}
		}

		return treeIdLeftLike;
	}

	public String getTreeIdLike() {
		if (treeIdLike != null && treeIdLike.trim().length() > 0) {
			if (!treeIdLike.startsWith("%")) {
				treeIdLike = "%" + treeIdLike;
			}
			if (!treeIdLike.endsWith("%")) {
				treeIdLike = treeIdLike + "%";
			}
		}

		return treeIdLike;
	}

	public String getTreeIdRightLike() {
		if (treeIdRightLike != null && treeIdRightLike.trim().length() > 0) {
			if (!treeIdRightLike.startsWith("%")) {
				treeIdRightLike = "%" + treeIdRightLike;
			}
		}

		return treeIdRightLike;
	}

	public String getUrlLike() {
		if (urlLike != null && urlLike.trim().length() > 0) {
			if (!urlLike.startsWith("%")) {
				urlLike = "%" + urlLike;
			}
			if (!urlLike.endsWith("%")) {
				urlLike = urlLike + "%";
			}
		}
		return urlLike;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("parent", "PARENT");
		addColumn("name", "NAME");
		addColumn("desc", "NODEDESC");
		addColumn("sort", "SORT");
		addColumn("code", "CODE");
		addColumn("icon", "icon");
		addColumn("iconCls", "iconCls");
		addColumn("url", "url");
		addColumn("locked", "locked");
	}

	public SysTreeQuery locked(Integer locked) {
		if (locked == null) {
			throw new RuntimeException("locked is null");
		}
		this.locked = locked;
		return this;
	}

	public SysTreeQuery lockedGreaterThanOrEqual(
			Integer lockedGreaterThanOrEqual) {
		if (lockedGreaterThanOrEqual == null) {
			throw new RuntimeException("locked is null");
		}
		this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
		return this;
	}

	public SysTreeQuery lockedLessThanOrEqual(Integer lockedLessThanOrEqual) {
		if (lockedLessThanOrEqual == null) {
			throw new RuntimeException("locked is null");
		}
		this.lockedLessThanOrEqual = lockedLessThanOrEqual;
		return this;
	}

	public SysTreeQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public SysTreeQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public SysTreeQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public void setDepartmentStatus(Integer departmentStatus) {
		this.departmentStatus = departmentStatus;
	}

	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
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

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setRelationColumn(String relationColumn) {
		this.relationColumn = relationColumn;
	}

	public void setRelationTable(String relationTable) {
		this.relationTable = relationTable;
	}

	public void setSortGreaterThan(Integer sortGreaterThan) {
		this.sortGreaterThan = sortGreaterThan;
	}

	public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
	}

	public void setSortLessThan(Integer sortLessThan) {
		this.sortLessThan = sortLessThan;
	}

	public void setSortLessThanOrEqual(Integer sortLessThanOrEqual) {
		this.sortLessThanOrEqual = sortLessThanOrEqual;
	}

	public void setTreeIdLeftLike(String treeIdLeftLike) {
		this.treeIdLeftLike = treeIdLeftLike;
	}

	public void setTreeIdLike(String treeIdLike) {
		this.treeIdLike = treeIdLike;
	}

	public void setTreeIdRightLike(String treeIdRightLike) {
		this.treeIdRightLike = treeIdRightLike;
	}

	public void setUrlLike(String urlLike) {
		this.urlLike = urlLike;
	}

	public SysTreeQuery sortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		if (sortGreaterThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
		return this;
	}

	public SysTreeQuery sortLessThanOrEqual(Integer sortLessThanOrEqual) {
		if (sortLessThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortLessThanOrEqual = sortLessThanOrEqual;
		return this;
	}

	public SysTreeQuery urlLike(String urlLike) {
		if (urlLike == null) {
			throw new RuntimeException("url is null");
		}
		this.urlLike = urlLike;
		return this;
	}

}