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
package com.glaf.oa.traveladdress.model;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.traveladdress.util.*;

@Entity
@Table(name = "oa_traveladdress")
public class Traveladdress implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "addressid", nullable = false)
	protected Long addressid;

	@Column(name = "travelid")
	protected Long travelid;

	@Column(name = "startadd", length = 100)
	protected String startadd;

	@Column(name = "endadd", length = 100)
	protected String endadd;

	@Column(name = "transportation", length = 100)
	protected String transportation;

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

	public Traveladdress() {

	}

	public Long getAddressid() {
		return this.addressid;
	}

	public void setAddressid(Long addressid) {
		this.addressid = addressid;
	}

	public Long getTravelid() {
		return this.travelid;
	}

	public String getStartadd() {
		return this.startadd;
	}

	public String getEndadd() {
		return this.endadd;
	}

	public String getTransportation() {
		return this.transportation;
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

	public void setStartadd(String startadd) {
		this.startadd = startadd;
	}

	public void setEndadd(String endadd) {
		this.endadd = endadd;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
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
		Traveladdress other = (Traveladdress) obj;
		if (addressid == null) {
			if (other.addressid != null)
				return false;
		} else if (!addressid.equals(other.addressid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((addressid == null) ? 0 : addressid.hashCode());
		return result;
	}

	public Traveladdress jsonToObject(JSONObject jsonObject) {
		return TraveladdressJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return TraveladdressJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TraveladdressJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}