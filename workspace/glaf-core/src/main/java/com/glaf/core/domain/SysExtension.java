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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "SYS_EXTENSION")
public class SysExtension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected Long id;

	/**
	 * 业务标识
	 */
	@Column(name = "BUSINESS_KEY", length = 200, nullable = false)
	protected String businessKey;

	/**
	 * 业务标识
	 */
	@Column(name = "SERVICE_KEY", length = 50, nullable = false)
	protected String serviceKey;

	/**
	 * type_cd(数据类型，取值：STRING, DATE, LONG, DOUBLE)
	 */
	@Column(name = "TYPE_CD", length = 6)
	protected String typeCd;

	@Column(name = "KEY_NAME", length = 100)
	protected String keyName;

	@Column(name = "STRING_VAL", length = 2000)
	protected String stringVal;

	@Lob
	@Column(name = "TEXT_VAL")
	protected String textVal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_VAL")
	protected Date dateVal;

	@Column(name = "INT_VAL")
	protected Integer intVal;

	@Column(name = "LONG_VAL")
	protected Long longVal;

	@Column(name = "DOUBLE_VAL")
	protected Double doubleVal;

	public SysExtension() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysExtension other = (SysExtension) obj;
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

	public Long getId() {
		return this.id;
	}

	public Integer getIntVal() {
		return this.intVal;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public Long getLongVal() {
		return this.longVal;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getStringVal() {
		return this.stringVal;
	}

	public String getTextVal() {
		return textVal;
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

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}

	public void setDoubleVal(Double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIntVal(Integer intVal) {
		this.intVal = intVal;
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

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
