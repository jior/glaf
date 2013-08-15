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
import com.glaf.oa.borrow.util.BorrowmoneyJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "oa_borrowmoney")
public class Borrowmoney implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "borrowmoneyid", nullable = false)
	protected Long borrowmoneyid;

	@Column(name = "borrowid")
	protected Long borrowid;

	@Column(name = "feename", length = 100)
	protected String feename;

	@Column(name = "feesum")
	protected Double feesum;

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

	public Borrowmoney() {

	}

	public Long getBorrowmoneyid() {
		return this.borrowmoneyid;
	}

	public void setBorrowmoneyid(Long borrowmoneyid) {
		this.borrowmoneyid = borrowmoneyid;
	}

	public Long getBorrowid() {
		return this.borrowid;
	}

	public String getFeename() {
		return this.feename;
	}

	public Double getFeesum() {
		return this.feesum;
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

	public void setFeename(String feename) {
		this.feename = feename;
	}

	public void setFeesum(Double feesum) {
		this.feesum = feesum;
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
		Borrowmoney other = (Borrowmoney) obj;
		if (borrowmoneyid == null) {
			if (other.borrowmoneyid != null)
				return false;
		} else if (!borrowmoneyid.equals(other.borrowmoneyid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((borrowmoneyid == null) ? 0 : borrowmoneyid.hashCode());
		return result;
	}

	public Borrowmoney jsonToObject(JSONObject jsonObject) {
		return BorrowmoneyJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return BorrowmoneyJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return BorrowmoneyJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}