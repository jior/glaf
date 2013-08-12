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

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.borrow.util.*;

@Entity
@Table(name = "oa_borrow")
public class Borrow implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "borrowid", nullable = false)
	protected Long borrowid;

	@Column(name = "borrowNo", length = 50)
	protected String borrowNo;

	@Column(name = "area", length = 20)
	protected String area;

	@Column(name = "company", length = 100)
	protected String company;

	@Column(name = "dept", length = 100)
	protected String dept;

	@Column(name = "appuser", length = 20)
	protected String appuser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "appdate")
	protected Date appdate;

	@Column(name = "post", length = 100)
	protected String post;

	@Column(name = "content", length = 2147483647)
	protected String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startdate")
	protected Date startdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "enddate")
	protected Date enddate;

	@Column(name = "daynum")
	protected Integer daynum;

	@Column(name = "details", length = 2147483647)
	protected String details;

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

	protected Date wdStatusFlag;

	public Borrow() {

	}

	public Date getWdStatusFlag() {
		return wdStatusFlag;
	}

	public void setWdStatusFlag(Date wdStatusFlag) {
		this.wdStatusFlag = wdStatusFlag;
	}

	public Long getBorrowid() {
		return this.borrowid;
	}

	public void setBorrowid(Long borrowid) {
		this.borrowid = borrowid;
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

	public String getAppuser() {
		return this.appuser;
	}

	public Date getAppdate() {
		return this.appdate;
	}

	public String getPost() {
		return this.post;
	}

	public String getContent() {
		return this.content;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public Integer getDaynum() {
		return this.daynum;
	}

	public String getDetails() {
		return this.details;
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

	public void setCompany(String company) {
		this.company = company;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public void setDaynum(Integer daynum) {
		this.daynum = daynum;
	}

	public void setDetails(String details) {
		this.details = details;
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

	public String getBorrowNo() {
		return borrowNo;
	}

	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrow other = (Borrow) obj;
		if (borrowid == null) {
			if (other.borrowid != null)
				return false;
		} else if (!borrowid.equals(other.borrowid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((borrowid == null) ? 0 : borrowid.hashCode());
		return result;
	}

	public Borrow jsonToObject(JSONObject jsonObject) {
		return BorrowJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return BorrowJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return BorrowJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}