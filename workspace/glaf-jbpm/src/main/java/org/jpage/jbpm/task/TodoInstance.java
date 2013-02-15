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


package org.jpage.jbpm.task;

import java.util.Date;

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
	 * ģ����
	 */
	private String moduleId;

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
	private String roleId;

	/**
	 * ��ɫ����
	 */
	private String roleName;

	/**
	 * ���ű��
	 */
	private String deptId;

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
	 * �б����ӵ�ַ
	 */
	private String listLink;

	/**
	 * �������
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
	private int greenQty;

	/**
	 * Caution
	 */
	private int yellowQty;

	/**
	 * Past Due
	 */
	private int redQty;

	/**
	 * ״̬
	 */
	private int status;

	private long versionNo;

	public TodoInstance() {

	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public Date getAlarmDate() {
		return alarmDate;
	}

	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getGreenQty() {
		return greenQty;
	}

	public void setGreenQty(int greenQty) {
		this.greenQty = greenQty;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getListLink() {
		return listLink;
	}

	public void setListLink(String listLink) {
		this.listLink = listLink;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public Date getPastDueDate() {
		return pastDueDate;
	}

	public void setPastDueDate(Date pastDueDate) {
		this.pastDueDate = pastDueDate;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public int getRedQty() {
		return redQty;
	}

	public void setRedQty(int redQty) {
		this.redQty = redQty;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getTodoId() {
		return todoId;
	}

	public void setTodoId(long todoId) {
		this.todoId = todoId;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public int getYellowQty() {
		return yellowQty;
	}

	public void setYellowQty(int yellowQty) {
		this.yellowQty = yellowQty;
	}

}
