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

import java.io.*;
import java.util.*;
import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.SystemParamJsonFactory;

@Entity
@Table(name = "SYS_PARAMS")
public class SystemParam implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 50, nullable = false)
	protected String id;

	/**
	 * ģ���ʶ��
	 */
	@Column(name = "service_key", length = 50, nullable = false)
	protected String serviceKey;

	/**
	 * ҵ���ʶ
	 */
	@Column(name = "business_key", length = 200, nullable = false)
	protected String businessKey;

	/**
	 * ���
	 */
	@Column(name = "type_cd", length = 20, nullable = false)
	protected String typeCd;

	/**
	 * key����
	 */
	@Column(name = "key_name", length = 50, nullable = false)
	protected String keyName;

	/**
	 * ����
	 */
	@Column(name = "title", length = 200)
	protected String title;

	/**
	 * ��������
	 */
	@Column(name = "java_type", length = 20, nullable = false)
	protected String javaType;

	/**
	 * �ַ���ֵ
	 */
	@Column(name = "string_val", length = 2000)
	protected String stringVal;

	/**
	 * �����ı�ֵ
	 */
	@Lob
	@Column(name = "text_val")
	protected String textVal;

	/**
	 * ����ֵ
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_val")
	protected Date dateVal;

	/**
	 * ����ֵ
	 */
	@Column(name = "int_val")
	protected Integer intVal;

	/**
	 * ������ֵ
	 */
	@Column(name = "long_val")
	protected Long longVal;

	/**
	 * ˫������ֵ
	 */
	@Column(name = "double_val")
	protected Double doubleVal;

	public SystemParam() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemParam other = (SystemParam) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public Date getDateVal() {
		return this.dateVal;
	}

	public Double getDoubleVal() {
		return this.doubleVal;
	}

	public String getId() {
		return id;
	}

	public Integer getIntVal() {
		return intVal;
	}

	public String getJavaType() {
		return javaType;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public Long getLongVal() {
		return this.longVal;
	}

	public String getServiceKey() {
		return this.serviceKey;
	}

	public String getStringVal() {
		return this.stringVal;
	}

	public String getTextVal() {
		return textVal;
	}

	public String getTitle() {
		return title;
	}

	public String getTypeCd() {
		return this.typeCd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SystemParam jsonToObject(JSONObject jsonObject) {
		return SystemParamJsonFactory.jsonToObject(jsonObject);
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}

	public void setDoubleVal(Double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIntVal(Integer intVal) {
		this.intVal = intVal;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}

	public void setTextVal(String textVal) {
		this.textVal = textVal;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public JSONObject toJsonObject() {
		return SystemParamJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SystemParamJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}