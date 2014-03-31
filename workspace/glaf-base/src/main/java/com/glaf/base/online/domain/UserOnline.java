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

package com.glaf.base.online.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.base.online.util.*;

@Entity
@Table(name = "SYS_USER_ONLINE")
public class UserOnline implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "ACTORID_", length = 50)
	protected String actorId;

	@Column(name = "NAME_", length = 50)
	protected String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGINDATE_")
	protected Date loginDate;

	@Column(name = "LOGINIP_", length = 100)
	protected String loginIP;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECKDATE_")
	protected Date checkDate;

	@Column(name = "CHECKDATEMS_")
	protected Long checkDateMs;

	@Column(name = "SESSIONID_", length = 200)
	protected String sessionId;

	public UserOnline() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserOnline other = (UserOnline) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getActorId() {
		return this.actorId;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public Long getCheckDateMs() {
		return checkDateMs;
	}

	public Long getId() {
		return this.id;
	}

	public Date getLoginDate() {
		return this.loginDate;
	}

	public String getLoginIP() {
		return this.loginIP;
	}

	public String getName() {
		return this.name;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public UserOnline jsonToObject(JSONObject jsonObject) {
		return UserOnlineJsonFactory.jsonToObject(jsonObject);
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public void setCheckDateMs(Long checkDateMs) {
		this.checkDateMs = checkDateMs;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public JSONObject toJsonObject() {
		return UserOnlineJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return UserOnlineJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
