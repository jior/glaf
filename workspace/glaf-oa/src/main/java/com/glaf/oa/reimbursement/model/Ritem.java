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
package com.glaf.oa.reimbursement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.reimbursement.util.RitemJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "oa_ritem")
public class Ritem implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ritemid", nullable = false)
	protected Long ritemid;

	@Column(name = "reimbursementid")
	protected Long reimbursementid;

	@Column(name = "feetype")
	protected Integer feetype;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "feedate")
	protected Date feedate;

	@Column(name = "subject", length = 100)
	protected String subject;

	@Column(name = "currency", length = 20)
	protected String currency;

	@Column(name = "itemsum")
	protected Double itemsum;

	@Column(name = "exrate")
	protected Double exrate;

	@Column(name = "createBy", length = 0)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	protected Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate")
	protected Date updateDate;

	@Column(name = "updateBy", length = 0)
	protected String updateBy;

	public Ritem() {

	}

	public Long getRitemid() {
		return this.ritemid;
	}

	public void setRitemid(Long ritemid) {
		this.ritemid = ritemid;
	}

	public Long getReimbursementid() {
		return this.reimbursementid;
	}

	public Integer getFeetype() {
		return this.feetype;
	}

	public Date getFeedate() {
		return this.feedate;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getCurrency() {
		return this.currency;
	}

	public Double getItemsum() {
		return this.itemsum;
	}

	public Double getExrate() {
		return this.exrate;
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

	public void setReimbursementid(Long reimbursementid) {
		this.reimbursementid = reimbursementid;
	}

	public void setFeetype(Integer feetype) {
		this.feetype = feetype;
	}

	public void setFeedate(Date feedate) {
		this.feedate = feedate;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setItemsum(Double itemsum) {
		this.itemsum = itemsum;
	}

	public void setExrate(Double exrate) {
		this.exrate = exrate;
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
		Ritem other = (Ritem) obj;
		if (ritemid == null) {
			if (other.ritemid != null)
				return false;
		} else if (!ritemid.equals(other.ritemid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ritemid == null) ? 0 : ritemid.hashCode());
		return result;
	}

	public Ritem jsonToObject(JSONObject jsonObject) {
		return RitemJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return RitemJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return RitemJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}