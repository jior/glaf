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
package com.glaf.form.core.domain;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.form.core.util.*;

@Entity
@Table(name = "FORM_HISTORY_INSTANCE")
public class FormHistoryInstance implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ACTORID_")
	protected String actorId;

	@Lob
	@Column(name = "CONTENT_")
	protected String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Id
	@Column(name = "ID_", length = 100, nullable = false)
	protected String id;

	@Column(name = "NAME_")
	protected String name;

	@Column(name = "NODEID_")
	protected String nodeId;

	@Column(name = "OBJECTID_")
	protected String objectId;

	@Column(name = "OBJECTTYPE_")
	protected int objectType = 0;

	@Column(name = "OBJECTVALUE_")
	protected String objectValue;

	@Column(name = "PROCESSINSTANCEID_")
	protected String processInstanceId;

	@Column(name = "REFID_")
	protected Long refId;

	@Column(name = "TASKINSTANCEID_")
	protected String taskInstanceId;

	@Column(name = "VERSIONNO_")
	protected long versionNo;

	public FormHistoryInstance() {
	}

	public String getActorId() {
		return actorId;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNodeId() {
		return nodeId;
	}

	public String getObjectId() {
		return objectId;
	}

	public int getObjectType() {
		return objectType;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public Long getRefId() {
		return refId;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public FormHistoryInstance jsonToObject(JSONObject jsonObject) {
		return FormHistoryInstanceJsonFactory.jsonToObject(jsonObject);
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public JSONObject toJsonObject() {
		return FormHistoryInstanceJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormHistoryInstanceJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}