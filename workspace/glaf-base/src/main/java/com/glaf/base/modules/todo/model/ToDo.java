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

package com.glaf.base.modules.todo.model;

import java.util.*;
import com.alibaba.fastjson.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.todo.util.ToDoJsonFactory;

public class ToDo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	private long id;

	/**
	 * ����
	 */
	private String code;

	/**
	 * Ӧ�ñ��
	 */
	private int appId = 0;

	/**
	 * ģ����
	 */
	private int moduleId = 0;

	/**
	 * ģ������
	 */
	private String moduleName;

	/**
	 * �����߱�Ż��ɫ����
	 */
	private String actorId;

	/**
	 * ��ɫ���
	 */
	private long roleId = 0;

	/**
	 * ��ɫ����
	 */
	private String roleCode;

	/**
	 * ���ű��
	 */
	private long deptId = 0;

	/**
	 * ��������
	 */
	private String deptName;

	/**
	 * ����
	 */
	private String alarm;

	/**
	 * ����
	 */
	private String news;

	/**
	 * ��ʼ�¼�
	 */
	private String eventFrom;

	/**
	 * �����¼�
	 */
	private String eventTo;

	/**
	 * ��������
	 */
	private int limitDay = 2;

	/**
	 * a Сʱ
	 */
	private int xa = 0;

	/**
	 * Сʱ
	 */
	private int xb = 0;

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
	 * ����
	 */
	private String type;

	/**
	 * ��������
	 */
	private String processName;

	/**
	 * ��������
	 */
	private String taskName;

	/**
	 * ����
	 */
	private String tablename;

	/**
	 * ִ�е�sql���
	 */
	private String sql;

	/**
	 * ������
	 */
	private String objectId;

	/**
	 * ����ֵ
	 */
	private String objectValue;

	/**
	 * �Ƿ����á�1�����á�0������
	 */
	private int enableFlag = 1;

	private long versionNo = 0;

	private Collection<Object> ok = new HashSet<Object>();

	private Collection<Object> caution = new HashSet<Object>();

	private Collection<Object> pastDue = new HashSet<Object>();

	public ToDo() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToDo other = (ToDo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getActorId() {
		return actorId;
	}

	public String getAlarm() {
		return alarm;
	}

	public int getAppId() {
		return appId;
	}

	public Collection<Object> getCaution() {
		return caution;
	}

	public String getCode() {
		return code;
	}

	public String getContent() {
		return content;
	}

	public long getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public int getEnableFlag() {
		return enableFlag;
	}

	public String getEventFrom() {
		return eventFrom;
	}

	public String getEventTo() {
		return eventTo;
	}

	public long getId() {
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

	public int getModuleId() {
		return moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getNews() {
		return news;
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

	public String getRoleCode() {
		return roleCode;
	}

	public long getRoleId() {
		return roleId;
	}

	public String getSql() {
		return sql;
	}

	public String getTablename() {
		return tablename;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTitle() {
		return title;
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
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public ToDo jsonToObject(JSONObject jsonObject) {
		return ToDoJsonFactory.jsonToObject(jsonObject);
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public void setCaution(Collection<Object> caution) {
		this.caution = caution;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setEnableFlag(int enableFlag) {
		this.enableFlag = enableFlag;
	}

	public void setEventFrom(String eventFrom) {
		this.eventFrom = eventFrom;
	}

	public void setEventTo(String eventTo) {
		this.eventTo = eventTo;
	}

	public void setId(long id) {
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

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setNews(String news) {
		this.news = news;
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

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTitle(String title) {
		this.title = title;
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
		return ToDoJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ToDoJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}