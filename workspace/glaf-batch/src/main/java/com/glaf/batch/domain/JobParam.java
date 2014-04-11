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

package com.glaf.batch.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.batch.util.JobParamJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "SYS_JOB_PARAMS")
public class JobParam implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	protected Long id;

	/**
	 * job_instance_id
	 */
	@Column(name = "job_instance_id")
	protected Long jobInstanceId;

	/**
	 * type_cd(数据类型，取值：STRING, DATE, LONG, DOUBLE)
	 */
	@Column(name = "type_cd", length = 6)
	protected String typeCd;

	/**
	 * key_name
	 */
	@Column(name = "key_name", length = 100)
	protected String keyName;

	/**
	 * string_val
	 */
	@Column(name = "string_val", length = 250)
	protected String stringVal;

	/**
	 * date_val
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_val")
	protected Date dateVal;

	/**
	 * long_val
	 */
	@Column(name = "int_val")
	protected Integer intVal;

	/**
	 * long_val
	 */
	@Column(name = "long_val")
	protected Long longVal;

	/**
	 * double_val
	 */
	@Column(name = "double_val")
	protected Double doubleVal;

	public JobParam() {

	}

	public Date getDateVal() {
		return this.dateVal;
	}

	public Double getDoubleVal() {
		return this.doubleVal;
	}

	public Long getId() {
		return id;
	}

	public Integer getIntVal() {
		return intVal;
	}

	public Long getJobInstanceId() {
		return this.jobInstanceId;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public Long getLongVal() {
		return this.longVal;
	}

	public String getStringVal() {
		return this.stringVal;
	}

	public String getTypeCd() {
		return this.typeCd;
	}

	public JobParam jsonToObject(JSONObject jsonObject) {
		return JobParamJsonFactory.jsonToObject(jsonObject);
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

	public void setJobInstanceId(Long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public JSONObject toJsonObject() {
		return JobParamJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return JobParamJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}