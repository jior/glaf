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
 

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
 
public class Todo implements java.io.Serializable, Comparable<Todo> {

	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	protected String id;

	/**
	 * ����
	 */
	protected String code;

	/**
	 * ģ����
	 */
	protected String moduleId;

	/**
	 * ģ������
	 */
	protected String moduleName;

	/**
	 * ��ɫ���
	 */
	protected String roleId;

	/**
	 * ��ɫ����
	 */
	protected String roleName;

	/**
	 * ����
	 */
	protected double limitDay;

	/**
	 * a Сʱ
	 */
	protected int xa;

	/**
	 * Сʱ
	 */
	protected int xb;

	/**
	 * ����
	 */
	protected String title;

	/**
	 * ����
	 */
	protected String content;

	/**
	 * ���ӵ�ַ
	 */
	protected String link;

	/**
	 * �б����ӵ�ַ
	 */
	protected String listLink;

	/**
	 * ȫ�����ݵ����ӵ�ַ
	 */
	protected String allListLink;

	/**
	 * �������
	 */
	protected String linkType;

	/**
	 * ��������
	 */
	protected String processName;

	/**
	 * ��������
	 */
	protected String taskName;

	/**
	 * TODO�ṩ��
	 */
	protected String provider;

	/**
	 * ������
	 */
	protected String objectId;

	/**
	 * ����ֵ
	 */
	protected String objectValue;

	/**
	 * �Ƿ����á�0�����á�1������
	 */
	protected int locked;

	protected int configFlag;

	protected int sortNo;

	protected long versionNo;

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

	public String getCode() {
		return code;
	}

	public int getConfigFlag() {
		return configFlag;
	}

	public String getContent() {
		return content;
	}

	public String getId() {
		return id;
	}

	public double getLimitDay() {
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

	public int getLocked() {
		return locked;
	}

	public String getModuleId() {
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

	public String getProcessName() {
		return processName;
	}

	public String getProvider() {
		return provider;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public int getSortNo() {
		return sortNo;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTitle() {
		return title;
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
			model.setModuleId(jsonObject.getString("moduleId"));
		}
		if (jsonObject.containsKey("moduleName")) {
			model.setModuleName(jsonObject.getString("moduleName"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getString("roleId"));
		}
		if (jsonObject.containsKey("roleName")) {
			model.setRoleName(jsonObject.getString("roleName"));
		}
		if (jsonObject.containsKey("limitDay")) {
			model.setLimitDay(jsonObject.getDouble("limitDay"));
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
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
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

	public void setCode(String code) {
		this.code = code;
	}

	public void setConfigFlag(int configFlag) {
		this.configFlag = configFlag;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLimitDay(double limitDay) {
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

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setModuleId(String moduleId) {
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

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTitle(String title) {
		this.title = title;
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
		jsonObject.put("locked", locked);
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
		jsonObject.put("locked", locked);
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