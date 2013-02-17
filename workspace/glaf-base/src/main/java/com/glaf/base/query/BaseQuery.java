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

package com.glaf.base.query;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.PageResult;

public class BaseQuery extends AbstractQuery<Object> {
	private static final long serialVersionUID = 1L;
	protected String createBy;
	protected String serviceKey;
	protected String sortColumn;
	protected String sortOrder;
	protected String orderBy;
	protected Integer locked;
	protected int pageNo;
	protected int pageSize;
	protected boolean isOwner = false;
	protected boolean isInitialized = false;
	protected boolean isFilterPermission = true;
	protected boolean processInstanceIsNull;
	protected boolean processInstanceIsNotNull;
	protected SysUser user;
	protected List<String> actorIds = new ArrayList<String>();
	protected List<String> dataInstanceIds = new ArrayList<String>();
	protected List<String> processInstanceIds = new ArrayList<String>();

	public BaseQuery() {

	}

	public BaseQuery actorIds(List<String> actorIds) {
		if (actorIds == null) {
			throw new RuntimeException("actorIds is null");
		}
		this.actorIds = actorIds;
		return this;
	}

	public BaseQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public BaseQuery dataInstanceIds(List<String> dataInstanceIds) {
		if (dataInstanceIds == null) {
			throw new RuntimeException("dataInstanceId is null");
		}
		this.dataInstanceIds = dataInstanceIds;
		return this;
	}

	public void ensureInitialized() {
		if (isInitialized) {
			return;
		}
		super.initQueryColumns();
		super.initQueryParameters();
		if (user != null) {
			if (StringUtils.equals(createBy, user.getActorId())) {
				isFilterPermission = false;
			}
			if (user.isSystemAdmin()) {
				isFilterPermission = false;
			}

			if (dataInstanceIds != null && !dataInstanceIds.isEmpty()) {
				isFilterPermission = false;
			}

			if (processInstanceIds != null && !processInstanceIds.isEmpty()) {
				isFilterPermission = false;
			}

			if (StringUtils.isNotEmpty(createBy)) {
				isFilterPermission = false;
			}

			if (isFilterPermission) {
				/**
				 * 用户可以访问的数据是模块访问数据+行数据
				 */

			}
		}
		isInitialized = true;
	}

	public List<String> getActorIds() {
		return actorIds;
	}

	public int getBegin() {
		if (pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize <= 0) {
			pageSize = PageResult.DEFAULT_PAGE_SIZE;
		}

		int begin = (pageNo - 1) * pageSize;
		return begin;
	}

	public String getCreateBy() {
		return createBy;
	}

	public List<String> getDataInstanceIds() {
		return dataInstanceIds;
	}

	public Integer getLocked() {
		return locked;
	}

	public boolean getOnlyDataModels() {
		return true;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		if (pageSize <= 0) {
			pageSize = PageResult.DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	public List<String> getProcessInstanceIds() {
		return processInstanceIds;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public SysUser getSysUser() {
		return user;
	}

	public SysUser getUser() {
		return user;
	}

	public boolean isFilterPermission() {
		return isFilterPermission;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public boolean isProcessInstanceIsNotNull() {
		return processInstanceIsNotNull;
	}

	public boolean isProcessInstanceIsNull() {
		return processInstanceIsNull;
	}

	public BaseQuery locked(Integer locked) {
		this.locked = locked;
		return this;
	}

	public BaseQuery processInstanceIds(List<String> processInstanceIds) {
		if (processInstanceIds == null) {
			throw new RuntimeException("Process instance id is null");
		}
		this.processInstanceIds = processInstanceIds;
		return this;
	}

	public BaseQuery serviceKey(String serviceKey) {
		if (serviceKey == null) {
			throw new RuntimeException("ServiceKey  is null");
		}
		this.serviceKey = serviceKey;
		return this;
	}

	public void setActorIds(List<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setDataInstanceIds(List<String> dataInstanceIds) {
		this.dataInstanceIds = dataInstanceIds;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
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

	public void setProcessInstanceIds(List<String> processInstanceIds) {
		this.processInstanceIds = processInstanceIds;
	}

	public void setProcessInstanceIsNotNull(boolean processInstanceIsNotNull) {
		this.processInstanceIsNotNull = processInstanceIsNotNull;
	}

	public void setProcessInstanceIsNull(boolean processInstanceIsNull) {
		this.processInstanceIsNull = processInstanceIsNull;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
