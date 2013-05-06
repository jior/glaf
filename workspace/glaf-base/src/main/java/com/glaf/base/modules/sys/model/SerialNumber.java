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

package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.SerialNumberJsonFactory;
import com.glaf.core.base.JSONable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "SerialNumber")
public class SerialNumber implements Serializable, JSONable {
	private static final long serialVersionUID = 7285967860734876783L;
	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 模块编号
	 */
	@Column(name = "moduleNo", length = 50)
	protected String moduleNo;

	/**
	 * 日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastDate")
	protected Date lastDate;

	/**
	 * 间隔
	 */
	@Column(name = "intervelNo")
	protected int intervelNo;

	/**
	 * 当前序号
	 */
	@Column(name = "currentSerail")
	protected int currentSerail;

	public int getCurrentSerail() {
		return currentSerail;
	}

	public long getId() {
		return id;
	}

	public int getIntervelNo() {
		return intervelNo;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public String getModuleNo() {
		return moduleNo;
	}

	public SerialNumber jsonToObject(JSONObject jsonObject) {
		return SerialNumberJsonFactory.jsonToObject(jsonObject);
	}

	public void setCurrentSerail(int currentSerail) {
		this.currentSerail = currentSerail;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIntervelNo(int intervelNo) {
		this.intervelNo = intervelNo;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SerialNumber other = (SerialNumber) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public JSONObject toJsonObject() {
		return SerialNumberJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SerialNumberJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}