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
package com.glaf.oa.salescontract.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.salescontract.util.*;

@Entity
@Table(name = "oa_salescontract")
public class Salescontract implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	protected Long id;

	@Column(name = "contactname", length = 200)
	protected String contactname;

	@Column(name = "projrctname", length = 200)
	protected String projrctname;

	@Column(name = "companyname", length = 100)
	protected String companyname;

	@Column(name = "supplisername", length = 100)
	protected String supplisername;

	@Column(name = "currency", length = 20)
	protected String currency;

	@Column(name = "contractsum")
	protected Double contractsum;

	@Column(name = "paytype")
	protected Integer paytype;

	@Column(name = "remarks", length = 1000)
	protected String remarks;

	@Column(name = "attachment", length = 1000)
	protected String attachment;

	@Column(name = "status")
	protected Integer status;

	@Column(name = "appuser", length = 50)
	protected String appuser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "appdate")
	protected Date appdate;

	@Column(name = "contractno", length = 50)
	protected String contractno;

	@Column(name = "processname", length = 100)
	protected String processname;

	@Column(name = "processinstanceid")
	protected Double processinstanceid;

	@Column(name = "wfstatus")
	protected Double wfstatus;

	@Column(name = "optionalsum")
	protected Double optionalsum;

	@Column(name = "firstpay")
	protected Double firstpay;

	@Column(name = "lastpay")
	protected Double lastpay;

	@Column(name = "discount")
	protected Double discount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deliverydate")
	protected Date deliverydate;

	@Column(name = "sales", length = 50)
	protected String sales;

	@Column(name = "contractsales", length = 50)
	protected String contractsales;

	@Column(name = "giftsum")
	protected Double giftsum;

	@Column(name = "giftremark", length = 1000)
	protected String giftremark;

	@Column(name = "remark", length = 1000)
	protected String remark;

	@Column(name = "area")
	protected String area;

	@Column(name = "company", length = 100)
	protected String company;

	@Column(name = "createby", length = 255)
	protected String createby;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdate")
	protected Date createdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedate")
	protected Date updatedate;

	@Column(name = "updateby", length = 255)
	protected String updateby;

	@Column(name = "headship", length = 255)
	protected String headship;

	@Column(name = "dept", length = 255)
	protected String dept;

	public Salescontract() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Salescontract other = (Salescontract) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getAppdate() {
		return this.appdate;
	}

	public String getAppuser() {
		return this.appuser;
	}

	public String getArea() {
		return this.area;
	}

	public String getAttachment() {
		return this.attachment;
	}

	public String getCompany() {
		return this.company;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public String getContactname() {
		return this.contactname;
	}

	public String getContractno() {
		return this.contractno;
	}

	public String getContractsales() {
		return this.contractsales;
	}

	public Double getContractsum() {
		return this.contractsum;
	}

	public String getCreateBy() {
		return this.createby;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public String getCurrency() {
		return this.currency;
	}

	public Date getDeliverydate() {
		return this.deliverydate;
	}

	public String getDept() {
		return dept;
	}

	public Double getDiscount() {
		return this.discount;
	}

	public Double getFirstpay() {
		return this.firstpay;
	}

	public String getGiftremark() {
		return this.giftremark;
	}

	public Double getGiftsum() {
		return this.giftsum;
	}

	public String getHeadship() {
		return headship;
	}

	public Long getId() {
		return this.id;
	}

	public Double getLastpay() {
		return this.lastpay;
	}

	public Double getOptionalsum() {
		return this.optionalsum;
	}

	public Integer getPaytype() {
		return this.paytype;
	}

	public Double getProcessinstanceid() {
		return this.processinstanceid;
	}

	public String getProcessname() {
		return this.processname;
	}

	public String getProjrctname() {
		return this.projrctname;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public String getSales() {
		return this.sales;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getSupplisername() {
		return this.supplisername;
	}

	public String getUpdateby() {
		return this.updateby;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public Double getWfstatus() {
		return this.wfstatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Salescontract jsonToObject(JSONObject jsonObject) {
		return SalescontractJsonFactory.jsonToObject(jsonObject);
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public void setContractsales(String contractsales) {
		this.contractsales = contractsales;
	}

	public void setContractsum(Double contractsum) {
		this.contractsum = contractsum;
	}

	public void setCreateBy(String createby) {
		this.createby = createby;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public void setCreateDate(Date createDate) {
		this.createdate = createDate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setDeliverydate(Date deliverydate) {
		this.deliverydate = deliverydate;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public void setFirstpay(Double firstpay) {
		this.firstpay = firstpay;
	}

	public void setGiftremark(String giftremark) {
		this.giftremark = giftremark;
	}

	public void setGiftsum(Double giftsum) {
		this.giftsum = giftsum;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastpay(Double lastpay) {
		this.lastpay = lastpay;
	}

	public void setOptionalsum(Double optionalsum) {
		this.optionalsum = optionalsum;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public void setProcessinstanceid(Double processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public void setProjrctname(String projrctname) {
		this.projrctname = projrctname;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setSupplisername(String supplisername) {
		this.supplisername = supplisername;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public void setWfstatus(Double wfstatus) {
		this.wfstatus = wfstatus;
	}

	public JSONObject toJsonObject() {
		return SalescontractJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SalescontractJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}