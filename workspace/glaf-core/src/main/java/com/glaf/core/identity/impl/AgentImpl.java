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

package com.glaf.core.identity.impl;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.identity.Agent;
import com.glaf.core.util.DateUtils;

public class AgentImpl implements Agent {

	private static final long serialVersionUID = 1L;
	/**
	 * ����
	 */

	protected String id;

	/**
	 * ί���� ��consigner
	 */

	protected String assignFrom;

	/**
	 * ί��������
	 */

	protected String assignFromName;

	/**
	 * �����ˣ�assignee
	 */

	protected String assignTo;

	/**
	 * ����������
	 */

	protected String assignToName;

	/**
	 * ��������
	 */

	protected String processName;

	/**
	 * ��������
	 */

	protected String taskName;

	/**
	 * ������
	 */

	protected String objectId;

	/**
	 * ����ֵ
	 */

	protected String objectValue;

	/**
	 * ��ʼ��Ч����
	 */

	protected Date startDate;

	/**
	 * ��������
	 */

	protected Date endDate;

	/**
	 * �������� 0-ȫ�ִ��� 1-����ָ�����̵�ȫ������ 2-����ָ�����̵�ָ������
	 */

	protected int agentType;

	protected String serviceKey;

	/**
	 * �������
	 */

	protected int locked;

	protected boolean valid = false;

	/**
	 * ��������
	 */
	protected Date createDate;

	public AgentImpl() {
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
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("assignFrom", assignFrom);
		jsonObject.put("assignTo", assignTo);
		jsonObject.put("agentType", agentType);
		jsonObject.put("createDate", createDate);
		jsonObject.put("serviceKey", serviceKey);
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (startDate != null) {
			jsonObject.put("startDate", startDate);
		}
		if (endDate != null) {
			jsonObject.put("endDate", endDate);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("assignFrom", assignFrom);
		jsonObject.put("assignTo", assignTo);
		jsonObject.put("agentType", agentType);
		jsonObject.put("serviceKey", serviceKey);
		jsonObject.put("createDate", DateUtils.getDate(createDate));
		jsonObject.put("createDate_date", DateUtils.getDate(createDate));
		jsonObject
				.put("createDate_datetime", DateUtils.getDateTime(createDate));
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (startDate != null) {
			jsonObject.put("startDate", DateUtils.getDate(startDate));
			jsonObject.put("startDate_date", DateUtils.getDate(startDate));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(startDate));
		}
		if (endDate != null) {
			jsonObject.put("endDate", DateUtils.getDate(endDate));
			jsonObject.put("endDate_date", DateUtils.getDate(endDate));
			jsonObject.put("endDate_datetime", DateUtils.getDateTime(endDate));
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}