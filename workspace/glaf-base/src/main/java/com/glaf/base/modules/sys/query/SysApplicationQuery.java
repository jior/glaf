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

public class SysApplicationQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> rowIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String desc;
	protected String descLike;
	protected List<String> descs;
	protected String url;
	protected String urlLike;
	protected List<String> urls;
	protected Integer sort;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortGreaterThan;
	protected Integer sortLessThanOrEqual;
	protected Integer sortLessThan;
	protected List<Integer> sorts;
	protected Integer showMenu;
	protected Integer showMenuGreaterThanOrEqual;
	protected Integer showMenuLessThanOrEqual;
	protected List<Integer> showMenus;
	protected Long nodeId;
	protected Long nodeIdGreaterThanOrEqual;
	protected Long nodeIdLessThanOrEqual;
	protected List<Long> nodeIds;

	public SysApplicationQuery() {

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

	public String getDesc() {
		return desc;
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

	public List<String> getDescs() {
		return descs;
	}

	public String getUrl() {
		return url;
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

	public List<String> getUrls() {
		return urls;
	}

	public Integer getSort() {
		return sort;
	}

	public Integer getSortGreaterThanOrEqual() {
		return sortGreaterThanOrEqual;
	}

	public Integer getSortLessThanOrEqual() {
		return sortLessThanOrEqual;
	}

	public List<Integer> getSorts() {
		return sorts;
	}

	public Integer getShowMenu() {
		return showMenu;
	}

	public Integer getShowMenuGreaterThanOrEqual() {
		return showMenuGreaterThanOrEqual;
	}

	public Integer getShowMenuLessThanOrEqual() {
		return showMenuLessThanOrEqual;
	}

	public List<Integer> getShowMenus() {
		return showMenus;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public Long getNodeIdGreaterThanOrEqual() {
		return nodeIdGreaterThanOrEqual;
	}

	public Long getNodeIdLessThanOrEqual() {
		return nodeIdLessThanOrEqual;
	}

	public List<Long> getNodeIds() {
		return nodeIds;
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

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}

	public void setDescs(List<String> descs) {
		this.descs = descs;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUrlLike(String urlLike) {
		this.urlLike = urlLike;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
	}

	public void setSortLessThanOrEqual(Integer sortLessThanOrEqual) {
		this.sortLessThanOrEqual = sortLessThanOrEqual;
	}

	public void setSorts(List<Integer> sorts) {
		this.sorts = sorts;
	}

	public void setShowMenu(Integer showMenu) {
		this.showMenu = showMenu;
	}

	public void setShowMenuGreaterThanOrEqual(Integer showMenuGreaterThanOrEqual) {
		this.showMenuGreaterThanOrEqual = showMenuGreaterThanOrEqual;
	}

	public void setShowMenuLessThanOrEqual(Integer showMenuLessThanOrEqual) {
		this.showMenuLessThanOrEqual = showMenuLessThanOrEqual;
	}

	public void setShowMenus(List<Integer> showMenus) {
		this.showMenus = showMenus;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIdGreaterThanOrEqual(Long nodeIdGreaterThanOrEqual) {
		this.nodeIdGreaterThanOrEqual = nodeIdGreaterThanOrEqual;
	}

	public void setNodeIdLessThanOrEqual(Long nodeIdLessThanOrEqual) {
		this.nodeIdLessThanOrEqual = nodeIdLessThanOrEqual;
	}

	public void setNodeIds(List<Long> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public SysApplicationQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public SysApplicationQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public SysApplicationQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public SysApplicationQuery desc(String desc) {
		if (desc == null) {
			throw new RuntimeException("desc is null");
		}
		this.desc = desc;
		return this;
	}

	public SysApplicationQuery descLike(String descLike) {
		if (descLike == null) {
			throw new RuntimeException("desc is null");
		}
		this.descLike = descLike;
		return this;
	}

	public SysApplicationQuery descs(List<String> descs) {
		if (descs == null) {
			throw new RuntimeException("descs is empty ");
		}
		this.descs = descs;
		return this;
	}

	public SysApplicationQuery url(String url) {
		if (url == null) {
			throw new RuntimeException("url is null");
		}
		this.url = url;
		return this;
	}

	public SysApplicationQuery urlLike(String urlLike) {
		if (urlLike == null) {
			throw new RuntimeException("url is null");
		}
		this.urlLike = urlLike;
		return this;
	}

	public SysApplicationQuery urls(List<String> urls) {
		if (urls == null) {
			throw new RuntimeException("urls is empty ");
		}
		this.urls = urls;
		return this;
	}

	public SysApplicationQuery sort(Integer sort) {
		if (sort == null) {
			throw new RuntimeException("sort is null");
		}
		this.sort = sort;
		return this;
	}

	public SysApplicationQuery sortGreaterThanOrEqual(
			Integer sortGreaterThanOrEqual) {
		if (sortGreaterThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
		return this;
	}

	public SysApplicationQuery sortLessThanOrEqual(Integer sortLessThanOrEqual) {
		if (sortLessThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortLessThanOrEqual = sortLessThanOrEqual;
		return this;
	}

	public SysApplicationQuery sorts(List<Integer> sorts) {
		if (sorts == null) {
			throw new RuntimeException("sorts is empty ");
		}
		this.sorts = sorts;
		return this;
	}

	public SysApplicationQuery showMenu(Integer showMenu) {
		if (showMenu == null) {
			throw new RuntimeException("showMenu is null");
		}
		this.showMenu = showMenu;
		return this;
	}

	public SysApplicationQuery showMenuGreaterThanOrEqual(
			Integer showMenuGreaterThanOrEqual) {
		if (showMenuGreaterThanOrEqual == null) {
			throw new RuntimeException("showMenu is null");
		}
		this.showMenuGreaterThanOrEqual = showMenuGreaterThanOrEqual;
		return this;
	}

	public SysApplicationQuery showMenuLessThanOrEqual(
			Integer showMenuLessThanOrEqual) {
		if (showMenuLessThanOrEqual == null) {
			throw new RuntimeException("showMenu is null");
		}
		this.showMenuLessThanOrEqual = showMenuLessThanOrEqual;
		return this;
	}

	public SysApplicationQuery showMenus(List<Integer> showMenus) {
		if (showMenus == null) {
			throw new RuntimeException("showMenus is empty ");
		}
		this.showMenus = showMenus;
		return this;
	}

	public SysApplicationQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public SysApplicationQuery nodeIdGreaterThanOrEqual(
			Long nodeIdGreaterThanOrEqual) {
		if (nodeIdGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeIdGreaterThanOrEqual = nodeIdGreaterThanOrEqual;
		return this;
	}

	public SysApplicationQuery nodeIdLessThanOrEqual(Long nodeIdLessThanOrEqual) {
		if (nodeIdLessThanOrEqual == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeIdLessThanOrEqual = nodeIdLessThanOrEqual;
		return this;
	}

	public SysApplicationQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
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

			if ("desc".equals(sortColumn)) {
				orderBy = "E.APPDESC" + a_x;
			}

			if ("url".equals(sortColumn)) {
				orderBy = "E.URL" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT" + a_x;
			}

			if ("showMenu".equals(sortColumn)) {
				orderBy = "E.SHOWMENU" + a_x;
			}

			if ("nodeId".equals(sortColumn)) {
				orderBy = "E.NODEID" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("name", "NAME");
		addColumn("desc", "APPDESC");
		addColumn("url", "URL");
		addColumn("sort", "SORT");
		addColumn("showMenu", "SHOWMENU");
		addColumn("nodeId", "NODEID");
	}

}