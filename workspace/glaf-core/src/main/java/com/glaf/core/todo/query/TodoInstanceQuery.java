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

package com.glaf.core.todo.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TodoInstanceQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String actorNameLike;
	protected Date alarmDateGreaterThanOrEqual;
	protected Date alarmDateLessThanOrEqual;
	protected Long appId;
	protected List<Long> appIds;
	protected String contentLike;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected Long deptId;
	protected List<Long> deptIds;
	protected String deptNameLike;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;
	protected String linkType;
	protected List<String> linkTypes;
	protected Long moduleId;
	protected List<Long> moduleIds;
	protected List<String> objectIds;
	protected List<String> objectValues;
	protected Date pastDueDateGreaterThanOrEqual;
	protected Date pastDueDateLessThanOrEqual;
	protected String provider;
	protected List<String> providers;
	protected String roleCode;
	protected String roleCodeLike;
	protected List<String> roleCodes;
	protected Long roleId;
	protected List<Long> roleIds;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected String taskInstanceId;
	protected String titleLike;
	protected Long todoId;
	protected List<Long> todoIds;
	protected Long versionNoGreaterThanOrEqual;
	protected Long versionNoLessThanOrEqual;

	public TodoInstanceQuery() {

	}

	public TodoInstanceQuery actorNameLike(String actorNameLike) {
		if (actorNameLike == null) {
			throw new RuntimeException("actorName is null");
		}
		this.actorNameLike = actorNameLike;
		return this;
	}

	public TodoInstanceQuery alarmDateGreaterThanOrEqual(
			Date alarmDateGreaterThanOrEqual) {
		if (alarmDateGreaterThanOrEqual == null) {
			throw new RuntimeException("alarmDate is null");
		}
		this.alarmDateGreaterThanOrEqual = alarmDateGreaterThanOrEqual;
		return this;
	}

	public TodoInstanceQuery alarmDateLessThanOrEqual(
			Date alarmDateLessThanOrEqual) {
		if (alarmDateLessThanOrEqual == null) {
			throw new RuntimeException("alarmDate is null");
		}
		this.alarmDateLessThanOrEqual = alarmDateLessThanOrEqual;
		return this;
	}

	public TodoInstanceQuery appId(Long appId) {
		if (appId == null) {
			throw new RuntimeException("appId is null");
		}
		this.appId = appId;
		return this;
	}

	public TodoInstanceQuery appIds(List<Long> appIds) {
		if (appIds == null) {
			throw new RuntimeException("appIds is empty ");
		}
		this.appIds = appIds;
		return this;
	}

	public TodoInstanceQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public TodoInstanceQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public TodoInstanceQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public TodoInstanceQuery deptId(Long deptId) {
		if (deptId == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptId = deptId;
		return this;
	}

	public TodoInstanceQuery deptIds(List<Long> deptIds) {
		if (deptIds == null) {
			throw new RuntimeException("deptIds is empty ");
		}
		this.deptIds = deptIds;
		return this;
	}

	public TodoInstanceQuery deptNameLike(String deptNameLike) {
		if (deptNameLike == null) {
			throw new RuntimeException("deptName is null");
		}
		this.deptNameLike = deptNameLike;
		return this;
	}

	public TodoInstanceQuery endDateGreaterThanOrEqual(
			Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public TodoInstanceQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
	}

	public String getActorId() {
		return actorId;
	}

	public List<String> getActorIds() {
		return actorIds;
	}

	public String getActorNameLike() {
		if (actorNameLike != null && actorNameLike.trim().length() > 0) {
			if (!actorNameLike.startsWith("%")) {
				actorNameLike = "%" + actorNameLike;
			}
			if (!actorNameLike.endsWith("%")) {
				actorNameLike = actorNameLike + "%";
			}
		}
		return actorNameLike;
	}

	public Date getAlarmDateGreaterThanOrEqual() {
		return alarmDateGreaterThanOrEqual;
	}

	public Date getAlarmDateLessThanOrEqual() {
		return alarmDateLessThanOrEqual;
	}

	public Long getAppId() {
		return appId;
	}

	public List<Long> getAppIds() {
		return appIds;
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

	public Date getCreateDate() {
		return createDate;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public Long getDeptId() {
		return deptId;
	}

	public List<Long> getDeptIds() {
		return deptIds;
	}

	public String getDeptNameLike() {
		if (deptNameLike != null && deptNameLike.trim().length() > 0) {
			if (!deptNameLike.startsWith("%")) {
				deptNameLike = "%" + deptNameLike;
			}
			if (!deptNameLike.endsWith("%")) {
				deptNameLike = deptNameLike + "%";
			}
		}
		return deptNameLike;
	}

	public Date getEndDateGreaterThanOrEqual() {
		return endDateGreaterThanOrEqual;
	}

	public Date getEndDateLessThanOrEqual() {
		return endDateLessThanOrEqual;
	}

	public String getLinkType() {
		return linkType;
	}

	public List<String> getLinkTypes() {
		return linkTypes;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public List<Long> getModuleIds() {
		return moduleIds;
	}

	public String getObjectId() {
		return objectId;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("actorId".equals(sortColumn)) {
				orderBy = "E.actorId" + a_x;
			}

			if ("actorName".equals(sortColumn)) {
				orderBy = "E.actorName" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.title" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.content" + a_x;
			}

			if ("provider".equals(sortColumn)) {
				orderBy = "E.provider" + a_x;
			}

			if ("link".equals(sortColumn)) {
				orderBy = "E.link" + a_x;
			}

			if ("linkType".equals(sortColumn)) {
				orderBy = "E.linkType" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.createDate" + a_x;
			}

			if ("startDate".equals(sortColumn)) {
				orderBy = "E.startDate" + a_x;
			}

			if ("endDate".equals(sortColumn)) {
				orderBy = "E.endDate" + a_x;
			}

			if ("alarmDate".equals(sortColumn)) {
				orderBy = "E.alarmDate" + a_x;
			}

			if ("pastDueDate".equals(sortColumn)) {
				orderBy = "E.pastDueDate" + a_x;
			}

			if ("taskInstanceId".equals(sortColumn)) {
				orderBy = "E.taskInstanceId" + a_x;
			}

			if ("processInstanceId".equals(sortColumn)) {
				orderBy = "E.processInstanceId" + a_x;
			}

			if ("deptId".equals(sortColumn)) {
				orderBy = "E.deptId" + a_x;
			}

			if ("deptName".equals(sortColumn)) {
				orderBy = "E.deptName" + a_x;
			}

			if ("roleId".equals(sortColumn)) {
				orderBy = "E.roleId" + a_x;
			}

			if ("roleCode".equals(sortColumn)) {
				orderBy = "E.roleCode" + a_x;
			}

			if ("rowId".equals(sortColumn)) {
				orderBy = "E.rowId" + a_x;
			}

			if ("todoId".equals(sortColumn)) {
				orderBy = "E.todoId" + a_x;
			}

			if ("appId".equals(sortColumn)) {
				orderBy = "E.appId" + a_x;
			}

			if ("moduleId".equals(sortColumn)) {
				orderBy = "E.moduleId" + a_x;
			}

			if ("objectId".equals(sortColumn)) {
				orderBy = "E.objectId" + a_x;
			}

			if ("objectValue".equals(sortColumn)) {
				orderBy = "E.objectValue" + a_x;
			}

			if ("versionNo".equals(sortColumn)) {
				orderBy = "E.versionNo" + a_x;
			}

		}
		return orderBy;
	}

	public Date getPastDueDateGreaterThanOrEqual() {
		return pastDueDateGreaterThanOrEqual;
	}

	public Date getPastDueDateLessThanOrEqual() {
		return pastDueDateLessThanOrEqual;
	}

	public String getProvider() {
		return provider;
	}

	public List<String> getProviders() {
		return providers;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public String getRoleCodeLike() {
		if (roleCodeLike != null && roleCodeLike.trim().length() > 0) {
			if (!roleCodeLike.startsWith("%")) {
				roleCodeLike = "%" + roleCodeLike;
			}
			if (!roleCodeLike.endsWith("%")) {
				roleCodeLike = roleCodeLike + "%";
			}
		}
		return roleCodeLike;
	}

	public List<String> getRoleCodes() {
		return roleCodes;
	}

	public Long getRoleId() {
		return roleId;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public Date getStartDateGreaterThanOrEqual() {
		return startDateGreaterThanOrEqual;
	}

	public Date getStartDateLessThanOrEqual() {
		return startDateLessThanOrEqual;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
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

	public Long getTodoId() {
		return todoId;
	}

	public List<Long> getTodoIds() {
		return todoIds;
	}

	public Long getVersionNoGreaterThanOrEqual() {
		return versionNoGreaterThanOrEqual;
	}

	public Long getVersionNoLessThanOrEqual() {
		return versionNoLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("actorId", "actorId");
		addColumn("actorName", "actorName");
		addColumn("title", "title");
		addColumn("content", "content");
		addColumn("provider", "provider");
		addColumn("link", "link");
		addColumn("linkType", "linkType");
		addColumn("createDate", "createDate");
		addColumn("startDate", "startDate");
		addColumn("endDate", "endDate");
		addColumn("alarmDate", "alarmDate");
		addColumn("pastDueDate", "pastDueDate");
		addColumn("taskInstanceId", "taskInstanceId");
		addColumn("processInstanceId", "processInstanceId");
		addColumn("deptId", "deptId");
		addColumn("deptName", "deptName");
		addColumn("roleId", "roleId");
		addColumn("roleCode", "roleCode");
		addColumn("rowId", "rowId");
		addColumn("todoId", "todoId");
		addColumn("appId", "appId");
		addColumn("moduleId", "moduleId");
		addColumn("objectId", "objectId");
		addColumn("objectValue", "objectValue");
		addColumn("versionNo", "versionNo");
	}

	public TodoInstanceQuery linkType(String linkType) {
		if (linkType == null) {
			throw new RuntimeException("linkType is null");
		}
		this.linkType = linkType;
		return this;
	}

	public TodoInstanceQuery linkTypes(List<String> linkTypes) {
		if (linkTypes == null) {
			throw new RuntimeException("linkTypes is empty ");
		}
		this.linkTypes = linkTypes;
		return this;
	}

	public TodoInstanceQuery moduleId(Long moduleId) {
		if (moduleId == null) {
			throw new RuntimeException("moduleId is null");
		}
		this.moduleId = moduleId;
		return this;
	}

	public TodoInstanceQuery moduleIds(List<Long> moduleIds) {
		if (moduleIds == null) {
			throw new RuntimeException("moduleIds is empty ");
		}
		this.moduleIds = moduleIds;
		return this;
	}

	public TodoInstanceQuery objectId(String objectId) {
		if (objectId == null) {
			throw new RuntimeException("objectId is null");
		}
		this.objectId = objectId;
		return this;
	}

	public TodoInstanceQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public TodoInstanceQuery objectValue(String objectValue) {
		if (objectValue == null) {
			throw new RuntimeException("objectValue is null");
		}
		this.objectValue = objectValue;
		return this;
	}

	public TodoInstanceQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public TodoInstanceQuery pastDueDateLessThanOrEqual(
			Date pastDueDateLessThanOrEqual) {
		if (pastDueDateLessThanOrEqual == null) {
			throw new RuntimeException("pastDueDate is null");
		}
		this.pastDueDateLessThanOrEqual = pastDueDateLessThanOrEqual;
		return this;
	}

	public TodoInstanceQuery provider(String provider) {
		if (provider == null) {
			throw new RuntimeException("provider is null");
		}
		this.provider = provider;
		return this;
	}

	public TodoInstanceQuery providers(List<String> providers) {
		if (providers == null) {
			throw new RuntimeException("providers is empty ");
		}
		this.providers = providers;
		return this;
	}

	public TodoInstanceQuery roleCode(String roleCode) {
		if (roleCode == null) {
			throw new RuntimeException("roleCode is null");
		}
		this.roleCode = roleCode;
		return this;
	}

	public TodoInstanceQuery roleCodeLike(String roleCodeLike) {
		if (roleCodeLike == null) {
			throw new RuntimeException("roleCode is null");
		}
		this.roleCodeLike = roleCodeLike;
		return this;
	}

	public TodoInstanceQuery roleCodes(List<String> roleCodes) {
		if (roleCodes == null) {
			throw new RuntimeException("roleCodes is empty ");
		}
		this.roleCodes = roleCodes;
		return this;
	}

	public TodoInstanceQuery roleId(Long roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public TodoInstanceQuery roleIds(List<Long> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorIds(List<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setActorNameLike(String actorNameLike) {
		this.actorNameLike = actorNameLike;
	}

	public void setAlarmDateGreaterThanOrEqual(Date alarmDateGreaterThanOrEqual) {
		this.alarmDateGreaterThanOrEqual = alarmDateGreaterThanOrEqual;
	}

	public void setAlarmDateLessThanOrEqual(Date alarmDateLessThanOrEqual) {
		this.alarmDateLessThanOrEqual = alarmDateLessThanOrEqual;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setAppIds(List<Long> appIds) {
		this.appIds = appIds;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public void setDeptNameLike(String deptNameLike) {
		this.deptNameLike = deptNameLike;
	}

	public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
	}

	public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public void setLinkTypes(List<String> linkTypes) {
		this.linkTypes = linkTypes;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public void setModuleIds(List<Long> moduleIds) {
		this.moduleIds = moduleIds;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setPastDueDateGreaterThanOrEqual(
			Date pastDueDateGreaterThanOrEqual) {
		this.pastDueDateGreaterThanOrEqual = pastDueDateGreaterThanOrEqual;
	}

	public void setPastDueDateLessThanOrEqual(Date pastDueDateLessThanOrEqual) {
		this.pastDueDateLessThanOrEqual = pastDueDateLessThanOrEqual;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setProviders(List<String> providers) {
		this.providers = providers;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setRoleCodeLike(String roleCodeLike) {
		this.roleCodeLike = roleCodeLike;
	}

	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}

	public void setTodoIds(List<Long> todoIds) {
		this.todoIds = todoIds;
	}

	public void setVersionNoGreaterThanOrEqual(Long versionNoGreaterThanOrEqual) {
		this.versionNoGreaterThanOrEqual = versionNoGreaterThanOrEqual;
	}

	public void setVersionNoLessThanOrEqual(Long versionNoLessThanOrEqual) {
		this.versionNoLessThanOrEqual = versionNoLessThanOrEqual;
	}

	public TodoInstanceQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public TodoInstanceQuery startDateLessThanOrEqual(
			Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public TodoInstanceQuery taskInstanceId(String taskInstanceId) {
		if (taskInstanceId == null) {
			throw new RuntimeException("taskInstanceId is null");
		}
		this.taskInstanceId = taskInstanceId;
		return this;
	}

	public TodoInstanceQuery taskInstanceIds(List<String> taskInstanceIds) {
		if (taskInstanceIds == null) {
			throw new RuntimeException("taskInstanceIds is empty ");
		}
		this.taskInstanceIds = taskInstanceIds;
		return this;
	}

	public TodoInstanceQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public TodoInstanceQuery todoId(Long todoId) {
		if (todoId == null) {
			throw new RuntimeException("todoId is null");
		}
		this.todoId = todoId;
		return this;
	}

	public TodoInstanceQuery todoIds(List<Long> todoIds) {
		if (todoIds == null) {
			throw new RuntimeException("todoIds is empty ");
		}
		this.todoIds = todoIds;
		return this;
	}

	public TodoInstanceQuery versionNoGreaterThanOrEqual(
			Long versionNoGreaterThanOrEqual) {
		if (versionNoGreaterThanOrEqual == null) {
			throw new RuntimeException("versionNo is null");
		}
		this.versionNoGreaterThanOrEqual = versionNoGreaterThanOrEqual;
		return this;
	}

	public TodoInstanceQuery versionNoLessThanOrEqual(
			Long versionNoLessThanOrEqual) {
		if (versionNoLessThanOrEqual == null) {
			throw new RuntimeException("versionNo is null");
		}
		this.versionNoLessThanOrEqual = versionNoLessThanOrEqual;
		return this;
	}

}