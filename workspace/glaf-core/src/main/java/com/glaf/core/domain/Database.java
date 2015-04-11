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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.core.domain.util.*;

/**
 * 
 * 实体对象
 * 
 */

@Entity
@Table(name = "SYS_DATABASE")
public class Database implements java.lang.Comparable<Database>, Serializable,
		JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	@Column(name = "NODEID_")
	protected Long nodeId;

	@Column(name = "NAME_", length = 200)
	protected String name;

	@Column(name = "CODE_", length = 50)
	protected String code;

	@Column(name = "TITLE_", length = 100)
	protected String title;

	@Column(name = "HOST_", length = 100)
	protected String host;

	@Column(name = "PORT_")
	protected Integer port;

	@Column(name = "USER_", length = 50)
	protected String user;

	@Column(name = "PASSWORD_", length = 100)
	protected String password;

	@Column(name = "KEY_", length = 1024)
	protected String key;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "LEVEL_")
	protected int level = 0;

	@Column(name = "PRIORITY_")
	protected int priority = 0;

	/**
	 * 读写操作，支持存储库读写分离<br/>
	 * 0-只读 <br/>
	 * 1-只写 <br/>
	 * 2-读写<br/>
	 */
	@Column(name = "OPERATION_")
	protected int operation = 2;

	@Column(name = "DBNAME_", length = 50)
	protected String dbname;

	@Column(name = "ACTIVE_", length = 10)
	protected String active;

	@Column(name = "VERIFY_", length = 10)
	protected String verify;

	@Column(name = "INITFLAG_", length = 10)
	protected String initFlag;

	@Column(name = "PROVIDERCLASS_", length = 100)
	protected String providerClass;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	@javax.persistence.Transient
	protected String connectionString;

	@javax.persistence.Transient
	protected Collection<String> actorIds = new HashSet<String>();

	@javax.persistence.Transient
	protected List<DatabaseAccess> accesses = new ArrayList<DatabaseAccess>();

	public Database() {

	}

	public void addAccessor(String actorId) {
		if (actorIds == null) {
			actorIds = new HashSet<String>();
		}
		actorIds.add(actorId);
	}

	public int compareTo(Database other) {
		if (other == null) {
			return -1;
		}

		Database field = other;

		int l = this.priority - field.getPriority();

		int ret = 0;

		if (l > 0) {
			ret = -1;
		} else if (l < 0) {
			ret = 1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Database other = (Database) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public List<DatabaseAccess> getAccesses() {
		return accesses;
	}

	public String getActive() {
		return this.active;
	}

	public Collection<String> getActorIds() {
		return actorIds;
	}

	public String getCode() {
		return code;
	}

	public String getConnectionString() {
		return this.connectionString;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getDbname() {
		return dbname;
	}

	public String getHost() {
		return this.host;
	}

	public long getId() {
		return this.id;
	}

	public String getInitFlag() {
		return initFlag;
	}

	public String getKey() {
		return key;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public int getOperation() {
		return operation;
	}

	public String getPassword() {
		return this.password;
	}

	public Integer getPort() {
		return this.port;
	}

	public int getPriority() {
		return priority;
	}

	public String getProviderClass() {
		return providerClass;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return this.type;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getUser() {
		return this.user;
	}

	public String getVerify() {
		return verify;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public Database jsonToObject(JSONObject jsonObject) {
		return DatabaseJsonFactory.jsonToObject(jsonObject);
	}

	public void setAccesses(List<DatabaseAccess> accesses) {
		this.accesses = accesses;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setActorIds(Collection<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInitFlag(String initFlag) {
		this.initFlag = initFlag;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setProviderClass(String providerClass) {
		this.providerClass = providerClass;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public JSONObject toJsonObject() {
		return DatabaseJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DatabaseJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
