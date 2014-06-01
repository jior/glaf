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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.SysLogJsonFactory;

@Entity
@Table(name = "SYS_LOG")
public class SysLog implements Serializable, JSONable {
	private static final long serialVersionUID = 3489584842305336744L;

	@Id
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "ACCOUNT", length = 50)
	private String account;

	@Column(name = "IP", length = 200)
	private String ip;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME")
	private Date createTime;

	@Column(name = "MODULEID", length = 50)
	private String moduleId;

	@Column(name = "OPERATE", length = 50)
	private String operate;

	@Column(name = "CONTENT", length = 2000)
	private String content;

	@Column(name = "FLAG")
	private int flag;

	@Column(name = "TIMEMS")
	private int timeMS;

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysLog other = (SysLog) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getAccount() {
		return account;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public int getFlag() {
		return flag;
	}

	public long getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getModuleId() {
		return moduleId;
	}

	public String getOperate() {
		return operate;
	}

	public int getTimeMS() {
		return timeMS;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public SysLog jsonToObject(JSONObject jsonObject) {
		return SysLogJsonFactory.jsonToObject(jsonObject);
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public void setTimeMS(int timeMS) {
		this.timeMS = timeMS;
	}

	public JSONObject toJsonObject() {
		return SysLogJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysLogJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}