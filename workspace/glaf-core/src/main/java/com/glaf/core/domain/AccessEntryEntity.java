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

package com.glaf.core.domain;

import java.util.Date;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.AccessEntry;
import com.glaf.core.base.AccessPoint;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.AccessEntryEntityJsonFactory;

@Entity
@Table(name = "SYS_ACCESSENTRY")
public class AccessEntryEntity implements java.io.Serializable, AccessEntry,
		JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Basic
	@Column(name = "APPLICATIONNAME_")
	protected String applicationName;

	@Basic
	@Column(name = "PROCESSDEFINITIONID_")
	protected String processDefinitionId;

	@Basic
	@Column(name = "PROCESSNAME_")
	protected String processName;

	@Basic
	@Column(name = "TASKNAME_")
	protected String taskName;

	@Basic
	@Column(name = "FORMNAME_")
	protected String formName;

	@Basic
	@Column(name = "ROLEID_")
	protected Long roleId;

	@Basic
	@Column(name = "ENTRYTYPE_")
	protected int entryType = 0;

	@Basic
	@Column(name = "EDITFILE_")
	protected String editFile;

	@Basic
	@Column(name = "OBJECTID_")
	protected String objectId;

	@Basic
	@Column(name = "OBJECTVALUE_")
	protected String objectValue;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Basic
	@Column(name = "CREATEBY_")
	protected String createBy;

	@javax.persistence.Transient
	protected Map<String, AccessPoint> accessPoints = new java.util.HashMap<String, AccessPoint>();

	public AccessEntryEntity() {

	}

	@Transient
	public void addAccessPoint(AccessPoint accessPoint) {
		if (accessPoints == null) {
			accessPoints = new java.util.HashMap<String, AccessPoint>();
		}
		accessPoints.put(accessPoint.getName(), accessPoint);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessEntryEntity other = (AccessEntryEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Map<String, AccessPoint> getAccessPoints() {
		if (accessPoints == null) {
			accessPoints = new java.util.HashMap<String, AccessPoint>();
		}
		return accessPoints;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getEditFile() {
		return editFile;
	}

	public int getEntryType() {
		return entryType;
	}

	public String getFormName() {
		return formName;
	}

	public String getId() {
		return id;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public String getProcessName() {
		return processName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public String getTaskName() {
		return taskName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public AccessEntryEntity jsonToObject(JSONObject jsonObject) {
		return AccessEntryEntityJsonFactory.jsonToObject(jsonObject);
	}

	@Transient
	public void removeAccessPoint(AccessPoint accessPoint) {
		if (accessPoints != null) {
			accessPoints.remove(accessPoint.getName());
		}
	}

	public void setAccessPoints(Map<String, AccessPoint> accessPoints) {
		this.accessPoints = accessPoints;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setEditFile(String editFile) {
		this.editFile = editFile;
	}

	public void setEntryType(int entryType) {
		this.entryType = entryType;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public JSONObject toJsonObject() {
		return AccessEntryEntityJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return AccessEntryEntityJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return "[processName=" + processName + " taskName=" + taskName
				+ " formName=" + formName + "]";
	}

}