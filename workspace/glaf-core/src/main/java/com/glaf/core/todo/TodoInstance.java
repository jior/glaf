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

import java.util.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
 

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TodoInstance implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	private long id;

	/**
	 * TODO����
	 */
	private long todoId;

	/**
	 * Ӧ�ñ��
	 */
	private long appId;

	/**
	 * ģ����
	 */
	private long moduleId;

	/**
	 * �����߱�Ż��ɫ����
	 */
	private String actorId;

	/**
	 * ����������
	 */
	private String actorName;

	/**
	 * ��ɫ���
	 */
	private long roleId;

	/**
	 * ��ɫ����
	 */
	private String roleCode;

	/**
	 * ��ɫ����
	 */
	private String roleName;

	/**
	 * ���ű��
	 */
	private long deptId;

	/**
	 * ��������
	 */
	private String deptName;

	/**
	 * ����
	 */
	private String title;

	/**
	 * ����
	 */
	private String content;

	/**
	 * ���ӵ�ַ
	 */
	private String link;

	/**
	 * �б�����ӵ�ַ
	 */
	private String listLink;

	/**
	 * ��������
	 */
	private String linkType;

	/**
	 * ��ʼ����
	 */
	private Date startDate;

	/**
	 * ��������
	 */
	private Date endDate;

	/**
	 * ��������
	 */
	private Date alarmDate;

	/**
	 * ����ʱ��
	 */
	private Date pastDueDate;

	/**
	 * ��¼���
	 */
	private String rowId;

	/**
	 * ����ʵ�����
	 */
	private String processInstanceId;

	/**
	 * ����ʵ�����
	 */
	private String taskInstanceId;

	/**
	 * ��������
	 */
	private Date createDate;

	/**
	 * TODO�ṩ��
	 */
	private String provider;

	/**
	 * ������
	 */
	private String objectId;

	/**
	 * ����ֵ
	 */
	private String objectValue;

	/**
	 * OK
	 */
	private int level01;

	/**
	 * Caution
	 */
	private int level02;

	/**
	 * Past Due
	 */
	private int level03;

	/**
	 * OK
	 */
	private int qty01;

	/**
	 * Caution
	 */
	private int qty02;

	/**
	 * Past Due
	 */
	private int qty03;

	private int qtyRedWarn;

	/**
	 * ״̬
	 */
	private int status;

	private long versionNo;

	private Todo todo;

	public TodoInstance() {

	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TodoInstance other = (TodoInstance) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getActorId() {
		return actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public Date getAlarmDate() {
		return alarmDate;
	}

	public long getAppId() {
		return appId;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public long getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public long getId() {
		return id;
	}

	public int getLevel01() {
		return level01;
	}

	public int getLevel02() {
		return level02;
	}

	public int getLevel03() {
		return level03;
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

	public long getModuleId() {
		return moduleId;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public Date getPastDueDate() {
		return pastDueDate;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProvider() {
		return provider;
	}

	public int getQty01() {
		return qty01;
	}

	public int getQty02() {
		return qty02;
	}

	public int getQty03() {
		return qty03;
	}

	public int getQtyRedWarn() {
		return qtyRedWarn;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public long getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRowId() {
		if (rowId != null) {
			rowId = rowId.trim();
		}
		return rowId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public int getStatus() {
		return status;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public String getTitle() {
		return title;
	}

	public Todo getTodo() {
		return todo;
	}

	public long getTodoId() {
		return todoId;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel01(int level01) {
		this.level01 = level01;
	}

	public void setLevel02(int level02) {
		this.level02 = level02;
	}

	public void setLevel03(int level03) {
		this.level03 = level03;
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

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setPastDueDate(Date pastDueDate) {
		this.pastDueDate = pastDueDate;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setQty01(int qty01) {
		this.qty01 = qty01;
	}

	public void setQty02(int qty02) {
		this.qty02 = qty02;
	}

	public void setQty03(int qty03) {
		this.qty03 = qty03;
	}

	public void setQtyRedWarn(int qtyRedWarn) {
		this.qtyRedWarn = qtyRedWarn;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}

	public void setTodoId(long todoId) {
		this.todoId = todoId;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public TodoInstance jsonToObject(JSONObject jsonObject) {
		TodoInstance model = new TodoInstance();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}
		if (jsonObject.containsKey("todoId")) {
			model.setTodoId(jsonObject.getLong("todoId"));
		}

		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getLong("deptId"));
		}
		if (jsonObject.containsKey("deptName")) {
			model.setDeptName(jsonObject.getString("deptName"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("alarmDate")) {
			model.setAlarmDate(jsonObject.getDate("alarmDate"));
		}

		if (jsonObject.containsKey("link")) {
			model.setLink(jsonObject.getString("link"));
		}
		if (jsonObject.containsKey("listLink")) {
			model.setListLink(jsonObject.getString("listLink"));
		}
		if (jsonObject.containsKey("linkType")) {
			model.setLinkType(jsonObject.getString("linkType"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getInteger("appId"));
		}
		if (jsonObject.containsKey("moduleId")) {
			model.setModuleId(jsonObject.getInteger("moduleId"));
		}

		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("roleCode")) {
			model.setRoleCode(jsonObject.getString("roleCode"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}

		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}

		if (jsonObject.containsKey("versionNo")) {
			model.setVersionNo(jsonObject.getLong("versionNo"));
		}
		return model;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (actorId != null) {
			jsonObject.put("actorId", actorId);
		}

		if (content != null) {
			jsonObject.put("content", content);
		}
		jsonObject.put("deptId", deptId);
		if (deptName != null) {
			jsonObject.put("deptName", deptName);
		}

		if (link != null) {
			jsonObject.put("link", link);
		}
		if (listLink != null) {
			jsonObject.put("listLink", listLink);
		}
		if (linkType != null) {
			jsonObject.put("linkType", linkType);
		}
		jsonObject.put("appId", appId);
		jsonObject.put("moduleId", moduleId);

		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		if (roleCode != null) {
			jsonObject.put("roleCode", roleCode);
		}
		jsonObject.put("roleId", roleId);

		if (title != null) {
			jsonObject.put("title", title);
		}

		jsonObject.put("versionNo", versionNo);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (actorId != null) {
			jsonObject.put("actorId", actorId);
		}

		if (content != null) {
			jsonObject.put("content", content);
		}
		jsonObject.put("deptId", deptId);
		if (deptName != null) {
			jsonObject.put("deptName", deptName);
		}

		if (link != null) {
			jsonObject.put("link", link);
		}
		if (listLink != null) {
			jsonObject.put("listLink", listLink);
		}
		if (linkType != null) {
			jsonObject.put("linkType", linkType);
		}
		jsonObject.put("appId", appId);
		jsonObject.put("moduleId", moduleId);

		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		if (roleCode != null) {
			jsonObject.put("roleCode", roleCode);
		}
		jsonObject.put("roleId", roleId);

		if (title != null) {
			jsonObject.put("title", title);
		}

		jsonObject.put("versionNo", versionNo);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}