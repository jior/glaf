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
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.batch.util.*;

@Entity
@Table(name = "SYS_JOB_STEP_DEF_PARAMS")
public class JobStepDefinitionParam implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected Long id;

	@Column(name = "JOB_DEFINITION_ID")
	protected Long jobDefinitionId;

	@Column(name = "STEP_DEFINITION_ID")
	protected Long stepDefinitionId;

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

	public JobStepDefinitionParam() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobStepDefinitionParam other = (JobStepDefinitionParam) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public Long getJobDefinitionId() {
		return this.jobDefinitionId;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public Long getLongVal() {
		return this.longVal;
	}

	public Long getStepDefinitionId() {
		return this.stepDefinitionId;
	}

	public String getStringVal() {
		return this.stringVal;
	}

	public String getTextVal() {
		return this.textVal;
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

	public JobStepDefinitionParam jsonToObject(JSONObject jsonObject) {
		return JobStepDefinitionParamJsonFactory.jsonToObject(jsonObject);
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

	public void setJobDefinitionId(Long jobDefinitionId) {
		this.jobDefinitionId = jobDefinitionId;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}

	public void setStepDefinitionId(Long stepDefinitionId) {
		this.stepDefinitionId = stepDefinitionId;
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

	public JSONObject toJsonObject() {
		return JobStepDefinitionParamJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return JobStepDefinitionParamJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
