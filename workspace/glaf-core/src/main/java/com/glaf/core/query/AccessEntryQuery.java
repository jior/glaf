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

import java.util.List;

public class AccessEntryQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String applicationName;
	protected List<String> applicationNames;
	protected String processDefinitionId;
	protected List<String> processDefinitionIds;
	protected String taskName;
	protected List<String> taskNames;
	protected List<String> formNames;
	protected Long roleId;
	protected List<Long> roleIds;
	protected Integer entryType;
	protected Integer entryTypeGreaterThanOrEqual;
	protected Integer entryTypeLessThanOrEqual;
	protected List<Integer> entryTypes;
	protected List<String> objectIds;
	protected List<String> objectValues;
	protected List<String> createBys;

	public AccessEntryQuery() {

	}

	public AccessEntryQuery applicationName(String applicationName) {
		if (applicationName == null) {
			throw new RuntimeException("applicationName is null");
		}
		this.applicationName = applicationName;
		return this;
	}

	public AccessEntryQuery applicationNames(List<String> applicationNames) {
		if (applicationNames == null) {
			throw new RuntimeException("applicationNames is empty ");
		}
		this.applicationNames = applicationNames;
		return this;
	}

	public AccessEntryQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public AccessEntryQuery entryType(Integer entryType) {
		if (entryType == null) {
			throw new RuntimeException("entryType is null");
		}
		this.entryType = entryType;
		return this;
	}

	public AccessEntryQuery entryTypeGreaterThanOrEqual(
			Integer entryTypeGreaterThanOrEqual) {
		if (entryTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("entryType is null");
		}
		this.entryTypeGreaterThanOrEqual = entryTypeGreaterThanOrEqual;
		return this;
	}

	public AccessEntryQuery entryTypeLessThanOrEqual(
			Integer entryTypeLessThanOrEqual) {
		if (entryTypeLessThanOrEqual == null) {
			throw new RuntimeException("entryType is null");
		}
		this.entryTypeLessThanOrEqual = entryTypeLessThanOrEqual;
		return this;
	}

	public AccessEntryQuery entryTypes(List<Integer> entryTypes) {
		if (entryTypes == null) {
			throw new RuntimeException("entryTypes is empty ");
		}
		this.entryTypes = entryTypes;
		return this;
	}

	public AccessEntryQuery formNames(List<String> formNames) {
		if (formNames == null) {
			throw new RuntimeException("formNames is empty ");
		}
		this.formNames = formNames;
		return this;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public List<String> getApplicationNames() {
		return applicationNames;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Integer getEntryType() {
		return entryType;
	}

	public Integer getEntryTypeGreaterThanOrEqual() {
		return entryTypeGreaterThanOrEqual;
	}

	public Integer getEntryTypeLessThanOrEqual() {
		return entryTypeLessThanOrEqual;
	}

	public List<Integer> getEntryTypes() {
		return entryTypes;
	}

	public List<String> getFormNames() {
		return formNames;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public List<String> getProcessDefinitionIds() {
		return processDefinitionIds;
	}

	public Long getRoleId() {
		return roleId;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public String getTaskName() {
		return taskName;
	}

	public List<String> getTaskNames() {
		return taskNames;
	}

	public AccessEntryQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public AccessEntryQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public AccessEntryQuery processDefinitionId(String processDefinitionId) {
		if (processDefinitionId == null) {
			throw new RuntimeException("processDefinitionId is null");
		}
		this.processDefinitionId = processDefinitionId;
		return this;
	}

	public AccessEntryQuery processDefinitionIds(
			List<String> processDefinitionIds) {
		if (processDefinitionIds == null) {
			throw new RuntimeException("processDefinitionIds is empty ");
		}
		this.processDefinitionIds = processDefinitionIds;
		return this;
	}

	public AccessEntryQuery roleId(Long roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public AccessEntryQuery roleIds(List<Long> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setApplicationNames(List<String> applicationNames) {
		this.applicationNames = applicationNames;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}

	public void setEntryTypeGreaterThanOrEqual(
			Integer entryTypeGreaterThanOrEqual) {
		this.entryTypeGreaterThanOrEqual = entryTypeGreaterThanOrEqual;
	}

	public void setEntryTypeLessThanOrEqual(Integer entryTypeLessThanOrEqual) {
		this.entryTypeLessThanOrEqual = entryTypeLessThanOrEqual;
	}

	public void setEntryTypes(List<Integer> entryTypes) {
		this.entryTypes = entryTypes;
	}

	public void setFormNames(List<String> formNames) {
		this.formNames = formNames;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public void setProcessDefinitionIds(List<String> processDefinitionIds) {
		this.processDefinitionIds = processDefinitionIds;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskNames(List<String> taskNames) {
		this.taskNames = taskNames;
	}

	public AccessEntryQuery taskName(String taskName) {
		if (taskName == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskName = taskName;
		return this;
	}

	public AccessEntryQuery taskNames(List<String> taskNames) {
		if (taskNames == null) {
			throw new RuntimeException("taskNames is empty ");
		}
		this.taskNames = taskNames;
		return this;
	}

}