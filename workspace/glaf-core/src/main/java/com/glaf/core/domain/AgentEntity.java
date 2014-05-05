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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.AgentJsonFactory;
import com.glaf.core.identity.Agent;

@Entity
@Table(name = "SYS_AGENT")
public class AgentEntity implements Agent, JSONable {

	private static final long serialVersionUID = 1L;
	/**
	 * 代理类型 0-全局代理 1-代理指定流程的全部任务 2-代理指定流程的指定任务
	 */
	@Column(name = "AGENTTYPE_")
	protected int agentType;

	/**
	 * 委托人 ，consigner
	 */
	@Column(name = "ASSIGNFROM_")
	protected String assignFrom;

	/**
	 * 委托人名称
	 */
	@Transient
	protected String assignFromName;

	/**
	 * 受托人，assignee
	 */
	@Column(name = "ASSIGNTO_")
	protected String assignTo;

	/**
	 * 受托人名称
	 */
	@Transient
	protected String assignToName;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * 结束日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE_")
	protected Date endDate;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 锁定标记
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 对象编号
	 */
	@Column(name = "OBJECTID_")
	protected String objectId;

	/**
	 * 对象值
	 */
	@Column(name = "OBJECTVALUE_")
	protected String objectValue;

	/**
	 * 流程名称
	 */
	@Column(name = "PROCESSNAME_")
	protected String processName;

	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	/**
	 * 开始生效日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE_")
	protected Date startDate;

	/**
	 * 任务名称
	 */
	@Column(name = "TASKNAME_")
	protected String taskName;

	@Transient
	protected boolean valid = false;

	public AgentEntity() {
		
	}

	public int getAgentEntityType() {
		return agentType;
	}

	public int getAgentType() {
		return agentType;
	}

	public String getAssignFrom() {
		return assignFrom;
	}

	public String getAssignFromName() {
		return assignFromName;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public String getAssignToName() {
		return assignToName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getId() {
		return id;
	}

	public int getLocked() {
		return locked;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getProcessName() {
		return processName;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getTaskName() {
		return taskName;
	}

	public boolean isValid() {
		boolean valid = false;
		Date now = new Date();
		if (locked == 0 && startDate != null && endDate != null) {
			if (now.getTime() >= startDate.getTime()
					&& now.getTime() <= endDate.getTime()) {
				valid = true;
			}
		}
		return valid;
	}

	public AgentEntity jsonToObject(JSONObject jsonObject) {
		return AgentJsonFactory.jsonToObject(jsonObject);
	}

	public void setAgentEntityType(int agentType) {
		this.agentType = agentType;
	}

	public void setAgentType(int agentType) {
		this.agentType = agentType;
	}

	public void setAssignFrom(String assignFrom) {
		this.assignFrom = assignFrom;
	}

	public void setAssignFromName(String assignFromName) {
		this.assignFromName = assignFromName;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public void setAssignToName(String assignToName) {
		this.assignToName = assignToName;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public JSONObject toJsonObject() {
		return AgentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return AgentJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}