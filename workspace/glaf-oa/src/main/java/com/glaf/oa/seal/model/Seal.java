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
package com.glaf.oa.seal.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.seal.util.*;

@Entity
@Table(name = "oa_seal")
public class Seal implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "sealid", nullable = false)
	protected Long sealid;

	@Column(name = "area", length = 50)
	protected String area;

	@Column(name = "company", length = 100)
	protected String company;

	@Column(name = "dept", length = 100)
	protected String dept;

	@Column(name = "post", length = 100)
	protected String post;

	@Column(name = "appuser", length = 20)
	protected String appuser;

	@Column(name = "sealtype", length = 50)
	protected String sealtype;

	@Column(name = "supplier", length = 100)
	protected String supplier;

	@Column(name = "content", length = 500)
	protected String content;

	@Column(name = "num")
	protected Integer num;

	@Column(name = "remark", length = 500)
	protected String remark;

	@Column(name = "attachment", length = 200)
	protected String attachment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "appdate")
	protected Date appdate;

	@Column(name = "status")
	protected Integer status;

	@Column(name = "processname", length = 100)
	protected String processname;

	@Column(name = "processinstanceid", length = 100)
	protected Double processinstanceid;

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

	@Column(name = "money", length = 10)
	protected Double money;

	public Seal() {

	}

	public Long getSealid() {
		return this.sealid;
	}

	public void setSealid(Long sealid) {
		this.sealid = sealid;
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

	public String getSealtype() {
		return this.sealtype;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public String getContent() {
		return this.content;
	}

	public Integer getNum() {
		return this.num;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getAttachment() {
		return this.attachment;
	}

	public Date getAppdate() {
		return this.appdate;
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

	public void setPost(String post) {
		this.post = post;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public void setSealtype(String sealtype) {
		this.sealtype = sealtype;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
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

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seal other = (Seal) obj;
		if (sealid == null) {
			if (other.sealid != null)
				return false;
		} else if (!sealid.equals(other.sealid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sealid == null) ? 0 : sealid.hashCode());
		return result;
	}

	public Seal jsonToObject(JSONObject jsonObject) {
		return SealJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SealJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SealJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}