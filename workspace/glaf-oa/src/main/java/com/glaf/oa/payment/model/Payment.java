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
package com.glaf.oa.payment.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.payment.util.*;

@Entity
@Table(name = "oa_payment")
public class Payment implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "paymentid", nullable = false)
	protected Long paymentid;

	@Column(name = "area", length = 20)
	protected String area;

	@Column(name = "post", length = 100)
	protected String post;

	@Column(name = "company", length = 100)
	protected String company;

	@Column(name = "dept", length = 100)
	protected String dept;

	@Column(name = "certificateno", length = 100)
	protected String certificateno;

	@Column(name = "receiptno", length = 100)
	protected String receiptno;

	@Column(name = "appuser", length = 20)
	protected String appuser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "appdate")
	protected Date appdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "maturitydate")
	protected Date maturitydate;

	@Column(name = "appsum")
	protected Double appsum;

	@Column(name = "currency", length = 20)
	protected String currency;

	@Column(name = "budgetno", length = 20)
	protected String budgetno;

	@Column(name = "use", length = 200)
	protected String use;

	@Column(name = "supname", length = 200)
	protected String supname;

	@Column(name = "supbank", length = 200)
	protected String supbank;

	@Column(name = "supaccount", length = 200)
	protected String supaccount;

	@Column(name = "supaddress", length = 200)
	protected String supaddress;

	@Column(name = "subject", length = 100)
	protected String subject;

	@Column(name = "checkno", length = 100)
	protected String checkno;

	@Column(name = "remark", length = 2147483647)
	protected String remark;

	@Column(name = "status")
	protected Integer status;

	@Column(name = "processname", length = 100)
	protected String processname;

	@Column(name = "processinstanceid")
	protected Long processinstanceid;

	@Column(name = "wfstatus")
	protected Double wfstatus;

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

	public Payment() {

	}

	public Long getPaymentid() {
		return this.paymentid;
	}

	public void setPaymentid(Long paymentid) {
		this.paymentid = paymentid;
	}

	public String getArea() {
		return this.area;
	}

	public String getPost() {
		return this.post;
	}

	public String getCompany() {
		return this.company;
	}

	public String getDept() {
		return this.dept;
	}

	public String getCertificateno() {
		return this.certificateno;
	}

	public String getReceiptno() {
		return this.receiptno;
	}

	public String getAppuser() {
		return this.appuser;
	}

	public Date getAppdate() {
		return this.appdate;
	}

	public Date getMaturitydate() {
		return this.maturitydate;
	}

	public Double getAppsum() {
		return this.appsum;
	}

	public String getCurrency() {
		return this.currency;
	}

	public String getBudgetno() {
		return this.budgetno;
	}

	public String getUse() {
		return this.use;
	}

	public String getSupname() {
		return this.supname;
	}

	public String getSupbank() {
		return this.supbank;
	}

	public String getSupaccount() {
		return this.supaccount;
	}

	public String getSupaddress() {
		return this.supaddress;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getCheckno() {
		return this.checkno;
	}

	public String getRemark() {
		return this.remark;
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

	public Double getWfstatus() {
		return this.wfstatus;
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

	public void setArea(String area) {
		this.area = area;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}

	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public void setMaturitydate(Date maturitydate) {
		this.maturitydate = maturitydate;
	}

	public void setAppsum(Double appsum) {
		this.appsum = appsum;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setBudgetno(String budgetno) {
		this.budgetno = budgetno;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public void setSupname(String supname) {
		this.supname = supname;
	}

	public void setSupbank(String supbank) {
		this.supbank = supbank;
	}

	public void setSupaccount(String supaccount) {
		this.supaccount = supaccount;
	}

	public void setSupaddress(String supaddress) {
		this.supaddress = supaddress;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setCheckno(String checkno) {
		this.checkno = checkno;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public void setWfstatus(Double wfstatus) {
		this.wfstatus = wfstatus;
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
		Payment other = (Payment) obj;
		if (paymentid == null) {
			if (other.paymentid != null)
				return false;
		} else if (!paymentid.equals(other.paymentid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((paymentid == null) ? 0 : paymentid.hashCode());
		return result;
	}

	public Payment jsonToObject(JSONObject jsonObject) {
		return PaymentJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return PaymentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return PaymentJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}