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

import java.io.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.base.modules.sys.util.*;

@Entity
@Table(name = "SYS_GROUP")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GROUPID", length = 50, nullable = false)
	protected String groupId;

	/**
	 * ������
	 */
	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	/**
	 * ����
	 */
	@Column(name = "GROUPDESC", length = 500)
	protected String desc;

	/**
	 * ����
	 */
	@Column(name = "NAME", length = 200)
	protected String name;

	/**
	 * ˳���
	 */
	@Column(name = "SORT")
	protected Integer sort;

	/**
	 * ����
	 */
	@Column(name = "TYPE", length = 50)
	protected String type;

	public Group() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public String getName() {
		return this.name;
	}

	public Integer getSort() {
		return this.sort;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		return result;
	}

	public Group jsonToObject(JSONObject jsonObject) {
		return GroupJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject toJsonObject() {
		return GroupJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return GroupJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
