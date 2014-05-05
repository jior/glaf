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

package com.glaf.core.todo;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "SYS_TODO")
public class Todo implements java.io.Serializable, Comparable<Todo>, JSONable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id", nullable = false)
	protected Long id;

	/**
	 * 代码
	 */
	@Column(name = "code", length = 50)
	protected String code;

	/**
	 * 模块编号
	 */
	@Column(name = "moduleId", length = 50)
	protected Long moduleId;

	/**
	 * 模块名称
	 */
	@Column(name = "moduleName", length = 50)
	protected String moduleName;

	/**
	 * 角色编号
	 */
	@Column(name = "roleId")
	protected Long roleId;

	@Column(name = "roleCode", length = 50)
	protected String roleCode;

	/**
	 * 角色名称
	 */
	@Column(name = "roleName", length = 50)
	protected String roleName;

	@Column(name = "appId")
	protected Long appId;

	@Column(name = "deptId")
	protected Long deptId;

	@javax.persistence.Transient
	protected String deptName;

	/**
	 * 期限
	 */
	@Column(name = "limitDay")
	protected int limitDay;

	/**
	 * a 小时
	 */
	@Column(name = "xa")
	protected int xa;

	/**
	 * 小时
	 */
	@Column(name = "xb")
	protected int xb;

	/**
	 * 标题
	 */
	@Column(name = "title")
	protected String title;

	/**
	 * 内容
	 */
	@Column(name = "content")
	protected String content;

	/**
	 * 链接地址
	 */
	@Column(name = "link")
	protected String link;

	/**
	 * 列表链接地址
	 */
	@Column(name = "listLink")
	protected String listLink;

	/**
	 * 全部数据的链接地址
	 */
	@Column(name = "allListLink")
	protected String allListLink;

	/**
	 * 链接类别
	 */
	@Column(name = "linkType", length = 50)
	protected String linkType;

	/**
	 * 流程名称
	 */
	@Column(name = "processName", length = 50)
	protected String processName;

	/**
	 * 任务名称
	 */
	@Column(name = "taskName", length = 50)
	protected String taskName;

	/**
	 * TODO提供者
	 */
	@Column(name = "provider", length = 50)
	protected String provider;

	/**
	 * 对象编号
	 */
	@Column(name = "objectId")
	protected String objectId;

	/**
	 * 对象值
	 */
	@Column(name = "objectValue")
	protected String objectValue;

	/**
	 * 是否启用。0－启用、1－禁用
	 */
	@Column(name = "enableFlag")
	protected int enableFlag;

	@Column(name = "configFlag")
	protected int configFlag;

	@Column(name = "sortNo")
	protected int sortNo;

	@Column(name = "versionNo")
	protected long versionNo;

	@Column(name = "type", length = 50)
	protected String type;

	@Lob
	@Column(name = "sql_", length = 2000)
	protected String sql;

	@javax.persistence.Transient
	protected int totalQty;

	@javax.persistence.Transient
	protected Collection<Object> ok = new HashSet<Object>();

	@javax.persistence.Transient
	protected Collection<Object> caution = new HashSet<Object>();

	@javax.persistence.Transient
	protected Collection<Object> pastDue = new HashSet<Object>();

	public Todo() {

	}

	public int compareTo(Todo o) {
		if (o == null) {
			return -1;
		}

		Todo obj = o;

		int l = this.sortNo - obj.getSortNo();

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
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
		Todo other = (Todo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAllListLink() {
		return allListLink;
	}

	public Long getAppId() {
		return appId;
	}

	public Collection<Object> getCaution() {
		return caution;
	}

	public String getCode() {
		return code;
	}

	public int getConfigFlag() {
		return configFlag;
	}

	public String getContent() {
		return content;
	}

	public Long getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public int getEnableFlag() {
		return enableFlag;
	}

	public Long getId() {
		return id;
	}

	public int getLimitDay() {
		return limitDay;
	}

	public String getLink() {
		return link;
	}

	public String getLinkType() {
		return linkType;
	}

	public String getListLink() {
		return listLink;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public Collection<Object> getOk() {
		return ok;
	}

	public Collection<Object> getPastDue() {
		return pastDue;
	}

	public String getProcessName() {
		return processName;
	}

	public String getProvider() {
		return provider;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public Long getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public int getSortNo() {
		return sortNo;
	}

	public String getSql() {
		return sql;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTitle() {
		return title;
	}

	public int getTotalQty() {
		return totalQty;
	}

	public String getType() {
		return type;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public int getXa() {
		return xa;
	}

	public int getXb() {
		return xb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Todo jsonToObject(JSONObject jsonObject) {
		Todo model = new Todo();
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("moduleId")) {
			model.setModuleId(jsonObject.getLong("moduleId"));
		}
		if (jsonObject.containsKey("moduleName")) {
			model.setModuleName(jsonObject.getString("moduleName"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}
		if (jsonObject.containsKey("roleName")) {
			model.setRoleName(jsonObject.getString("roleName"));
		}
		if (jsonObject.containsKey("limitDay")) {
			model.setLimitDay(jsonObject.getInteger("limitDay"));
		}
		if (jsonObject.containsKey("xa")) {
			model.setXa(jsonObject.getInteger("xa"));
		}
		if (jsonObject.containsKey("xb")) {
			model.setXb(jsonObject.getInteger("xb"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("link")) {
			model.setLink(jsonObject.getString("link"));
		}
		if (jsonObject.containsKey("listLink")) {
			model.setListLink(jsonObject.getString("listLink"));
		}
		if (jsonObject.containsKey("allListLink")) {
			model.setAllListLink(jsonObject.getString("allListLink"));
		}
		if (jsonObject.containsKey("linkType")) {
			model.setLinkType(jsonObject.getString("linkType"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("provider")) {
			model.setProvider(jsonObject.getString("provider"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getInteger("enableFlag"));
		}
		if (jsonObject.containsKey("configFlag")) {
			model.setConfigFlag(jsonObject.getInteger("configFlag"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("versionNo")) {
			model.setVersionNo(jsonObject.getLong("versionNo"));
		}
		return model;
	}

	public void setAllListLink(String allListLink) {
		this.allListLink = allListLink;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setCaution(Collection<Object> caution) {
		this.caution = caution;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setConfigFlag(int configFlag) {
		this.configFlag = configFlag;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setEnableFlag(int enableFlag) {
		this.enableFlag = enableFlag;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLimitDay(int limitDay) {
		this.limitDay = limitDay;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public void setListLink(String listLink) {
		this.listLink = listLink;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setOk(Collection<Object> ok) {
		this.ok = ok;
	}

	public void setPastDue(Collection<Object> pastDue) {
		this.pastDue = pastDue;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public void setXa(int xa) {
		this.xa = xa;
	}

	public void setXb(int xb) {
		this.xb = xb;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (code != null) {
			jsonObject.put("code", code);
		}
		if (moduleId != null) {
			jsonObject.put("moduleId", moduleId);
		}
		if (moduleName != null) {
			jsonObject.put("moduleName", moduleName);
		}
		if (roleId != null) {
			jsonObject.put("roleId", roleId);
		}
		if (roleName != null) {
			jsonObject.put("roleName", roleName);
		}
		jsonObject.put("limitDay", limitDay);
		jsonObject.put("xa", xa);
		jsonObject.put("xb", xb);
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		if (link != null) {
			jsonObject.put("link", link);
		}
		if (listLink != null) {
			jsonObject.put("listLink", listLink);
		}
		if (allListLink != null) {
			jsonObject.put("allListLink", allListLink);
		}
		if (linkType != null) {
			jsonObject.put("linkType", linkType);
		}
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (provider != null) {
			jsonObject.put("provider", provider);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		jsonObject.put("enableFlag", enableFlag);
		jsonObject.put("configFlag", configFlag);
		jsonObject.put("sortNo", sortNo);
		jsonObject.put("versionNo", versionNo);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (code != null) {
			jsonObject.put("code", code);
		}
		if (moduleId != null) {
			jsonObject.put("moduleId", moduleId);
		}
		if (moduleName != null) {
			jsonObject.put("moduleName", moduleName);
		}
		if (roleId != null) {
			jsonObject.put("roleId", roleId);
		}
		if (roleName != null) {
			jsonObject.put("roleName", roleName);
		}
		jsonObject.put("limitDay", limitDay);
		jsonObject.put("xa", xa);
		jsonObject.put("xb", xb);
		if (title != null) {
			jsonObject.put("title", title);
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		if (link != null) {
			jsonObject.put("link", link);
		}
		if (listLink != null) {
			jsonObject.put("listLink", listLink);
		}
		if (allListLink != null) {
			jsonObject.put("allListLink", allListLink);
		}
		if (linkType != null) {
			jsonObject.put("linkType", linkType);
		}
		if (processName != null) {
			jsonObject.put("processName", processName);
		}
		if (taskName != null) {
			jsonObject.put("taskName", taskName);
		}
		if (provider != null) {
			jsonObject.put("provider", provider);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		jsonObject.put("enableFlag", enableFlag);
		jsonObject.put("configFlag", configFlag);
		jsonObject.put("sortNo", sortNo);
		jsonObject.put("versionNo", versionNo);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}