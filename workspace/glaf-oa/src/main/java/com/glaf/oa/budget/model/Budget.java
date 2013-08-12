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
package com.glaf.oa.budget.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.budget.util.*;

@Entity
@Table(name = "oa_budget")
public class Budget implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "budgetid", nullable = false)
	protected Long budgetid;

	@Column(name = "budgetno", length = 20)
	protected String budgetno;

	@Column(name = "area", length = 20)
	protected String area;

	@Column(name = "company", length = 100)
	protected String company;

	@Column(name = "dept", length = 100)
	protected String dept;

	@Column(name = "post", length = 100)
	protected String post;

	@Column(name = "appuser", length = 20)
	protected String appuser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "appdate")
	protected Date appdate;

	@Column(name = "proname", length = 200)
	protected String proname;

	@Column(name = "procontent")
	protected String procontent;

	@Column(name = "budgetsum")
	protected Double budgetsum;

	@Column(name = "currency", length = 20)
	protected String currency;

	@Column(name = "paymentmodel")
	protected Integer paymentmodel;

	@Column(name = "paymenttype")
	protected Integer paymenttype;

	@Column(name = "supname", length = 100)
	protected String supname;

	@Column(name = "supaccount", length = 100)
	protected String supaccount;

	@Column(name = "supbank", length = 100)
	protected String supbank;

	@Column(name = "supaddress", length = 200)
	protected String supaddress;

	@Column(name = "attachment", length = 200)
	protected String attachment;

	@Column(name = "status")
	protected Integer status;

	@Column(name = "processname", length = 100)
	protected String processname;

	@Column(name = "processinstanceid")
	protected Double processinstanceid;

	@Column(name = "wfstatus")
	protected Double wfstatus;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "paymentdate")
	protected Date paymentdate;

	@Column(name = "remark", length = 1000)
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

	public Budget() {

	}

	public Long getBudgetid() {
		return this.budgetid;
	}

	public void setBudgetid(Long budgetid) {
		this.budgetid = budgetid;
	}

	public String getBudgetno() {
		return this.budgetno;
	}

	public String getArea() {
		return this.area;
	}

	public String getCompany() {
		return this.company;
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

	public Date getAppdate() {
		return this.appdate;
	}

	public String getProname() {
		return this.proname;
	}

	public String getProcontent() {
		return this.procontent;
	}

	public Double getBudgetsum() {
		return this.budgetsum;
	}

	public String getCurrency() {
		return this.currency;
	}

	public Integer getPaymentmodel() {
		return this.paymentmodel;
	}

	public Integer getPaymenttype() {
		return this.paymenttype;
	}

	public String getSupname() {
		return this.supname;
	}

	public String getSupaccount() {
		return this.supaccount;
	}

	public String getSupbank() {
		return this.supbank;
	}

	public String getSupaddress() {
		return this.supaddress;
	}

	public String getAttachment() {
		return this.attachment;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getProcessname() {
		return this.processname;
	}

	public Double getProcessinstanceid() {
		return this.processinstanceid;
	}

	public Double getWfstatus() {
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

	public Date getPaymentdate() {
		return this.paymentdate;
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

	public void setBudgetno(String budgetno) {
		this.budgetno = budgetno;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public void setProcontent(String procontent) {
		this.procontent = procontent;
	}

	public void setBudgetsum(Double budgetsum) {
		this.budgetsum = budgetsum;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setPaymentmodel(Integer paymentmodel) {
		this.paymentmodel = paymentmodel;
	}

	public void setPaymenttype(Integer paymenttype) {
		this.paymenttype = paymenttype;
	}

	public void setSupname(String supname) {
		this.supname = supname;
	}

	public void setSupaccount(String supaccount) {
		this.supaccount = supaccount;
	}

	public void setSupbank(String supbank) {
		this.supbank = supbank;
	}

	public void setSupaddress(String supaddress) {
		this.supaddress = supaddress;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public void setProcessinstanceid(Double processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public void setWfstatus(Double wfstatus) {
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

	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
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
		Budget other = (Budget) obj;
		if (budgetid == null) {
			if (other.budgetid != null)
				return false;
		} else if (!budgetid.equals(other.budgetid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((budgetid == null) ? 0 : budgetid.hashCode());
		return result;
	}

	public Budget jsonToObject(JSONObject jsonObject) {
		return BudgetJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return BudgetJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return BudgetJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}