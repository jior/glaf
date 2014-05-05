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

package com.glaf.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.DataAccess;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.DataAccessEntityJsonFactory;

@Entity
@Table(name = "SYS_DATAACCESS")
public class DataAccessEntity implements DataAccess, JSONable {

	private static final long serialVersionUID = 1L;

	/**
	 * 基础代码
	 */
	@Column(name = "BASECODE_", length = 100)
	private String baseCode;

	/**
	 * 数据实例BUSINESSKEY
	 */
	@Column(name = "BUSINESSKEY_", length = 50)
	protected String businessKey;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "OBJECTID_")
	protected String objectId;

	@Column(name = "OBJECTVALUE_")
	protected String objectValue;

	/**
	 * 操作（新增，修改，删除，查看，审核，打印，导出）
	 */
	@Column(name = "OPERATION_", length = 100)
	private String operation;

	/**
	 * 服务标识
	 */
	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	/**
	 * 访问目标（用户、部门、角色或上级）
	 */
	@Column(name = "TARGET_", length = 50)
	protected String target;

	/**
	 * 访问目标类型<br/>
	 * 0-用户<br/>
	 * 1-部门<br/>
	 * 2-角色<br/>
	 * 3-上级<br/>
	 */
	@Column(name = "TARGETTYPE_")
	protected int targetType;

	public DataAccessEntity() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataAccessEntity other = (DataAccessEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public String getId() {
		return id;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getOperation() {
		return operation;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getTarget() {
		return target;
	}

	public int getTargetType() {
		return targetType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public DataAccessEntity jsonToObject(JSONObject jsonObject) {
		return DataAccessEntityJsonFactory.jsonToObject(jsonObject);
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	public JSONObject toJsonObject() {
		return DataAccessEntityJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DataAccessEntityJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}