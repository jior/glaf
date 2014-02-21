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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "SYS_JOB_EXECUTION_PARAMS")
public class JobExecutionParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	@Column(name = "JOB_EXECUTION_ID")
	protected long jobExecutionId;

	@Column(name = "JOB_INSTANCE_ID")
	protected long jobInstanceId;

	@Column(name = "TYPE_CD", length = 6)
	protected String typeCd;

	@Column(name = "KEY_NAME", length = 100)
	protected String keyName;

	@Column(name = "STRING_VAL", length = 250)
	protected String stringVal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_VAL")
	protected Date dateVal;

	@Column(name = "INT_VAL")
	protected Integer intVal;

	@Column(name = "LONG_VAL")
	protected Long longVal;

	@Column(name = "DOUBLE_VAL")
	protected Double doubleVal;

	public JobExecutionParam() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobExecutionParam other = (JobExecutionParam) obj;
		if (id != other.id)
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

	public long getJobExecutionId() {
		return this.jobExecutionId;
	}

	public long getJobInstanceId() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}

	public void setDoubleVal(Double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIntVal(Integer intVal) {
		this.intVal = intVal;
	}

	public void setJobExecutionId(long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public void setJobInstanceId(long jobInstanceId) {
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
