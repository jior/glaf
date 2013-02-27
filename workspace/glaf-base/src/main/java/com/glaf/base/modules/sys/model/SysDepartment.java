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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;

public class SysDepartment implements Serializable, JSONable {
	private static final long serialVersionUID = -1700125499848402378L;
	private long id;
	private String name;
	private String desc;
	private Date createTime;
	private int sort;
	private String no;
	private String code;
	private String code2;
	private SysTree node;
	private String fincode;
	private long nodeId;
	private SysDepartment parent;
	private Set<SysDeptRole> roles = new HashSet<SysDeptRole>();
	private List<SysDepartment> children = new ArrayList<SysDepartment>();
	private int status = 0;// 是否有效[默认有效]

	public SysDepartment() {

	}

	public void addChild(SysDepartment dept) {
		if (children == null) {
			children = new ArrayList<SysDepartment>();
		}
		dept.setParent(this);
		children.add(dept);
	}

	public SysDepartment getParent() {
		return parent;
	}

	public void setParent(SysDepartment parent) {
		this.parent = parent;
	}

	public List<SysDepartment> getChildren() {
		return children;
	}

	public void setChildren(List<SysDepartment> children) {
		this.children = children;
	}

	public String getCode() {
		return code;
	}

	public String getCode2() {
		return code2;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getDesc() {
		return desc;
	}

	public String getFincode() {
		return fincode;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNo() {
		return no;
	}

	public SysTree getNode() {
		return node;
	}

	public long getNodeId() {
		return nodeId;
	}

	public Set<SysDeptRole> getRoles() {
		return roles;
	}

	public int getSort() {
		return sort;
	}

	public int getStatus() {
		return status;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setFincode(String fincode) {
		this.fincode = fincode;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setNode(SysTree node) {
		this.node = node;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public void setRoles(Set<SysDeptRole> roles) {
		this.roles = roles;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public SysDepartment jsonToObject(JSONObject jsonObject) {
		SysDepartment model = new SysDepartment();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("no")) {
			model.setNo(jsonObject.getString("no"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("code2")) {
			model.setCode2(jsonObject.getString("code2"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("fincode")) {
			model.setFincode(jsonObject.getString("fincode"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		return model;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (desc != null) {
			jsonObject.put("desc", desc);
		}
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		jsonObject.put("sort", sort);
		if (no != null) {
			jsonObject.put("no", no);
		}
		if (code != null) {
			jsonObject.put("code", code);
		}
		if (code2 != null) {
			jsonObject.put("code2", code2);
		}
		jsonObject.put("status", status);
		if (fincode != null) {
			jsonObject.put("fincode", fincode);
		}
		jsonObject.put("nodeId", nodeId);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (desc != null) {
			jsonObject.put("desc", desc);
		}
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		jsonObject.put("sort", sort);
		if (no != null) {
			jsonObject.put("no", no);
		}
		if (code != null) {
			jsonObject.put("code", code);
		}
		if (code2 != null) {
			jsonObject.put("code2", code2);
		}
		jsonObject.put("status", status);
		if (fincode != null) {
			jsonObject.put("fincode", fincode);
		}
		jsonObject.put("nodeId", nodeId);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}