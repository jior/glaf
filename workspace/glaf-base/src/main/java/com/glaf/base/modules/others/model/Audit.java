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

package com.glaf.base.modules.others.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.others.util.AuditJsonFactory;
import com.glaf.core.base.JSONable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "MyAudit")
public class Audit implements Serializable, JSONable {
	private static final long serialVersionUID = 4192168036356165765L;
	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	protected Date createDate;

	/**
	 * 部门编号
	 */
	@Column(name = "DEPTID")
	protected long deptId;

	/**
	 * 部门名称
	 */
	@Column(name = "DEPTNAME", length = 250)
	protected String deptName;

	/**
	 * 标记
	 */
	@Column(name = "FLAG")
	protected int flag;

	/**
	 * 职位
	 */
	@Column(name = "HEADSHIP", length = 50)
	protected String headship;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 领导编号
	 */
	@Column(name = "LEADERID")
	protected long leaderId;

	/**
	 * 领导名称
	 */
	@Column(name = "LEADERNAME", length = 50)
	protected String leaderName;

	/**
	 * 评价
	 */
	@Column(name = "MEMO", length = 500)
	protected String memo;

	/**
	 * 引用ID
	 */
	@Column(name = "REFERID")
	protected long referId;

	/**
	 * 引用类型
	 */
	@Column(name = "REFERTYPE")
	protected int referType;

	public Date getCreateDate() {
		return createDate;
	}

	public long getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public int getFlag() {
		return flag;
	}

	public String getHeadship() {
		return headship;
	}

	public long getId() {
		return id;
	}

	public long getLeaderId() {
		return leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public String getMemo() {
		return memo;
	}

	public long getReferId() {
		return referId;
	}

	public int getReferType() {
		return referType;
	}

	public Audit jsonToObject(JSONObject jsonObject) {
		return AuditJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLeaderId(long leaderId) {
		this.leaderId = leaderId;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setReferId(long referId) {
		this.referId = referId;
	}

	public void setReferType(int referType) {
		this.referType = referType;
	}

	public JSONObject toJsonObject() {
		return AuditJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return AuditJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}