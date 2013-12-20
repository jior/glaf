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

import java.util.List;

import com.glaf.core.query.DataQuery;

public class TodoQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String actorIdx;
	protected String code;
	protected List<String> codes;
	protected Long appId;
	protected List<Long> appIds;
	protected Long deptId;
	protected List<Long> deptIds;
	protected Long moduleId;
	protected List<Long> moduleIds;
	protected String moduleNameLike;
	protected Long roleId;
	protected List<Long> roleIds;
	protected String roleCode;
	protected String roleName;
	protected String roleNameLike;
	protected String titleLike;
	protected String contentLike;
	protected String linkLike;
	protected String listLinkLike;
	protected String allListLinkLike;
	protected String linkType;
	protected String taskName;
	protected String taskNameLike;
	protected List<String> taskNames;
	protected String provider;
	protected List<String> objectIds;
	protected List<String> objectValues;
	protected Integer configFlag;
	protected Integer enableFlag;
	protected Integer sortNo;
	protected Long todoId;
	protected List<Long> todoIds;
	protected String type;

	public TodoQuery() {

	}

	public TodoQuery allListLinkLike(String allListLinkLike) {
		if (allListLinkLike == null) {
			throw new RuntimeException("allListLink is null");
		}
		this.allListLinkLike = allListLinkLike;
		return this;
	}

	public TodoQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public TodoQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public TodoQuery configFlag(Integer configFlag) {
		if (configFlag == null) {
			throw new RuntimeException("configFlag is null");
		}
		this.configFlag = configFlag;
		return this;
	}

	public TodoQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public String getActorIdx() {
		return actorIdx;
	}

	public String getAllListLinkLike() {
		return allListLinkLike;
	}

	public Long getAppId() {
		return appId;
	}

	public List<Long> getAppIds() {
		return appIds;
	}

	public String getCode() {
		return code;
	}

	public List<String> getCodes() {
		return codes;
	}

	public Integer getConfigFlag() {
		return configFlag;
	}

	public String getContentLike() {
		return contentLike;
	}

	public Long getDeptId() {
		return deptId;
	}

	public List<Long> getDeptIds() {
		return deptIds;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public String getLinkLike() {
		return linkLike;
	}

	public String getLinkType() {
		return linkType;
	}

	public String getListLinkLike() {
		return listLinkLike;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public List<Long> getModuleIds() {
		return moduleIds;
	}

	public String getModuleNameLike() {
		return moduleNameLike;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public String getProvider() {
		return provider;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public Long getRoleId() {
		return roleId;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleNameLike() {
		return roleNameLike;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskNameLike() {
		return taskNameLike;
	}

	public List<String> getTaskNames() {
		return taskNames;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public Long getTodoId() {
		return todoId;
	}

	public List<Long> getTodoIds() {
		return todoIds;
	}

	public String getType() {
		return type;
	}

	public TodoQuery linkLike(String linkLike) {
		if (linkLike == null) {
			throw new RuntimeException("link is null");
		}
		this.linkLike = linkLike;
		return this;
	}

	public TodoQuery linkType(String linkType) {
		if (linkType == null) {
			throw new RuntimeException("linkType is null");
		}
		this.linkType = linkType;
		return this;
	}

	public TodoQuery listLinkLike(String listLinkLike) {
		if (listLinkLike == null) {
			throw new RuntimeException("listLink is null");
		}
		this.listLinkLike = listLinkLike;
		return this;
	}

	public TodoQuery moduleId(Long moduleId) {
		if (moduleId == null) {
			throw new RuntimeException("moduleId is null");
		}
		this.moduleId = moduleId;
		return this;
	}

	public TodoQuery moduleIds(List<Long> moduleIds) {
		if (moduleIds == null) {
			throw new RuntimeException("moduleIds is empty ");
		}
		this.moduleIds = moduleIds;
		return this;
	}

	public TodoQuery moduleNameLike(String moduleNameLike) {
		if (moduleNameLike == null) {
			throw new RuntimeException("moduleName is null");
		}
		this.moduleNameLike = moduleNameLike;
		return this;
	}

	public TodoQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public TodoQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public TodoQuery provider(String provider) {
		if (provider == null) {
			throw new RuntimeException("provider is null");
		}
		this.provider = provider;
		return this;
	}

	public TodoQuery roleCode(String roleCode) {
		if (roleCode == null) {
			throw new RuntimeException("roleCode is null");
		}
		this.roleCode = roleCode;
		return this;
	}

	public TodoQuery roleId(Long roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public TodoQuery roleIds(List<Long> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}
	
	public TodoQuery deptIds(List<Long> deptIds) {
		if (deptIds == null) {
			throw new RuntimeException("deptIds is empty ");
		}
		this.deptIds = deptIds;
		return this;
	}

	public TodoQuery roleName(String roleName) {
		if (roleName == null) {
			throw new RuntimeException("roleName is null");
		}
		this.roleName = roleName;
		return this;
	}

	public TodoQuery roleNameLike(String roleNameLike) {
		if (roleNameLike == null) {
			throw new RuntimeException("roleName is null");
		}
		this.roleNameLike = roleNameLike;
		return this;
	}

	public void setActorIdx(String actorIdx) {
		this.actorIdx = actorIdx;
	}

	public void setAllListLinkLike(String allListLinkLike) {
		this.allListLinkLike = allListLinkLike;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setAppIds(List<Long> appIds) {
		this.appIds = appIds;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public void setConfigFlag(Integer configFlag) {
		this.configFlag = configFlag;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	public void setLinkLike(String linkLike) {
		this.linkLike = linkLike;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public void setListLinkLike(String listLinkLike) {
		this.listLinkLike = listLinkLike;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public void setModuleIds(List<Long> moduleIds) {
		this.moduleIds = moduleIds;
	}

	public void setModuleNameLike(String moduleNameLike) {
		this.moduleNameLike = moduleNameLike;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRoleNameLike(String roleNameLike) {
		this.roleNameLike = roleNameLike;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}

	public void setTaskNames(List<String> taskNames) {
		this.taskNames = taskNames;
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

	public void setType(String type) {
		this.type = type;
	}

	public TodoQuery sortNo(Integer sortNo) {
		if (sortNo == null) {
			throw new RuntimeException("sortNo is null");
		}
		this.sortNo = sortNo;
		return this;
	}

	public TodoQuery taskName(String taskName) {
		if (taskName == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskName = taskName;
		return this;
	}

	public TodoQuery taskNameLike(String taskNameLike) {
		if (taskNameLike == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskNameLike = taskNameLike;
		return this;
	}

	public TodoQuery taskNames(List<String> taskNames) {
		if (taskNames == null) {
			throw new RuntimeException("taskNames is empty ");
		}
		this.taskNames = taskNames;
		return this;
	}

	public TodoQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public TodoQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}