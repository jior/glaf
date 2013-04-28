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
package com.glaf.base.modules.workspace.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MyMenuQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long userId;
	protected Long userIdGreaterThanOrEqual;
	protected Long userIdLessThanOrEqual;
	protected List<Long> userIds;
	protected String titleLike;
	protected String urlLike;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortGreaterThan;
	protected Integer sortLessThanOrEqual;
	protected Integer sortLessThan;

	public MyMenuQuery() {

	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE" + a_x;
			}

			if ("url".equals(sortColumn)) {
				orderBy = "E.URL" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT" + a_x;
			}

		}
		return orderBy;
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

	public Long getUserId() {
		return userId;
	}

	public Long getUserIdGreaterThanOrEqual() {
		return userIdGreaterThanOrEqual;
	}

	public Long getUserIdLessThanOrEqual() {
		return userIdLessThanOrEqual;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("userId", "USERID");
		addColumn("title", "TITLE");
		addColumn("url", "URL");
		addColumn("sort", "SORT");
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

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setUrlLike(String urlLike) {
		this.urlLike = urlLike;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserIdGreaterThanOrEqual(Long userIdGreaterThanOrEqual) {
		this.userIdGreaterThanOrEqual = userIdGreaterThanOrEqual;
	}

	public void setUserIdLessThanOrEqual(Long userIdLessThanOrEqual) {
		this.userIdLessThanOrEqual = userIdLessThanOrEqual;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public MyMenuQuery sortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		if (sortGreaterThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
		return this;
	}

	public MyMenuQuery sortLessThanOrEqual(Integer sortLessThanOrEqual) {
		if (sortLessThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortLessThanOrEqual = sortLessThanOrEqual;
		return this;
	}

	public MyMenuQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public MyMenuQuery urlLike(String urlLike) {
		if (urlLike == null) {
			throw new RuntimeException("url is null");
		}
		this.urlLike = urlLike;
		return this;
	}

	public MyMenuQuery userId(Long userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public MyMenuQuery userIdGreaterThanOrEqual(Long userIdGreaterThanOrEqual) {
		if (userIdGreaterThanOrEqual == null) {
			throw new RuntimeException("userId is null");
		}
		this.userIdGreaterThanOrEqual = userIdGreaterThanOrEqual;
		return this;
	}

	public MyMenuQuery userIdLessThanOrEqual(Long userIdLessThanOrEqual) {
		if (userIdLessThanOrEqual == null) {
			throw new RuntimeException("userId is null");
		}
		this.userIdLessThanOrEqual = userIdLessThanOrEqual;
		return this;
	}

	public MyMenuQuery userIds(List<Long> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

}