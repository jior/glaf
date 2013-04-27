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
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.DynamicAccess;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.DynamicAccessEntityJsonFactory;

@Entity
@Table(name = "SYS_DYNAMICACCESS")
public class DynamicAccessEntity implements DynamicAccess, JSONable {

	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * �����ʶ
	 */
	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	/**
	 * ʵ�����ͣ�mybatis3��jpa��
	 */
	@Column(name = "ENTITYTYPE_", length = 10)
	protected String entityType;

	/**
	 * ����SQL
	 */
	@Column(name = "FILTERSQL_", length = 500)
	protected String filterSql;

	/**
	 * �������������޸ģ�ɾ�����鿴����ˣ���ӡ��������
	 */
	@Column(name = "OPERATION_", length = 50)
	private String operation;

	/**
	 * ����Ŀ�꣨�û������š���ɫ���ϼ���
	 */
	@Column(name = "TARGET_", length = 50)
	protected String target;

	/**
	 * ����Ŀ������<br/>
	 * 0-�û�<br/>
	 * 1-����<br/>
	 * 2-��ɫ<br/>
	 * 3-�ϼ�<br/>
	 */
	@Column(name = "TARGETTYPE_")
	protected int targetType;

	/**
	 * ��չ�ֶ�-���
	 */
	@Column(name = "OBJECTID_")
	protected String objectId;

	/**
	 * ��չ�ֶ�-ֵ
	 */
	@Column(name = "OBJECTVALUE_")
	protected String objectValue;

	public DynamicAccessEntity() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DynamicAccessEntity other = (DynamicAccessEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getEntityType() {
		return entityType;
	}

	public String getFilterSql() {
		return filterSql;
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

	public DynamicAccessEntity jsonToObject(JSONObject jsonObject) {
		return DynamicAccessEntityJsonFactory.jsonToObject(jsonObject);
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setFilterSql(String filterSql) {
		this.filterSql = filterSql;
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
		return DynamicAccessEntityJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DynamicAccessEntityJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}