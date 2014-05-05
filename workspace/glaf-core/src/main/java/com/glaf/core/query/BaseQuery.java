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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.security.LoginContext;
import com.glaf.core.util.Paging;

@SuppressWarnings("rawtypes")
public class BaseQuery extends AbstractQuery<Object> {
	private static final long serialVersionUID = 1L;
	protected String actorId;
	protected List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
	protected List<String> businessKeys = new java.util.concurrent.CopyOnWriteArrayList<String>();
	protected String createBy;
	protected QueryCondition currentQueryCondition;
	protected boolean isFilterPermission = true;
	protected boolean isInitialized = false;
	protected boolean isOwner = false;
	protected Integer locked;
	protected LoginContext loginContext;
	protected String orderBy;
	protected int pageNo;
	protected int pageSize;
	protected Object parameter;
	protected List rowIds = new java.util.concurrent.CopyOnWriteArrayList();
	protected String serviceKey;
	protected String sortField;
	protected String sortOrder;
	protected Date afterCreateDate;
	protected Date beforeCreateDate;

	public BaseQuery() {

	}

	public BaseQuery actorId(String actorId) {
		if (actorId == null) {
			throw new RuntimeException("actorId is null");
		}
		this.actorId = actorId;
		return this;
	}

	public BaseQuery actorIds(List<String> actorIds) {
		if (actorIds == null) {
			throw new RuntimeException("actorIds is null");
		}
		this.actorIds = actorIds;
		return this;
	}

	public BaseQuery afterCreateDate(Date afterCreateDate) {
		if (afterCreateDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.afterCreateDate = afterCreateDate;
		return this;
	}

	public BaseQuery beforeCreateDate(Date beforeCreateDate) {
		if (beforeCreateDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.beforeCreateDate = beforeCreateDate;
		return this;
	}

	public BaseQuery businessKeys(List<String> businessKeys) {
		if (businessKeys == null) {
			throw new RuntimeException("businessKeys is null");
		}
		this.businessKeys = businessKeys;
		return this;
	}

	public BaseQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public void ensureInitialized() {
		if (isInitialized) {
			return;
		}
		if (loginContext != null) {
			if (StringUtils.equals(createBy, loginContext.getActorId())) {
				isFilterPermission = false;
			}

			if (StringUtils.isNotEmpty(createBy)) {
				isFilterPermission = false;
			}

			if (isFilterPermission) {
				/**
				 * 用户可以访问的数据是模块访问数据+行数据
				 */
				// ///////////////////////////////////////////////////////
				// 模块访问权限
				// ///////////////////////////////////////////////////////
				/**
				 * 访问用户
				 */
				if (loginContext.getActorId() != null) {

					this.actorIds.add(loginContext.getActorId());
				}

				/**
				 * 访问部门
				 */
				if (loginContext.getDeptId() != 0) {

				}

			}
		}
		isInitialized = true;
	}

	public String getActorId() {
		return actorId;
	}

	public List<String> getActorIds() {
		return actorIds;
	}

	public Date getAfterCreateDate() {
		return afterCreateDate;
	}

	public Date getBeforeCreateDate() {
		return beforeCreateDate;
	}

	public int getBegin() {
		if (pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}

		int begin = (pageNo - 1) * pageSize;
		return begin;
	}

	public List<String> getBusinessKeys() {
		return businessKeys;
	}

	public String getCreateBy() {
		return createBy;
	}

	public QueryCondition getCurrentQueryCondition() {
		return currentQueryCondition;
	}

	public Integer getLocked() {
		return locked;
	}

	public LoginContext getLoginContext() {
		return loginContext;
	}

	public boolean getOnlyDataModels() {
		return true;
	}

	public String getOrderBy() {
		if (orderBy != null) {
			return orderBy;
		}

		if (this.getSortField() != null) {
			orderBy = " E." + this.getSortField() + "_";
			if (this.getSortOrder() != null) {
				orderBy += " " + this.getSortOrder();
			}
		}

		return orderBy;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	public Object getParameter() {
		return parameter;
	}

	public List getRowIds() {
		return rowIds;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getSortField() {
		return sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public boolean isFilterPermission() {
		return isFilterPermission;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public BaseQuery locked(Integer locked) {
		this.locked = locked;
		return this;
	}

	public BaseQuery rowIds(List rowIds) {
		if (rowIds == null) {
			throw new RuntimeException("rowIds is null");
		}
		this.rowIds = rowIds;
		return this;
	}

	public BaseQuery serviceKey(String serviceKey) {
		if (serviceKey == null) {
			throw new RuntimeException("serviceKey  is null");
		}
		this.serviceKey = serviceKey;
		return this;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorIds(List<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setAfterCreateDate(Date afterCreateDate) {
		this.afterCreateDate = afterCreateDate;
	}

	public void setBeforeCreateDate(Date beforeCreateDate) {
		this.beforeCreateDate = beforeCreateDate;
	}

	public void setBusinessKeys(List<String> businessKeys) {
		this.businessKeys = businessKeys;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCurrentQueryCondition(QueryCondition currentQueryCondition) {
		this.currentQueryCondition = currentQueryCondition;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setLoginContext(LoginContext loginContext) {
		this.loginContext = loginContext;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	public void setRowIds(List rowIds) {
		this.rowIds = rowIds;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}