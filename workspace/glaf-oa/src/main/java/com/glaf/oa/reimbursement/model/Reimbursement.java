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

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.reimbursement.util.*;

@Entity
@Table(name = "oa_reimbursement")
public class Reimbursement implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "reimbursementid", nullable = false)
	protected Long reimbursementid;

	@Column(name = "reimbursementno", length = 20)
	protected String reimbursementno;

	@Column(name = "area", length = 20)
	protected String area;

	@Column(name = "dept", length = 20)
	protected String dept;

	@Column(name = "post", length = 100)
	protected String post;

	@Column(name = "appuser", length = 20)
	protected String appuser;

	@Column(name = "subject", length = 200)
	protected String subject;

	@Column(name = "budgetno", length = 20)
	protected String budgetno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "appdate")
	protected Date appdate;

	@Column(name = "appsum")
	protected Double appsum;

	@Column(name = "status")
	protected Integer status;

	@Column(name = "processname", length = 100)
	protected String processname;

	@Column(name = "processinstanceid")
	protected Long processinstanceid;

	@Column(name = "wfstatus")
	protected Long wfstatus;

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

	@Column(name = "company", length = 100)
	protected String company;

	@Column(name = "budgetsum")
	protected Double budgetsum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "budgetDate")
	protected Date budgetDate;

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

	protected String brand;

	protected Date wdStatusFlag;

	public Date getWdStatusFlag() {
		return wdStatusFlag;
	}

	public void setWdStatusFlag(Date wdStatusFlag) {
		this.wdStatusFlag = wdStatusFlag;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Reimbursement() {

	}

	public Long getReimbursementid() {
		return this.reimbursementid;
	}

	public void setReimbursementid(Long reimbursementid) {
		this.reimbursementid = reimbursementid;
	}

	public String getReimbursementno() {
		return this.reimbursementno;
	}

	public String getArea() {
		return this.area;
	}

	public String getDept() {
		return this.dept;
	}

	public String getPost() {
		return this.post;
	}

	public String getAppuser() {
		return this.appuser;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getBudgetno() {
		return this.budgetno;
	}

	public Date getAppdate() {
		return this.appdate;
	}

	public Double getAppsum() {
		return this.appsum;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getProcessname() {
		return this.processname;
	}

	public Long getProcessinstanceid() {
		return this.processinstanceid;
	}

	public Long getWfstatus() {
		return this.wfstatus;
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

	public String getCompany() {
		return this.company;
	}

	public Double getBudgetsum() {
		return this.budgetsum;
	}

	public Date getBudgetDate() {
		return this.budgetDate;
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

	public void setReimbursementno(String reimbursementno) {
		this.reimbursementno = reimbursementno;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBudgetno(String budgetno) {
		this.budgetno = budgetno;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public void setAppsum(Double appsum) {
		this.appsum = appsum;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public void setProcessinstanceid(Long processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public void setWfstatus(Long wfstatus) {
		this.wfstatus = wfstatus;
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

	public void setCompany(String company) {
		this.company = company;
	}

	public void setBudgetsum(Double budgetsum) {
		this.budgetsum = budgetsum;
	}

	public void setBudgetDate(Date budgetDate) {
		this.budgetDate = budgetDate;
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
		Reimbursement other = (Reimbursement) obj;
		if (reimbursementid == null) {
			if (other.reimbursementid != null)
				return false;
		} else if (!reimbursementid.equals(other.reimbursementid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((reimbursementid == null) ? 0 : reimbursementid.hashCode());
		return result;
	}

	public Reimbursement jsonToObject(JSONObject jsonObject) {
		return ReimbursementJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ReimbursementJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ReimbursementJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}