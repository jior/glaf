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
package com.glaf.oa.travelpersonnel.model;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.travelpersonnel.util.*;

@Entity
@Table(name = "oa_travelpersonnel")
public class Travelpersonnel implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "personnelid", nullable = false)
	protected Long personnelid;

	@Column(name = "travelid")
	protected Long travelid;

	@Column(name = "dept", length = 100)
	protected String dept;

	@Column(name = "personnel", length = 200)
	protected String personnel;

	@Column(name = "remark")
	protected String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	protected Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate")
	protected Date updateDate;

	@Column(name = "createBy", length = 0)
	protected String createBy;

	@Column(name = "updateBy", length = 0)
	protected String updateBy;

	public Travelpersonnel() {

	}

	public Long getPersonnelid() {
		return this.personnelid;
	}

	public void setPersonnelid(Long personnelid) {
		this.personnelid = personnelid;
	}

	public Long getTravelid() {
		return this.travelid;
	}

	public String getDept() {
		return this.dept;
	}

	public String getPersonnel() {
		return this.personnel;
	}

	public String getRemark() {
		return this.remark;
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

	public void setTravelid(Long travelid) {
		this.travelid = travelid;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Travelpersonnel other = (Travelpersonnel) obj;
		if (personnelid == null) {
			if (other.personnelid != null)
				return false;
		} else if (!personnelid.equals(other.personnelid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((personnelid == null) ? 0 : personnelid.hashCode());
		return result;
	}

	public Travelpersonnel jsonToObject(JSONObject jsonObject) {
		return TravelpersonnelJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return TravelpersonnelJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TravelpersonnelJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}