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

package com.glaf.activiti.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EX_ACT_ACTIVITY_INSTANCE")
public class ActivityInstance implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "ACTORID_", length = 50)
	protected String actorId;

	@Column(name = "PROCESSINSTANCEID_", length = 100)
	protected String processInstanceId;

	@Column(name = "EVENTNAME_")
	protected String eventName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDTIME_")
	protected Date endTime;

	@Column(name = "STATE_", length = 100)
	protected String state;

	@Column(name = "OUTCOME_", length = 100)
	protected String outcome;

	@Column(name = "TYPE_", length = 100)
	protected String type;

	@Column(name = "APPROVE_", length = 100)
	protected String approve;

	@Column(name = "OPINION_", length = 1000)
	protected String opinion;

	public String getActorId() {
		return actorId;
	}

	public String getApprove() {
		return approve;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getEventName() {
		return eventName;
	}

	public String getId() {
		return id;
	}

	public String getOpinion() {
		return opinion;
	}

	public String getOutcome() {
		return outcome;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getState() {
		return state;
	}

	public String getType() {
		return type;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setType(String type) {
		this.type = type;
	}

}