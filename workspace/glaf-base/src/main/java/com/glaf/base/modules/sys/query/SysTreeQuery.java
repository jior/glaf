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
	protected List<Long> rowIds;
	protected Long parent;
	protected Long parentGreaterThanOrEqual;
	protected Long parentLessThanOrEqual;
	protected List<Long> parents;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String descLike;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortGreaterThan;
	protected Integer sortLessThanOrEqual;
	protected Integer sortLessThan;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected String urlLike;	 
	protected Integer lockedGreaterThanOrEqual;
	protected Integer lockedLessThanOrEqual;
	protected Integer departmentStatus;

	public SysTreeQuery() {

	}

	public Integer getSortGreaterThan() {
		return sortGreaterThan;
	}

	public void setSortGreaterThan(Integer sortGreaterThan) {
		this.sortGreaterThan = sortGreaterThan;
	}

	public Integer getSortLessThan() {
		return sortLessThan;
	}

	public void setSortLessThan(Integer sortLessThan) {
		this.sortLessThan = sortLessThan;
	}

	public Long getParent() {
		return parent;
	}

	public Long getParentGreaterThanOrEqual() {
		return parentGreaterThanOrEqual;
	}

	public Long getParentLessThanOrEqual() {
		return parentLessThanOrEqual;
	}

	public List<Long> getParents() {
		return parents;
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

	public Integer getSortGreaterThanOrEqual() {
		return sortGreaterThanOrEqual;
	}

	public Integer getSortLessThanOrEqual() {
		return sortLessThanOrEqual;
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

	public Integer getLocked() {
		return locked;
	}

	public Integer getLockedGreaterThanOrEqual() {
		return lockedGreaterThanOrEqual;
	}

	public Integer getLockedLessThanOrEqual() {
		return lockedLessThanOrEqual;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public void setParentGreaterThanOrEqual(Long parentGreaterThanOrEqual) {
		this.parentGreaterThanOrEqual = parentGreaterThanOrEqual;
	}

	public void setParentLessThanOrEqual(Long parentLessThanOrEqual) {
		this.parentLessThanOrEqual = parentLessThanOrEqual;
	}

	public void setParents(List<Long> parents) {
		this.parents = parents;
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

	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}

	public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
	}

	public void setSortLessThanOrEqual(Integer sortLessThanOrEqual) {
		this.sortLessThanOrEqual = sortLessThanOrEqual;
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

	public void setUrlLike(String urlLike) {
		this.urlLike = urlLike;
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

	public SysTreeQuery parent(Long parent) {
		if (parent == null) {
			throw new RuntimeException("parent is null");
		}
		this.parent = parent;
		return this;
	}

	public SysTreeQuery parentGreaterThanOrEqual(Long parentGreaterThanOrEqual) {
		if (parentGreaterThanOrEqual == null) {
			throw new RuntimeException("parent is null");
		}
		this.parentGreaterThanOrEqual = parentGreaterThanOrEqual;
		return this;
	}

	public SysTreeQuery parentLessThanOrEqual(Long parentLessThanOrEqual) {
		if (parentLessThanOrEqual == null) {
			throw new RuntimeException("parent is null");
		}
		this.parentLessThanOrEqual = parentLessThanOrEqual;
		return this;
	}

	public SysTreeQuery parents(List<Long> parents) {
		if (parents == null) {
			throw new RuntimeException("parents is empty ");
		}
		this.parents = parents;
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

	public SysTreeQuery descLike(String descLike) {
		if (descLike == null) {
			throw new RuntimeException("desc is null");
		}
		this.descLike = descLike;
		return this;
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

	public SysTreeQuery urlLike(String urlLike) {
		if (urlLike == null) {
			throw new RuntimeException("url is null");
		}
		this.urlLike = urlLike;
		return this;
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

	public Integer getDepartmentStatus() {
		return departmentStatus;
	}

	public void setDepartmentStatus(Integer departmentStatus) {
		this.departmentStatus = departmentStatus;
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

}