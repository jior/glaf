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
package com.glaf.oa.purchase.model;

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
import com.glaf.oa.purchase.util.PurchaseitemJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "oa_purchaseitem")
public class Purchaseitem implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "purchaseitemid", nullable = false)
	protected Long purchaseitemid;

	@Column(name = "purchaseid")
	protected Long purchaseid;

	@Column(name = "content", length = 200)
	protected String content;

	@Column(name = "specification", length = 200)
	protected String specification;

	@Column(name = "quantity")
	protected Double quantity;

	@Column(name = "referenceprice")
	protected Double referenceprice;

	@Column(name = "remark")
	protected String remark;

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

	public Purchaseitem() {

	}

	public Long getPurchaseitemid() {
		return this.purchaseitemid;
	}

	public void setPurchaseitemid(Long purchaseitemid) {
		this.purchaseitemid = purchaseitemid;
	}

	public Long getPurchaseid() {
		return this.purchaseid;
	}

	public String getContent() {
		return this.content;
	}

	public String getSpecification() {
		return this.specification;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public Double getReferenceprice() {
		return this.referenceprice;
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

	public void setPurchaseid(Long purchaseid) {
		this.purchaseid = purchaseid;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void setReferenceprice(Double referenceprice) {
		this.referenceprice = referenceprice;
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
		Purchaseitem other = (Purchaseitem) obj;
		if (purchaseitemid == null) {
			if (other.purchaseitemid != null)
				return false;
		} else if (!purchaseitemid.equals(other.purchaseitemid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((purchaseitemid == null) ? 0 : purchaseitemid.hashCode());
		return result;
	}

	public Purchaseitem jsonToObject(JSONObject jsonObject) {
		return PurchaseitemJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return PurchaseitemJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return PurchaseitemJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}