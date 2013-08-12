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
package com.glaf.oa.stravel.model;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.stravel.util.*;

@Entity
@Table(name = "oa_stravel")
public class Stravel implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "travelid", nullable = false)
	protected Long travelid;

	@Column(name = "area")
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startdate")
	protected Date startdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "enddate")
	protected Date enddate;

	@Column(name = "travelsum")
	protected Double travelsum;

	@Column(name = "content")
	protected String content;

	@Column(name = "traveltype")
	protected Integer traveltype;

	@Column(name = "others")
	protected String others;

	@Column(name = "status")
	protected Integer status;

	@Column(name = "processname", length = 100)
	protected String processname;

	@Column(name = "processinstanceid")
	protected Long processinstanceid;

	@Column(name = "wfstatus")
	protected Integer wfstatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	protected Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate")
	protected Date updateDate;

	@Column(name = "createBy", length = 50)
	protected String createBy;

	@Column(name = "updateBy", length = 50)
	protected String updateBy;

	@javax.persistence.Transient
	protected Double feesumAccount;

	public Stravel() {

	}

	public Long getTravelid() {
		return this.travelid;
	}

	public void setTravelid(Long travelid) {
		this.travelid = travelid;
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

	public Date getStartdate() {
		return this.startdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public Double getTravelsum() {
		return this.travelsum;
	}

	public String getContent() {
		return this.content;
	}

	public Integer getTraveltype() {
		return this.traveltype;
	}

	public String getOthers() {
		return this.others;
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

	public Integer getWfstatus() {
		return this.wfstatus;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getCreateBy() {
		return this.createBy;
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

	public void setPost(String post) {
		this.post = post;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public void setTravelsum(Double travelsum) {
		this.travelsum = travelsum;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTraveltype(Integer traveltype) {
		this.traveltype = traveltype;
	}

	public void setOthers(String others) {
		this.others = others;
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

	public void setWfstatus(Integer wfstatus) {
		this.wfstatus = wfstatus;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Double getFeesumAccount() {
		return feesumAccount;
	}

	public void setFeesumAccount(Double feesumAccount) {
		this.feesumAccount = feesumAccount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stravel other = (Stravel) obj;
		if (travelid == null) {
			if (other.travelid != null)
				return false;
		} else if (!travelid.equals(other.travelid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((travelid == null) ? 0 : travelid.hashCode());
		return result;
	}

	public Stravel jsonToObject(JSONObject jsonObject) {
		return StravelJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return StravelJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return StravelJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}