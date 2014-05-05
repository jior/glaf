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

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.*;
import javax.persistence.*;

import com.glaf.core.base.JSONable;
import com.glaf.core.todo.util.*;

@Entity
@Table(name = "SYS_TODO_INSTANCE")
public class TodoInstance implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参与者
	 */
	@Column(name = "actorId")
	protected String actorId;

	/**
	 * 参与者姓名
	 */
	@Column(name = "actorName")
	protected String actorName;

	/**
	 * 报警日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "alarmDate")
	protected Date alarmDate;

	/**
	 * 应用编号
	 */
	@Column(name = "appId")
	protected Long appId;

	/**
	 * 内容
	 */
	@Column(name = "content")
	protected String content;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	protected Date createDate;

	/**
	 * 部门编号
	 */
	@Column(name = "deptId")
	protected Long deptId;

	/**
	 * 部门名称
	 */
	@Column(name = "deptName")
	protected String deptName;

	/**
	 * 结束日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endDate")
	protected Date endDate;

	@Id
	@Column(name = "ID", length = 50, nullable = false)
	protected Long id;

	/**
	 * 链接
	 */
	@Column(name = "link_")
	protected String link;

	/**
	 * 链接类型
	 */
	@Column(name = "linkType")
	protected String linkType;

	@javax.persistence.Transient
	protected String listLink;

	/**
	 * 模块编号
	 */
	@Column(name = "moduleId")
	protected Long moduleId;

	/**
	 * 扩展名
	 */
	@Column(name = "objectId")
	protected String objectId;

	/**
	 * 控制值
	 */
	@Column(name = "objectValue")
	protected String objectValue;

	/**
	 * 过期日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pastDueDate")
	protected Date pastDueDate;

	/**
	 * 流程实例编号
	 */
	@Column(name = "processInstanceId")
	protected String processInstanceId;

	/**
	 * 提供者
	 */
	@Column(name = "provider")
	protected String provider;

	@javax.persistence.Transient
	protected Integer qty01;

	@javax.persistence.Transient
	protected Integer qty02;

	@javax.persistence.Transient
	protected Integer qty03;

	@javax.persistence.Transient
	protected Integer qtyRedWarn;

	/**
	 * 角色代码
	 */
	@Column(name = "roleCode")
	protected String roleCode;

	/**
	 * 角色编号
	 */
	@Column(name = "roleId")
	protected Long roleId;

	/**
	 * 业务编号
	 */
	@Column(name = "rowId_")
	protected String rowId;

	/**
	 * 开始日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startDate")
	protected Date startDate;

	@javax.persistence.Transient
	protected Integer status;

	/**
	 * 任务实例编号
	 */
	@Column(name = "taskInstanceId")
	protected String taskInstanceId;

	/**
	 * 主题
	 */
	@Column(name = "title")
	protected String title;

	@javax.persistence.Transient
	protected Todo todo;

	/**
	 * TODO定义编号
	 */
	@Column(name = "todoId")
	protected Long todoId;

	/**
	 * 版本
	 */
	@Column(name = "versionNo")
	protected Long versionNo;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TodoInstance other = (TodoInstance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

	public Integer getQty01() {
		return qty01;
	}

	public Integer getQty02() {
		return qty02;
	}

	public Integer getQty03() {
		return qty03;
	}

	public Integer getQtyRedWarn() {
		return qtyRedWarn;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public long getRoleId() {
		return roleId;
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

	public Integer getStatus() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public TodoInstance jsonToObject(JSONObject jsonObject) {
		return TodoInstanceJsonFactory.jsonToObject(jsonObject);
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

	public void setAppId(Long appId) {
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

	public void setDeptId(Long deptId) {
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

	public void setId(Long id) {
		this.id = id;
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

	public void setModuleId(Long moduleId) {
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

	public void setQty01(Integer qty01) {
		this.qty01 = qty01;
	}

	public void setQty02(Integer qty02) {
		this.qty02 = qty02;
	}

	public void setQty03(Integer qty03) {
		this.qty03 = qty03;
	}

	public void setQtyRedWarn(Integer qtyRedWarn) {
		this.qtyRedWarn = qtyRedWarn;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(Integer status) {
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

	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public JSONObject toJsonObject() {
		return TodoInstanceJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TodoInstanceJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}