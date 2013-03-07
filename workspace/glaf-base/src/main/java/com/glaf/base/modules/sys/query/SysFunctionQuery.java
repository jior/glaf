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

public class SysFunctionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> rowIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String funcDesc;
	protected String funcDescLike;
	protected List<String> funcDescs;
	protected String funcMethod;
	protected String funcMethodLike;
	protected List<String> funcMethods;
	protected Integer sort;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortGreaterThan;
	protected Integer sortLessThanOrEqual;
	protected Integer sortLessThan;
	protected List<Integer> sorts;
	protected Long appId;
	protected Long appIdGreaterThanOrEqual;
	protected Long appIdLessThanOrEqual;
	protected List<Long> appIds;

	public SysFunctionQuery() {

	}

	public SysFunctionQuery appId(Long appId) {
		if (appId == null) {
			throw new RuntimeException("appId is null");
		}
		this.appId = appId;
		return this;
	}

	public SysFunctionQuery appIdGreaterThanOrEqual(Long appIdGreaterThanOrEqual) {
		if (appIdGreaterThanOrEqual == null) {
			throw new RuntimeException("appId is null");
		}
		this.appIdGreaterThanOrEqual = appIdGreaterThanOrEqual;
		return this;
	}

	public SysFunctionQuery appIdLessThanOrEqual(Long appIdLessThanOrEqual) {
		if (appIdLessThanOrEqual == null) {
			throw new RuntimeException("appId is null");
		}
		this.appIdLessThanOrEqual = appIdLessThanOrEqual;
		return this;
	}

	public SysFunctionQuery appIds(List<Long> appIds) {
		if (appIds == null) {
			throw new RuntimeException("appIds is empty ");
		}
		this.appIds = appIds;
		return this;
	}

	public SysFunctionQuery funcDesc(String funcDesc) {
		if (funcDesc == null) {
			throw new RuntimeException("funcDesc is null");
		}
		this.funcDesc = funcDesc;
		return this;
	}

	public SysFunctionQuery funcDescLike(String funcDescLike) {
		if (funcDescLike == null) {
			throw new RuntimeException("funcDesc is null");
		}
		this.funcDescLike = funcDescLike;
		return this;
	}

	public SysFunctionQuery funcDescs(List<String> funcDescs) {
		if (funcDescs == null) {
			throw new RuntimeException("funcDescs is empty ");
		}
		this.funcDescs = funcDescs;
		return this;
	}

	public SysFunctionQuery funcMethod(String funcMethod) {
		if (funcMethod == null) {
			throw new RuntimeException("funcMethod is null");
		}
		this.funcMethod = funcMethod;
		return this;
	}

	public SysFunctionQuery funcMethodLike(String funcMethodLike) {
		if (funcMethodLike == null) {
			throw new RuntimeException("funcMethod is null");
		}
		this.funcMethodLike = funcMethodLike;
		return this;
	}

	public SysFunctionQuery funcMethods(List<String> funcMethods) {
		if (funcMethods == null) {
			throw new RuntimeException("funcMethods is empty ");
		}
		this.funcMethods = funcMethods;
		return this;
	}

	public Long getAppId() {
		return appId;
	}

	public Long getAppIdGreaterThanOrEqual() {
		return appIdGreaterThanOrEqual;
	}

	public Long getAppIdLessThanOrEqual() {
		return appIdLessThanOrEqual;
	}

	public List<Long> getAppIds() {
		return appIds;
	}

	public String getFuncDesc() {
		return funcDesc;
	}

	public String getFuncDescLike() {
		if (funcDescLike != null && funcDescLike.trim().length() > 0) {
			if (!funcDescLike.startsWith("%")) {
				funcDescLike = "%" + funcDescLike;
			}
			if (!funcDescLike.endsWith("%")) {
				funcDescLike = funcDescLike + "%";
			}
		}
		return funcDescLike;
	}

	public List<String> getFuncDescs() {
		return funcDescs;
	}

	public String getFuncMethod() {
		return funcMethod;
	}

	public String getFuncMethodLike() {
		if (funcMethodLike != null && funcMethodLike.trim().length() > 0) {
			if (!funcMethodLike.startsWith("%")) {
				funcMethodLike = "%" + funcMethodLike;
			}
			if (!funcMethodLike.endsWith("%")) {
				funcMethodLike = funcMethodLike + "%";
			}
		}
		return funcMethodLike;
	}

	public List<String> getFuncMethods() {
		return funcMethods;
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

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("funcDesc".equals(sortColumn)) {
				orderBy = "E.FUNCDESC" + a_x;
			}

			if ("funcMethod".equals(sortColumn)) {
				orderBy = "E.FUNCMETHOD" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT" + a_x;
			}

			if ("appId".equals(sortColumn)) {
				orderBy = "E.APPID" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getSort() {
		return sort;
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

	public List<Integer> getSorts() {
		return sorts;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("name", "NAME");
		addColumn("funcDesc", "FUNCDESC");
		addColumn("funcMethod", "FUNCMETHOD");
		addColumn("sort", "SORT");
		addColumn("appId", "APPID");
	}

	public SysFunctionQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public SysFunctionQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public SysFunctionQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setAppIdGreaterThanOrEqual(Long appIdGreaterThanOrEqual) {
		this.appIdGreaterThanOrEqual = appIdGreaterThanOrEqual;
	}

	public void setAppIdLessThanOrEqual(Long appIdLessThanOrEqual) {
		this.appIdLessThanOrEqual = appIdLessThanOrEqual;
	}

	public void setAppIds(List<Long> appIds) {
		this.appIds = appIds;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}

	public void setFuncDescLike(String funcDescLike) {
		this.funcDescLike = funcDescLike;
	}

	public void setFuncDescs(List<String> funcDescs) {
		this.funcDescs = funcDescs;
	}

	public void setFuncMethod(String funcMethod) {
		this.funcMethod = funcMethod;
	}

	public void setFuncMethodLike(String funcMethodLike) {
		this.funcMethodLike = funcMethodLike;
	}

	public void setFuncMethods(List<String> funcMethods) {
		this.funcMethods = funcMethods;
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

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public void setSorts(List<Integer> sorts) {
		this.sorts = sorts;
	}

	public SysFunctionQuery sort(Integer sort) {
		if (sort == null) {
			throw new RuntimeException("sort is null");
		}
		this.sort = sort;
		return this;
	}

	public SysFunctionQuery sortGreaterThanOrEqual(
			Integer sortGreaterThanOrEqual) {
		if (sortGreaterThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
		return this;
	}

	public SysFunctionQuery sortLessThanOrEqual(Integer sortLessThanOrEqual) {
		if (sortLessThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortLessThanOrEqual = sortLessThanOrEqual;
		return this;
	}

	public SysFunctionQuery sorts(List<Integer> sorts) {
		if (sorts == null) {
			throw new RuntimeException("sorts is empty ");
		}
		this.sorts = sorts;
		return this;
	}

}