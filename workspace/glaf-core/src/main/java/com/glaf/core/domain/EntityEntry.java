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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.EntityEntryJsonFactory;

@Entity
@Table(name = "SYS_ENTITYENTRY")
public class EntityEntry implements java.io.Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 节点编号
	 */
	@Column(name = "NODEID_")
	protected Long nodeId;

	/**
	 * 实体编号
	 */
	@Column(name = "ENTITYID_", length = 50)
	protected String entityId;

	/**
	 * 实体Key
	 */
	@Column(name = "ENTRYKEY_", length = 50)
	protected String entryKey;

	/**
	 * 实体类型
	 */
	@Column(name = "ENTRYTYPE_")
	protected int entryType = 0;

	/**
	 * 模块编号
	 */
	@Column(name = "MODULEID_", length = 50)
	protected String moduleId;

	/**
	 * 数据代码
	 */
	@Column(name = "DATACODE_", length = 50)
	protected String dataCode;

	/**
	 * 是否向下传播
	 */
	@Column(name = "ISPROPAGATIONALLOWED_", length = 10)
	protected String isPropagationAllowed = "0";

	/**
	 * 开始日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE_")
	protected Date startDate;

	/**
	 * 结束日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE_")
	protected Date endDate;

	@Transient
	protected boolean valid = false;

	/**
	 * 主题
	 */
	@Column(name = "SUBJECT_")
	protected String subject;

	/**
	 * 目标ID
	 */
	@Column(name = "OBJECTID_")
	protected String objectId;

	/**
	 * 目标值
	 */
	@Column(name = "OBJECTVALUE_")
	protected String objectValue;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 访问点
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "entityEntry")
	protected List<EntryPoint> entryPoints = new java.util.ArrayList<EntryPoint>();

	public EntityEntry() {

	}

	public EntityEntry jsonToObject(JSONObject jsonObject) {
		return EntityEntryJsonFactory.jsonToObject(jsonObject);
	}

	public void addEntryPoint(EntryPoint entryPoint) {
		if (entryPoints == null) {
			entryPoints = new java.util.ArrayList<EntryPoint>();
		}
		entryPoint.setEntityEntry(this);
		entryPoints.add(entryPoint);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityEntry other = (EntityEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getDataCode() {
		return dataCode;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getEntityId() {
		return entityId;
	}

	public String getEntryKey() {
		return entryKey;
	}

	public List<EntryPoint> getEntryPoints() {
		return entryPoints;
	}

	public int getEntryType() {
		return entryType;
	}

	public String getId() {
		return id;
	}

	public String getIsPropagationAllowed() {
		return isPropagationAllowed;
	}

	public String getModuleId() {
		return moduleId;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getSubject() {
		return subject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean isPropagationAllowed() {
		if (StringUtils.equalsIgnoreCase(isPropagationAllowed, "1")
				|| StringUtils.equalsIgnoreCase(isPropagationAllowed, "Y")
				|| StringUtils.equalsIgnoreCase(isPropagationAllowed, "true")) {
			return true;
		}
		return false;
	}

	public boolean isValid() {
		valid = false;
		Date now = new Date();
		if (startDate != null && endDate != null) {
			if (now.getTime() >= startDate.getTime()
					&& now.getTime() <= endDate.getTime()) {
				valid = true;
			}
		}
		return valid;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setEntryKey(String entryKey) {
		this.entryKey = entryKey;
	}

	public void setEntryPoints(List<EntryPoint> entryPoints) {
		this.entryPoints = entryPoints;
	}

	public void setEntryType(int entryType) {
		this.entryType = entryType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsPropagationAllowed(String isPropagationAllowed) {
		this.isPropagationAllowed = isPropagationAllowed;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setPropagationAllowed(boolean isPropagationAllowed) {
		if (isPropagationAllowed) {
			this.isPropagationAllowed = "1";
		} else {
			this.isPropagationAllowed = "0";
		}
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public JSONObject toJsonObject() {
		return EntityEntryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EntityEntryJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}