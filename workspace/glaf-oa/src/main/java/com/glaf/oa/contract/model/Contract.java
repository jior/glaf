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
package com.glaf.oa.contract.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.contract.util.*;

@Entity
@Table(name = "oa_contract")
public class Contract implements Serializable, JSONable {
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

	@Column(name = "currency", length = 25)
	protected String currency;

	@Column(name = "contractsum")
	protected Double contractsum;

	@Column(name = "paytype")
	protected Integer paytype;

	@Column(name = "remarks", length = 1000)
	protected String remarks;

	@Column(name = "attachment", length = 200)
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

	@Column(name = "processinstanceid", length = 100)
	protected String processinstanceid;

	@Column(name = "wfstatus")
	protected Double wfstatus;

	@Column(name = "appusername", length = 50)
	protected String appusername;

	@Column(name = "brands1", length = 100)
	protected String brands1;

	@Column(name = "brands1account")
	protected Double brands1account;

	@Column(name = "brands2", length = 100)
	protected String brands2;

	@Column(name = "brands2account")
	protected Double brands2account;

	@Column(name = "brands3", length = 100)
	protected String brands3;

	@Column(name = "brands3account")
	protected Double brands3account;

	@Column(name = "area", length = 20)
	protected String area;

	@Column(name = "post", length = 100)
	protected String post;

	@Column(name = "dept", length = 100)
	protected String dept;

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

	public Contract() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContactname() {
		return this.contactname;
	}

	public String getProjrctname() {
		return this.projrctname;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public String getSupplisername() {
		return this.supplisername;
	}

	public String getCurrency() {
		return this.currency;
	}

	public Double getContractsum() {
		return this.contractsum;
	}

	public Integer getPaytype() {
		return this.paytype;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public String getAttachment() {
		return this.attachment;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getAppuser() {
		return this.appuser;
	}

	public Date getAppdate() {
		return this.appdate;
	}

	public String getContractno() {
		return this.contractno;
	}

	public String getProcessname() {
		return this.processname;
	}

	public String getProcessinstanceid() {
		return this.processinstanceid;
	}

	public Double getWfstatus() {
		return this.wfstatus;
	}

	public String getAppusername() {
		return this.appusername;
	}

	public String getBrands1() {
		return this.brands1;
	}

	public Double getBrands1account() {
		return this.brands1account;
	}

	public String getBrands2() {
		return this.brands2;
	}

	public Double getBrands2account() {
		return this.brands2account;
	}

	public String getBrands3() {
		return this.brands3;
	}

	public Double getBrands3account() {
		return this.brands3account;
	}

	public String getArea() {
		return this.area;
	}

	public String getPost() {
		return this.post;
	}

	public String getDept() {
		return this.dept;
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

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public void setProjrctname(String projrctname) {
		this.projrctname = projrctname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public void setSupplisername(String supplisername) {
		this.supplisername = supplisername;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setContractsum(Double contractsum) {
		this.contractsum = contractsum;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public void setWfstatus(Double wfstatus) {
		this.wfstatus = wfstatus;
	}

	public void setAppusername(String appusername) {
		this.appusername = appusername;
	}

	public void setBrands1(String brands1) {
		this.brands1 = brands1;
	}

	public void setBrands1account(Double brands1account) {
		this.brands1account = brands1account;
	}

	public void setBrands2(String brands2) {
		this.brands2 = brands2;
	}

	public void setBrands2account(Double brands2account) {
		this.brands2account = brands2account;
	}

	public void setBrands3(String brands3) {
		this.brands3 = brands3;
	}

	public void setBrands3account(Double brands3account) {
		this.brands3account = brands3account;
	}

	public void setArea(String eara) {
		this.area = eara;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public void setDept(String dept) {
		this.dept = dept;
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
		Contract other = (Contract) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Contract jsonToObject(JSONObject jsonObject) {
		return ContractJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ContractJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ContractJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}