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
package com.glaf.oa.optional.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.oa.optional.util.*;

@Entity
@Table(name = "OA_OPTIONAL")
public class Optional implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OPTIONALID", nullable = false)
	protected Integer optionalId;

	@Column(name = "ID")
	protected Integer id;

	@Column(name = "CODE", length = 50)
	protected String code;

	@Column(name = "PRICE", length = 0)
	protected Double price;

	@Column(name = "REMARK", length = 0)
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

	public Optional() {

	}

	public Integer getOptionalId() {
		return this.optionalId;
	}

	public void setOptionalId(Integer optionalId) {
		this.optionalId = optionalId;
	}

	public Integer getId() {
		return this.id;
	}

	public String getCode() {
		return this.code;
	}

	public Double getPrice() {
		return this.price;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setPrice(Double price) {
		this.price = price;
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
		Optional other = (Optional) obj;
		if (optionalId == null) {
			if (other.optionalId != null)
				return false;
		} else if (!optionalId.equals(other.optionalId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((optionalId == null) ? 0 : optionalId.hashCode());
		return result;
	}

	public Optional jsonToObject(JSONObject jsonObject) {
		return OptionalJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return OptionalJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return OptionalJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}