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
package com.glaf.oa.borrow.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.borrow.util.BorrowadderssJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "oa_borrowadderss")
public class Borrowadderss implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "addressid", nullable = false)
	protected Long addressid;

	@Column(name = "borrowid")
	protected Long borrowid;

	@Column(name = "start", length = 100)
	protected String start;

	@Column(name = "reach", length = 100)
	protected String reach;

	@Column(name = "remark", length = 2147483647)
	protected String remark;

	@Column(name = "createBy", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	protected Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate")
	protected Date updateDate;

	@Column(name = "updateBy", length = 50)
	protected String updateBy;

	public Borrowadderss() {

	}

	public Long getAddressid() {
		return this.addressid;
	}

	public void setAddressid(Long addressid) {
		this.addressid = addressid;
	}

	public Long getBorrowid() {
		return this.borrowid;
	}

	public String getStart() {
		return this.start;
	}

	public String getReach() {
		return this.reach;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setBorrowid(Long borrowid) {
		this.borrowid = borrowid;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setReach(String reach) {
		this.reach = reach;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrowadderss other = (Borrowadderss) obj;
		if (addressid == null) {
			if (other.addressid != null)
				return false;
		} else if (!addressid.equals(other.addressid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((addressid == null) ? 0 : addressid.hashCode());
		return result;
	}

	public Borrowadderss jsonToObject(JSONObject jsonObject) {
		return BorrowadderssJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return BorrowadderssJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return BorrowadderssJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}